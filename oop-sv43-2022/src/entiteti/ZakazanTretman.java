package entiteti;

import java.time.LocalDate;
import java.time.LocalTime;

import util.Stanje;

public class ZakazanTretman {
	private int id;
	private Tretman tretman;
	private double cena;
	private LocalDate datumTretmana;
	private LocalTime vremeTretmana;
	private Zaposleni kozmeticar;
	private Stanje stanje;
	private Klijent klijent;
	private Zaposleni recepcioner;
	
	public ZakazanTretman(int id, Tretman tretman, double cena, LocalDate datumTretmana, LocalTime vremeTretmana, Zaposleni kozmeticar, Stanje stanje, Klijent klijent, Zaposleni recepcioner) {
		this.setId(id);
		this.setTretman(tretman);
		this.setCena(cena);
		this.setDatumTretmana(datumTretmana);
		this.setVremeTretmana(vremeTretmana);
		this.setKozmeticar(kozmeticar);
		this.setStanje(stanje);
		this.setKlijent(klijent);
		this.setRecepcioner(recepcioner);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Tretman getTretman() {
		return tretman;
	}
	
	public void setTretman(Tretman tretman) {
		this.tretman = tretman;
	}
	
	public double getCena() {
		return cena;
	}
	
	public void setCena(double cena) {
		this.cena = cena;
	}
	
	public LocalDate getDatumTretmana() {
		return datumTretmana;
	}
	
	public void setDatumTretmana(LocalDate datumTretmana) {
		this.datumTretmana = datumTretmana;
	}
	
	public LocalTime getVremeTretmana() {
		return vremeTretmana;
	}
	
	public void setVremeTretmana(LocalTime vremeTretmana) {
		this.vremeTretmana = vremeTretmana;
	}
	
	public Zaposleni getKozmeticar() {
		return kozmeticar;
	}
	
	public void setKozmeticar(Zaposleni kozmeticar) {
		this.kozmeticar = kozmeticar;
	}
	
	public Stanje getStanje() {
		return stanje;
	}
	
	public void setStanje(Stanje stanje) {
		this.stanje = stanje;
	}
	
	public Klijent getKlijent() {
		return klijent;
	}
	
	public void setKlijent(Klijent klijent) {
		this.klijent = klijent;
	}
	
	public Zaposleni getRecepcioner() {
		return recepcioner;
	}
	
	public void setRecepcioner(Zaposleni recepcioner) {
		this.recepcioner = recepcioner;
	}
	
	@Override
	public String toString() {
		return "id: " +this.getId()+ "|tip tretmana: " +this.getTretman().getNaziv()+ "|datum i vreme: " +this.getDatumTretmana()+" "+this.getVremeTretmana()+ "|cena: " +this.getTretman().getCena();
	}
}
