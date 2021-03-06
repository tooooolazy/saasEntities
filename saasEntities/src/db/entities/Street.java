package db.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.tooooolazy.gwt.widgets.shared.SelectItem;

import db.enums.StatusEnum;

@Entity
@Table(name = "streets")
public class Street implements java.io.Serializable, SelectItem {

	private Integer id;
	private User userByEdUserId;
	private User userByCrUserId;
	private Date crDate;
	private Date edDate;
	private String street;
	private City city;
	private Street aliasFor;
	private StatusEnum status;

	public Street() {
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
	@JoinColumn(name = "city_id", nullable = false)
	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@Column(name = "street", length = 150)
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "alias_id", nullable = true)
	public Street getAliasFor() {
		return this.aliasFor;
	}

	public void setAliasFor(Street aliasFor) {
		this.aliasFor = aliasFor;
	}
	@Column(name = "status")
	public StatusEnum getStatus() {
		return this.status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	@Override
	@Transient
	public boolean hasLangDefined(String lang) {
		return true;
	}
	@Override
	@Transient
	public String getDisplayValue(String lang) {
		return getStreet();
	}
	@Override
	@Transient
	public String getDefaultValue() {
		return getStreet();
	}

	@Override
	@Transient
	public Object getPK() {
		return id;
	}

	public String toString() {
		return getId() + ": " + getCity();
	}
}
