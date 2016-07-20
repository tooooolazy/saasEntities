package db.enums;

import com.tooooolazy.util.Messages;

public enum LabelGroupEnum {
	NONE(0), COUNTRY (1), COUNTY(2), CITY(3), TITLE(4), SECONDARY_TITLE(5), PROFESSION(6), VALUE_TYPES(7), ADDRESS_TYPE(8)
	, DUMMY3(9)
	, ARTICLE_BODY(10), ARTICLE_TITLE(11), ARTICLE_SUBTITLE(12), ARTICLE_FOOTER(13), ARTICLE_CATEGORY(14)
	, DUMMY_15(15), DUMMY_16(16), DUMMY_17(17), DUMMY_18(18), DUMMY_19(19)
	, EMAIL_RELATED(20);

	private int value;

	LabelGroupEnum(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}

	public String toString() {
		return Messages.getString(getClass(), name());
	}
	public static LabelGroupEnum fromValue(int v) {
		return LabelGroupEnum.values()[v];
	}
}
