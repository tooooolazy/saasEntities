package db.entities;

// Generated 16 ÷ев 2012 11:18:36 рм by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class UserAccount.
 * @see db.entities.UserAccount
 * @author Hibernate Tools
 */
public class UserAccountHome {

	private static final Log log = LogFactory.getLog(UserAccountHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(UserAccount transientInstance) {
		log.debug("persisting UserAccount instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(UserAccount persistentInstance) {
		log.debug("removing UserAccount instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public UserAccount merge(UserAccount detachedInstance) {
		log.debug("merging UserAccount instance");
		try {
			UserAccount result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UserAccount findById(int id) {
		log.debug("getting UserAccount instance with id: " + id);
		try {
			UserAccount instance = entityManager.find(UserAccount.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
