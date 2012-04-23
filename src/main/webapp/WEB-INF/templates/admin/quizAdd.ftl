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

<h3>Quiz Admin   </h3> 
	<#assign addQuiz = true>
	<#include "quizForm.ftl" />
	
	<#if quiz.insertSuccessful>
  		<h4 style="color:green;">1 quiz has been added successfully.</h4>
  		<p class="note">
  			${sucessfulMsg}
  		</p>
	</#if>
</body>
</html>