/**
 * 
 */
package com.tooooolazy.controllers;

import java.util.Map;

import org.json.JSONObject;

import com.tooooolazy.gwt.server.Initializer;
import com.tooooolazy.gwt.shared.interfaces.AddressController;
import com.tooooolazy.gwt.shared.interfaces.InitializationController;
import com.tooooolazy.gwt.shared.interfaces.LanguageController;

/**
 * @author tooooolazy
 *
 */
public class DB_InitializationController extends DB_Controller implements InitializationController {

	/* (non-Javadoc)
	 * @see com.tooooolazy.controllers.DB_Controller#initializeDB()
	 */
	@Override
	protected void initializeDB() {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Object> getInitializationData(String className) {
		// TODO Auto-generated method stub
		if (className.equals("com.tooooolazy.gwt.client.BaseRegisterComponent")) {
			AddressController ac = (AddressController) getController( getControllerClass( Initializer.ADDRESS_CONTROLLER_CLASS ) );
			ac.getCountries();
		}
		
		return null;
	}

}
