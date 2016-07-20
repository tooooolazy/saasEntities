package db.enums;

import com.tooooolazy.util.Messages;

public enum TypeClassGroupEnum {
	GENERIC(0), VIEW(1);

	private int value;

	TypeClassGroupEnum(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}

	public String toString() {
		return Messages.getString(getClass(), name());
	}
}
