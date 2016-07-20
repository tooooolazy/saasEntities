/**
 * 
 */
package com.tooooolazy.controllers;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.tooooolazy.db.FactoryUtil;
import com.tooooolazy.gwt.server.Initializer;
import com.tooooolazy.gwt.shared.interfaces.HasPrimaryKey;
import com.tooooolazy.gwt.shared.interfaces.TransactionalController;
import com.tooooolazy.util.TLZUtils;

import db.entities.User;

/**
 * @author tooooolazy
 *
 */
public abstract class DB_Controller {
	protected User god;
	protected static Logger logger = Logger.getAnonymousLogger();

	protected static boolean hasActiveTx() {
		try {
			return getEntityManager().getTransaction().isActive();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	protected static EntityTransaction getNewTransaction() {
		return createEntityManager().getTransaction();
	}
	/**
	 * <p>
	 * We also might need to refactor controllers that use this.
	 * </p>
	 * Returns the current EntityManager. If no one exists or current is NOT open a new one i created and returned
	 * @return
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = FactoryUtil.getEntityManager();
		return em;
	}
	protected static EntityManager createEntityManager() {
		return FactoryUtil.getMysqlEntityManagerFactory().createEntityManager();
	}
	/**
	 * Helper function
	 * @return Controller class from property file
	 */
	protected String getControllerClass( String controller ) {
		return Initializer.singleton().getProperty( controller );
	}
	/**
	 * Helper function
	 * @return Controller Object
	 */
	protected Object getController(String controllerClass) {
		return Initializer.singleton().getController( controllerClass );
	}

	/**
	 * Should be overridden in every Controller so that we know what initial data need to be added into DB
	 * when there is nothing there. At least that is the idea!
	 */
	protected abstract void initializeDB();

	protected abstract class DummyPK<T> implements HasPrimaryKey<T>, Serializable {
		@Override
		public void setPK(T pk) {
		}
		@Override
		public String getDbEntityClass() {
			return null;
		}
	}
	/**
	 * Makes sure that a transaction exists. If there is none one is created and false is returned. if there is one true is returned.
	 * @return
	 */
	protected boolean ensureTransaction() {
		if (!getEntityManager().getTransaction().isActive() ) {
			getEntityManager().getTransaction().begin();
			return false;
		}
		return true;
	}
	public void doTransactional(Object obj, String method) {
		if (obj instanceof TransactionalController) {
			try {
				doTransactional(obj, method, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}
		}
	}
	public Object doTransactional(Object obj, String method, Object[] params) throws Exception {
		return doTransactional(obj, method, null, params);
	}
	/**
	 * Uses reflection to wrap the given 'method' of the given DB controller 'obj' in a DB transaction.
	 * @param obj
	 * @param method
	 * @param types
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Object doTransactional(Object obj, String method, Class[] types, Object[] params) throws Exception {
		Object res = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			em = getEntityManager();
			tx = em.getTransaction();
			tx.begin();

			if (types == null && params == null) {
				TLZUtils.invokeMethod(obj, method, null);
			} else if (types == null && params != null) {
				res = TLZUtils.invokeMethod(obj, method, params);
			} else if (types != null && params != null){
				res = TLZUtils.invokeMethod(obj, method, types, params);
			} else { // this will probably throw an exception!! but it's ok. We should have been more careful making the call!
				TLZUtils.invokeMethod(obj, method, null);
			}
			tx.commit();
		} catch (InvocationTargetException e) {
			tx.rollback();
			throw (Exception)e.getTargetException();
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			if ( em != null && em.isOpen() ) {
				em.close();
			}
		}
		return res;
	}
	/**
	 * Saves the given bean and updates the edDate field using reflection. If such field does not exist nothing bad happens.
	 * @param bean
	 */
	public void save(Object bean) {
		TLZUtils.invokeSetterByProperty(bean, Initializer.ED_DATE_FIELD, new Date());
		getEntityManager().merge(bean);
	}
	public void refresh(Object bean) {
		getEntityManager().refresh(bean);
	}
}
