package obradaPodataka;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entiteti.KozmetickiSalon;
import entiteti.Tretman;
import entiteti.ZakazanTretman;
import entiteti.Zaposleni;

public class KozmetickiSalonOperacije {
	private final String KOZMETICKI_SALON_PUTANJA = "datoteke/kozmetickiSalon.csv";
	
	public static void ispisiPodatkeKozmetickogSalona(KozmetickiSalon kozmetickiSalon) {
		System.out.println("Naziv salona: " + kozmetickiSalon.getNaziv());
		System.out.println("Radno vreme:  " + kozmetickiSalon.getPocetakRadnogVremena() +"-"+ kozmetickiSalon.getKrajRadnogVremena());
		System.out.println("Cenovnik:");
		System.out.println(kozmetickiSalon.getCenovnik());
	}
	
	//funkcija dodajSalon ne bi imala smisla jer moze postojati samo jedan kozmeticki salon
	
	public static KozmetickiSalon izmeniPodatkeKozmetickogSalona(KozmetickiSalon kozmetickiSalon, String naziv, LocalTime pocetakRadnogVremena, LocalTime krajRadnogVremena) throws Exception {		
		if(naziv.equals("") || naziv == null) {
			throw new Exception("Greska, ima salona nije prosledjeno");
		}
		
		if(pocetakRadnogVremena == null) {
			throw new Exception("Greska, nije prosledjen pocetak radnog vremena salona");
		}
		
		if(krajRadnogVremena == null) {
			throw new Exception("Greska, nije prosledjen kraj radnog vremena salona");
		}
		
		kozmetickiSalon.setNaziv(naziv);
		kozmetickiSalon.setPocetakRadnogVremena(pocetakRadnogVremena);
		kozmetickiSalon.setKrajRadnogVremena(krajRadnogVremena);
		
		return kozmetickiSalon;
	}
	
	public static HashMap<String, Double> formirajCenovnik(List<Tretman> sviTretmani) {
		HashMap<String, Double> cenovnik = new HashMap<>();
		
		for(Tretman tretman: sviTretmani) {
			cenovnik.put(tretman.getNaziv(), tretman.getCena());
		}
		
		return cenovnik;
	}
	
	//funkcija obrisiSalon ne bi imala smisla jer ne mozemo obrisati jedini kozmeticki salon koji imamo
	
	public double izracunajUkupanProfit(List<ZakazanTretman> sviZakazaniTretmani) {
		double profit = 0;
		
		for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
			if(zakazanTretman.getStanje().stanje == 0 || zakazanTretman.getStanje().stanje == 1 || zakazanTretman.getStanje().stanje == 4) {
				profit += zakazanTretman.getTretman().getCena();
			}else if(zakazanTretman.getStanje().stanje == 2) {
				profit += zakazanTretman.getTretman().getCena()*0.1;
			}else {
				continue;
			}
		}
		return profit;
	}
	
	public double izracunajRashode(List<Zaposleni> sviZaposleni) {
		double rashodi = 0;
		for(Zaposleni zaposleni: sviZaposleni) {
			rashodi += zaposleni.getPlata();
		}
		return rashodi;
	}
	
	public double izracunajProfitPoMesecu(List<ZakazanTretman> sviZakazaniTretmani, int mesec) {
		double profit = 0;
		String pocetak = "01." + (mesec < 10 ? "0"+Integer.toString(mesec) : Integer.toString(mesec)) +".2023.";
		String kraj = "";
		if(mesec == 1 || mesec == 3 || mesec == 5 || mesec == 7 || mesec == 8 || mesec == 10 || mesec == 12) {
			kraj += "31." + (mesec < 10 ? "0"+Integer.toString(mesec) : Integer.toString(mesec)) +".2023.";
		}else if(mesec == 2) {
			kraj += "28." + (mesec < 10 ? "0"+Integer.toString(mesec) : Integer.toString(mesec)) +".2023.";
		}else {
			kraj += "30." + (mesec < 10 ? "0"+Integer.toString(mesec) : Integer.toString(mesec)) +".2023.";
		}
		
		DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		LocalDate pocetakMeseca = LocalDate.parse(pocetak, datumFormat);
		LocalDate krajMeseca = LocalDate.parse(kraj, datumFormat);
		
		for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
			if(zakazanTretman.getDatumTretmana().compareTo(pocetakMeseca) > 0 && zakazanTretman.getDatumTretmana().compareTo(krajMeseca) < 0 && zakazanTretman.getStanje().stanje != 3) {
				profit += zakazanTretman.getCena();
			}
		}
		
		return profit;
	}
	
	public HashMap<String, Double> UcitajCenovnik() {
		HashMap<String, Double> cenovnik = new HashMap<>();
		for(Tretman tretman: UcitajPodatke.getSviTretmani()) {
			cenovnik.put(tretman.getNaziv(), tretman.getCena());
		}
		return cenovnik;
	}
	
	public void ucitajKozmetickiSalonIzFajla() throws IOException {
		List<String[]> kozmetickiSalonFajl = CSVReaderWriter.read(KOZMETICKI_SALON_PUTANJA);
		for(String[] red: kozmetickiSalonFajl) {
			String naziv = red[0];
			
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime pocetakRadnogVremena = LocalTime.parse(red[1], timeFormatter);
			LocalTime krajRadnogVremena = LocalTime.parse(red[2], timeFormatter);
			double iznosZaPopust = Double.parseDouble(red[3]);
			
			KozmetickiSalon kozmetickiSalon = new KozmetickiSalon(naziv, pocetakRadnogVremena, krajRadnogVremena, iznosZaPopust);
			UcitajPodatke.setKozmetickiSalon(kozmetickiSalon);
			break;
		}
	}
	
	public void sacuvajKozmetickiTretmanFajl(KozmetickiSalon kozmetickiSalon) throws IOException {
		List<String[]> kozmetickiSalonFajl = new ArrayList<>();
		String[] temp = new String[4];
		temp[0] = kozmetickiSalon.getNaziv();
		
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		temp[1] = kozmetickiSalon.getPocetakRadnogVremena().format(timeFormatter);
		temp[2] = kozmetickiSalon.getKrajRadnogVremena().format(timeFormatter);
		temp[3] = Double.toString(kozmetickiSalon.getIznosZaPopust());
		
		kozmetickiSalonFajl.add(temp);
		CSVReaderWriter.write(KOZMETICKI_SALON_PUTANJA, kozmetickiSalonFajl);
	}
}
