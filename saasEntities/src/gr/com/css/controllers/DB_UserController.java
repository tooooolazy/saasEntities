package gr.com.css.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.util.HexUtils;
import org.json.JSONObject;

import com.tooooolazy.controllers.DB_Controller;
import com.tooooolazy.controllers.UserUtils;
import com.tooooolazy.gwt.server.Initializer;
import com.tooooolazy.gwt.shared.Credentials;
import com.tooooolazy.gwt.shared.RegistrationData;
import com.tooooolazy.gwt.shared.exceptions.InvalidCredentialsException;
import com.tooooolazy.gwt.shared.exceptions.MultipleLoginException;
import com.tooooolazy.gwt.shared.exceptions.NoInternalException;
import com.tooooolazy.gwt.shared.exceptions.SessionExpiredException;
import com.tooooolazy.gwt.shared.exceptions.UserDeletedException;
import com.tooooolazy.gwt.shared.exceptions.UserInactiveException;
import com.tooooolazy.gwt.shared.exceptions.UserNotVerifiedException;
import com.tooooolazy.gwt.shared.exceptions.UserVerificationException;
import com.tooooolazy.gwt.shared.exceptions.UsernameExistsException;
import com.tooooolazy.gwt.shared.exceptions.WrongLoginIPException;
import com.tooooolazy.gwt.shared.interfaces.LanguageController;
import com.tooooolazy.gwt.shared.interfaces.SecurityController;
import com.tooooolazy.gwt.shared.interfaces.UserController;
import com.tooooolazy.util.TLZSecurity;
import com.tooooolazy.util.TLZUtils;

import db.entities.Language;
import db.entities.Role;
import db.entities.Session;
import db.entities.SessionId;
import db.entities.User;
import db.entities.UserAccount;
import db.entities.UserRole;
import db.entities.UserRoleId;
import db.enums.RoleEnum;
import db.enums.StatusEnum;
import db.enums.UserConnectionEnum;

public class DB_UserController extends DB_Controller implements UserController {
	private final static int ITERATION_NUMBER = 1000;

	public DB_UserController() {
		getGod();
		if (god == null) {
			addGod();
		}
	}
	protected User getGod() {
		if (god == null) {
			Query q = getEntityManager().createQuery("select userRole.user from UserRole userRole where userRole.id.roleId = :role");
			q.setParameter("role", RoleEnum.GOD.getValue());
			try {
				god = (User)q.getSingleResult();
			} catch (NoResultException e) {
				// no god user. Need to create him
				logger.info("will create GOD user.");
			} catch (NonUniqueResultException e) {
				// more than one god users
				// need to think what to do!!
				// - could delete all add recreate one
				// - could delete all but one
				// Need to think about referential integrity
				// - could just throw a Runtime exception and fuck up everything for those who fucked with the GOD user!!!
				logger.info("we got more than one GOD users.");
			}
		}
		return god;
	}
	@Override
	protected void initializeDB() {
		
	}
	protected void addGod() {
		// TODO we also need to make sure that God user is always and only active and none of its crucial data (username, password) can be modified
		RegistrationData rd = new RegistrationData( new Credentials("tooooolazy", "77m0316") );
		rd.setEmail("gpatou@tooooolazy.com");

		LanguageController lc = (LanguageController) getController( getControllerClass( Initializer.LANGUAGE_CONTROLLER_CLASS ) );
		Language l = (Language)lc.findLanguage("en");
		SecurityController sc = (SecurityController)getController( getControllerClass( Initializer.SECURITY_CONTROLLER_CLASS) );
		Role r = (Role) sc.getRole(new DummyPK<RoleEnum>() {
			@Override
			public RoleEnum getPK() {
				return RoleEnum.GOD;
			}
		});

		try {
			god = (User)createUser(rd, l, r, StatusEnum.ACTIVE);
		} catch (UsernameExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected Object handleFirstLogin(User user, HttpServletRequest request) {
		// create new Session in DB
		Session session = createUserSession(user, request);

		return user;
	}
	/**
	 * Helper function. Creates a new User session in DB and updates the User object. 
	 * @param user
	 * @param request
	 */
	protected Session createUserSession(User user, HttpServletRequest request) {
		String ip = "";
		// TODO might want to do something different here
		// but it has to be random enough!!!
		String sId = new Date().toString();
		
		if (request != null) {
			ip = request.getHeader("x-forwarded-for"); if(ip == null) ip = request.getRemoteAddr(); 
			sId = request.getSession().getId();
		}

		// create new Session in DB
		SessionId sessionId = new SessionId(sId, user.getId());
		Session nSession = new Session(sessionId, user, new Date(), ip);

		// save new user session
		getEntityManager().persist(nSession);

		user.getSessions().add(nSession);
		user.setSessionId(nSession.getId().getId());
		user.setEdDate(new Date());
		
		getEntityManager().persist(user);
//		getEntityManager().flush();

		return nSession;
	}
	protected Object handleMultipleLogins(Session dbSession, User user, HttpServletRequest request) throws WrongLoginIPException, MultipleLoginException {
		Session nSession = null;

		if (allowMultipleLogins()) {
			String ip = request.getHeader("x-forwarded-for"); if(ip == null) ip = request.getRemoteAddr(); 

			// if multiple logins are allowed should they be from the same IP?:
			if ( !ip.equals( dbSession.getFromIp() ) ) {
				if (allowMultipleLoginsFromDifferentIPs()) { // security risk if allowed!!
					nSession = createUserSession(user, request);
				} else {
					// if login must originate from same IP, do NOT allow login
					throw new WrongLoginIPException();
				}
			} else {
			}
		} else {
			/*
			 * if multiple logins are NOT allowed 2 choices:
			 *  - use new session --> invalidate last session (most likely)
			 *  - do NOT allow new login
			 */
			if (useNewSession()) {
				// invalidate last session and remove from DB
				getEntityManager().remove(dbSession);
				user.setSessionId(null);
				user.getSessions().remove(dbSession);
				getEntityManager().persist(user);
//				getEntityManager().flush();
				// finally create new session
				nSession = createUserSession(user, request);
			} else {
				// do NOT allow login
				throw new MultipleLoginException();
			}
		}

		return user;
	}
	public Object login(Credentials credentials) {
		try {
			return login(credentials, null);
		} catch (WrongLoginIPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MultipleLoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCredentialsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserInactiveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotVerifiedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserDeletedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return credentials.getUsername(); // should never get here 
	}

	public Object login(Credentials credentials, HttpServletRequest request) throws WrongLoginIPException, MultipleLoginException, InvalidCredentialsException, UserInactiveException, UserNotVerifiedException, UserDeletedException {
		String username = credentials.getUsername();
		String password = credentials.getPassword();

//		createSubscriber(1501);
		
		Query q = getEntityManager().createQuery("select user from User user where username = :username and isDeleted = false");
		q.setParameter("username", username);

		boolean validCredentials = false;
		try {
//			getEntityManager().getTransaction().begin();
			User user = (User) q.getSingleResult();

			String digest, salt;

			digest = user.getPassword();
			salt = user.getSalt();

			validCredentials = TLZSecurity.verify(digest, password, salt, ITERATION_NUMBER);

			if (validCredentials) {
				checkUserStatus(credentials, user);

				// make sure user selection for 'remember me' is stored in DB
				// Should be discarded if something goes wrong (exception is thrown - need to verify this)
				user.setRememberMe(credentials.getRememberMe());
				// check if already logged in
				q = getEntityManager().createQuery("select session from Session session where user = :user");
				q.setParameter("user", user);

				Session session = null;
				try {
					session = (Session) q.getSingleResult();
					// if something is found...
					// user already logged in!!
					return handleMultipleLogins(session, user, request);
				} catch (NoResultException e) {
					// no other session for the given user exists
					return handleFirstLogin(user, request);
				} catch (NonUniqueResultException e) {
					// user already logged more than ONE time - same handling as user been logged in only once
					return handleMultipleLogins(session, user, request);
				}
			} else {
				throw new InvalidCredentialsException(credentials);
			}
		} catch (NoResultException e) {
			throw new InvalidCredentialsException(credentials);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
//			getEntityManager().getTransaction().commit();

		}

		return username;
	}
	public Object login(JSONObject userDetails, Integer type, HttpServletRequest request) throws WrongLoginIPException, MultipleLoginException,
			InvalidCredentialsException, UserInactiveException, UserNotVerifiedException, UserDeletedException, NoInternalException {
		return null;
	}
	/**
	 * @param credentials
	 * @param user
	 * @throws UserInactiveException
	 * @throws UserNotVerifiedException
	 * @throws UserDeletedException
	 */
	protected void checkUserStatus(Credentials credentials, User user) throws UserInactiveException, UserNotVerifiedException, UserDeletedException {
		getEntityManager().refresh(user);

		if (user.getIsDeleted())
			throw new UserDeletedException(credentials);
		if (user.getStatus().isInactive())
			throw new UserInactiveException(credentials);
		if (user.getStatus().isNotVerified())
			throw new UserNotVerifiedException(credentials);
	}
	public Object verifySession(String cookie, HttpServletRequest request, boolean link) throws UserInactiveException, UserNotVerifiedException, UserDeletedException {
		Query q = getEntityManager().createQuery("select session from Session session where id.id = :cookie");
		q.setParameter("cookie", cookie);

		String ip = request.getHeader("x-forwarded-for"); if(ip == null) ip = request.getRemoteAddr(); 

		List<Session> sessions = q.getResultList();
		Iterator<Session> it = sessions.iterator();

		while (it.hasNext()) {
			Session session = it.next();

			boolean invalidUserStatus = false;
			try {
				checkUserStatus(new Credentials(session.getUser().getUsername(), ""), session.getUser());
			} catch (UserInactiveException e) {
				invalidUserStatus = true;
				throw e;
			} catch (UserNotVerifiedException e) {
				invalidUserStatus = true;
				throw e;
			} catch (UserDeletedException e) {
				invalidUserStatus = true;
				throw e;
			} finally {
				if (invalidUserStatus) {
					invalidateUserSession(session);
				}
			}

			if (!session.getFromIp().equals( ip ) ) {
				if (allowMultipleLoginsFromDifferentIPs()) {
					if (link)
						return linkUser(session);
					else
						return session.getUser();
				} else {
					return null;
				}
			} else {
				if (link)
					return linkUser(session);
				else
					return session.getUser();
			}
		}
		return null;
	}
	/**
	 * @param session
	 */
	protected void invalidateUserSession(Session session) {
		session.getUser().setSessionId(null);
		getEntityManager().persist(session.getUser());
		getEntityManager().remove(session);
	}
	/**
	 * @param session
	 * @return
	 */
	protected User linkUser(Session session) {
		User user = session.getUser();
		getEntityManager().refresh(user);

		session.setVDate(new Date());
		getEntityManager().persist(session);
		user.setSessionId(session.getId().getId());
		getEntityManager().persist(user);
//		getEntityManager().flush();

		return user;
	}

	////////////////////////////////////////
	// TODO  get next configuration values from config file
	public boolean allowMultipleLogins() {
		return false;
	}
	public boolean allowMultipleLoginsFromDifferentIPs() {
		return false;
	}
	public boolean useNewSession() {
		return true;
	}
	public boolean rememberLogin() {
		return true;
	}
	////////////////////////////////////////

	@Override
	public Object logout(Object userPK) throws SessionExpiredException {
		User user = getEntityManager().find(User.class, userPK);
		Session session = getEntityManager().find( Session.class, new SessionId(user.getSessionId(), user.getId()) );
		invalidateUserSession(session);

		return user;
	}

	public Object registerUser(RegistrationData rd) throws UsernameExistsException {
//		throw new UsernameExistsException(rd);
		LanguageController languageController = (LanguageController) getController( getControllerClass( Initializer.LANGUAGE_CONTROLLER_CLASS ) );
		Language lang = (Language) languageController.findLanguage(rd.getLanguage().getLanguage());

		StatusEnum status = StatusEnum.ACTIVE;
		if (Initializer.singleton().getPropertyAsBoolean(Initializer.EMAIL_VERIFICATION_REQUIRED_KEY))
			status = StatusEnum.NOT_VERIFIED;
		Object res = createUser(rd, lang, null, status);

		return res;
	}

	protected User createSubscriber(int id) {
		User u = null;
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");

			// Salt generation 64 bits long
			byte[] bSalt = new byte[8];
			random.nextBytes(bSalt);
	
			// Digest computation
			byte[] bDigest = TLZSecurity.getHash(ITERATION_NUMBER, "xxxx"+String.valueOf(id), bSalt);
			String sDigest = TLZSecurity.byteToBase64(bDigest);
			String sSalt = TLZSecurity.byteToBase64(bSalt);

			u = new User(getEntityManager().find(Language.class, 1), String.valueOf(id), sDigest, new Date(), sSalt);
			u.setEdDate(new Date());
			u.setId(id);
			u.setStatus(StatusEnum.NOT_VERIFIED);
			getEntityManager().persist(u);

			Role role = getEntityManager().find(Role.class, RoleEnum.SUBSCRIBER);
			UserRole ur = new UserRole(new UserRoleId(u.getId(), role.getId().getValue()), u, role);
			ur.setCrDate(new Date());
			ur.setEdDate(new Date());
			getEntityManager().persist(ur);

			UserAccount ua = new UserAccount();
			ua.setId(id);
			ua.setFirstName("ΑΝΔΡΕΑ");
			ua.setLastName("ΚΟΥΚΟΒΙΝΗ");
			ua.setCrDate(new Date());
			ua.setEdDate(new Date());
			ua.setUserById(u);
			getEntityManager().persist(ua);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
	}
	protected Object createUser(RegistrationData rd, Language lang, Role role, StatusEnum status) throws UsernameExistsException {
		Object res = 0;
		if (status == null) // safety precaution
			status = StatusEnum.NOT_VERIFIED;

		Query q = getEntityManager().createQuery("select user from User user where username = :username");
		q.setParameter("username", rd.getUsername());

		if (q.getResultList().size() > 0)
			throw new UsernameExistsException(rd);

		// Uses a secure Random not a simple Random
//		SecureRandom random;
		try {
			// Salt generation 64 bits long
			byte[] bSalt = TLZSecurity.createSalt(8);

			// Digest computation
			String sDigest = TLZSecurity.encodePassword(rd.getPassword(), bSalt, ITERATION_NUMBER);
			String sSalt = TLZSecurity.byteToBase64(bSalt);

			User u = new User(lang, rd.getUsername(), sDigest, new Date(), sSalt);
			u.setStatus(status != null ? status : StatusEnum.NOT_VERIFIED);

			// TODO create user Account as well
			// AND also create a TypedValue for the email
			getEntityManager().persist(u);
//			getEntityManager().flush();
			if (role != null) {
				UserRole ur = new UserRole(new UserRoleId(u.getId(), role.getId().getValue()), u, role);
				getEntityManager().persist(ur);
//				getEntityManager().flush();
			}
			logger.info("id: " + u.getId());
			getEntityManager().refresh(u);
			res = u;

			if (Initializer.singleton().getPropertyAsBoolean(Initializer.EMAIL_VERIFICATION_REQUIRED_KEY) && status.isNotVerified()) {
				// verification will be done against salt!!
				String baseURL = Initializer.singleton().getProperty(Initializer.BASE_URL_KEY);
				String verificationUrl = Initializer.singleton().getProperty(Initializer.EMAIL_VERIFICATION_URL_KEY);
				String hex = HexUtils.convert(sSalt.getBytes());
				verificationUrl = baseURL + verificationUrl + "?verify=" + u.getId() + "_" + hex;
				UserUtils.sendUserEmailVerificationURL( rd.getEmail(), verificationUrl );
			} else {
				UserUtils.sendUserRegistrationEmail( rd.getEmail() );
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = e;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = e;
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = e;
		}

		return res;
	}

	public void verifyFromEmail(String verification) throws UserVerificationException {
		String userid_str = verification.substring(0, verification.indexOf('_'));
		String ver = verification.substring(verification.indexOf('_') + 1);
		int userid = Integer.parseInt( userid_str );
		User user = getEntityManager().find(User.class, userid);
		getEntityManager().refresh(user);

		String _sSalt = HexUtils.convert(user.getSalt().getBytes());
		if (_sSalt.equals(ver) && user.getStatus().isNotVerified()) {
			user.setStatus(StatusEnum.ACTIVE);
			user.setEdDate(new Date());
			getEntityManager().merge(user);
		} else
			throw new UserVerificationException(user.getUsername());
	}

	@Override
	public void requestPasswordReset(String username) {
		Query q = getEntityManager().createQuery("select user from User user where username = :username");
		q.setParameter("username", username);

	}

	@Override
	public String getUserEmail(String username) {
		Query q = getEntityManager().createQuery("select user from User user where username = :username");
		q.setParameter("username", username);
		try {
			User u = (User) q.getSingleResult();
			// return u.get
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> getUsers() {
//		Query q = getEntityManager().createQuery("select user from User user");
		Query q = getEntityManager().createQuery("select user from User user left join fetch user.userAccountById left join fetch user.language");
		return q.getResultList();
	}
}
