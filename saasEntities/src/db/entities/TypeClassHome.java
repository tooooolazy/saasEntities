package db.entities;

// Generated 29 Ìבס 2012 9:53:02 נל by Hibernate Tools 3.4.0.CR1

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Home object for domain model class TypeClass.
 * @see db.entities.TypeClass
 * @author Hibernate Tools
 */
public class TypeClassHome {

	private static final Log log = LogFactory.getLog(TypeClassHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(TypeClass transientInstance) {
		log.debug("persisting TypeClass instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TypeClass persistentInstance) {
		log.debug("removing TypeClass instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TypeClass merge(TypeClass detachedInstance) {
		log.debug("merging TypeClass instance");
		try {
			TypeClass result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TypeClass findById(int id) {
		log.debug("getting TypeClass instance with id: " + id);
		try {
			TypeClass instance = entityManager.find(TypeClass.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
