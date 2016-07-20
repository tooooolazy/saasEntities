package db.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import db.enums.UserConnectionEnum;

@Embeddable
public class UserConnectionId implements java.io.Serializable {
	private int userId;
	private UserConnectionEnum type;
	private String value;

	public UserConnectionId() {
	}

	public UserConnectionId(int userId, UserConnectionEnum type, String value) {
		this.userId = userId;
		this.type = type;
		this.value = value;
	}

	@Column(name = "user_id", nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "type", nullable = false)
	public UserConnectionEnum getType() {
		return this.type;
	}

	public void setType(UserConnectionEnum type) {
		this.type = type;
	}

	@Column(name = "value", length = 150)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserConnectionId))
			return false;
		UserConnectionId castOther = (UserConnectionId) other;

		return (this.getUserId() == castOther.getUserId()) && (this.getType().equals( castOther.getType() ))
				&& (this.getValue().equals( castOther.getValue() ));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getUserId();
		result = 37 * result + this.getType().getValue();
		result = 37 * result + this.getValue().hashCode();
		return result;
	}

}
