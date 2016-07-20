package db.entities;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

import com.tooooolazy.gwt.widgets.shared.SelectItem;
import com.tooooolazy.util.TLZUtils;

@Entity
@BatchSize(size=20)
@Table(name = "publications")
public class Publication extends BaseEntity implements SelectItem {
	private Integer id;
	private Integer pubYear;
	private Integer pubMonth;
	private Integer pubDay;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "pub_year", nullable = false)
	public Integer getPubYear() {
		return this.pubYear;
	}

	public void setPubYear(Integer pubYear) {
		this.pubYear = pubYear;
	}

	@Column(name = "pub_month", nullable = false)
	public Integer getPubMonth() {
		return this.pubMonth;
	}

	public void setPubMonth(Integer pubMonth) {
		this.pubMonth = pubMonth;
	}

	@Column(name = "pub_day", nullable = false)
	public Integer getPubDay() {
		return this.pubDay;
	}

	public void setPubDay(Integer pubDay) {
		this.pubDay = pubDay;
	}

	@Override
	@Transient
	public Object getPK() {
		return getId();
	}

	@Override
	@Transient
	public String getDisplayValue(String lang) {
		return getDefaultValue();
	}

	@Override
	@Transient
	public String getDefaultValue() {
		return getId() + " - " + getPubDay() + "/" + getPubMonth() + "/" + getPubYear();
	}

	@Override
	@Transient
	public boolean hasLangDefined(String lang) {
		return false;
	}

	public String toString() {
		String s = getDefaultValue();
		return s;
	}

	@Transient
	public Date getPublicationDate() {
		try {
			return TLZUtils.sdfGreece.parse(getPubDay() + "/" + getPubMonth() + "/" + getPubYear());
		} catch (ParseException e) {
			return null;
		}
	}
	@Transient
	public void setPublicationDate(Date pDate) {
		if (pDate == null)
			return;

		Calendar c = Calendar.getInstance();
		c.setTime(pDate);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int day = c.get(Calendar.DAY_OF_MONTH);

		setPubDay(day);
		setPubMonth(month);
		setPubYear(year);
	}
}
