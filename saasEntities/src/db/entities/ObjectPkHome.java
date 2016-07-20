package db.entities;

// Generated 17 ��� 2012 1:43:55 �� by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class ObjectPk.
 * @see db.entities.ObjectPk
 * @author Hibernate Tools
 */
public class ObjectPkHome {

	private static final Log log = LogFactory.getLog(ObjectPkHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(ObjectPk transientInstance) {
		log.debug("persisting ObjectPk instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(ObjectPk persistentInstance) {
		log.debug("removing ObjectPk instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public ObjectPk merge(ObjectPk detachedInstance) {
		log.debug("merging ObjectPk instance");
		try {
			ObjectPk result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ObjectPk findById(int id) {
		log.debug("getting ObjectPk instance with id: " + id);
		try {
			ObjectPk instance = entityManager.find(ObjectPk.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
