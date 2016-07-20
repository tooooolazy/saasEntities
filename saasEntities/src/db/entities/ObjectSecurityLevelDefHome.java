package db.entities;

// Generated 17 ��� 2012 1:43:55 �� by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class ObjectSecurityLevelDef.
 * @see db.entities.ObjectSecurityLevelDef
 * @author Hibernate Tools
 */
public class ObjectSecurityLevelDefHome {

	private static final Log log = LogFactory.getLog(ObjectSecurityLevelDefHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(ObjectSecurityLevelDef transientInstance) {
		log.debug("persisting ObjectSecurityLevelDef instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(ObjectSecurityLevelDef persistentInstance) {
		log.debug("removing ObjectSecurityLevelDef instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public ObjectSecurityLevelDef merge(ObjectSecurityLevelDef detachedInstance) {
		log.debug("merging ObjectSecurityLevelDef instance");
		try {
			ObjectSecurityLevelDef result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public ObjectSecurityLevelDef findById(ObjectSecurityLevelDefId id) {
		log.debug("getting ObjectSecurityLevelDef instance with id: " + id);
		try {
			ObjectSecurityLevelDef instance = entityManager.find(ObjectSecurityLevelDef.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
