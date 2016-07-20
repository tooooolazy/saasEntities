package db.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserValueId implements java.io.Serializable {
	private int userId;
	private int valueId;

	public UserValueId() {
	}

	public UserValueId(int userId, int valueId) {
		this.userId = userId;
		this.valueId = valueId;
	}

	@Column(name = "user_id", nullable = false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Column(name = "typedvalue_id", nullable = false)
	public int getValueId() {
		return this.valueId;
	}

	public void setValueId(int valueId) {
		this.valueId = valueId;
	}
}
