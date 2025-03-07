package entiteti;

public class Klijent extends Korisnik{
	private String uloga;
	private KarticaLojalnosti karticaLojalnosti;
	
	public Klijent(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String telefon,
			String adresa, KarticaLojalnosti karticaLojalnosti) {
		super(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa);
		this.setUloga();
		this.setKarticaLojalnosti(karticaLojalnosti);
	}
	
	public String getUloga() {
		return uloga;
	}
	
	public void setUloga() {
		this.uloga = "klijent";
	}
	
	public KarticaLojalnosti getKarticaLojalnosti() {
		return karticaLojalnosti;
	}
	
	public void setKarticaLojalnosti(KarticaLojalnosti karticaLojalnosti) {
		this.karticaLojalnosti = karticaLojalnosti;
	}
	
	@Override
	public String toString() {
		return "korisnicko ime: " +this.getKorisnickoIme()+ "|ime: " +this.getIme()+ "|prezime: " +this.getPrezime()+ "|uloga: " +this.uloga;
	}
}
