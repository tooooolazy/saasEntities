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

import org.hibernate.annotations.BatchSize;

import com.tooooolazy.util.Messages;
import com.tooooolazy.util.TLZUtils;

import db.enums.StatusEnum;
import db.enums.SubscriptionTypeEnum;

@Entity
@BatchSize(size=20)
@Table(name = "subscriptions")
public class Subscription {
	private Integer id;
	private Date subscriptionDate;
	private UserAccount userAccount;
	private Address deliveryAddress;

	private User userByEdUserId;
	private User userByCrUserId;
	private Date crDate;
	private Date edDate;
	private StatusEnum status;
	private Boolean isDeleted;

	private SubscriptionTypeEnum type;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "sub_date", nullable = false)
	public Date getSubscriptionDate() {
		return this.subscriptionDate;
	}

	public void setSubscriptionDate(Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userAccount_id")
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "address_id")
	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
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

	@Column(name = "status")
	public StatusEnum getStatus() {
		return this.status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	@Column(name = "deleted", nullable = false)
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Column(name = "type")
	public SubscriptionTypeEnum getType() {
		return this.type;
	}

	public void setType(SubscriptionTypeEnum type) {
		this.type = type;
	}

	@Transient
	public String getLang() {
		try {
			return getDeliveryAddress().getCountry().getLanguage().getLanguage();
		} catch (Exception e) {
		}
		return Messages.getLang();
	}
	@Transient
	public boolean isLocal() {
		if (getDeliveryAddress() != null) {
			if (getDeliveryAddress().getCountry() != null)
				return getDeliveryAddress().getCountry().getAlpha2Code().equals("GR");
			else
				return true;
		}
		return true;
	}

	@Transient
	public String getLine1(boolean capitals) {
		String s = getTitle(capitals);

		if (!isLocal()) 
			s += getName(capitals);

		return s;
	}
	@Transient
	public String getLine2(boolean capitals) {
		if (isLocal())
			return getName(capitals);
		else
			return getProfession(capitals);

	}
	@Transient
	public String getLine3(boolean capitals) {
		if (isLocal())
			return getProfession(capitals);
		else
			return getAddress1(capitals);

	}
	@Transient
	public String getLine4(boolean capitals) {
		if (isLocal())
			return getAddress1(capitals);
		else
			return getAddress2(capitals);

	}
	@Transient
	public String getLine5(boolean capitals) {
		if (isLocal())
			return getAddress2(capitals);
		else {
			if (getDeliveryAddress() != null)
				return TLZUtils.toUpperCaseGreek( getDeliveryAddress().getCountry().getDisplayValue(getLang()) );
		}
		return "";
	}
	@Transient
	public String getTitle(boolean capitals) {
		String s = "";
		if (getUserAccount() != null) {
			if (getUserAccount().getTitle() != null)
				s = getUserAccount().getTitle().getDisplayValue(getLang());
			else {
				if (!TLZUtils.isEmpty( getUserAccount().getTitleStr() )) {
					s = getUserAccount().getTitleStr();
				}
			}
			if (capitals)
				s = TLZUtils.toUpperCaseGreek(s);
		}
		return s;
	}
	@Transient
	public String getName(boolean capitals) {
		String s = "";
		if (getUserAccount() != null) {
			if (!TLZUtils.isEmpty(getUserAccount().getLastName()))
				s = getUserAccount().getLastName() + " ";
			if (!TLZUtils.isEmpty(getUserAccount().getMiddleName()))
				s += getUserAccount().getMiddleName() + " ";
			if (!TLZUtils.isEmpty(getUserAccount().getFirstName()))
				s += getUserAccount().getFirstName();	
			if (capitals)
				s = TLZUtils.toUpperCaseGreek(s);
		}
		return s;
	}
	@Transient
	public String getProfession(boolean capitals) {
		String s = "";
		if (getUserAccount() != null) {
			if (getUserAccount().getProfession() != null)
				s = getUserAccount().getProfession().getDisplayValue(getLang()) + " ";
			else {
				if (!TLZUtils.isEmpty( getUserAccount().getProfessionStr() )) {
					s = getUserAccount().getProfessionStr() + " ";
				}
			}
			if (getUserAccount().getSecondaryTitle() != null) {
				if (s.length() > 0)
					s += " / ";
				s += getUserAccount().getSecondaryTitle().getDisplayValue(getLang());
			}
			if (capitals)
				s = TLZUtils.toUpperCaseGreek(s);
		}
		return s;
	}
	@Transient
	public String getAddress1(boolean capitals) {
		String s = "";
		if (getDeliveryAddress() != null) {
			if (!TLZUtils.isEmpty( getDeliveryAddress().getAddress() ))
				s = getDeliveryAddress().getAddress();
		}
		if (capitals)
			s = TLZUtils.toUpperCaseGreek(s);
		return s;
	}
	@Transient
	public String getAddress2(boolean capitals) {
		String s = "";
		if (getDeliveryAddress() != null) {
			if (!TLZUtils.isEmpty( getDeliveryAddress().getPostalCode() ))
				s = getDeliveryAddress().getPostalCode() + " ";

			if (getDeliveryAddress().getStatus().isActive()) {
				if (getDeliveryAddress().getCity() != null)
					s += getDeliveryAddress().getCity().getDisplayValue(getLang()) + " ";
				else {
					if (!TLZUtils.isEmpty( getDeliveryAddress().getCityStr() )) {
						s += getDeliveryAddress().getCityStr() + " ";
					}
				}
			} else {
				if (!TLZUtils.isEmpty( getDeliveryAddress().getCityStr() )) {
					s += getDeliveryAddress().getCityStr() + " ";
				}
			}
		}
		if (capitals)
			s = TLZUtils.toUpperCaseGreek(s);
		return s;
	}
	@Transient
	public String getCountry(boolean capitals) {
		String s = "";
		if (getDeliveryAddress() != null) {
			if (getDeliveryAddress().getCountry() != null)
				s += getDeliveryAddress().getCountry().getDisplayValue(getLang()) ;
			if (capitals)
				s = TLZUtils.toUpperCaseGreek(s);
		}
		return s;
	}

	protected boolean selected;
	@Transient
	public boolean getSelected() {
		return selected;
	}
	public void setSelected(boolean b) {
		selected = b;
	}
}
