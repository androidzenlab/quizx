<#assign s=JspTaglibs["http://www.springframework.org/tags"] />
<#assign f=JspTaglibs["http://www.springframework.org/tags/form"] />

<html>
<head>

	<script type="text/javascript" charset="utf-8">
		$(document).ready(function() {
		  $('#categories').change(function() {
			window.location='${springMacroRequestContext.getContextPath()}/admin/categoryVersion.htm?categoryId=' + this.value		  
		  });
		});
	
	/* ajax version
		$(document).ready(function() {
		  $('#categories').change(function() {
		    $.get("${springMacroRequestContext.getContextPath()}/admin/categoryVersion.htm", 
		    	{categoryId: $('#categories option:selected').val()}, 
			    	function(data){
			    	"1,2,3".split(",") // ["1", "2", "3"]
			    		var newOption = $('<option value="'+5+'">'+val+'</option>');
 						$('#categoryVersions').append(newOption);			    	
   					}
		    	); 
		  });
		});
		*/
	</script>

</head>
<body> 

<div class="links">
	<a href="${springMacroRequestContext.getContextPath()}/home.htm">Home</a>
	<a href="${springMacroRequestContext.getContextPath()}/admin/adminHome.htm">Admin Home</a>
	<a href="${springMacroRequestContext.getContextPath()}/j_spring_security_logout">Logout</a>
</div>

<h3>Category Version Admin   </h3> 

<@f.form commandName="categoryVersion" method="post">
	
	<table>	
  		<tr>
			<td><@f.label path="categoryId">Category:</@f.label></td>
			<td>
                <@f.select id="categories" path="categoryId">
	                <#list categories as category>
	                	<option value="${category.id}" 
		                	<#if categoryVersion.categoryId==category.id>
		                		selected="selected"
		           	        </#if> >
	                   		${category.name}
	                    </option>
	                </#list>
	            </@f.select>
	        </td>    
		</tr>
		<tr>
			<td><@f.label path="newestQuizVersion">Newest Quiz Version:</@f.label></td>
			<td><@f.input path="newestQuizVersion" size="2" readonly="true"/></td>
		</tr>
		<tr>
			<td><@f.label>Newest Downloadable Version:</@f.label></td>
			<td><@f.label>${categoryVersion.newestDownloadableVersion}</@f.label></td>
		</tr>
		<tr>
			<td><@f.label path="newestCategoryVersion">Newest Category Version:</@f.label></td>
			<td><@f.input path="newestCategoryVersion" size="2" readonly="true"/></td>
		</tr>
		<tr>
			<td></td>
			<td>
				<input type="submit" name="Submit" value="Increase Category Version" 
					<#if !categoryVersion.canInsertNewVersion>disabled</#if> />
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
               <input type="submit" name="cancel" value="Cancel" />
            </td>
		</tr>
	</table>
</@f.form>

</body>
</html>