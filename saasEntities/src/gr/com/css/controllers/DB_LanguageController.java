/**
 * 
 */
package gr.com.css.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.tooooolazy.controllers.DB_Controller;
import com.tooooolazy.gwt.shared.interfaces.LanguageController;
import com.tooooolazy.gwt.widgets.shared.LanguageNotFoundException;
import com.tooooolazy.gwt.widgets.shared.SupportedLanguage;

import db.entities.Label;
import db.entities.LabelML;
import db.entities.LabelMLId;
import db.entities.Language;
import db.enums.LabelGroupEnum;

/**
 * @author tooooolazy
 *
 */
public class DB_LanguageController extends DB_Controller implements LanguageController {
	protected List<SupportedLanguage> supportedLanguages;

	public DB_LanguageController() {
		getLanguages();
		if (supportedLanguages == null)
			addSupportedLanguages();
		if (getDBresources("en").size() == 0) {
			addDefaultLabels();
		}
	}
	@Override
	protected void initializeDB() {
	}

	/* (non-Javadoc)
	 * @see gr.com.css.interfaces.LanguageController#getLanguages()
	 */
	@Override
	public List getLanguages() {
		if (supportedLanguages == null) {
			Query q = getEntityManager().createQuery("select lang from Language lang");
			supportedLanguages = q.getResultList();
			if (supportedLanguages.isEmpty()) {
				supportedLanguages = null;
//				throw new LanguageNotFoundException(null);
			}
		}
		return supportedLanguages;
	}

	@Override
	public SupportedLanguage findLanguage(String language) {
		if (supportedLanguages == null) {
			Query q = getEntityManager().createQuery("select language from Language language where language.language = :language");
			q.setParameter("language", language );
			try {
				Language lang = (Language)q.getSingleResult();
				return lang;
			} catch (NoResultException e) {
				throw new LanguageNotFoundException(language);
			}
		} else {
			for (SupportedLanguage l : supportedLanguages) {
				if (l.getLanguage().equals(language)) {
					return l;
				}
			}
			throw new LanguageNotFoundException(language);
		}
	}

	/**
	 * Creates a new Label in DB. Since a Transaction is required for this action, one is created if needed.
	 * @param lang
	 * @param key
	 * @param value
	 * @param isDefault should the value be stored in the main table as well (eg in 'Label')
	 */
	public Label createLabel(String language, String key, String value, boolean isDefault) {
		Language lang = (Language)findLanguage(language);

		// first get existing label for that resource key (if there is none create it)
		Label l = findLabel(key, true);

		// we don't know if it is a new label or not!!!

		// create the specific language Label
		// what if ML exists??
		LabelMLId id = new LabelMLId(l.getId(), lang.getId());

		// look for ML existence
		LabelML ml = getEntityManager().find(LabelML.class, id);

		if (ml == null) { // need to create new
			ml = new LabelML(id, l, lang, value, new Date());
		} else { // should update current
			ml.setResourceValue(value);
			ml.setEdDate(new Date());
		}

		if (isDefault) {
			l.setResourceValue(value);
			getEntityManager().persist(l);
		}

		// if it is the default value maybe skip the ML record??
		// NEVER MIND always create/save the ML record!
		getEntityManager().persist(ml);
		getEntityManager().flush();

		return l;
	}
	public Object createLabel(String lang, String key, String value) {
		return createLabel(lang, key, value, false);
	}
	public Object createDefaultLabel(String lang, String key, String value) {
		return createLabel(lang, key, value, true);
	}
	/*
	 * It uses findLabel(String key, boolean doCreate)
	 * and passes false, since a Label will not be created.
	 * @param key
	 * @return
	 */
	@Override
	public Label findLabel(String key) {
		return findLabel(key, false);
	}
	/**
	 * Retrieves the Label from DB based on the provided 'key'. If the label does not exist and 'doCreate' is true, then it is created.
	 * @param key
	 * @param doCreate
	 * @return
	 */
	protected Label findLabel(String key, boolean doCreate) {
		Label l = null;
		Query q = getEntityManager().createQuery("Select label from Label label where resource_key = :key");
		q.setParameter("key", key);
		try {
			l = (Label)q.getSingleResult();
			return l;
		} catch (NoResultException e) {
			if (doCreate) {
				l = new Label(key, new Date());

				getEntityManager().persist(l);
			}
		}
		return l;
	}

	@Override
	public List<Label> getDBresources(String language) {
		return getDBresources(0, language);
	}
	@Override
	public List<Label> getDBresources(int group, String language) {
		Language lang = (Language)findLanguage(language);
		List<Label> res = getDBLabels(lang, group);

		return res;
	}

	/**
	 * Helper to retrieve DB defined Labels
	 * group specifies the group of labels to retrieve, 0 is the global group where common labels belong
	 * @param l
	 * @param group
	 * @return
	 */
	protected List<Label> getDBLabels(Language lang, int group) {
		Query q = getEntityManager().createQuery("select label from Label label where label.groupId = :group and label.id in (select ml.id.labelId from LabelML ml where ml.id.langId = :lId)");
		q.setParameter("lId", lang.getId());
		q.setParameter("group", group);
		List<Label> res = q.getResultList();
//		Iterator<Label> it = res.iterator();
//		while (it.hasNext()) {
//			Label ll = it.next();
////			getEntityManager().refresh(ll);
//			Iterator<LabelML> it2 = ll.getLabelMLs().iterator();
//			while (it2.hasNext()) {
//				LabelML ml = it2.next();
//				System.out.println(ml.getResourceValue());
//			}
//		}
		if (res.isEmpty() && group == 0) {
//			throw new LabelsNotFoundException();
		}
		return res;
	}
	@Override
	public Map<String, String> getDBresourcesAsMap(String language) {
		return getDBresourcesAsMap(0, language);
	}
	@Override
	public Map<String, String> getDBresourcesAsMap(int group, String language) {
		Map<String, String> map = new HashMap<String, String>();

		List<Label> res = getDBresources(group, language);

		Iterator<Label> it = res.iterator();
		while (it.hasNext()) {
			Label l = it.next();
			map.put(l.getResourceKey(), l.getResourceValue());

			Iterator<LabelML> it2 = l.getLabelMLs().iterator();
			while (it2.hasNext()) {
				LabelML ml = it2.next();
				if (language.equals(ml.getLanguage().getLanguage()))
					map.put(l.getResourceKey(), ml.getResourceValue());
			}
		}

		return map;
	}

	//////////////////////////////////////////////////
	/**
	 * Initialized DB with 2 default languages. Since a Transaction is required for this action, one is created if needed.
	 */
	public void addSupportedLanguages() {
		Language el = new Language("EN", "English", new Date(), "en");
		Language en = new Language("ΕΛ", "Ελληνικά", new Date(), "el");

		getEntityManager().persist( el );
		getEntityManager().persist( en );
		getEntityManager().flush();
	}
	public void addDefaultLabels() {
		Language en_l = (Language)findLanguage("en");
		Language el_l = (Language)findLanguage("el");

		// label with def language value (en) set 
		Label l = new Label("Form.requiredMessage", "Enter required data", new Date());
		l.setGroupId(LabelGroupEnum.NONE);
		getEntityManager().persist(l);
		LabelML l_el = new LabelML(new LabelMLId(l.getId(), el_l.getId()), l, el_l, "Εισάγετε τα υποχρεωτικά πεδία", new Date());
		LabelML l_en = new LabelML(new LabelMLId(l.getId(), en_l.getId()), l, en_l, "Enter required data", new Date());
		l.getLabelMLs().add(l_el);
		l.getLabelMLs().add(l_en);
		getEntityManager().persist(l);
		getEntityManager().persist(l_el);
		getEntityManager().persist(l_en);
		getEntityManager().flush();
	}
}
