package db.entities;

// Generated 21 ��� 2012 12:09:39 �� by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SessionId generated by hbm2java
 */
@Embeddable
public class SessionId implements java.io.Serializable {

	private String id;
	private int userId;

	public SessionId() {
	}

	public SessionId(String id, int userId) {
		this.id = id;
		this.userId = userId;
	}

	@Column(name = "id", nullable = false, length = 45)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "user_id", nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SessionId))
			return false;
		SessionId castOther = (SessionId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null && castOther.getId() != null && this.getId().equals(castOther.getId())))
				&& (this.getUserId() == castOther.getUserId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result + this.getUserId();
		return result;
	}
	public String toString() {
		return id + ": " + userId;
	}

}