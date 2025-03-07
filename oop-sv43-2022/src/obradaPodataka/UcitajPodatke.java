package obradaPodataka;

import java.util.List;

import entiteti.Klijent;
import entiteti.KozmetickiSalon;
import entiteti.TipTretmana;
import entiteti.Tretman;
import entiteti.ZakazanTretman;
import entiteti.Zaposleni;

public class UcitajPodatke {
	private static List<Klijent> sviKlijenti;
	private static List<Zaposleni> sviZaposleni;
	private static List<TipTretmana> sviTipoviTretmana;
	private static List<Tretman> sviTretmani;
	private static List<ZakazanTretman> sviZakazaniTretmani;
	private static KozmetickiSalon kozmetickiSalon;
	
	public UcitajPodatke() {}

	public static List<Klijent> getSviKlijenti() {
		return sviKlijenti;
	}

	public static void setSviKlijenti(List<Klijent> sviKlijenti) {
		UcitajPodatke.sviKlijenti = sviKlijenti;
	}

	public static List<Zaposleni> getSviZaposleni() {
		return sviZaposleni;
	}

	public static void setSviZaposleni(List<Zaposleni> sviZaposleni) {
		UcitajPodatke.sviZaposleni = sviZaposleni;
	}

	public static List<TipTretmana> getSviTipoviTretmana() {
		return sviTipoviTretmana;
	}

	public static void setSviTipoviTretmana(List<TipTretmana> sviTipoviTretmana) {
		UcitajPodatke.sviTipoviTretmana = sviTipoviTretmana;
	}

	public static List<Tretman> getSviTretmani() {
		return sviTretmani;
	}

	public static void setSviTretmani(List<Tretman> sviTretmani) {
		UcitajPodatke.sviTretmani = sviTretmani;
	}

	public static List<ZakazanTretman> getSviZakazaniTretmani() {
		return sviZakazaniTretmani;
	}

	public static void setSviZakazaniTretmani(List<ZakazanTretman> sviZakazaniTretmani) {
		UcitajPodatke.sviZakazaniTretmani = sviZakazaniTretmani;
	}
	
	public static KozmetickiSalon getKozmetickiSalon() {
		return kozmetickiSalon;
	}
	
	public static void setKozmetickiSalon(KozmetickiSalon kozmetickiSalon) {
		UcitajPodatke.kozmetickiSalon = kozmetickiSalon;
	}
}
