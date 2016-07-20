package db.enums;

public enum RoleEnum {
	NONE(0), GOD (1), ADMIN (2), SUBSCRIBER (3), SPECIAL(4), VAGIA(5), UPLOADER(6), KARYDIA(7);

	private int value;

	RoleEnum(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}

	public boolean isGod() {
		return this == GOD;
	}
	public boolean isAdmin() {
		return this == ADMIN;
	}
	public boolean isSpecial() {
		return this == SPECIAL;
	}
	public boolean isVagia() {
		return this == VAGIA;
	}
}
