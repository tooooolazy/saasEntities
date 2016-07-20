package db.entities;

// Generated 13 Απρ 2012 12:23:52 μμ by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Label.
 * @see db.entities.Label
 * @author Hibernate Tools
 */
public class LabelHome {

	private static final Log log = LogFactory.getLog(LabelHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Label transientInstance) {
		log.debug("persisting Label instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Label persistentInstance) {
		log.debug("removing Label instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Label merge(Label detachedInstance) {
		log.debug("merging Label instance");
		try {
			Label result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Label findById(Integer id) {
		log.debug("getting Label instance with id: " + id);
		try {
			Label instance = entityManager.find(Label.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
