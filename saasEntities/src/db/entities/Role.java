package db.entities;

// Generated 21 ��� 2012 12:09:39 �� by Hibernate Tools 3.4.0.CR1

import db.enums.RoleEnum;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Role generated by hbm2java
 */
@Entity
@Table(name = "roles")
public class Role implements java.io.Serializable {

	private RoleEnum id;
	private String role;
	private String descriprion;
	private Date crDate;
	private Date edDate;
	private Set<MethodSecurityLevelDef> methodSecurityLevelDefs = new HashSet<MethodSecurityLevelDef>(0);
	private Set<ObjectSecurityLevelDef> objectSecurityLevelDefs = new HashSet<ObjectSecurityLevelDef>(0);
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);

	public Role() {
	}

	public Role(RoleEnum id, String role) {
		this.id = id;
		this.role = role;
	}

	public Role(RoleEnum id, String role, String descriprion, Date crDate, Date edDate, Set<MethodSecurityLevelDef> methodSecurityLevelDefs,
			Set<ObjectSecurityLevelDef> objectSecurityLevelDefs, Set<UserRole> userRoles) {
		this.id = id;
		this.role = role;
		this.descriprion = descriprion;
		this.crDate = crDate;
		this.edDate = edDate;
		this.methodSecurityLevelDefs = methodSecurityLevelDefs;
		this.objectSecurityLevelDefs = objectSecurityLevelDefs;
		this.userRoles = userRoles;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public RoleEnum getId() {
		return this.id;
	}

	public void setId(RoleEnum id) {
		this.id = id;
	}

	@Column(name = "role", nullable = false, length = 45)
	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "descriprion", length = 45)
	public String getDescriprion() {
		return this.descriprion;
	}

	public void setDescriprion(String descriprion) {
		this.descriprion = descriprion;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	public Set<MethodSecurityLevelDef> getMethodSecurityLevelDefs() {
		return this.methodSecurityLevelDefs;
	}

	public void setMethodSecurityLevelDefs(Set<MethodSecurityLevelDef> methodSecurityLevelDefs) {
		this.methodSecurityLevelDefs = methodSecurityLevelDefs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	public Set<ObjectSecurityLevelDef> getObjectSecurityLevelDefs() {
		return this.objectSecurityLevelDefs;
	}

	public void setObjectSecurityLevelDefs(Set<ObjectSecurityLevelDef> objectSecurityLevelDefs) {
		this.objectSecurityLevelDefs = objectSecurityLevelDefs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public String toString() {
		return getId().getValue() + ": " + getId().name();
	}
}
