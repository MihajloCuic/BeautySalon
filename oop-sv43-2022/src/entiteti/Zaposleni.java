package entiteti;

import java.util.List;

import util.StrucnaSprema;
import util.Uloga;

public class Zaposleni extends Korisnik{
	private StrucnaSprema nivoStrucneSpreme;
	private int godineStaza;
	private double plata;
	private double bonus;
	private Uloga uloga;
	private List<String> tretmani;
	
	public Zaposleni(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String telefon, String adresa, 
					Uloga uloga, StrucnaSprema nivoStrucneSpreme, int godineStaza, double plata, double bonus, List<String> tretmani) {
		super(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa);
		this.setStrucnaSprema(nivoStrucneSpreme);
		this.setGodineStaza(godineStaza);
		this.setPlata(plata);
		this.setBonus(bonus);
		this.setUloga(uloga);
		this.setTretman(tretmani);
	}
	
	public Zaposleni(String korisnickoIme, String lozinka, String ime, String prezime, String pol, String telefon,
			String adresa, Uloga uloga, StrucnaSprema nivoStrucneSpreme, int godineStaza, double plata, double bonus) {
		super(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa);
		this.setStrucnaSprema(nivoStrucneSpreme);
		this.setGodineStaza(godineStaza);
		this.setPlata(plata);
		this.setBonus(bonus);
		this.setUloga(uloga);
	}
	
	public StrucnaSprema getStrucnaSprema() {
		return nivoStrucneSpreme;
	}
	
	public void setStrucnaSprema(StrucnaSprema nivoStrucneSpreme) {
		this.nivoStrucneSpreme = nivoStrucneSpreme;
	}
	
	public int getGodineStaza() {
		return godineStaza;
	}
	
	public void setGodineStaza(int godineStaza) {
		this.godineStaza = godineStaza;
	}
	
	public double getPlata() {
		return plata;
	}
	
	public void setPlata(double plata) {
		this.plata = plata;
	}
	
	public double getBonus() {
		return bonus;
	}
	
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	
	public Uloga getUloga() {
		return uloga;
	}
	
	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}
	
	public List<String> getTretman() {
		return tretmani;
	}
	
	public void setTretman(List<String> tretmani) {
		this.tretmani = tretmani;
	}
	
	@Override
	public String toString() {
		return "korisnicko ime: " +this.getKorisnickoIme()+ "|ime: " +this.getIme()+ "|prezime: " +this.getPrezime()+ "|uloga: " +this.getUloga();
	}
}
