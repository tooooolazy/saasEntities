/**
 * 
 */
package gr.com.css.controllers;


import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.json.JSONArray;

import com.tooooolazy.controllers.DB_Controller;
import com.tooooolazy.controllers.Importer;
import com.tooooolazy.gwt.server.Initializer;
import com.tooooolazy.gwt.shared.interfaces.AddressController;
import com.tooooolazy.gwt.shared.interfaces.LanguageController;
import com.tooooolazy.util.TLZUtils;

import db.entities.City;
import db.entities.Country;
import db.entities.County;
import db.entities.Label;
import db.entities.Language;

/**
 * @author tooooolazy
 *
 */
public class DB_AddressController extends DB_Controller implements AddressController {
	public DB_AddressController() {
		if (getCountries().isEmpty()) {
//			addCountries(); // not working in Vaadin7 as I thought - will refactor
		}
	}


	@Override
	protected void initializeDB() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Country> getCountries() {
		Query q = getEntityManager().createQuery("select country from Country country where isActive = 0");
		return q.getResultList();
	}

//	@Override
//	public List<County> getCounties() {
//		Query q = getEntityManager().createQuery("select county from County county");
//		return q.getResultList();
//	}

	public List<County> getCounties(String country_alpha2_code) {
		Query q = getEntityManager().createQuery("select county from County county where country.alpha2Code = :country_alpha2_code");
		q.setParameter("country_alpha2_code", country_alpha2_code);
		return q.getResultList();
	}

//	@Override
//	public List<City> getCities() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public List<City> getCities(Integer county_id) {
		Query q = getEntityManager().createQuery("select city from City city where county.id = :county_id");
		q.setParameter("county_id", county_id);
		return q.getResultList();
	}

	public Country findCountryByLanguage(String language) {
		Query q = getEntityManager().createQuery("select country from Country country where country.language.language = :language");
		q.setParameter("language", language);
		List<Country> list = q.getResultList();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	//////////////////////////////////////////
	protected void addCountries() {
		InputStream in =  TLZUtils.getInputStream( Initializer.singleton().getProperty(Initializer.ADDRESS_DATA_FILE_KEY) );
		List<JSONArray> list = Importer.getEntities(in, "countries_iso", 0, 5, new String[] {"alpha2Code", "alpha3Code", "numCode","defLang", "en", "el"});

		LanguageController lc = (LanguageController)getController( Initializer.singleton().getProperty(Initializer.LANGUAGE_CONTROLLER_CLASS) );
//		getEntityManager().getTransaction().begin();
		Iterator<JSONArray> it = list.iterator();
		while (it.hasNext()) {
			JSONArray ja = it.next();
			
			Country country = new Country(ja.optString(0), ja.optString(1), (short)ja.optInt(2));
			if (ja.optInt(3) > 0) {
				Language l = getEntityManager().find(Language.class, ja.optInt(3));
				country.setLanguage(l);

			}
			// create and save labels for country names
			if ( ja.optString(1) != null && ja.optString(1).trim().length() == 3) {
				Label label = (Label) lc.createDefaultLabel("en", "country." + ja.optString(1), ja.optString(4));
				getEntityManager().flush();
	//			int lid = ((Label) lc.createDefaultLabel("en", "country." + ja.optString(1), ja.optString(4))).getId();
				Label label2 = (Label) lc.createLabel("el", "country." + ja.optString(1), ja.optString(5));
	//			getEntityManager().merge(label); // causes an extra label (with no mls) to be saved
				country.setLabel(label);
		
				getEntityManager().persist( country );
			}
		}
		getEntityManager().flush();
	}
	protected void addCounties() {
		InputStream in =  TLZUtils.getInputStream( Initializer.singleton().getProperty(Initializer.ADDRESS_DATA_FILE_KEY) );
		List<JSONArray> list = Importer.getEntities(in, "counties", 0, 4, new String[] {"alpha2Code", "en", "el", "zipFrom", "zipTo"});

		LanguageController lc = (LanguageController)getController( Initializer.singleton().getProperty(Initializer.LANGUAGE_CONTROLLER_CLASS) );
		Iterator<JSONArray> it = list.iterator();
		while (it.hasNext()) {
			JSONArray ja = it.next();

			// create and save labels for county names
			if ( ja.optString(0) != null && ja.optString(0).trim().length() == 2) {
				Country country = getEntityManager().find(Country.class, ja.optString(0));
				County county = new County(country, new Date());
				Label label = (Label) lc.createDefaultLabel("en", null, ja.optString(2));
				getEntityManager().flush();
	//			int lid = ((Label) lc.createDefaultLabel("en", "country." + ja.optString(1), ja.optString(4))).getId();
				Label label2 = (Label) lc.createLabel("el", null, ja.optString(1));
	//			getEntityManager().merge(label); // causes an extra label (with no mls) to be saved
				county.setLabel(label);
		
				getEntityManager().persist( country );
			}
		}
		getEntityManager().flush();
	}
}
