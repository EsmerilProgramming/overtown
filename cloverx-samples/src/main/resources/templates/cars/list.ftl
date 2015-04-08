<#import "/main-template.ftl" as p>
<@p.page>

<#include "/cars/_form.ftl" />

<table border="1">
  <thead>
  <tr>
    <td>Model</td>
    <td>Year</td>
  </tr>
  </thead>
  <tbody>
  <#list cars as car>
    <tr>
      <td>${car.model}</td>
      <td>${car.year}</td>
    </tr>
  </#list>
  </tbody>
</table>
</@p.page>