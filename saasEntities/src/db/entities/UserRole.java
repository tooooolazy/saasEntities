package db.entities;

// Generated 26 ��� 2012 1:35:02 �� by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * UserRole generated by hbm2java
 */
@Entity
@Table(name = "userroles")
public class UserRole implements java.io.Serializable {

	private UserRoleId id;
	private User user;
	private Role role;
	private Date crDate;
	private Date edDate;

	public UserRole() {
	}

	public UserRole(UserRoleId id, User user, Role role) {
		this.id = id;
		this.user = user;
		this.role = role;
	}

	public UserRole(UserRoleId id, User user, Role role, Date crDate, Date edDate) {
		this.id = id;
		this.user = user;
		this.role = role;
		this.crDate = crDate;
		this.edDate = edDate;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false, length = 45)),
			@AttributeOverride(name = "roleId", column = @Column(name = "role_id", nullable = false)) })
	public UserRoleId getId() {
		return this.id;
	}

	public void setId(UserRoleId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false, insertable = false, updatable = false)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cr_date", length = 19)
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

	public String toString() {
		return role.getId().name();
	}
}
