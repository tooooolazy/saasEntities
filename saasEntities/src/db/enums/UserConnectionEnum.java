package db.enums;

import com.tooooolazy.util.Messages;

public enum UserConnectionEnum {
	FACEBOOK(0), TWITTER(1), LINKEDIN(2);

	private int value;

	UserConnectionEnum(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}

	public boolean isFacebook() {
		return this == FACEBOOK;
	}
	public boolean isTwitter() {
		return this == TWITTER;
	}
	public boolean isLinkedin() {
		return this == LINKEDIN;
	}
	public String toString() {
		return Messages.getString(getClass(), name());
	}
	public static UserConnectionEnum getByValue(int value) {
		switch (value) {
			case 0: return FACEBOOK;
			case 1: return TWITTER;
			case 2: return LINKEDIN;
			default: return null;
		}
	}
}
