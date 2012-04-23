<#assign s=JspTaglibs["http://www.springframework.org/tags"] />
<#assign f=JspTaglibs["http://www.springframework.org/tags/form"] />

<html>
<head>
	<script type="text/javascript" charset="utf-8">
			$(document).ready(function() {
				oTable = $('#example').dataTable({});
			} );
	</script>
</head>
<body> 

<div class="links">
	<a href="${springMacroRequestContext.getContextPath()}/home.htm">Home</a>
	<a href="${springMacroRequestContext.getContextPath()}/admin/adminHome.htm">Admin Home</a>
	<a href="${springMacroRequestContext.getContextPath()}/j_spring_security_logout">Logout</a>
</div>

<h3>Category Admin   </h3> 
		
	<#assign addCategory = true>
	<#include "categoryForm.ftl" />
	
	<#if categories?? && categories?size != 0>
	<div style="margin-left:5px;margin-top:20px;margin-right:50px;">
	<table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
	    <thead>
	        <tr>
				<th>ID</th>
				<th>Name</th>
				<th>Image</th>
				<th>Edit</th>
				<th>Delete</th>
	        </tr>
	    </thead>
	
	    <tbody>
			<#list categories as cate>
            <tr>
				<td>${cate.id}</td>
				<td>${cate.name}</td>
				<td><image title="Category Image" src="${springMacroRequestContext.getContextPath()}/${cate.imageUrl}"/></td>
				<td><a href="${springMacroRequestContext.getContextPath()}/admin/categoryEdit.htm?id=${cate.id}">
					<img src="${springMacroRequestContext.getContextPath()}/images/edit.png" title="Edit"></img></a>
				</td>
				<td><a href="${springMacroRequestContext.getContextPath()}/admin/categoryDelete.htm?id=${cate.id}"
					onClick="return confirmDelete();">
	  				<img src="${springMacroRequestContext.getContextPath()}/images/delete.png" 
	  					title="Delete"></img></a>
	  			</td>			
            </tr>
	        </#list> 
	    </tbody>	
	    <tfoot>
	        <tr>
	        <tr>
				<th>ID</th>
				<th>Name</th>
				<th>Image</th>
				<th>Edit</th>
				<th>Delete</th>
	        </tr>
	        </tr>
	    </tfoot>
	</table>
	</div>
</#if>
	
</body>
</html>