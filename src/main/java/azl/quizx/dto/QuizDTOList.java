package azl.quizx.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//we create this class because spring rest didn't support List
@XmlRootElement(name="quizs")
public class QuizDTOList {
	 
	//@XmlElement(required = true)
	private List<QuizDTO> data;
	 
	@XmlElement(required = false)
	public List<QuizDTO> getData() {
	  return data;
	}
	 
	public void setData(List<QuizDTO> data) {
	  this.data = data;
	}
}