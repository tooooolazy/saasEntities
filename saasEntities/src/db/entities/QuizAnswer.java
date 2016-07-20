package db.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class QuizAnswer {
	private Integer id;


	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
