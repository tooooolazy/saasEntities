package db.entities;

// Generated 29 Ìבס 2012 8:59:26 נל by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class MethodSecurityLevelDef.
 * @see db.entities.MethodSecurityLevelDef
 * @author Hibernate Tools
 */
public class MethodSecurityLevelDefHome {

	private static final Log log = LogFactory.getLog(MethodSecurityLevelDefHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(MethodSecurityLevelDef transientInstance) {
		log.debug("persisting MethodSecurityLevelDef instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(MethodSecurityLevelDef persistentInstance) {
		log.debug("removing MethodSecurityLevelDef instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public MethodSecurityLevelDef merge(MethodSecurityLevelDef detachedInstance) {
		log.debug("merging MethodSecurityLevelDef instance");
		try {
			MethodSecurityLevelDef result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public MethodSecurityLevelDef findById(MethodSecurityLevelDefId id) {
		log.debug("getting MethodSecurityLevelDef instance with id: " + id);
		try {
			MethodSecurityLevelDef instance = entityManager.find(MethodSecurityLevelDef.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
