package obradaPodataka;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entiteti.Klijent;
import entiteti.Tretman;
import entiteti.ZakazanTretman;
import entiteti.Zaposleni;

public class Izvestaji {
	public HashMap<Zaposleni, List<ZakazanTretman>> kozmetickiTretmaniPoKozmeticaru(LocalDate pocetniDatum, LocalDate KrajnjiDatum) {
		HashMap<Zaposleni, List<ZakazanTretman>> zakazaniTretmaniPoZaposlenom = new HashMap<>();
		for(Zaposleni zaposleni: UcitajPodatke.getSviZaposleni()) {
			if(zaposleni.getUloga().uloga != 2) {
				continue;
			}
			List<ZakazanTretman> zakazaniTretmani = new ArrayList<>();
			for(ZakazanTretman zakazanTretman: UcitajPodatke.getSviZakazaniTretmani()) {
				if(zakazanTretman.getStanje().stanje != 1 || zakazanTretman.getStanje().stanje != 4) {
					continue;
				}
				if(zakazanTretman.getKozmeticar().getKorisnickoIme().equals(zaposleni.getKorisnickoIme()) && zakazanTretman.getDatumTretmana().isAfter(pocetniDatum) && zakazanTretman.getDatumTretmana().isBefore(KrajnjiDatum)) {
					zakazaniTretmani.add(zakazanTretman);
				}
			}
			zakazaniTretmaniPoZaposlenom.put(zaposleni, zakazaniTretmani);
		}
		return zakazaniTretmaniPoZaposlenom;
	}
	
	public HashMap<String, Integer> kozmetickiTretmaniPoStanju(LocalDate pocetniDatum, LocalDate krajnjiDatum) {
		HashMap<String, Integer> zakazaniTretmaniPoStanju = new HashMap<>();
		int brojZakazanih = 0;
		int brojIzvrsenih = 0;
		int brojOtkazanihKlijent = 0;
		int brojOtkazanihSalon = 0;
		int brojNijeSePojavio = 0;
		for(ZakazanTretman zakazanTretman: UcitajPodatke.getSviZakazaniTretmani()) {
			if(zakazanTretman.getDatumTretmana().isAfter(pocetniDatum) && zakazanTretman.getDatumTretmana().isBefore(krajnjiDatum)) {
				switch(zakazanTretman.getStanje().stanje) {
				case 0:
					brojZakazanih++;
					break;
				case 1:
					brojIzvrsenih++;
					break;
				case 2:
					brojOtkazanihKlijent++;
					break;
				case 3:
					brojOtkazanihSalon++;
					break;
				case 4:
					brojNijeSePojavio++;
					break;
				default:
					break;
				}
			}
		}
		zakazaniTretmaniPoStanju.put("zakazan tretman", brojZakazanih);
		zakazaniTretmaniPoStanju.put("izvrsen tretman", brojIzvrsenih);
		zakazaniTretmaniPoStanju.put("otkazao klijent", brojOtkazanihKlijent);
		zakazaniTretmaniPoStanju.put("otkazao salon", brojOtkazanihSalon);
		zakazaniTretmaniPoStanju.put("klijent se nije pojavio", brojNijeSePojavio);
		
		return zakazaniTretmaniPoStanju;
	}
	
	public HashMap<Tretman, List<ZakazanTretman>> izvestajKozmetickeUsluge(LocalDate pocetniDatum, LocalDate krajnjiDatum) {
		HashMap<Tretman, List<ZakazanTretman>> kozmetickeUsluge = new HashMap<>();
		for(Tretman tretman: UcitajPodatke.getSviTretmani()) {
			List<ZakazanTretman> zakazaniTretmani = new ArrayList<>();
			for(ZakazanTretman zakazanTretman: UcitajPodatke.getSviZakazaniTretmani()) {
				if(zakazanTretman.getTretman().getId() == tretman.getId() && zakazanTretman.getDatumTretmana().isAfter(pocetniDatum) && zakazanTretman.getDatumTretmana().isBefore(krajnjiDatum)) {
					zakazaniTretmani.add(zakazanTretman);
				}
			}
			kozmetickeUsluge.put(tretman, zakazaniTretmani);
		}
		
		return kozmetickeUsluge;
	}
	
	public List<Klijent> izvestajKarticeLojalnosti() {
		List<Klijent> klijentiKojiIspunjavajuUslov = new ArrayList<>();
		for(Klijent klijent: UcitajPodatke.getSviKlijenti()) {
			if(!klijent.getKarticaLojalnosti().getPravoNaPopust() && klijent.getKarticaLojalnosti().imaPravoNaPopust()) {
				klijentiKojiIspunjavajuUslov.add(klijent);
			}
		}
		return klijentiKojiIspunjavajuUslov;
	}
}
