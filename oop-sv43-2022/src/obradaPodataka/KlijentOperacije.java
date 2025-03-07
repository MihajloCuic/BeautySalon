package obradaPodataka;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entiteti.KarticaLojalnosti;
import entiteti.Klijent;
import entiteti.Tretman;
import entiteti.ZakazanTretman;
import entiteti.Zaposleni;
import util.Stanje;

public class KlijentOperacije {
	private final String KLIJENTI_PUTANJA = "datoteke/klijenti.csv";
	private List<Klijent> sviKlijenti;
	
	public KlijentOperacije(List<Klijent> sviKlijenti) {
		this.sviKlijenti = sviKlijenti;
	}
	
	public void ispisiKlijente() {
		for(Klijent klijent: sviKlijenti) {
			System.out.println(klijent);
		}
	}
	
	public List<Klijent> dodajKlijenta(String korisnickoIme, String lozinka, String ime, String prezime,
										String pol, String telefon, String adresa, double potrosenIznos, boolean pravoNaPopust) throws Exception {
		for(Klijent k: sviKlijenti) {
			if(k.getKorisnickoIme().equals(korisnickoIme)) {
				throw new Exception("KorisnickoIme");
			}
		}
		
		if(korisnickoIme.equals("") || korisnickoIme == null) {
			throw new Exception("KorisnickoIme");
		}
		if(lozinka.equals("") || lozinka == null) {
			throw new Exception("Lozinka");
		}
		if(ime.equals("") || ime == null) {
			throw new Exception("Ime");
		}
		if(prezime.equals("") || prezime == null) {
			throw new Exception("Prezime");
		}
		if(pol.equals("") || pol == null) {
			throw new Exception("Pol");
		}
		if(telefon.equals("") || telefon == null) {
			throw new Exception("Telefon");
		}
		if(adresa.equals("") || adresa == null) {
			throw new Exception("Adresa");
		}
		if(potrosenIznos < 0) {
			throw new Exception("PotrosenIznos");
		}
		
		KarticaLojalnosti karticaLojalnosti = new KarticaLojalnosti(potrosenIznos, pravoNaPopust);
		Klijent klijent = new Klijent(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, karticaLojalnosti);
		sviKlijenti.add(klijent);
		return sviKlijenti;
	}
	
	public List<Klijent> izmeniKlijenta(String korisnickoIme, String lozinka, String ime, String prezime,
			String pol, String telefon, String adresa, double potrosenIznos, boolean pravoNaPopust) throws Exception {
		boolean izmena = false;
		
		if(korisnickoIme.equals("") || korisnickoIme == null) {
			throw new Exception("KorisnickoIme");
		}
		if(lozinka.equals("") || lozinka == null) {
			throw new Exception("Lozinka");
		}
		if(ime.equals("") || ime == null) {
			throw new Exception("Ime");
		}
		if(prezime.equals("") || prezime == null) {
			throw new Exception("Prezime");
		}
		if(pol.equals("") || pol == null) {
			throw new Exception("Pol");
		}
		if(telefon.equals("") || telefon == null) {
			throw new Exception("Telefon");
		}
		if(adresa.equals("") || adresa == null) {
			throw new Exception("Adresa");
		}
		if(potrosenIznos < 0) {
			throw new Exception("PotrosenIznos");
		}

		KarticaLojalnosti karticaLojalnosti = new KarticaLojalnosti(potrosenIznos, pravoNaPopust);
		for(Klijent k: sviKlijenti) {
			if(k.getKorisnickoIme().equals(korisnickoIme)) {
				k.setLozinka(lozinka);
				k.setIme(ime);
				k.setPrezime(prezime);
				k.setPol(pol);
				k.setTelefon(telefon);
				k.setAdresa(adresa);
				k.setKarticaLojalnosti(karticaLojalnosti);
				
				izmena = true;
				break;
			}
		}

		if(izmena == false) {
			throw new Exception("Greska, nije doslo do izmene korisnika");
		}

		return sviKlijenti;
	}
	
	public List<Klijent> obrisiKlijenta(String korisnickoIme) {
		boolean obrisan = false;
		
		for(Klijent k: sviKlijenti) {
			if(k.getKorisnickoIme().equals(korisnickoIme)) {
				sviKlijenti.remove(k);
				
				obrisan = true;
				break;
			}
		}
		
		if(obrisan == false) {
			return null;
		}
		
		return sviKlijenti;
	}
	
	public List<Klijent> ucitajKlijenteIzFajl() throws IOException {
		List<String[]> ucitaniKlijenti = CSVReaderWriter.read(KLIJENTI_PUTANJA);
		List<Klijent> sviKlijenti = new ArrayList<>();
		
		for(String[] red: ucitaniKlijenti) {
			String korisnickoIme = red[0];
			String lozinka = red[1];
			String ime = red[2];
			String prezime = red[3];
			String pol = red[4];
			String telefon = red[5];
			String adresa = red[6];
			KarticaLojalnosti karticaLojalnosti = new KarticaLojalnosti(Double.parseDouble(red[8]), Boolean.parseBoolean(red[9]));
			
			Klijent klijent = new Klijent(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, karticaLojalnosti);
			sviKlijenti.add(klijent);
		}
		UcitajPodatke.setSviKlijenti(sviKlijenti);
		return sviKlijenti;
	}
	
	public void sacuvajKlijenteFajl() throws IOException {
		List<String[]> sviKlijentiFajl = new ArrayList<>();
		
		for(Klijent klijent: sviKlijenti) {
			String[] temp = new String[10];
			temp[0] = klijent.getKorisnickoIme();
			temp[1] = klijent.getLozinka();
			temp[2] = klijent.getIme();
			temp[3] = klijent.getPrezime();
			temp[4] = klijent.getPol();
			temp[5] = klijent.getTelefon();
			temp[6] = klijent.getAdresa();
			temp[7] = klijent.getUloga();
			temp[8] = Double.toString(klijent.getKarticaLojalnosti().getPotrosenIznos());
			temp[9] = Boolean.toString(klijent.getKarticaLojalnosti().getPravoNaPopust());
			
			sviKlijentiFajl.add(temp);
		}
		
		CSVReaderWriter.write(KLIJENTI_PUTANJA, sviKlijentiFajl);
	}
	
	public Tretman pronadjiTretman(int idTretmana, List<Tretman> sviTretmani) {
		for(Tretman tretman: sviTretmani) {
			if(tretman.getId() == idTretmana) {
				return tretman;
			}
		}
		return null;
	}
	
	public Klijent pronadjiKlijenta(String klijentKI) {
		for(Klijent klijent: sviKlijenti) {
			if(klijent.getKorisnickoIme().equals(klijentKI)) {
				return klijent;
			}
		}
		return null;
	}
	
	public Zaposleni pronadjiZaposlenog(String zaposleniID, List<Zaposleni> sviZaposleni) {
		for(Zaposleni zaposleni: sviZaposleni) {
			if(zaposleni.getKorisnickoIme().equals(zaposleniID)) {
				return zaposleni;
			}
		}
		return null;
	}
	
	public List<ZakazanTretman> zakaziTretman(List<ZakazanTretman> sviZakazaniTretmani, int idTretmana, List<Tretman> sviTretmani,
												String datumTretmana, String vremeTretmana, String kozmeticarKI, List<Zaposleni> sviZaposleni,
												String klijentKI, String recepcionerKI) throws Exception {
		Tretman tretman = pronadjiTretman(idTretmana, sviTretmani);
		Zaposleni kozmeticar = pronadjiZaposlenog(kozmeticarKI, sviZaposleni);
		Zaposleni recepcioner = pronadjiZaposlenog(recepcionerKI, sviZaposleni);
		Klijent klijent = pronadjiKlijenta(klijentKI);
		
		DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		DateTimeFormatter vremeFormat = DateTimeFormatter.ofPattern("HH:mm");
		LocalDate datum = LocalDate.parse(datumTretmana, datumFormat);
		LocalTime vreme = LocalTime.parse(vremeTretmana, vremeFormat);
		
		if(kozmeticar == null) {
			for(Zaposleni zaposleni: sviZaposleni) {
				if(zaposleni.getUloga().uloga == 2) {
					boolean poklapanje = false;
					for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
						if(zakazanTretman.getKozmeticar().getKorisnickoIme().equals(zaposleni.getKorisnickoIme()) && zakazanTretman.getDatumTretmana().compareTo(datum) == 0 && zakazanTretman.getVremeTretmana().compareTo(vreme) == 0) {
							poklapanje = true;
							break;
						}
					}
					if(!poklapanje) {
						kozmeticar = zaposleni;
						break;
					}
				}
			}
		}
		
		ZakazanTretmanOperacije zakazanTretmanOperacije = new ZakazanTretmanOperacije(sviZakazaniTretmani);
		sviZakazaniTretmani = zakazanTretmanOperacije.dodajZakazanTretman(tretman, datum, vreme, kozmeticar, klijent, recepcioner);
		
		double potrosenIznos = klijent.getKarticaLojalnosti().getPotrosenIznos();
		klijent.getKarticaLojalnosti().setPotrosenIznos(potrosenIznos + tretman.getCena());
		sacuvajKlijenteFajl();
		return sviZakazaniTretmani;
	}
	
	public List<ZakazanTretman> otkaziTretman(List<ZakazanTretman> sviZakazaniTretmani, int zakazanTretmanID) throws Exception {
		for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
			if(zakazanTretman.getId() == zakazanTretmanID) {
				if(zakazanTretman.getStanje().stanje != 0) {
					break;
				}
				ZakazanTretmanOperacije zakazanTretmanOperacije = new ZakazanTretmanOperacije(sviZakazaniTretmani);
				sviZakazaniTretmani = zakazanTretmanOperacije.izmeniZakazanTretman(zakazanTretmanID, zakazanTretman.getTretman(), zakazanTretman.getCena(), zakazanTretman.getDatumTretmana(), zakazanTretman.getVremeTretmana(), zakazanTretman.getKozmeticar(), Stanje.OTKAZAOKLIJENT, zakazanTretman.getKlijent(), zakazanTretman.getRecepcioner());
			
				double potrosenIznos = zakazanTretman.getKlijent().getKarticaLojalnosti().getPotrosenIznos();
				potrosenIznos -= zakazanTretman.getTretman().getCena()*0.9;
				zakazanTretman.setCena(zakazanTretman.getCena()*0.1);
				zakazanTretman.getKlijent().getKarticaLojalnosti().setPotrosenIznos(potrosenIznos);
				sacuvajKlijenteFajl();
				return sviZakazaniTretmani;
			}
		}
		throw new Exception("Greska, tretman nije pronadjen");
	}
	
	public List<ZakazanTretman> ispisiZakazaneTretmaneKlijenta(List<ZakazanTretman> sviZakazaniTretmani, String klijentKI) {
		List<ZakazanTretman> sviZakazaniTretmaniKlijenta = new ArrayList<>();
		if(sviZakazaniTretmani == null) {
			return null;
		}
		
		for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
			if(zakazanTretman.getKlijent().getKorisnickoIme().equals(klijentKI)) {
				sviZakazaniTretmaniKlijenta.add(zakazanTretman);
			}
		}
		return sviZakazaniTretmaniKlijenta;
	}
}
