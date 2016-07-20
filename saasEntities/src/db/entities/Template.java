package db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

import com.tooooolazy.gwt.widgets.shared.SelectItem;

@Entity
@BatchSize(size=5)
@Table(name = "templates")
public class Template extends BaseEntity implements SelectItem {
	private Integer id;
	private String description;
	private String html;
	private String htmlFile;
	private Integer width;
	private Integer height;
	private Integer columns;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "descr")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "html")
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
	@Column(name = "html_file")
	public String getHtmlFile() {
		return htmlFile;
	}

	public void setHtmlFile(String htmlFile) {
		this.htmlFile = htmlFile;
	}

	@Column(name = "width")
	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Column(name = "height")
	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Column(name = "columns")
	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
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
		return getId() + ": " + getWidth() + "px x " + getHeight() + "px - " + getColumns();
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
}
