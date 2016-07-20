package com.tooooolazy.controllers;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.tooooolazy.gwt.server.Initializer;
import com.tooooolazy.gwt.shared.RegistrationData;
import com.tooooolazy.util.TLZMail;

import db.entities.Subscription;
import db.entities.User;

public class UserUtils {
	/**
	 * Informs Admin of new user
	 * @param u
	 * @param rd
	 */
	public static void sendNewUserCreatedEmail(User u, RegistrationData rd) {
		try {
			String email = "info@aitoloakarnaniki.gr";
//			if (rd.getEmail() != null)
//				email = rd.getEmail();
			TLZMail.sendHTMLEmail(Initializer.singleton().getProperties(), email, "New User", "New User created:<br/>"
					+ rd.toString() + "<br/><br/>"
					+ u.toString() + "<br/>"
					+ u.getUserAccountById());
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * informs Admin of new User Account
	 * @param u
	 */
	public static void sendAccountDetailsEmail(User u) {
		try {
			String email = "info@aitoloakarnaniki.gr";
//			if (u.getDefEmail() != null)
//				email = u.getDefEmail();
			TLZMail.sendHTMLEmail(Initializer.singleton().getProperties(), email, "New User Account", "New User Account created:<br/>"
				+ "User: " + u.toString() + "<br/>"
				+ "Account: " + u.getUserAccountById() + "<br/>"
				+ u.getUserAccountById().getDefPhone() + "<br/>"
				+ "Email: " + u.getUserAccountById().getDefEmail()
				);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * informs Admin of new Subscription request
	 * @param s
	 */
	public static void sendSubscriptionDetailsEmail(Subscription s) {
		try {
			String email = "info@aitoloakarnaniki.gr";
			TLZMail.sendHTMLEmail(Initializer.singleton().getProperties(), email, "New Subscription", "New Subscription created:<br/>"
				+ "Subscription: " + "<br/>"
				+ s.getLine1(true) + "<br/>"
				+ s.getLine2(true) + "<br/>"
				+ s.getLine3(true) + "<br/>"
				+ s.getLine4(true) + "<br/>"
				+ s.getLine5(true) + "<br/><br/>"
				+ "Account: " + s.getUserAccount() + "<br/>"
				+ "Address: " + s.getDeliveryAddress() + "<br/>"
				+ "Phone: " + s.getUserAccount().getDefPhone() + "<br/>"
				+ "Email: " + s.getUserAccount().getDefEmail() + "<br/>"
				+ "User: " + s.getUserAccount().getUserById()
				);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	public static void sendExternalUserRegistrationEmail(RegistrationData rd) {
		try {
			String email = rd.getEmail() != null ? rd.getEmail() : "info@aitoloakarnaniki.gr";
			TLZMail.sendHTMLEmail(Initializer.singleton().getProperties(), email, "Welcome", "Your Account has been created:<br/>" + rd.toString());
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	public static void sendUserRegistrationEmail(String email) {
		try {
			TLZMail.sendHTMLEmail(Initializer.singleton().getProperties(), email, "Welcome", "Your Account has been created");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void sendUserEmailVerificationURL(String email, String verificationUrl) {
		try {
			TLZMail.sendHTMLEmail(Initializer.singleton().getProperties(), email, "Verify your Account", "Click <a href='" + verificationUrl + "'>here</a> to verify your account");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
