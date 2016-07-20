package db.enums;

import com.tooooolazy.util.Messages;

public enum StatusEnum {
	NOT_VERIFIED(0), ACTIVE (1), INACTIVE (2);

	private int value;

	StatusEnum(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}

	public boolean isNotVerified() {
		return this == NOT_VERIFIED;
	}
	public boolean isActive() {
		return this == ACTIVE;
	}
	public boolean isInactive() {
		return this == INACTIVE;
	}
	public String toString() {
		return Messages.getString(getClass(), name());
	}
}
