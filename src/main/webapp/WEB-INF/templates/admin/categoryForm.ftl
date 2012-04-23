<@f.form commandName="category" method="post" enctype="multipart/form-data">
	<table>	
		<tr>
			<td><@f.label path="name">Category Name:</@f.label><em>*</em></td>
			<td><@f.input path="name"  maxlength="20" size="15" /></td>
			<td><@f.errors cssClass="errorClass" path="name" /></td>			
		</tr>
		
		<tr>
			<td><@f.label path="uploadImage">Image:</@f.label><#if addCategory><em>*</em></#if>	</td>
			<td><@f.input path="uploadImage"  type="file" /></td>
			<td><@f.errors cssClass="errorClass" path="uploadImage" /></td>			
		</tr>
  		<tr>
			<td></td>
			<td>
				<#if addCategory>
					<input type="submit" name="add" value="Add" />
	               <input type="submit" name="cancelAdd" value="Cancel" />
				<#else>
					<input type="submit" name="Submit" value="Edit" />
	               <input type="submit" name="cancel" value="Cancel" />
				</#if>	
			</td>
			<td><#if addCategory>(also add a record categoryVersion=1)</#if></td>			
		</tr>
	</table>
</@f.form>