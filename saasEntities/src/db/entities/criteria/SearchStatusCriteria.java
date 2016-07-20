package db.entities.criteria;

import javax.persistence.Query;

import com.tooooolazy.util.BooleanStatus;
import com.tooooolazy.util.SearchCriteria;

import db.enums.StatusEnum;

public abstract class SearchStatusCriteria<PK> extends SearchCriteria<PK> {
	protected StatusEnum status;

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}
	@Override
	public StringBuffer createWhereClause() {
		StringBuffer sb = super.createWhereClause();
		if (status != null) {
			sb.append("and s.status= :sStatus ");
		}
		return sb;
	}

	public void setQueryParams(Query q) {
		super.setQueryParams(q);
		if (status != null) {
			q.setParameter("sStatus", status);
		}
	}
}
