package db.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.BatchSize;

import com.tooooolazy.gwt.widgets.shared.SelectItem;
import com.tooooolazy.util.bean.annotations.TlzHidden;

@Entity
@BatchSize(size=50)
@Table(name = "articles", uniqueConstraints = @UniqueConstraint(columnNames = {"pub_id", "sequence"}))
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Article extends BaseEntity implements SelectItem {
	private Integer id;
	private Publication publication;
	private Label body;
	private Label title;
	private Label subTitle;
	private Label footer;
	private Integer dOrder;
	private Template template;
	private Set<ArticleCategory> articleCategories;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "sequence", nullable = false)
	public Integer getDispOrder() {
		return this.dOrder;
	}

	public void setDispOrder(Integer dOrder) {
		this.dOrder = dOrder;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pub_id")
	public Publication getPublication() {
		return this.publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "body_id")
	public Label getBody() {
		return this.body;
	}

	public void setBody(Label body) {
		this.body = body;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "title_id")
	public Label getTitle() {
		return this.title;
	}

	public void setTitle(Label title) {
		this.title = title;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subtitle_id")
	public Label getSubTitle() {
		return this.subTitle;
	}

	public void setSubTitle(Label subTitle) {
		this.subTitle = subTitle;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "footer_id")
	public Label getFooter() {
		return footer;
	}

	public void setFooter(Label footer) {
		this.footer = footer;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "templ_id")
	public Template getTemplate() {
		return this.template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "article", orphanRemoval=true, cascade=CascadeType.PERSIST)
	@TlzHidden
	@BatchSize(size = 50) 
	public Set<ArticleCategory> getArticleCategories() {
		return this.articleCategories;
	}

	public void setArticleCategories(Set<ArticleCategory> articleCategories) {
		this.articleCategories = articleCategories;
	}

	protected boolean selected;
	@Transient
	public boolean getSelected() {
		return selected;
	}
	public void setSelected(boolean b) {
		selected = b;
	}

	@Override
	@Transient
	public Object getPK() {
		return getId();
	}

	@Override
	@Transient
	public String getDisplayValue(String lang) {
		return getId() + ": " + getTitle().getDisplayValue(lang);
	}

	@Override
	@Transient
	public String getDefaultValue() {
		return getId() + ": " + getTitle().getDefaultValue();
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
	public int getWidth() {
		int w = 400;
		if (getTemplate() != null && getTemplate().getWidth() != null && getTemplate().getWidth() > 0)
			w = getTemplate().getWidth();
		return w;

	}
	@Transient
	public int getColumns() {
		int c = 1;
		if (getTemplate() != null && getTemplate().getColumns() != null && getTemplate().getColumns() > 0)
			c = getTemplate().getColumns();
		return c;
	}

	@Transient
	public Object getCategryIds() {
		Set<Integer> categoryIds = new HashSet<Integer>();
		if (getArticleCategories() == null || getArticleCategories().isEmpty())
			return categoryIds;

		for (ArticleCategory ac : getArticleCategories()) {
			categoryIds.add(ac.getId().getCategoryId());
		}
		return categoryIds;
	}

	@Transient
	public String getCategoryForTooltip() {
		String categories = "";
		for (ArticleCategory ac : getArticleCategories()) {
			categories += "<br/>" + ac.getCategory().getDefaultValue();
		}
		return categories;
	}

}
