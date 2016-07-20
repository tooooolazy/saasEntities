package db.enums;

import com.tooooolazy.util.Messages;

public enum ParameterCategoryEnum {
	GENERIC(0), VIEW(1), EMAILING(2), SIGNIN(3);

	private int value;

	ParameterCategoryEnum(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}

	public String toString() {
		return Messages.getString(getClass(), name());
	}

}
