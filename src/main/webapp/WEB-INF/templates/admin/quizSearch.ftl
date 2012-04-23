<#assign s=JspTaglibs["http://www.springframework.org/tags"] />
<#assign f=JspTaglibs["http://www.springframework.org/tags/form"] />

<html>
<head>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		oTable = $('#example').dataTable({});
	});
	
		$(document).ready(function() {
		  $('#categories').change(function() {
			window.location='${springMacroRequestContext.getContextPath()}/admin/quizSearch.htm?categoryId=' + this.value		  
		  });
		});
</script>
	
</head>
<body id="dt_example" class="ex_highlight_row"> 
<div class="links">
	<a href="${springMacroRequestContext.getContextPath()}/home.htm">Home</a>
	<a href="${springMacroRequestContext.getContextPath()}/admin/adminHome.htm">Admin Home</a>
	<a href="${springMacroRequestContext.getContextPath()}/j_spring_security_logout">Logout</a>
</div>

<h3>Quiz Search   </h3> 
<@f.form commandName="quizSearchDto" method="post">
	<table>	
  		<tr>
			<td><@f.label path="categoryId">Category:</@f.label></td>
			<td>
                <@f.select id="categories" path="categoryId" onchange="">
	                <#list categories as category>
	                   	<option value="${category.id}" 
		                	<#if quizSearchDto.categoryId==category.id>
		                		selected="selected"
		           	        </#if> >
	                   		${category.name}
	                    </option>
	                </#list>
	            </@f.select>
	        </td>    
			<td></td>			
		</tr>
		<tr>
			<td><@f.label path="categoryVersion">Category Version:</@f.label></td>
			<td>
                <@f.select id="categoryVersions" path="categoryVersion">
            	 	<@f.option value="-1">All versions</@f.option>
            	 	<@f.option value="0">0 (no version)</@f.option>
	                <#list versions as version>
	                	<@f.option value="${version}">
	                   		${version}
	                    </@f.option>
	                </#list>
	            </@f.select>
	        </td>    
			<td></td>			
		</tr>
  		<tr>
			<td></td>
			<td>
				<input type="submit" name="search" value="Search" />
			</td>
			<td>
				<input type="submit" name="cancel" value="Cancel" />
			</td>
		</tr>
		<tr>	
			<td></td>
			<td>
               	<#if quizSearchDto.canBulkModify>
					<input type="submit" name="bulkModify" value="Modify version 0 to newest version" /> 
       	        </#if> 
			</td>			
		</tr>		
	</table>
</@f.form>

<div style="margin-left:5px;margin-top:10px;margin-right:5px;">

<#if quizs??>
	<table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
	    <thead>
	        <tr>
				<th>Version</th>
				<th>Question</th>
				<th>Correct Answer</th>
				<th>Answer2</th>
				<th>Answer3</th>
				<th>Answer4</th>
				<th>Status</th>
				<th>Edit</th>
				<th>Delete</th>
	        </tr>
	    </thead>
	
	    <tbody>
	        <#list quizs as quiz>
	            <tr>	            	
	            	<td>${quiz.categoryVersion}</td>
	            	<td>${quiz.question}</td>
	            	<td>${quiz.correctAnswer}</td>
	            	<td>${quiz.answer2}</td>
	            	<td>${quiz.answer3}</td>
	            	<td>${quiz.answer4}</td>
	            	<td>${quiz.status}</td>
	               <td><a href="${springMacroRequestContext.getContextPath()}/admin/quizEdit.htm?id=${quiz.id}">
						<img src="${springMacroRequestContext.getContextPath()}/images/edit.png" title="Edit"></img></a>
				   </td>
	            	<td><a href="${springMacroRequestContext.getContextPath()}/admin/quizDelete.htm?id=${quiz.id}"
						onClick="return confirmDelete();">
  						<img src="${springMacroRequestContext.getContextPath()}/images/delete.png" 
  					title="Delete"></img></a>
  				</td>                       
	            	
	            </tr>
	        </#list> 
	    </tbody>
	
	    <tfoot>
	        <tr>
				<th>Version</th>
				<th>Question</th>
				<th>Correct Answer</th>
				<th>Answer2</th>
				<th>Answer3</th>
				<th>Answer4</th>
				<th>Status</th>
				<th>Edit</th>
				<th>Delete</th>
	        </tr>
	    </tfoot>
	</table>
</#if>

</div>

<#if modifiedQuizs??>
	<h4 style="color:green;">${modifiedQuizs} quizs have been modified to newest category version successfully.</h4>
</#if>
</body>
</html>