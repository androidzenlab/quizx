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

<h3>Quiz Bulk Admin   </h3> 
<@f.form commandName="quiz" method="post" enctype="multipart/form-data">
	<table>	
		<tr>
			<td><@f.label path="bulkQuiz">Quiz File:</@f.label><em>*</em></td>
			<td><@f.input path="bulkQuiz"  type="file" /></td>
			<td><@f.errors cssClass="errorClass" path="bulkQuiz" /></td>			
		</tr>
  		<tr>
			<td></td>
			<td>
				<input type="submit" name="Submit" value="Upload Quizs" />
                <input type="submit" name="cancel" value="Cancel" />
			</td>
			<td></td>			
		</tr>
	</table>
</@f.form>
	
<#if quiz.insertSuccessful>
  <h4 style="color:green;">The bulk insert completed successfully.</h4>
</#if>
</body>
</html>