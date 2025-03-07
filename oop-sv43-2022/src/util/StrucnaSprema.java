package util;

public enum StrucnaSprema {
	OSNOVNA(1.0), SREDNJA(1.2), VISA(1.5), FAKULTET(1.7);
	public double strucnaSprema;
	
	private StrucnaSprema() {}
	private StrucnaSprema(double strucnaSprema) {this.strucnaSprema = strucnaSprema;}
	
	private String[] opis = {"osnovna škola", "srednja škola", "viša škola", "fakultet"};
	
	@Override
	public String toString() {
		return opis[this.ordinal()];
	}	
}
