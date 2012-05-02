package azl.quizx.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "Category")
//@org.hibernate.annotations.Cache(usage=org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class Category {
	@Id
	@GeneratedValue
	private long id;
	
    @Column(unique=true, nullable=false) 
	private String name;
	
	@Column(nullable = false)
	private String imageFileName;

	
	
	public long getId() {
		return id;
	}
	
	@SuppressWarnings("unused")
	private void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
}