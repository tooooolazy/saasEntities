package db.entities;

// Generated 16 ÷ев 2012 10:49:30 рм by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class TypedValue.
 * @see db.entities.TypedValue
 * @author Hibernate Tools
 */
public class TypedValueHome {

	private static final Log log = LogFactory.getLog(TypedValueHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(TypedValue transientInstance) {
		log.debug("persisting TypedValue instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TypedValue persistentInstance) {
		log.debug("removing TypedValue instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TypedValue merge(TypedValue detachedInstance) {
		log.debug("merging TypedValue instance");
		try {
			TypedValue result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TypedValue findById(Integer id) {
		log.debug("getting TypedValue instance with id: " + id);
		try {
			TypedValue instance = entityManager.find(TypedValue.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
