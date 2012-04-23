<#assign s=JspTaglibs["http://www.springframework.org/tags"] />
<#assign f=JspTaglibs["http://www.springframework.org/tags/form"] />

<html>
<head></head>
<body> 
<div class="links">
	<a href="${springMacroRequestContext.getContextPath()}/home.htm">Home</a>
	<a href="${springMacroRequestContext.getContextPath()}/admin/adminHome.htm">Admin Home</a>
	<a href="${springMacroRequestContext.getContextPath()}/j_spring_security_logout">Logout</a>
</div>

<h4>There are following validation errors:   </h4> 
<p>${validationErrors}</p>	
<p><a href="${springMacroRequestContext.getContextPath()}/admin/quizBulk.htm">Back</a></p.
</body>
</html>