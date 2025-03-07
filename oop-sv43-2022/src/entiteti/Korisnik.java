package entiteti;

public abstract class Korisnik {
	private String korisnickoIme;
	private String lozinka;
	private String ime;
	private String prezime;
	private String pol;
	private String telefon;
	private String adresa;
	
	public Korisnik(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String telefon, String adresa) {
		this.setKorisnickoIme(korisnickoIme);
		this.setLozinka(lozinka);
		this.setIme(ime);
		this.setPrezime(prezime);
		this.setPol(pol);
		this.setTelefon(telefon);
		this.setAdresa(adresa);
	}
	
	public String getKorisnickoIme() {
		return korisnickoIme;
	}
	
	public void setKorisnickoIme(String korisnickoIme) {
		this.korisnickoIme = korisnickoIme;
	}
	
	public String getLozinka() {
		return lozinka;
	}
	
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	
	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public String getPrezime() {
		return prezime;
	}
	
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	
	public String getPol() {
		return pol;
	}
	
	public void setPol(String pol) {
		this.pol = pol;
	}
	
	public String getTelefon() {
		return telefon;
	}
	
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	
	public String getAdresa() {
		return adresa;
	}
	
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
}
