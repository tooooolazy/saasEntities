package db.entities;

// Generated 21 ��� 2012 12:09:39 �� by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Type generated by hbm2java
 */
@Entity
@Table(name = "types", uniqueConstraints = @UniqueConstraint(columnNames = { "class_id", "type" }))
public class Type implements java.io.Serializable {

	private Integer id;
	private User userByEdUserId;
	private User userByCrUserId;
	private TypeClass typeClass;
	private String type;
	private Date crDate;
	private Date edDate;
	private Label label;
	private Set<TypedValue> typedValues = new HashSet<TypedValue>(0);

	public Type() {
	}

	public Type(TypeClass typeClass, String type, Date crDate) {
		this.typeClass = typeClass;
		this.type = type;
		this.crDate = crDate;
	}

	public Type(User userByEdUserId, User userByCrUserId, TypeClass typeClass, String type, Date crDate, Date edDate, Set<TypedValue> typedValues) {
		this.userByEdUserId = userByEdUserId;
		this.userByCrUserId = userByCrUserId;
		this.typeClass = typeClass;
		this.type = type;
		this.crDate = crDate;
		this.edDate = edDate;
		this.typedValues = typedValues;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id", nullable = false)
	public TypeClass getTypeClass() {
		return this.typeClass;
	}

	public void setTypeClass(TypeClass typeClass) {
		this.typeClass = typeClass;
	}

	@Column(name = "type", nullable = false, length = 45)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "label_id")
	public Label getLabel() {
		return this.label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
	public Set<TypedValue> getTypedValues() {
		return this.typedValues;
	}

	public void setTypedValues(Set<TypedValue> typedValues) {
		this.typedValues = typedValues;
	}

	public String toString() {
		return type;
	}
}
