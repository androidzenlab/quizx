package azl.quizx.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

/**
 * JPA has no column indexing except the "unique", so Hibernate index annotation is used
 * to create database index. 
 */
@Entity
@Table(name="quiz")
@org.hibernate.annotations.Table(
		appliesTo = "quiz",
		   indexes = {
		      @Index(name="quiz_idx_cateId_ver", columnNames = {"category_id", "categoryVersion"})
		   }
		)
public class Quiz {
	public enum Status {
		NEW("NEW"), UPDATE("UPDATE"), DISCARD("DISCARD");		
		private String status;
		
		private Status(String s){
			status = s;
		}
	
		@Override
		public String toString(){
			return status;
		}
    };
	 
	@Id
	@Column(nullable = false)
	@GeneratedValue
	private long id;
	
    @Column(nullable=false) 
	private String question;
	
	@Column(nullable = false)
	private String correctAnswer;
	
	@Column(nullable = false)
	private String answer2;

	@Column(nullable = false)
	private String answer3;

	@Column(nullable = false)
	private String answer4;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Category category;
	
	@Column(nullable = true)
	private int categoryVersion;
	
	@Column(nullable = true,length = 10)
	@Enumerated(EnumType.STRING)
	private Status status;
	
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
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public String getAnswer2() {
		return answer2;
	}
	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}
	public String getAnswer3() {
		return answer3;
	}
	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}
	public String getAnswer4() {
		return answer4;
	}
	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString(){
		StringBuffer buf = new StringBuffer("id="+id);
		buf.append(",question=" + question);
		buf.append(",correctAnswer=" + correctAnswer);
		buf.append(",answer2=" + answer2);
		buf.append(",answer3=" + answer3);
		buf.append(",answer4=" + answer4);
		buf.append(",categoryName=" + category.getName());
		buf.append(",verstion=" + categoryVersion);
		buf.append(",status=" + status);
		return buf.toString();
	}
}