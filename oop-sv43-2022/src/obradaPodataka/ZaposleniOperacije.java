package obradaPodataka;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import entiteti.Klijent;
import entiteti.TipTretmana;
import entiteti.Tretman;
import entiteti.ZakazanTretman;
import entiteti.Zaposleni;
import util.Stanje;
import util.StrucnaSprema;
import util.Uloga;

public class ZaposleniOperacije {
	private final String ZAPOSLENI_PUTANJA = "datoteke/zaposleni.csv";
	private List<Zaposleni> sviZaposleni;
	
	public ZaposleniOperacije(List<Zaposleni> sviZaposleni) {
		this.sviZaposleni = sviZaposleni;
	}
	
	public void ispisiZaposlene() {
		for(Zaposleni zaposleni: sviZaposleni) {
			System.out.println(zaposleni);
		}
	}
	
	public List<Zaposleni> dodajZaposlenog(String korisnickoIme, String lozinka, String ime, 
													String prezime, String pol, String telefon, String adresa, Uloga uloga, 
													StrucnaSprema nivoStrucneSpreme, int godineStaza, List<String> tretmani) throws Exception {
		if(korisnickoIme.equals("") || korisnickoIme == null) {
			throw new Exception("Prosledjeno prazno polje za korisnicko ime");
		}
		if(lozinka.equals("") || lozinka == null) {
			throw new Exception("Prosledjeno prazno polje za lozinka");
		}
		if(ime.equals("") || ime == null) {
			throw new Exception("Prosledjeno prazno polje za ime");
		}
		if(prezime.equals("") || prezime == null) {
			throw new Exception("Prosledjeno prazno polje za prezime");
		}
		if(pol.equals("") || pol == null) {
			throw new Exception("Prosledjeno prazno polje za pol");
		}
		if(telefon.equals("") || telefon == null) {
			throw new Exception("Prosledjeno prazno polje za telefon");
		}
		if(adresa.equals("") || adresa == null) {
			throw new Exception("Prosledjeno prazno polje za adresa");
		}
		if(uloga.uloga != 1 && uloga.uloga != 2 && uloga.uloga != 3) {
			throw new Exception("Prosledjeno prazno polje za uloga");
		}
		if(godineStaza < 0) {
			throw new Exception("Prosledjena pogresna vrednost za godine staza");
		}
		
		double plata = izracunajPlatu(nivoStrucneSpreme, 0, godineStaza);
		
		if(plata <= 0) {
			throw new Exception("Prosledjena pogresna vrednost za platu");
		}
		
		
		if(uloga.uloga == 2) {
			Zaposleni zaposleni = new Zaposleni(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, uloga, nivoStrucneSpreme, godineStaza, plata, 0, tretmani);
			sviZaposleni.add(zaposleni);
		}else {
			Zaposleni zaposleni = new Zaposleni(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, uloga, nivoStrucneSpreme, godineStaza, plata, 0);
			sviZaposleni.add(zaposleni);
		}
		return sviZaposleni;
	}
	
	public List<Zaposleni> izmeniZaposlenog(String korisnickoIme, String lozinka, String ime, String prezime, String pol, 
											String telefon, String adresa, Uloga uloga, StrucnaSprema nivoStrucneSpreme, 
											int godineStaza, double plata, double bonus, List<String> tretmani) throws Exception {
		boolean izmena = false;
		
		if(korisnickoIme.equals("") || korisnickoIme == null) {
			throw new Exception("Prosledjeno prazno polje za korisnicko ime");
		}
		if(lozinka.equals("") || lozinka == null) {
			throw new Exception("Prosledjeno prazno polje za lozinka");
		}
		if(ime.equals("") || ime == null) {
			throw new Exception("Prosledjeno prazno polje za ime");
		}
		if(prezime.equals("") || prezime == null) {
			throw new Exception("Prosledjeno prazno polje za prezime");
		}
		if(pol.equals("") || pol == null) {
			throw new Exception("Prosledjeno prazno polje za pol");
		}
		if(telefon.equals("") || telefon == null) {
			throw new Exception("Prosledjeno prazno polje za telefon");
		}
		if(adresa.equals("") || adresa == null) {
			throw new Exception("Prosledjeno prazno polje za adresa");
		}
		if(uloga.uloga != 1 && uloga.uloga != 2 && uloga.uloga != 3) {
			throw new Exception("Prosledjeno prazno polje za uloga");
		}
		if(godineStaza < 0) {
			throw new Exception("Prosledjena pogresna vrednost za godine staza");
		}
		if(plata <= 0) {
			throw new Exception("Prosledjena pogresna vrednost za platu");
		}
		if(bonus < 0) {
			throw new Exception("Prosledjena pogresna vrednost za bonus");
		}
		
		for(Zaposleni z: sviZaposleni) {
			if(z.getKorisnickoIme().equals(korisnickoIme)) {
				z.setLozinka(lozinka);
				z.setIme(ime);
				z.setPrezime(prezime);
				z.setPol(pol);
				z.setTelefon(telefon);
				z.setAdresa(adresa);
				z.setUloga(uloga);
				z.setStrucnaSprema(nivoStrucneSpreme);
				z.setGodineStaza(godineStaza);
				z.setPlata(plata);
				z.setBonus(bonus);
				
				if(z.getUloga().uloga == 2) {
					z.setTretman(tretmani);
				}
				
				izmena = true;
				break;
			}
		}
		
		if(izmena == false) {
			throw new Exception("Zaposleni nije izmenjen!");
		}
		return sviZaposleni;
	}
	
	public List<Zaposleni> obrisiZaposlenog(String korisnickoIme) {	
		boolean obrisan = false;
		for(Zaposleni z: sviZaposleni) {
			if(z.getKorisnickoIme().equals(korisnickoIme)) {
				sviZaposleni.remove(z);
				
				obrisan = true;
				break;
			}
		}
		
		if(!obrisan) {
			System.out.println("Zaposleni nije obrisan");
		}
		return sviZaposleni;
	}
	//Upis i ispis iz fajla
	public List<Zaposleni> ucitajZaposleneIzFajla() throws IOException {
		List<String[]> ucitaniZaposleni = CSVReaderWriter.read(ZAPOSLENI_PUTANJA);
		List<Zaposleni> sviZaposleni = new ArrayList<>();
		
		for(String[] red: ucitaniZaposleni) {
			String korisnickoIme = red[0];
			String lozinka = red[1];
			String ime = red[2];
			String prezime = red[3];
			String pol = red[4];
			String telefon = red[5];
			String adresa = red[6];
			Uloga uloga = Uloga.valueOf(red[7]);
			StrucnaSprema nivoStrucneSpreme = StrucnaSprema.valueOf(red[8]);
			int godineStaza = Integer.parseInt(red[9]);
			double plata = Double.parseDouble(red[10]);
			double bonus = Double.parseDouble(red[11]);
			
			if(uloga.uloga == 2) {
				String[] nizTretmana = red[12].split(",");
				List<String> tretmani = new ArrayList<>();
				for(String tretman: nizTretmana) {
					tretmani.add(tretman);
				}
				
				Zaposleni zaposleni = new Zaposleni(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, uloga, nivoStrucneSpreme, godineStaza, plata, bonus, tretmani);
				sviZaposleni.add(zaposleni);
			}else {
				Zaposleni zaposleni = new Zaposleni(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, uloga, nivoStrucneSpreme, godineStaza, plata, bonus);
				sviZaposleni.add(zaposleni);
			}
		}
		UcitajPodatke.setSviZaposleni(sviZaposleni);
		return sviZaposleni;
	}

	public void sacuvajZaposleneFajl() throws IOException {
		List<String[]> sviZaposleniFajl = new ArrayList<>();
		
		for(Zaposleni zaposleni: sviZaposleni) {
			if(zaposleni.getUloga().uloga == 2) {
				String[] temp = new String[13];
				temp[0] = zaposleni.getKorisnickoIme();
				temp[1] = zaposleni.getLozinka();
				temp[2] = zaposleni.getIme();
				temp[3] = zaposleni.getPrezime();
				temp[4] = zaposleni.getPol();
				temp[5] = zaposleni.getTelefon();
				temp[6] = zaposleni.getAdresa();
				temp[7] = zaposleni.getUloga().name();
				temp[8] = zaposleni.getStrucnaSprema().name();
				temp[9] = Integer.toString(zaposleni.getGodineStaza());
				temp[10] = Double.toString(zaposleni.getPlata());
				temp[11] = Double.toString(zaposleni.getBonus());
				if(zaposleni.getTretman() != null) {
					temp[12] = String.join(",", zaposleni.getTretman());
				}
				
				sviZaposleniFajl.add(temp);
			}else {
				String[] temp = new String[12];
				temp[0] = zaposleni.getKorisnickoIme();
				temp[1] = zaposleni.getLozinka();
				temp[2] = zaposleni.getIme();
				temp[3] = zaposleni.getPrezime();
				temp[4] = zaposleni.getPol();
				temp[5] = zaposleni.getTelefon();
				temp[6] = zaposleni.getAdresa();
				temp[7] = zaposleni.getUloga().name();
				temp[8] = zaposleni.getStrucnaSprema().name();
				temp[9] = Integer.toString(zaposleni.getGodineStaza());
				temp[10] = Double.toString(zaposleni.getPlata());
				temp[11] = Double.toString(zaposleni.getBonus());
				
				sviZaposleniFajl.add(temp);
			}
		}
		
		CSVReaderWriter.write(ZAPOSLENI_PUTANJA, sviZaposleniFajl);
	}
	
	public double izracunajPlatu(StrucnaSprema koeficijentStrucneSpreme, double bonus, int godineStaza) {
		int osnovica = 30000;
		return koeficijentStrucneSpreme.strucnaSprema*osnovica + godineStaza*1000 + bonus;
	}
	
	public double obracunajBonusZaMesec(List<ZakazanTretman> sviZakazaniTretmani, Uloga uloga, int mesec, String tipBonusa) {
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
		
		double bonus = 0;
		double zarada = 0;
		if(uloga.uloga == 2) {
			for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
				if(zakazanTretman.getStanje().stanje == 1 && zakazanTretman.getDatumTretmana().compareTo(pocetakMeseca) > 0 && zakazanTretman.getDatumTretmana().compareTo(krajMeseca) < 0) {
					if(tipBonusa.equals("zarada")) {
						zarada += zakazanTretman.getCena();
					}else {
						bonus += 200;
					}
				}
			}
			if(tipBonusa.equals("zarada")) {
				bonus = (zarada/1000) * 100; 
			}
		}else if(uloga.uloga == 3) {
			for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
				if(zakazanTretman.getDatumTretmana().compareTo(pocetakMeseca) > 0 && zakazanTretman.getDatumTretmana().compareTo(krajMeseca) < 0) {
					if(tipBonusa.equals("zarada")) {
						zarada += zakazanTretman.getCena();
					}else {
						bonus += 100;
					}
				}
			}
			if(tipBonusa.equals("zarada")) {
				bonus = (zarada/1000) * 50;
			}
		}else if(uloga.uloga == 1) {
			for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
				zarada += zakazanTretman.getCena();
			}
			if(zarada > 50000) {
				bonus = (zarada/1000) * 100;
			}
		}
		return bonus;
	}
	
	//Pomocne funkcije za pronalazenje trazenih objekata
	public Tretman pronadjiTretman(int tretmanID, List<Tretman> sviTretmani) {
		for(Tretman tretman: sviTretmani) {
			if(tretman.getId() == tretmanID) {
				return tretman;
			}
		}
		return null;
	}
	
	public TipTretmana pronadjiTipTretmana(int tipTretmanaID, List<TipTretmana> sviTipoviTretmana) {
		for(TipTretmana tipTretmana: sviTipoviTretmana) {
			if(tipTretmana.getId() == tipTretmanaID) {
				return tipTretmana;
			}
		}
		return null;
	}
	
	public Klijent pronadjiKlijenta(String klijentKI, List<Klijent> sviKlijenti) {
		for(Klijent klijent: sviKlijenti) {
			if(klijent.getKorisnickoIme().equals(klijentKI)) {
				return klijent;
			}
		}
		return null;
	}
	
	public Zaposleni pronadjiZaposlenog(String zaposleniID) {
		for(Zaposleni zaposleni: sviZaposleni) {
			if(zaposleni.getKorisnickoIme().equals(zaposleniID)) {
				return zaposleni;
			}
		}
		return null;
	}
	
	public List<ZakazanTretman> zakaziTretman(List<ZakazanTretman> sviZakazaniTretmani, int tretmanID, List<Tretman> sviTretmani, 
											String datumTretmana, String vremeTretmana, String kozmeticarKI, String klijentKI, 
											List<Klijent> sviKlijenti, String zaposleniKI) throws Exception {
		Zaposleni zaposleni = pronadjiZaposlenog(zaposleniKI);
		if(zaposleni.getUloga().uloga == 2) {
			throw new Exception("Kozmeticar ne moze da zakazuje tretmane");
		}
		
		Tretman tretman = pronadjiTretman(tretmanID, sviTretmani);
		Klijent klijent = pronadjiKlijenta(klijentKI, sviKlijenti);
		Zaposleni kozmeticar = pronadjiZaposlenog(kozmeticarKI);
		
		DateTimeFormatter datumFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		DateTimeFormatter vremeFormat = DateTimeFormatter.ofPattern("HH:mm");
		LocalDate datum = LocalDate.parse(datumTretmana, datumFormat);
		LocalTime vreme = LocalTime.parse(vremeTretmana, vremeFormat);
		
		if(kozmeticar == null) {
			for(Zaposleni z: sviZaposleni) {
				if(z.getUloga().uloga == 2) {
					boolean poklapanje = false;
					for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
						if(zakazanTretman.getKozmeticar().getKorisnickoIme().equals(zaposleni.getKorisnickoIme()) && zakazanTretman.getDatumTretmana().compareTo(datum) == 0 && zakazanTretman.getVremeTretmana().compareTo(vreme) == 0 && zakazanTretman.getStanje().stanje == 0) {
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
		sviZakazaniTretmani = zakazanTretmanOperacije.dodajZakazanTretman(tretman, datum, vreme, kozmeticar, klijent, kozmeticar);
		
		double potrosenIznos = klijent.getKarticaLojalnosti().getPotrosenIznos();
		klijent.getKarticaLojalnosti().setPotrosenIznos(potrosenIznos + tretman.getCena());
		KlijentOperacije klijentOperacije = new KlijentOperacije(sviKlijenti);
		klijentOperacije.sacuvajKlijenteFajl();
		return sviZakazaniTretmani;
	}
	
	public List<ZakazanTretman> otkaziTretman(List<ZakazanTretman> sviZakazaniTretmani, int zakazanTretmanID) throws Exception{
		for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
			if(zakazanTretman.getId() == zakazanTretmanID) {
				ZakazanTretmanOperacije zakazanTretmanOperacije = new ZakazanTretmanOperacije(sviZakazaniTretmani);
				sviZakazaniTretmani = zakazanTretmanOperacije.izmeniZakazanTretman(zakazanTretmanID, zakazanTretman.getTretman(), zakazanTretman.getCena(), zakazanTretman.getDatumTretmana(), zakazanTretman.getVremeTretmana(), zakazanTretman.getKozmeticar(), Stanje.OTKAZAOSALON, zakazanTretman.getKlijent(), zakazanTretman.getKozmeticar());
				
				double potrosenIznos = zakazanTretman.getKlijent().getKarticaLojalnosti().getPotrosenIznos();
				potrosenIznos -= zakazanTretman.getTretman().getCena();
				zakazanTretman.getKlijent().getKarticaLojalnosti().setPotrosenIznos(potrosenIznos);
				KlijentOperacije klijentOperacije = new KlijentOperacije(UcitajPodatke.getSviKlijenti());
				klijentOperacije.sacuvajKlijenteFajl();
				return sviZakazaniTretmani;
			}
		}
		throw new Exception("Greska, zakazan tretman nije pronadjen");
	}
	
	public List<ZakazanTretman> izvrsiTretman(List<ZakazanTretman> sviZakazaniTretmani, int zakazanTretmanID, String stanje) throws Exception {
		for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
			if(zakazanTretman.getId() == zakazanTretmanID) {
				ZakazanTretmanOperacije zakazanTretmanOperacije = new ZakazanTretmanOperacije(sviZakazaniTretmani);
				sviZakazaniTretmani = zakazanTretmanOperacije.izmeniZakazanTretman(zakazanTretmanID, zakazanTretman.getTretman(), zakazanTretman.getCena(), zakazanTretman.getDatumTretmana(), zakazanTretman.getVremeTretmana(), zakazanTretman.getKozmeticar(), Stanje.valueOf(stanje), zakazanTretman.getKlijent(), zakazanTretman.getRecepcioner());
				return sviZakazaniTretmani;
			}
		}
		throw new Exception("Greska, trazeni tretman nije pronadjen!");
	}
	
	public List<ZakazanTretman> filtrirajZakazaneTretmane(List<ZakazanTretman> sviZakazaniTretmani, int idTretmana, int idTipaTretmana, double minCena, double maxCena) {
		List<ZakazanTretman> filtriraniTretmani = new ArrayList<>();
		Tretman tretman = pronadjiTretman(idTretmana, UcitajPodatke.getSviTretmani());
		TipTretmana tipTretmana = pronadjiTipTretmana(idTipaTretmana, UcitajPodatke.getSviTipoviTretmana());
		for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
			if(zakazanTretman.getTretman().getCena() < minCena || zakazanTretman.getTretman().getCena() > maxCena) {
				continue;
			}
			if(tretman != null && !zakazanTretman.getTretman().getNaziv().equals(tretman.getNaziv())) {
				continue;
			}
			if(tipTretmana != null && !zakazanTretman.getTretman().getTipTretmana().equals(tipTretmana.getNaziv())) {
				continue;
			}
			filtriraniTretmani.add(zakazanTretman);
		}
		return filtriraniTretmani;
	}
	
	public List<ZakazanTretman> prikaziRasporedKozmeticara(List<ZakazanTretman> sviZakazaniTretmani, String kozmeticarKI) {
		List<ZakazanTretman> sviZakazaniTretmaniKozmeticara = new ArrayList<>();
		for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
			if(zakazanTretman.getKozmeticar().getKorisnickoIme().equals(kozmeticarKI)) {
				sviZakazaniTretmaniKozmeticara.add(zakazanTretman);
			}
		}
		return sviZakazaniTretmaniKozmeticara;
	}
}
