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
import javax.validation.constraints.NotNull;

import db.enums.StatusEnum;

@Entity
@Table(name = "archive")
public class Archive implements java.io.Serializable {
	private Integer id;

	private String filename;
	private int forYear;
	private int forMonth;
	private int archiveNo;
	private Date pubDate;

	private User userByEdUserId;
	private User userByCrUserId;
	private Date crDate;
	private Date edDate;

	private StatusEnum status;

	public Archive() {
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "filename", nullable = false, length = 100)
	@NotNull
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "for_year", nullable = false)
	public int getForYear() {
		return this.forYear;
	}

	public void setForYear(int forYear) {
		this.forYear = forYear;
	}

	@Column(name = "for_month", nullable = false)
	public int getForMonth() {
		return this.forMonth;
	}

	public void setForMonth(int forMonth) {
		this.forMonth = forMonth;
	}

	@Column(name = "archive_no", nullable = false)
	public int getArchiveNo() {
		return this.archiveNo;
	}

	public void setArchiveNo(int archiveNo) {
		this.archiveNo = archiveNo;
	}

	@Column(name = "pub_date", nullable = false)
	public Date getPubDate() {
		return this.pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
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
}
