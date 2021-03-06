package db.entities;

// Generated 16 ��� 2012 10:49:30 �� by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class Type.
 * @see db.entities.Type
 * @author Hibernate Tools
 */
public class TypeHome {

	private static final Log log = LogFactory.getLog(TypeHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Type transientInstance) {
		log.debug("persisting Type instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Type persistentInstance) {
		log.debug("removing Type instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Type merge(Type detachedInstance) {
		log.debug("merging Type instance");
		try {
			Type result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Type findById(Integer id) {
		log.debug("getting Type instance with id: " + id);
		try {
			Type instance = entityManager.find(Type.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
