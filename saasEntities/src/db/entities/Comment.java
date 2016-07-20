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
@Table(name = "comments")
public class Comment implements java.io.Serializable {
	private Integer id;
	private String comment;
	private String nickname;
	private TypeClass typeClass;
	private int category;
	
	private Integer likes;
	private Integer dislikes;
	private String ip;

	private User userByEdUserId;
	private User userByCrUserId;
	private Date crDate;
	private Date edDate;

	private StatusEnum status;

	public Comment() {
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "comment", nullable = false, length = 2000)
	@NotNull
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Column(name = "nickname", nullable = false, length = 50)
	@NotNull
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id", nullable = false)
	public TypeClass getTypeClass() {
		return this.typeClass;
	}

	public void setTypeClass(TypeClass typeClass) {
		this.typeClass = typeClass;
	}

	@Column(name = "category", nullable = false)
	public int getCategory() {
		return this.category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	@Column(name = "likes")
	public Integer getLikes() {
		return this.likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}
	@Column(name = "dislikes")
	public Integer getDislikes() {
		return this.dislikes;
	}

	public void setDislikes(Integer dislikes) {
		this.dislikes = dislikes;
	}

	@Column(name = "ip", length = 100)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public String toString() {
		return getId() + ": " + (getComment().length()>30?(getComment().substring(0, 27) + "..."):getComment()) + " - " + getNickname();
	}
}
