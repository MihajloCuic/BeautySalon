package entiteti;

import obradaPodataka.UcitajPodatke;

public class KarticaLojalnosti {
	private double potrosenIznos;
	private boolean pravoNaPopust;
	
	public KarticaLojalnosti() {}
	
	public KarticaLojalnosti(double potrosenIznos, boolean pravoNaPopust) {
		this.setPotrosenIznos(potrosenIznos);
		this.setPravoNaPopust(pravoNaPopust);
	}
	
	public double getPotrosenIznos() {
		return potrosenIznos;
	}
	
	public void setPotrosenIznos(double potrosenIznos) {
		this.potrosenIznos = potrosenIznos;
	}
	
	public boolean getPravoNaPopust() {
		return pravoNaPopust;
	}
	
	public void setPravoNaPopust(boolean pravoNaPopust) {
		this.pravoNaPopust = pravoNaPopust;
	}
	
	public boolean imaPravoNaPopust() {
		if(this.potrosenIznos >= UcitajPodatke.getKozmetickiSalon().getIznosZaPopust()) {
			return true;
		}
		else {
			return false;
		}
	}
}
