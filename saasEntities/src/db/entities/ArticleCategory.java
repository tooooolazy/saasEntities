package db.entities;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

@Entity
@BatchSize(size=50)
@Table(name = "articlecategories")
public class ArticleCategory extends BaseEntity {
	private ArticleCategoryId id;
	private Article article;
	private Label category;

	public ArticleCategory() {
	}
	public ArticleCategory(ArticleCategoryId id) {
		this.id = id;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "articleId", column = @Column(name = "article_id", nullable = false)),
			@AttributeOverride(name = "categoryId", column = @Column(name = "category_id", nullable = false)) })
	public ArticleCategoryId getId() {
		return this.id;
	}

	public void setId(ArticleCategoryId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id", nullable = false, insertable = false, updatable = false)
	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false, insertable = false, updatable = false)
	public Label getCategory() {
		return this.category;
	}

	public void setCategory(Label category) {
		this.category = category;
	}
}
