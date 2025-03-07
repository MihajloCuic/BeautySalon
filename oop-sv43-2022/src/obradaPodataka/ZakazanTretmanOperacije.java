package obradaPodataka;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entiteti.Klijent;
import entiteti.Tretman;
import entiteti.ZakazanTretman;
import entiteti.Zaposleni;
import util.Stanje;

public class ZakazanTretmanOperacije {
	private final String ZAKAZANI_TRETMANI_PUTANJA = "datoteke/zakazaniTretmani.csv";
	private List<ZakazanTretman> sviZakazaniTretmani;
	
	public ZakazanTretmanOperacije(List<ZakazanTretman> sviZaposleniTretmani) {
		this.sviZakazaniTretmani = sviZaposleniTretmani;
	}
	
	public int dodeliId() {
		return sviZakazaniTretmani.size();
	}
	
	public void ispisiZakazaneTretmane() {
		for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
			System.out.println(zakazanTretman);
		}
	}
	
	public List<ZakazanTretman> dodajZakazanTretman(Tretman tretman, LocalDate datumTretmana, LocalTime vremeTretmana, 
													Zaposleni kozmeticar, Klijent klijent, Zaposleni recepcioner) throws Exception{
		int id = dodeliId();
		double cena = tretman.getCena();
		
		for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
			if(zakazanTretman.getId() == id) {
				throw new Exception("Greska, vec postoji tretman sa zadatim id-jem");
			}
			if(zakazanTretman.getDatumTretmana().compareTo(datumTretmana) == 0 && zakazanTretman.getVremeTretmana().compareTo(vremeTretmana) == 0 && zakazanTretman.getKozmeticar().getKorisnickoIme().equals(kozmeticar.getKorisnickoIme())) {
				throw new Exception("Greska pri zakazivanju tretmana " +tretman.getNaziv()+"od strane "+ klijent.getKorisnickoIme()+"\nIzabrani kozmeticar ima vec zakazan tretman za odabrani datum i vreme!");
			}
		}
		
		if(LocalDate.now().isAfter(datumTretmana)) {
			throw new Exception("Greska, ne mozete zakazati tretman u proslosti");
		}
		
		if(LocalDate.now().equals(datumTretmana) && LocalTime.now().isAfter(vremeTretmana)) {
			throw new Exception("Greska, ne mozete zakazati tretman u proslosti");
		}
		
		if(klijent.getKarticaLojalnosti().getPravoNaPopust()) {
			cena -= tretman.getCena()*0.1;
		}
		ZakazanTretman zakazanTretman;
		if(recepcioner == null) {
			Zaposleni zaposleni = new Zaposleni("online", "online", "online", "online",null,null,null,null,null, 0,0,0);
			zakazanTretman = new ZakazanTretman(id, tretman, cena, datumTretmana, vremeTretmana, kozmeticar, Stanje.valueOf("ZAKAZAN"), klijent, zaposleni);
		}else {
			zakazanTretman = new ZakazanTretman(id, tretman, cena, datumTretmana, vremeTretmana, kozmeticar, Stanje.valueOf("ZAKAZAN"), klijent, recepcioner);
		}	
		sviZakazaniTretmani.add(zakazanTretman);
		return sviZakazaniTretmani;
	}
	
	public List<ZakazanTretman> izmeniZakazanTretman(int id, Tretman tretman, double cena, LocalDate datumTretmana, LocalTime vremeTretmana, 
													Zaposleni kozmeticar, Stanje stanje, Klijent klijent, Zaposleni recepcioner) throws Exception{
		boolean izmena = false;
		
		for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
			if(zakazanTretman.getDatumTretmana() == datumTretmana && zakazanTretman.getVremeTretmana() == vremeTretmana && zakazanTretman.getKozmeticar().getKorisnickoIme().equals(kozmeticar.getKorisnickoIme()) && zakazanTretman.getStanje().stanje == 0) {
				if(!zakazanTretman.getKlijent().getKorisnickoIme().equals(klijent.getKorisnickoIme())) {
					throw new Exception("Greska, izabrani kozmeticar ima vec zakazan tretman za odabrani datum i vreme!");
				}
			}
		}
		
		if(cena < 0) {
			throw new Exception("Cena tretmana ne moze biti manja od nule");
		}
		
		if(stanje.stanje != 0 && stanje.stanje != 1 && stanje.stanje != 2 && stanje.stanje != 3 && stanje.stanje != 4) {
			throw new Exception("Greska, prosledjeni status tretmana nije validan");
		}
		
		for(ZakazanTretman z: sviZakazaniTretmani) {
			if(z.getId() == id) {
				z.setTretman(tretman);
				z.setCena(cena);
				z.setDatumTretmana(datumTretmana);
				z.setVremeTretmana(vremeTretmana);
				z.setKozmeticar(kozmeticar);
				z.setStanje(stanje);
				z.setKlijent(klijent);
				if(recepcioner == null) {
					Zaposleni zaposleni = new Zaposleni("online", "online", "online", "online",null,null,null,null,null, 0,0,0);
					z.setRecepcioner(zaposleni);
				}else {
					z.setRecepcioner(recepcioner);
				}
				izmena = true;
				break;
			}
		}
		
		if(izmena == false) {
			return null;
		}
		
		return sviZakazaniTretmani;
	}
	
	public List<ZakazanTretman> obrisiZakazanTretman(int id) {
		boolean obrisan = false;
		
		for(ZakazanTretman z: sviZakazaniTretmani) {
			if(z.getId() == id) {
				sviZakazaniTretmani.remove(z);
				
				obrisan = true;
				break;
			}
		}
		
		if(obrisan == false) {
			return null;
		}
		
		return sviZakazaniTretmani;
	}
	
	public Tretman pronadjiTretman(int id, List<Tretman> sviTretmani) {
		for(Tretman tretman: sviTretmani) {
			if(id == tretman.getId()) {
				return tretman;
			}
		}
		return null;
	}
	
	public Klijent pronadjiKlijenta(String korisnickoIme, List<Klijent> sviKlijenti) {		
		for(Klijent klijent: sviKlijenti) {
			if(klijent.getKorisnickoIme().equals(korisnickoIme)) {
				return klijent;
			}
		}
		Klijent klijent = new Klijent(korisnickoIme, null, null, null, null, null, null, null);
		return klijent;
	}
	
	public Zaposleni pronadjiZaposlenog(String korisnickoIme, List<Zaposleni> sviZaposleni) {
		for(Zaposleni zaposleni: sviZaposleni) {
			if(zaposleni.getKorisnickoIme().equals(korisnickoIme)) {
				return zaposleni;
			}
		}
		Zaposleni zaposleni = new Zaposleni(korisnickoIme, null, null, null, null, null, null, null, null, 0, 0, 0, null);
		return zaposleni;
	}
	
	public List<ZakazanTretman> ucitajZakazaneTretmaneIzFajla() throws IOException {
		List<String[]> sviZakazaniTretmaniFajl = CSVReaderWriter.read(ZAKAZANI_TRETMANI_PUTANJA);
		List<ZakazanTretman> sviZakazaniTretmani = new ArrayList<>();
		
		List<Tretman> sviTretmani = UcitajPodatke.getSviTretmani();
		List<Klijent> sviKlijenti = UcitajPodatke.getSviKlijenti();	
		List<Zaposleni> sviZaposleni = UcitajPodatke.getSviZaposleni();
		
		for(String[] zakazanTretman: sviZakazaniTretmaniFajl) {
			int id = Integer.parseInt(zakazanTretman[0]);
			
			Tretman tretman = pronadjiTretman(Integer.parseInt(zakazanTretman[1]), sviTretmani);
			if(tretman == null) {
				return null;
			}
			double cena = Double.parseDouble(zakazanTretman[2]);
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
			LocalDate datumTretmana = LocalDate.parse(zakazanTretman[3], dateFormatter);
			
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime vremeTretmana = LocalTime.parse(zakazanTretman[4], timeFormatter);
			
			Zaposleni kozmeticar = pronadjiZaposlenog(zakazanTretman[5], sviZaposleni);
			Stanje stanje = Stanje.valueOf(zakazanTretman[6]);
			
			Klijent klijent = pronadjiKlijenta(zakazanTretman[7], sviKlijenti);
			
			Zaposleni recepcioner = pronadjiZaposlenog(zakazanTretman[8], sviZaposleni);
			
			if(klijent == null) {
				return null;
			}
			
			ZakazanTretman zakazanKozmetickiTretman = new ZakazanTretman(id, tretman, cena, datumTretmana, vremeTretmana, kozmeticar, stanje, klijent, recepcioner);
			sviZakazaniTretmani.add(zakazanKozmetickiTretman);
			UcitajPodatke.setSviZakazaniTretmani(sviZakazaniTretmani);
		}
		UcitajPodatke.setSviZakazaniTretmani(sviZakazaniTretmani);
		return sviZakazaniTretmani;
	}
	
	public void sacuvajZakazaneTretmaneFajl() throws IOException {
		List<String[]> sviZakazaniTretmaniFajl = new ArrayList<>();
		
		for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
			String[] temp = new String[9];
			temp[0] = Integer.toString(zakazanTretman.getId());
			temp[1] = Integer.toString(zakazanTretman.getTretman().getId());
			temp[2] = Double.toString(zakazanTretman.getCena());
			
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
			temp[3] = zakazanTretman.getDatumTretmana().format(dateFormatter);
			
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
			temp[4] = zakazanTretman.getVremeTretmana().format(timeFormatter);
			
			temp[5] = zakazanTretman.getKozmeticar().getKorisnickoIme();
			temp[6] = zakazanTretman.getStanje().name();
			temp[7] = zakazanTretman.getKlijent().getKorisnickoIme();
			if(zakazanTretman.getRecepcioner() == null) {
				temp[8] = "online";
			}else {
				temp[8] = zakazanTretman.getRecepcioner().getKorisnickoIme();
			}
			
			sviZakazaniTretmaniFajl.add(temp);
		}
		CSVReaderWriter.write(ZAKAZANI_TRETMANI_PUTANJA, sviZakazaniTretmaniFajl);
	}
}
