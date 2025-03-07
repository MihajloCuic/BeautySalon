package util;

public enum Uloga {
	MENADZER(1), KOZMETICAR(2), RECEPCIONER(3);
	public int uloga;
	
	private Uloga() {}
	private Uloga(int uloga) {this.uloga = uloga;}
	
	private String[] opis = {"menadzer", "kozmeticar", "recepcioner"};
	
	@Override
	public String toString() {
		return opis[this.ordinal()];
	}
	StrucnaSprema valueOf(Object selectedItem) {
		
		return null;
	}
}
