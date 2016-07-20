package db.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.tooooolazy.util.Messages;

import db.enums.StatusEnum;

@Entity
@Table(name = "quiz")
public class Quiz {
	private Integer id;
	private Label question;
	private String description;
	private Set<QuizOption> options = new HashSet<QuizOption>(0);
	
	private User userByEdUserId;
	private User userByCrUserId;
	private Date crDate;
	private Date edDate;
	private StatusEnum status;
	private Boolean isDeleted;


	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", nullable = false)
	public Label getQuestion() {
		return question;
	}

	public void setQuestion(Label question) {
		this.question = question;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Transient
	public Set<QuizOption> getActiveOptions() {
		Set<QuizOption> active = new HashSet<QuizOption>();
		for (QuizOption qz : getOptions()) {
			if (qz.getStatus().isActive() && !qz.getIsDeleted()) {
				active.add(qz);
			}
		}
		return active;
	}
	@Transient
	public Set<String> getActiveOptionResults() {
		Set<String> active = new HashSet<String>();
		for (QuizOption qz : getOptions()) {
			if (qz.getStatus().isActive() && !qz.getIsDeleted()) {
				active.add(qz.getDisplayValue(Messages.getLang()) + ": " + qz.getCount());
			}
		}
		return active;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz")
	public Set<QuizOption> getOptions() {
		return this.options;
	}

	public void setOptions(Set<QuizOption> options) {
		this.options = options;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ed_user_id")
	public User getUserByEdUserId() {
		return this.userByEdUserId;
	}

	public void setUserByEdUserId(User userByEdUserId) {
		this.userByEdUserId = userByEdUserId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cr_user_id")
	public User getUserByCrUserId() {
		return this.userByCrUserId;
	}

	public void setUserByCrUserId(User userByCrUserId) {
		this.userByCrUserId = userByCrUserId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cr_date", nullable = false, length = 19)
	public Date getCrDate() {
		return this.crDate;
	}

	public void setCrDate(Date crDate) {
		this.crDate = crDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ed_date", length = 19)
	public Date getEdDate() {
		return this.edDate;
	}

	public void setEdDate(Date edDate) {
		this.edDate = edDate;
	}

	@Column(name = "status")
	public StatusEnum getStatus() {
		return this.status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	@Column(name = "deleted", nullable = false)
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
