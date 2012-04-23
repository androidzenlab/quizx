package azl.quizx.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class CategoryVersion {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Category category;
	
	@Column(nullable = false)
	private int categoryVersion;
	

	public long getId() {
		return id;
	}
	
	@SuppressWarnings("unused")
	private void setId(long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getCategoryVersion() {
		return categoryVersion;
	}

	public void setCategoryVersion(int categoryVersion) {
		this.categoryVersion = categoryVersion;
	}
}