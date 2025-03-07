package util;

public enum Stanje {
	ZAKAZAN(0), IZVRSEN(1), OTKAZAOKLIJENT(2), OTKAZAOSALON(3), NIJESEPOJAVIO(4);
	public int stanje;
	
	private Stanje() {}
	private Stanje(int stanje) {this.stanje = stanje;}
	
	private String[] opis = {"zakazan tretman", "izvrsen tretman", "otkazao klijent", "otkazao salon", "klijent se nije pojavio"};
	
	@Override
	public String toString() {
		return opis[this.ordinal()];
	}
}