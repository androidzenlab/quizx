<@f.form commandName="quiz" method="post">
	<table>	
		<tr>
			<td><@f.label path="question">Question:</@f.label><em>*</em></td>
			<td><@f.input path="question"  maxlength="255" size="60" /></td>
			<td><@f.errors cssClass="errorClass" path="question" /></td>			
		</tr>		
		<tr>
			<td><@f.label path="correctAnswer">Correct Answer:</@f.label><em>*</em></td>
			<td><@f.input path="correctAnswer" maxlength="255" size="40" /></td>
			<td><@f.errors cssClass="errorClass" path="correctAnswer" /></td>			
		</tr>
		<tr>
			<td><@f.label path="answer2">Answer2:</@f.label><em>*</em></td>
			<td><@f.input path="answer2" maxlength="255" size="40" /></td>
			<td><@f.errors cssClass="errorClass" path="answer2" /></td>			
		</tr>
		<tr>
			<td><@f.label path="answer3">Answer3:</@f.label><em>*</em></td>
			<td><@f.input path="answer3" maxlength="255" size="40" /></td>
			<td><@f.errors cssClass="errorClass" path="answer3" /></td>			
		</tr>
		<tr>
			<td><@f.label path="answer4">Answer4:</@f.label><em>*</em></td>
			<td><@f.input path="answer4" maxlength="255" size="40" /></td>
			<td><@f.errors cssClass="errorClass" path="answer4" /></td>			
		</tr>
  		<tr>
			<td><@f.label path="categoryId">Category:</@f.label><#if addQuiz><em>*</em></#if></td>
			<td>
				<@f.select path="categoryId">
            	 	<@f.option value=""></@f.option>
	                <#list categories as category>
	                	<@f.option value="${category.id}">
	                   		${category.name}
	                    </@f.option>
	                </#list>
	            </@f.select>
	        </td>    
			<td><@f.errors cssClass="errorClass" path="categoryId" /></td>			
		</tr>
		<#if !addQuiz>
			<tr>
				<td><@f.label path="categoryVersion">Category Version:</@f.label></td>
				<td><@f.input path="categoryVersion" readonly="true" size="2" /></td>
				<td><@f.errors cssClass="errorClass" path="categoryVersion" /></td>			
			</tr>
		</#if>
  		<tr>
			<td></td>
			<td>
				<#if addQuiz>
					<input type="submit" name="Submit" value="Add" />
					<input type="submit" name="cancel" value="Cancel" />					
				<#else>
					<input type="submit" name="Submit" value="Edit" />
					<input type="submit" name="cancel" value="Cancel" />
				</#if>	
			</td>
			<td></td>			
		</tr>
		
	</table>
</@f.form>