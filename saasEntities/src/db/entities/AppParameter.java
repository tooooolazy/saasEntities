package db.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import db.enums.ParameterCategoryEnum;

@Entity
@Table(name = "appparameters")
public class AppParameter {
	private Integer id;

	private ParameterCategoryEnum category;
	private Integer order;
	private String description;
	private String value1;
	private String value2;
	private String value3;
	private String value4;

	private Date crDate;
	private Date edDate;
	private User userByEdUserId;
	private User userByCrUserId;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "category_id")
	@NotNull
	public ParameterCategoryEnum getCategory() {
		return this.category;
	}

	public void setCategory(ParameterCategoryEnum category) {
		this.category = category;
	}

	@Column(name = "disp_order", nullable = false)
	@NotNull
	public Integer getOrder() {
		return this.order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Column(name = "value1", length = 150)
	public String getValue1() {
		return this.value1;
	}

	public void setValue1(String value) {
		this.value1 = value;
	}

	@Column(name = "value2", length = 150)
	public String getValue2() {
		return this.value2;
	}

	public void setValue2(String value) {
		this.value2 = value;
	}

	@Column(name = "value3", length = 150)
	public String getValue3() {
		return this.value3;
	}

	public void setValue3(String value) {
		this.value3 = value;
	}

	@Column(name = "value4", length = 150)
	public String getValue4() {
		return this.value4;
	}

	public void setValue4(String value) {
		this.value4 = value;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cr_user_id")
	public User getUserByCrUserId() {
		return this.userByCrUserId;
	}
	
	public void setUserByCrUserId(User userByCrUserId) {
		this.userByCrUserId = userByCrUserId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ed_user_id")
	public User getUserByEdUserId() {
		return this.userByEdUserId;
	}

	public void setUserByEdUserId(User userByEdUserId) {
		this.userByEdUserId = userByEdUserId;
	}

	@Column(name = "description", length = 150)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public Integer getValue1AsInteger() {
		if ( getValue1() != null ) {
			try {
				return Integer.parseInt( getValue1() );
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
	@Transient
	public Long getValue1AsLong() {
		if ( getValue1() != null ) {
			try {
				return Long.parseLong( getValue1() );
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
	@Transient
	public Integer getValue2AsInteger() {
		if ( getValue2() != null ) {
			try {
				return Integer.parseInt( getValue2() );
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
	@Transient
	public Integer getValue3AsInteger() {
		if ( getValue3() != null ) {
			try {
				return Integer.parseInt( getValue3() );
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
	@Transient
	public Integer getValue4AsInteger() {
		if ( getValue4() != null ) {
			try {
				return Integer.parseInt( getValue4() );
			} catch (NumberFormatException e) {
				return null;
			}
		}
		return null;
	}
}
