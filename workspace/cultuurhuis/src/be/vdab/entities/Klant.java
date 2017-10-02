package be.vdab.entities;

import java.io.Serializable;

public class Klant implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;
	private String voornaam;
	private String familienaam;
	private Adres adres;
	private String gebruikersnaam;
	private String paswoord;
	public Klant() {
	}
	public Klant(String voornaam, String familienaam, Adres adres, String gebruikersnaam, String paswoord) {
		setVoornaam(voornaam);
		setFamilienaam(familienaam);
		setAdres(adres);
		setGebruikersnaam(gebruikersnaam);
		setPaswoord(paswoord);
	}
	public Klant(long id, String voornaam, String familienaam, Adres adres, String gebruikersnaam, String paswoord) {
		this(voornaam, familienaam, adres, gebruikersnaam, paswoord);
		setId(id);
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getVoornaam() {
		return voornaam;
	}
	public void setVoornaam(String voornaam) {
		if(isStringValid(voornaam)) {
			this.voornaam = voornaam;
		}else {
			throw new IllegalArgumentException();
		}
	}
	public String getFamilienaam() {
		return familienaam;
	}
	public void setFamilienaam(String familienaam) {
		if(isStringValid(familienaam)) {
			this.familienaam = familienaam;
		}else {
			throw new IllegalArgumentException();
		}
	}
	public Adres getAdres() {
		return adres;
	}
	public void setAdres(Adres adres) {
		if(adres != null) {
			this.adres = adres;
		}else {
			throw new IllegalArgumentException();
		}
	}
	public String getGebruikersnaam() {
		return gebruikersnaam;
	}
	public void setGebruikersnaam(String gebruikersnaam) {
		if(isStringValid(gebruikersnaam)) {
			this.gebruikersnaam = gebruikersnaam;
		}else {
			throw new IllegalArgumentException();
		}
	}
	public String getPaswoord() {
		return paswoord;
	}
	public void setPaswoord(String paswoord) {
		if(isStringValid(paswoord)) {
			this.paswoord = paswoord;
		}else {
			throw new IllegalArgumentException();
		}
	}
	public static boolean isStringValid(String string) {
		return string != null && !string.trim().isEmpty();
	}
}
