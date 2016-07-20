package db.entities.criteria;


public class ArchiveCriteria extends SearchStatusCriteria<Integer> {

	public ArchiveCriteria() {
		super();
		deleted = null;
	}
	@Override
	public boolean hasNone() {
		return false;
	}

	@Override
	public StringBuffer createCountQuery() {
		StringBuffer sb = super.createCountQuery();
		sb.append("select count(s) from Archive s ");
		return sb;
	}
	@Override
	public StringBuffer createFetchQuery() {
		StringBuffer sb = super.createFetchQuery();
		sb.append("select s from Archive s ");
		return sb;
	}
}
