package azl.quizx.dto;

import javax.validation.ValidationException;

public class WebCategoryVersionDTO {
	private long categoryId;
	
	//the newest version in the Quiz table
	private int  newestQuizVersion;

	//the newest version in the CategoryVersion table
	private int  newestCategoryVersion;

	
	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public void increaseVersion(){
		newestCategoryVersion++;
	}
	
	public int getNewestQuizVersion() {
		return newestQuizVersion;
	}

	public void setNewestQuizVersion(int newestQuizVersion) {
		this.newestQuizVersion = newestQuizVersion;
	}

	public int getNewestCategoryVersion() {
		return newestCategoryVersion;
	}

	public void setNewestCategoryVersion(int newestCategoryVersion) {
		this.newestCategoryVersion = newestCategoryVersion;
	}

	//newestDownloadableVersion = newestCategoryVersion - 1 
	public String getNewestDownloadableVersion(){
		if (newestCategoryVersion > 1){
			return "" + (newestCategoryVersion-1);
		} else {
			return "None";
		}
	}
	
	public boolean isCanInsertNewVersion() {
		if (newestCategoryVersion < newestQuizVersion){
			throw new ValidationException("newestCategoryVersion should be less than newestQuizVersion");
		} else if (newestCategoryVersion == newestQuizVersion && newestCategoryVersion != 0){
			return true;
		} else {
			return false;
		}
	}
}