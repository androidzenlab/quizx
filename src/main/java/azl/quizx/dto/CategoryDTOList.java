package azl.quizx.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//we create this class because spring rest didn't support List
@XmlRootElement(name="categories")
public class CategoryDTOList {
	 
	//@XmlElement(required = true)
	private List<CategoryDTO> data;
	 
	@XmlElement(required = false)
	public List<CategoryDTO> getData() {
	  return data;
	}
	 
	public void setData(List<CategoryDTO> data) {
	  this.data = data;
	}
}