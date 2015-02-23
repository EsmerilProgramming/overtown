package org.esmerilprogramming.cloverx.server.handlers;

import io.undertow.UndertowMessages;
import io.undertow.util.CopyOnWriteMap;
import io.undertow.util.PathTemplate;
import io.undertow.util.PathTemplateMatch;
import io.undertow.util.PathTemplateMatcher;

import java.util.*;

public class CustomPathTemplateMatcher<T> {

  /**
   * Map of path template stem to the path templates that share the same base.
   */
  private Map<String, Set<PathTemplateHolder>> pathTemplateMap = new CopyOnWriteMap<>();

  /**
   * lengths of all registered paths
   */
  private volatile int[] lengths = {};

  public PathMatchResult<T> match(final String path) {
    final Map<String, String> params = new HashMap<>();
    int length = path.length();
    final int[] lengths = this.lengths;
    for (int i = 0; i < lengths.length; ++i) {
      int pathLength = lengths[i];
      if (pathLength == length) {
        Set<PathTemplateHolder> entry = pathTemplateMap.get(path);
        if (entry != null) {
          PathMatchResult<T> res = handleStemMatch(entry, path, params);
          if (res != null) {
            return res;
          }
        }
      } else if (pathLength < length) {
        char c = path.charAt(pathLength);
        if (c == '/') {
          String part = path.substring(0, pathLength);
          Set<PathTemplateHolder> entry = pathTemplateMap.get(part);
          if (entry != null) {
            PathMatchResult<T> res = handleStemMatch(entry, path, params);
            if (res != null) {
              return res;
            }
          }
        }
      }
    }
    return null;
  }

  private PathMatchResult<T> handleStemMatch(Set<PathTemplateHolder> entry, final String path, final Map<String, String> params) {
    for (PathTemplateHolder val : entry) {
      if (val.template.matches(path, params)) {
        return new PathMatchResult<>(params, val.template.getTemplateString(), val.value);
      } else {
        params.clear();
      }
    }
    return null;
  }


  public synchronized CustomPathTemplateMatcher<T> add(final CustomPathTemplate template, final T value) {
    Set<PathTemplateHolder> values = pathTemplateMap.get( trimBase(template) );
    Set<PathTemplateHolder> newValues;
    if (values == null) {
      newValues = new TreeSet<>();
    } else {
      newValues = new TreeSet<>(values);
    }
    PathTemplateHolder holder = new PathTemplateHolder(value, template);
    if (newValues.contains(holder)) {
      CustomPathTemplate equivalent = null;
      for (PathTemplateHolder item : newValues) {
        if (item.compareTo(holder) == 0) {
          equivalent = item.template;
          break;
        }
      }
      throw UndertowMessages.MESSAGES.matcherAlreadyContainsTemplate(template.getTemplateString(), equivalent.getTemplateString());
    }
    newValues.add(holder);
    pathTemplateMap.put(trimBase(template), newValues);
    buildLengths();
    return this;
  }

  private String trimBase(CustomPathTemplate template) {
    if (template.getBase().endsWith("/")) {
      return template.getBase().substring(0, template.getBase().length() - 1);
    }
    return template.getBase();
  }

  private void buildLengths() {
    final Set<Integer> lengths = new TreeSet<>(new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        return -o1.compareTo(o2);
      }
    });
    for (String p : pathTemplateMap.keySet()) {
      lengths.add(p.length());
    }

    int[] lengthArray = new int[lengths.size()];
    int pos = 0;
    for (int i : lengths) {
      lengthArray[pos++] = i; //-1 because the base paths end with a /
    }
    this.lengths = lengthArray;
  }

  public synchronized CustomPathTemplateMatcher<T> add(final String pathTemplate, final T value) {
    final CustomPathTemplate template = CustomPathTemplate.create(pathTemplate);
    return add(template, value);
  }

  public synchronized CustomPathTemplateMatcher<T> addAll(CustomPathTemplateMatcher<T> pathTemplateMatcher) {
    for (Map.Entry<String, Set<PathTemplateHolder>> entry : pathTemplateMatcher.getPathTemplateMap().entrySet()) {
      for (PathTemplateHolder pathTemplateHolder : entry.getValue()) {
        add(pathTemplateHolder.template, pathTemplateHolder.value);
      }
    }
    return this;
  }

  Map<String, Set<PathTemplateHolder>> getPathTemplateMap() {
    return pathTemplateMap;
  }

  public Set<CustomPathTemplate> getPathTemplates() {
    Set<CustomPathTemplate> templates = new HashSet<>();
    for (Set<PathTemplateHolder> holders : pathTemplateMap.values()) {
      for (PathTemplateHolder holder: holders) {
        templates.add(holder.template);
      }
    }
    return templates;
  }

  public synchronized CustomPathTemplateMatcher<T> remove(final String pathTemplate) {
    final CustomPathTemplate template = CustomPathTemplate.create(pathTemplate);
    return remove(template);
  }

  private synchronized CustomPathTemplateMatcher<T> remove(CustomPathTemplate template) {
    Set<PathTemplateHolder> values = pathTemplateMap.get(trimBase(template));
    Set<PathTemplateHolder> newValues;
    if (values == null) {
      return this;
    } else {
      newValues = new TreeSet<>(values);
    }
    Iterator<PathTemplateHolder> it = newValues.iterator();
    while (it.hasNext()) {
      PathTemplateHolder next = it.next();
      if (next.template.getTemplateString().equals(template.getTemplateString())) {
        it.remove();
        break;
      }
    }
    if (newValues.size() == 0) {
      pathTemplateMap.remove(trimBase(template));
    } else {
      pathTemplateMap.put(trimBase(template), newValues);
    }
    buildLengths();
    return this;
  }


  public synchronized T get(String template) {
    CustomPathTemplate pathTemplate = CustomPathTemplate.create(template);
    Set<PathTemplateHolder> values = pathTemplateMap.get(trimBase(pathTemplate));
    if(values == null) {
      return null;
    }
    for (PathTemplateHolder next : values) {
      if (next.template.getTemplateString().equals(template)) {
        return next.value;
      }
    }
    return null;
  }

  public static class PathMatchResult<T> extends PathTemplateMatch {
    private final T value;

    public PathMatchResult(Map<String, String> parameters, String matchedTemplate, T value) {
      super(matchedTemplate, parameters);
      this.value = value;
    }

    public T getValue() {
      return value;
    }
  }

  private final class PathTemplateHolder implements Comparable<PathTemplateHolder> {
    final T value;
    final CustomPathTemplate template;

    private PathTemplateHolder(T value, CustomPathTemplate template) {
      this.value = value;
      this.template = template;
    }

    @Override
    public int compareTo(PathTemplateHolder o) {
      return template.compareTo(o.template);
    }
  }

}
