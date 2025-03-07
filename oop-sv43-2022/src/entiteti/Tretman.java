package entiteti;

public class Tretman {
	private int id;
	private String naziv;
	private String trajanjeTretmana;
	private double cena;
	private String tipTretmana;
	
	public Tretman(int id, String naziv, String trajanjeTretmana, double cena, String tipTretmana) {
		this.setId(id);
		this.setNaziv(naziv);
		this.setCena(cena);
		this.setTipTretmana(tipTretmana);
		this.setTrajanjeTretmana(trajanjeTretmana);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNaziv() {
		return naziv;
	}
	
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
	public String getTrajanjeTretmana() {
		return trajanjeTretmana;
	}
	
	public void setTrajanjeTretmana(String trajanjeTretmana) {
		this.trajanjeTretmana = trajanjeTretmana;
	}
	
	public double getCena() {
		return cena;
	}
	
	public void setCena(double cena) {
		this.cena = cena;
	}
	
	public String getTipTretmana() {
		return tipTretmana;
	}
	
	public void setTipTretmana(String tipTretmana) {
		this.tipTretmana = tipTretmana;
	}
	
	@Override
	public String toString() {
		return "id: " +this.getId()+ "|tip tretmana: " +this.getTipTretmana()+ "|naziv: " +this.getNaziv()+ "|cena: " +this.getCena() +"|trajanje: " +this.getTrajanjeTretmana();
	}
}
