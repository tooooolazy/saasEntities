package db.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.BatchSize;

import com.tooooolazy.util.Messages;

import db.enums.StatusEnum;

@Entity
@BatchSize(size=20)
@Table(name = "addresses")
public class Address implements java.io.Serializable {
	private Integer id;
	private User userByEdUserId;
	private User userByCrUserId;
	private Date crDate;
	private Date edDate;

	private String postalCode;
	private String address;
	private String cityStr;
	private String countyStr;
	private City city;
	private County county; // should be same as county in City above
	private Country country; // should be same as country in County above
	private StatusEnum status;

	public Address() {
	}

	@Id
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

	@Column(name = "pc", length = 20)
	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "address", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "cityStr", length = 200)
	public String getCityStr() {
		return this.cityStr;
	}

	public void setCityStr(String cityStr) {
		this.cityStr = cityStr;
	}

	@Column(name = "countyStr", length = 200)
	public String getCountyStr() {
		return this.countyStr;
	}

	public void setCountyStr(String countyStr) {
		this.countyStr = countyStr;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id", nullable = true)
	public City getCity() {
		return this.city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "county_id", nullable = true)
	public County getCounty() {
		return this.county;
	}

	public void setCounty(County county) {
		this.county = county;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "country_alpha2_code", nullable = true)
	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@Column(name = "status")
	public StatusEnum getStatus() {
		return this.status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String toString() {
		return getId() + ": " + getAddress() + ", " + getPostalCode() + " " + (getCity()!=null?(getCity().getDisplayValue(Messages.getLang()) + ", "):getCityStr()) + (getCounty()!=null?(getCounty().getDisplayValue(Messages.getLang()) + ", "):getCountyStr()) + (getCountry()!=null?(getCountry().getDisplayValue(Messages.getLang()) + ", "):"");
	}
}
