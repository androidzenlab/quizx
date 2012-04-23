package azl.quizx.dto;

import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement(name="category")
public class CategoryDTO {	
	private long id;
	
	@NotBlank
	private String name;
	
	private String imageUrl;
	
	private int newestVersionNumber;//i.e. newest downloadable version number
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getNewestVersionNumber() {
		return newestVersionNumber;
	}

	public void setNewestVersionNumber(int newestVersionNumber) {
		this.newestVersionNumber = newestVersionNumber;
	}			
}