package db.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tooooolazy.gwt.shared.interfaces.HasPrimaryKey;

import db.enums.StatusEnum;

/**
 * Holds properties that all (or most) entities should have
 * @author tooooolazy
 *
 */
@MappedSuperclass
public abstract class BaseEntity {
	protected User userByEdUserId;
	protected User userByCrUserId;
	protected Date crDate;
	protected Date edDate;
	protected StatusEnum status;
	protected Boolean isDeleted;

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
