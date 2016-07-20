package db.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.tooooolazy.gwt.widgets.shared.SelectItem;
import com.tooooolazy.util.Messages;

import db.enums.StatusEnum;


@Entity
@Table(name = "quiz_option")
public class QuizOption implements SelectItem {
	private Integer id;
	private Quiz quiz;
	private Label option;
	private Integer count;
	
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
	@JoinColumn(name = "quiz_id", nullable = false)
	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_id", nullable = false)
	public Label getOption() {
		return option;
	}

	public void setOption(Label option) {
		this.option = option;
	}

	@Column(name = "count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	@Override
	@Transient
	public Object getPK() {
		return id;
	}

	@Override
	@Transient
	public String getDisplayValue(String lang) {
		if (getOption() != null) {
			return getOption().getDisplayValue(lang);
		}
		return getDefaultValue();
	}

	@Override
	@Transient
	public String getDefaultValue() {
		return getOption().getDefaultValue();
	}

	@Override
	@Transient
	public boolean hasLangDefined(String lang) {
		if (getOption() != null) {
			for (LabelML ml : getOption().getLabelMLs()) {
				if (ml.getLanguage().getLanguage().equals(lang))
					return true;
			}
		}
		return false;
	}
	public String toString() {
		String s = getDisplayValue(Messages.getLang());
		if (s == null)
			s = getDefaultValue();
		return s;
	}
}
