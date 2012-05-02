package azl.quizx.dto;

public class CategoryVersionDTO {
	private long categoryId;
	
	//the newest version in the Quiz table
	private String  newestQuizVersion;

	//the newest version in the CategoryVersion table
	private String  newestCategoryVersion;

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getNewestQuizVersion() {
		return nullToNone(newestQuizVersion);
	}

	public void setNewestQuizVersion(String newestQuizVersion) {
		this.newestQuizVersion = newestQuizVersion;
	}

	public String getNewestCategoryVersion() {
		return nullToNone(newestCategoryVersion);
	}

	public void setNewestCategoryVersion(String newestCategoryVersion) {
		this.newestCategoryVersion = newestCategoryVersion;
	}	
	
	private String nullToNone(String version){
		if (version == null){
			return "None";
		} else {
			return version;
		}
	}
}