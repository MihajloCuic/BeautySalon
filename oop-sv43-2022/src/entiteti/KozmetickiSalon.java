package entiteti;

import java.time.LocalTime;
import java.util.HashMap;

public class KozmetickiSalon {
	private String naziv;
	private LocalTime pocetakRadnogVremena;
	private LocalTime krajRadnogVremena;
	private double iznosZaPopust;
	private HashMap<String, Double> cenovnik;
	
	public KozmetickiSalon(String naziv, LocalTime pocetakRadnogVremena, LocalTime krajRadnogVremena, double iznosZaPopust) {
		this.setNaziv(naziv);
		this.setPocetakRadnogVremena(pocetakRadnogVremena);
		this.setKrajRadnogVremena(krajRadnogVremena);
		this.setIznosZaPopust(iznosZaPopust);
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public LocalTime getPocetakRadnogVremena() {
		return pocetakRadnogVremena;
	}
	
	public void setPocetakRadnogVremena(LocalTime pocetakRadnogVremena) {
		this.pocetakRadnogVremena = pocetakRadnogVremena;
	}
	
	public LocalTime getKrajRadnogVremena() {
		return krajRadnogVremena;
	}
	
	public void setKrajRadnogVremena(LocalTime krajRadnogVremena) {
		this.krajRadnogVremena = krajRadnogVremena;
	}
	
	public HashMap<String, Double> getCenovnik() {
		return cenovnik;
	}
	
	public void setCenovnik(HashMap<String, Double> cenovnik) {
		this.cenovnik = cenovnik;
	}
	
	public double getIznosZaPopust() {
		return iznosZaPopust;
	}
	
	public void setIznosZaPopust(double iznosZaPopust) {
		this.iznosZaPopust = iznosZaPopust;
	}
	
	@Override
	public String toString() {
		return "naziv salona: " +this.naziv+ "|radno vreme: " +this.pocetakRadnogVremena+ "-" +this.krajRadnogVremena;
	}
}
