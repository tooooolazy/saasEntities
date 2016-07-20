package db.enums;

import com.tooooolazy.util.Messages;

public enum SubscriptionTypeEnum {
	AITOLOAKARNANIKI(0),
	KARYDIA(1);

	private int value;

	SubscriptionTypeEnum(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}

	public boolean isAitoloakarnaniki() {
		return this == AITOLOAKARNANIKI;
	}
	public boolean isKarydia() {
		return this == KARYDIA;
	}
	public String toString() {
		return Messages.getString(getClass(), name());
	}
}
