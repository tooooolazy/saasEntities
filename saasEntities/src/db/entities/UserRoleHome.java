package db.entities;

// Generated 26 Ìבס 2012 1:35:03 לל by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class UserRole.
 * @see db.entities.UserRole
 * @author Hibernate Tools
 */
public class UserRoleHome {

	private static final Log log = LogFactory.getLog(UserRoleHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(UserRole transientInstance) {
		log.debug("persisting UserRole instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(UserRole persistentInstance) {
		log.debug("removing UserRole instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public UserRole merge(UserRole detachedInstance) {
		log.debug("merging UserRole instance");
		try {
			UserRole result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UserRole findById(UserRoleId id) {
		log.debug("getting UserRole instance with id: " + id);
		try {
			UserRole instance = entityManager.find(UserRole.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
