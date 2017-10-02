package be.vdab.entities;

import java.io.Serializable;

public class Adres implements Serializable {
	private static final long serialVersionUID = 1L;
	private String straat;
	private int huisNr;
	private int postcode;
	private String gemeente;

	public Adres() {
	}

	public Adres(String straat, int huisNr, int postcode, String gemeente) {
		setStraat(straat);
		setHuisNr(huisNr);
		setPostcode(postcode);
		setGemeente(gemeente);
	}

	public String getStraat() {
		return straat;
	}

	public void setStraat(String straat) {
		if (isStringValid(straat)) {
			this.straat = straat;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public int getHuisNr() {
		return huisNr;
	}

	public void setHuisNr(int huisNr) {
		if (isIntValid(huisNr)) {
			this.huisNr = huisNr;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		if (isIntValid(postcode)) {
			this.postcode = postcode;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public String getGemeente() {
		return gemeente;
	}

	public void setGemeente(String gemeente) {
		if (isStringValid(gemeente)) {
			this.gemeente = gemeente;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static boolean isStringValid(String string) {
		return string != null && !string.trim().isEmpty();
	}

	public static boolean isIntValid(int integer) {
		return integer >= 0;
	}
}
