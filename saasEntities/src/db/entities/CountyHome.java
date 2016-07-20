package db.entities;

// Generated 18 ��� 2012 11:03:54 �� by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class County.
 * @see db.entities.County
 * @author Hibernate Tools
 */
public class CountyHome {

	private static final Log log = LogFactory.getLog(CountyHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(County transientInstance) {
		log.debug("persisting County instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(County persistentInstance) {
		log.debug("removing County instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public County merge(County detachedInstance) {
		log.debug("merging County instance");
		try {
			County result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public County findById(Integer id) {
		log.debug("getting County instance with id: " + id);
		try {
			County instance = entityManager.find(County.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
