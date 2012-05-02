package azl.quizx.dto;

import org.springframework.web.multipart.commons.CommonsMultipartFile;


public class WebCategoryDTO extends CategoryDTO{	
	//image from upload
	private CommonsMultipartFile uploadImage;
	
	public CommonsMultipartFile getUploadImage() {
		return uploadImage;
	}

	public void setUploadImage(CommonsMultipartFile uploadImage) {
		this.uploadImage = uploadImage;
	}

	public String getImageFileName(){
		return this.uploadImage.getOriginalFilename();
	}
	
	/**
	 * This resolve the problem that mysql allows e.g. "Car" and "CAr" insert into
	 * db for a "unique" column.
	 */
	public String getLowcaseName(){
		return this.getName().trim().toLowerCase();
	}
}