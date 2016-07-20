package db.entities;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "uservalue")
public class UserValue {

	private UserValueId id;
	private User userById;
	private TypedValue value;
	private Boolean isDeleted;

	public UserValue() {
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)),
			@AttributeOverride(name = "valueId", column = @Column(name = "typedvalue_id", nullable = false)) })
	public UserValueId getId() {
		return this.id;
	}

	public void setId(UserValueId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	public User getUserById() {
		return this.userById;
	}

	public void setUserById(User userById) {
		this.userById = userById;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typedvalue_id", nullable = false, insertable = false, updatable = false)
	public TypedValue getValue() {
		return this.value;
	}

	public void setValue(TypedValue value) {
		this.value = value;
	}

	@Column(name = "deleted", nullable = false)
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
