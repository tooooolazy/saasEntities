package db.entities;

// Generated 13 Απρ 2012 12:23:52 μμ by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class LabelML.
 * @see db.entities.LabelML
 * @author Hibernate Tools
 */
public class LabelMLHome {

	private static final Log log = LogFactory.getLog(LabelMLHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(LabelML transientInstance) {
		log.debug("persisting LabelML instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(LabelML persistentInstance) {
		log.debug("removing LabelML instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public LabelML merge(LabelML detachedInstance) {
		log.debug("merging LabelML instance");
		try {
			LabelML result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public LabelML findById(LabelMLId id) {
		log.debug("getting LabelML instance with id: " + id);
		try {
			LabelML instance = entityManager.find(LabelML.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
