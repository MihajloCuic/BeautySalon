package entiteti;

public class TipTretmana {
	private int id;
	private String naziv;
	
	public TipTretmana(int id, String naziv) {
		this.setId(id);
		this.setNaziv(naziv);
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
	
	@Override
	public String toString() {
		return "id: " +this.getId()+ "|naziv: " +this.getNaziv();
	}
}
