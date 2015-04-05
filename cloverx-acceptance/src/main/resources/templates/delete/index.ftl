<html>
<head>
  <title>Index with Template</title>
</head>
<body>
<h1 id="pageTitle">Index with template</h1>
<br/>

<form method="get" action="${request.exchange.resolvedPath}/index/delete">
  <fieldset>
      <legend>Delete with Get</legend>
      <input type="hidden" id="_method" name="_method" value="DELETE" />
      <input type="text" id="name" name="name" />
      <button type="submit" id="putSubmit">submit</button>
  </fieldset>
</form>

<form method="post" action="${request.exchange.resolvedPath}/index/delete">
  <fieldset>
    <legend>Delete with POST</legend>
    <input type="hidden" id="_method" name="_method" value="DELETE" />
    <input type="text" id="deletePostName" name="name" />
    <button type="submit" id="deletePostSubmit">submit</button>
  </fieldset>
</form>
</body>
</html>