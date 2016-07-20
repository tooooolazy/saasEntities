/**
 * 
 */
package gr.com.css.controllers;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Query;

import org.apache.commons.lang.SerializationUtils;

import com.tooooolazy.controllers.DB_Controller;
import com.tooooolazy.gwt.shared.interfaces.HasPrimaryKey;
import com.tooooolazy.gwt.shared.interfaces.SecurityController;

import db.entities.MethodSecurityLevelDef;
import db.entities.ObjectSecurityLevelDef;
import db.entities.Role;
import db.entities.User;
import db.entities.UserRole;
import db.enums.RoleEnum;


/**
 * @author tooooolazy
 *
 */
public class DB_SecurityController extends DB_Controller implements SecurityController<User, MethodSecurityLevelDef, ObjectSecurityLevelDef, Role> {
	private static String ObjectLevelSecurityDefsSQL = "CREATE TABLE osecleveldefs ( role_id int(11) NOT NULL, class_id int(11) NOT NULL, object_pk varbinary(100) NOT NULL, allow bit(1) DEFAULT NULL, cr_date datetime DEFAULT NULL, ed_date datetime DEFAULT NULL, cr_user_id int(11) DEFAULT NULL, ed_user_id int(11) DEFAULT NULL, PRIMARY KEY (role_id,class_id,object_pk), KEY fk_osecdef_role (role_id), KEY fk_osecdef_cruser (cr_user_id), KEY fk_osecdef_eduser (ed_user_id), KEY fk_osecdef_typeclass (class_id), CONSTRAINT fk_osecdef_cruser FOREIGN KEY (cr_user_id) REFERENCES user (id) ON DELETE NO ACTION ON UPDATE NO ACTION, CONSTRAINT fk_osecdef_eduser FOREIGN KEY (ed_user_id) REFERENCES user (id) ON DELETE NO ACTION ON UPDATE NO ACTION, CONSTRAINT fk_osecdef_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE NO ACTION ON UPDATE NO ACTION, CONSTRAINT fk_osecdef_typeclass FOREIGN KEY (class_id) REFERENCES typeclass (id) ON DELETE NO ACTION ON UPDATE NO ACTION)";

	protected List<MethodSecurityLevelDef> methodSecurityDefs;
	protected List<ObjectSecurityLevelDef> objectSecurityDefs;
	protected List<Role> roles;

	public DB_SecurityController() {
		getRoles();
		if (roles == null)
			addRoles();
	}

	protected void initializeDB() {
	}
	@Override
	public List<Role> getRoles() {
		if (roles == null) {
			Query q = getEntityManager().createQuery("select role from Role role");
			roles = q.getResultList();
			if (roles.isEmpty()) {
				roles = null;
//				throw new NoUserRolesDefinedException();
			}
		}
		return roles;
	}
	@Override
	public Role getRole(HasPrimaryKey role) {
		return getEntityManager().find(Role.class, role.getPK());
	}

	@Override
	public List<MethodSecurityLevelDef> getMethodSecurityDefs() {
		if (methodSecurityDefs == null) {
			// TODO might want to add a new column in table: Class = the class that the method belongs to
			Query q = getEntityManager().createQuery("select msld from MethodSecurityLevelDef msld");
			methodSecurityDefs = q.getResultList();
		}
		return methodSecurityDefs;
	}

	@Override
	public List<ObjectSecurityLevelDef> getObjectSecurityDefs() {
		if (objectSecurityDefs == null) {
			Query q = getEntityManager().createQuery("select osld from ObjectSecurityLevelDef osld");
			objectSecurityDefs = q.getResultList();
			if (objectSecurityDefs.size() == 0) {
				// lets add a test row...
				// TODO remove this --> we'll need to create an admin page to add object security defs (due to the blod column)
				// or create a setup function that adds all required security definitions
//				ObjectSecurityLevelDef o = new ObjectSecurityLevelDef(new ObjectSecurityLevelDefId(2, 3, new Integer(2)),
//						getEntityManager().find(Role.class, RoleEnum.ADMIN), 
//						getEntityManager().find(TypeClass.class, 3));
//				o.setAllow(true);
//				getEntityManager().getTransaction().begin();
//				getEntityManager().persist(o);
//				getEntityManager().flush();
//				getEntityManager().getTransaction().commit();
				// TODO need to verify that we can update rows with blob in primary key
			}
		}
		return objectSecurityDefs;
	}

	@Override
	public boolean hasAccess(User user, Method method, Class _class, Object[] params) {
		String methodName = method.getName();

		Logger.getAnonymousLogger().info("Checking if user " + user.getId() + " has access to method: " + methodName);

		if (user.isGod())
			return true;

		if ( hasMethodAccess (user, method) )
			return hasObjectAccess(user, params);

		return false;
	}

	@Override
	public boolean hasAccess(User user, String methodName, Class _class, Object[] params) {
		// TODO Auto-generated method stub
		return false;
	}
	protected boolean hasMethodAccess(User user, Method method) {
		// check if any method security is defined first
		/* 2 ways to do it:
		 * - use a query (less code)
		 * - go through data already retrieved form DB (should be faster?? and no extra query!)
		*/
		Query q = getEntityManager().createQuery("select msld from MethodSecurityLevelDef msld where id.method = :methodname and typeClass.className = :classname ");
		q.setParameter("methodname", method.getName());
		q.setParameter("classname", method.getDeclaringClass().getName());

		// if there are none grand access
		if (q.getResultList().size() == 0) {
			Logger.getAnonymousLogger().info("No access rules defined for method: " + method + ". Access Granted!");
			return true;
		}
		
		// else for the user to have access he must have a security definition in his list for the given method!
		/* 2 ways to do it:
		 * - go through all of users security defs and check only those referencing given method
		 * - retrieve only those defs referencing given method (loop will be smaller)
		 */
		List<MethodSecurityLevelDef> msDefs = getUserMethodSecurityDefs(user, method);

		boolean hasAccess = false;
		Iterator<MethodSecurityLevelDef> it = msDefs.iterator();
		while (it.hasNext()) {
			MethodSecurityLevelDef msld = it.next();

			if (msld.getTypeClass().getClassName().equals(method.getDeclaringClass().getName()) && msld.getId().getMethod().equals( method.getName() ) ) {
				// we might have conflicting rules, one saying allow and one say not allow
				// so even one 'not allow' rule exist then access should not be granted
				if (!msld.getAllow()) {
					hasAccess =  false;
					break;
				} else
					hasAccess = true;
			}
		}
		
		return hasAccess;
	}
	protected boolean hasObjectAccess(User user, Object[] params) {
		// first see if we have an appropriate object
		HasPrimaryKey obj = null;
		for (int i=0; i<params.length; i++) {
			if (params[i] instanceof HasPrimaryKey) {
				obj = (HasPrimaryKey)params[i];
			}
		}

		// if we have one check for access rights
		if (obj != null) {
			// check if any security is defined for the object first
			/* 2 ways to do it:
			 * - use a query (less code)
			 * - go through data already retrieved form DB (should be faster?? and no extra query!)
			*/
			Query q = getEntityManager().createQuery("select osld from ObjectSecurityLevelDef osld where typeClass.className = :classname and id.objectPk = :pk ");
			q.setParameter("classname", obj.getDbEntityClass());
			q.setParameter("pk", SerializationUtils.serialize((Serializable)obj.getPK()) );
	
			// if there are none grand access
			if (q.getResultList().size() == 0) {
				Logger.getAnonymousLogger().info("No access rules defined for object: " + obj.getDbEntityClass() + ". Access Granted!");
				return true;
			}

			// else for the user to have access he must have a security definition in his list for the given object!
			/* 2 ways to do it:
			 * - go through all of users security defs and check only those referencing given object
			 * - retrieve only those defs referencing given object (loop will be smaller)
			 */
			List<ObjectSecurityLevelDef> osDefs = getUserObjectSecurityDefs(user, obj);

			boolean hasAccess = false;
			Iterator<ObjectSecurityLevelDef> it = osDefs.iterator();
			while (it.hasNext()) {
				ObjectSecurityLevelDef osld = it.next();
				Object hpk = osld.getObjectPk();
				if ( osld.getTypeClass().getClassName().equals(obj.getDbEntityClass()) )  {
					if ( obj.getPK().equals( hpk ) ) {
						// we might have conflicting rules, one saying allow and one say not allow
						// so even one 'not allow' rule exist then access should not be granted
						if (!osld.getAllow()) {
							hasAccess =  false;
							break;
						} else
							hasAccess = true;
					}
				}
			}
			return hasAccess;
		}
		// else no need to check access rights
		return true;
	}
	/**
	 * Retrieves the DB entity referenced by the given DTO
	 * @param obj the DTO that implements the <code>HasPrimaryKey</code> interface.
	 * @return
	 */
	protected Object getReferrencedObject(HasPrimaryKey obj) {
		try {
			String objClass = obj.getDbEntityClass();
			Class _class = Class.forName( objClass );
			Object o = getEntityManager().find(_class, obj.getPK());
			return o;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retrieves all Method Level Access Definitions assigned to the given user
	 * @param user db.entities.User object
	 * @return
	 */
	protected List<MethodSecurityLevelDef> getUserMethodSecurityDefs(User user) {
		ArrayList<MethodSecurityLevelDef> sDefs = new ArrayList<MethodSecurityLevelDef>();

		Iterator<UserRole> userRoles = user.getUserRoles().iterator();
		while (userRoles.hasNext()) {
			UserRole userRole = userRoles.next();
			Role role = userRole.getRole();
			sDefs.addAll( role.getMethodSecurityLevelDefs() );
		}
		return sDefs;
	}
	/**
	 * Retrieves all Method Level Access Definitions assigned to the given user for the given Method
	 * @param user db.entities.User object
	 * @param method java.lang.reflect.Method Object
	 * @return
	 */
	protected List<MethodSecurityLevelDef> getUserMethodSecurityDefs(User user, Method method) {
		Query q = getEntityManager().createQuery("select msld from MethodSecurityLevelDef msld where id.method = :methodname and typeClass.className = :classname and msld.role in (select ur.role from UserRole ur where ur.user = :user)");
		q.setParameter("methodname", method.getName());
		q.setParameter("classname", method.getDeclaringClass().getName());
		q.setParameter("user", user);

		List<MethodSecurityLevelDef> sDefs = q.getResultList();

		return sDefs;
	}
	/**
	 * Retrieves all Object Level Access Definitions assigned to the given user
	 * @param user db.entities.User object
	 * @return
	 */
	protected List<ObjectSecurityLevelDef> getUserObjectSecurityDefs(User user) {
		ArrayList<ObjectSecurityLevelDef> sDefs = new ArrayList<ObjectSecurityLevelDef>();

		Iterator<UserRole> userRoles = user.getUserRoles().iterator();
		while (userRoles.hasNext()) {
			UserRole userRole = userRoles.next();
			Role role = userRole.getRole();
			sDefs.addAll( role.getObjectSecurityLevelDefs() );
		}
		return sDefs;
	}
	/**
	 * Retrieves all Object Level Access Definitions assigned to the given user for the given Object
	 * @param user db.entities.User object
	 * @param obj the DTO that implements the <code>HasPrimaryKey</code> interface.
	 * @return
	 */
	protected List<ObjectSecurityLevelDef> getUserObjectSecurityDefs(User user, HasPrimaryKey obj) {
		Query q = getEntityManager().createQuery("select osld from ObjectSecurityLevelDef osld where typeClass.className = :classname and id.objectPk = :pk and role in (select ur.role from UserRole ur where user = :user)");
		q.setParameter("classname", obj.getDbEntityClass());
		q.setParameter("pk", SerializationUtils.serialize((Serializable)obj.getPK()));
		q.setParameter("user", user);

		List<ObjectSecurityLevelDef> sDefs = q.getResultList();

		return sDefs;
	}

	/////////////////////////////////////////////////
	/**
	 * 
	 */
	public void addRoles() {
		Role god = new Role(RoleEnum.GOD, RoleEnum.GOD.name());
		god.setCrDate(new Date());
		god.setEdDate(new Date());
		Role admin = new Role(RoleEnum.ADMIN, RoleEnum.ADMIN.name());
		admin.setCrDate(new Date());
		admin.setEdDate(new Date());
		getEntityManager().persist( god );
		getEntityManager().persist( admin );
		getEntityManager().flush();
	}

	@Override
	public boolean isSecure(String methodName, Class methodClass) {
		// TODO Auto-generated method stub
		return false;
	}

}
