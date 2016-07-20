package db.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ArticleCategoryId implements java.io.Serializable {
	private int articleId;
	private int categoryId;

	public ArticleCategoryId() {
	}

	public ArticleCategoryId(int articleId, int categoryId) {
		this.articleId = articleId;
		this.categoryId = categoryId;
	}

	@Column(name = "article_id", nullable = false)
	public int getArticleId() {
		return this.articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	@Column(name = "category_id", nullable = false)
	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

}
