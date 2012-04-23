<#assign s=JspTaglibs["http://www.springframework.org/tags"] />

<html>
<head></head>
<body> 

<div class="links">
	<a href="${springMacroRequestContext.getContextPath()}/home.htm">Home</a>
	<a href="${springMacroRequestContext.getContextPath()}/j_spring_security_logout">Logout</a>
</div>

<h2>Admin</h2>
<ul>
	  <li>
		<a href="${springMacroRequestContext.getContextPath()}/admin/category.htm">Category</a>
	  </li>
	  <li>
		<a href="${springMacroRequestContext.getContextPath()}/admin/categoryVersion.htm">Category/Quiz Version</a>
	  </li>
	  <li>
		<a href="${springMacroRequestContext.getContextPath()}/admin/quiz.htm">Quiz Insert</a>
  	  </li>
	  <li>
		<a href="${springMacroRequestContext.getContextPath()}/admin/quizBulk.htm">Quiz Bulk Insert</a>
  	  </li>
	  <li>
		<a href="${springMacroRequestContext.getContextPath()}/admin/quizSearch.htm">Quiz Search/Edit/Delete</a>
  	  </li>
</ul>

</body>
</html>