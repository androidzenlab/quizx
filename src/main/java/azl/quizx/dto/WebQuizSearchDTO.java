package azl.quizx.dto;

/**
 * For web search/edit/delete page. 
 */
public class WebQuizSearchDTO{
	//@NotBlank   -- the annotation seems to have problem with session obj, so use manual validation
	private long categoryId;
	
	private int categoryVersion;
	
	//In the search page, when the web flow comes from edit/delete page, then we need to display the quizs.
	private boolean fromEditDeletePage;
	
	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public int getCategoryVersion() {
		return categoryVersion;
	}

	public void setCategoryVersion(int categoryVersion) {
		this.categoryVersion = categoryVersion;
	}

	public boolean isFromEditDeletePage() {
		return fromEditDeletePage;
	}

	public void setFromEditDeletePage(boolean fromEditDeletePage) {
		this.fromEditDeletePage = fromEditDeletePage;
	}

	public boolean getCanBulkModify(){
		return (categoryVersion == 0 && categoryId > 0);
	}
}