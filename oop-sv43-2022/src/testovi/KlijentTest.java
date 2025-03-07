package testovi;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entiteti.KarticaLojalnosti;
import entiteti.Klijent;
import entiteti.Tretman;
import entiteti.ZakazanTretman;
import entiteti.Zaposleni;
import obradaPodataka.KlijentOperacije;
import obradaPodataka.UcitajPodatke;
import util.Stanje;
import util.Uloga;

class KlijentTest {
	public static List<Klijent> sviKlijenti;
	public static Klijent klijent1;
	public static Klijent klijent2;
	public static KlijentOperacije klijentOperacije;
	public static String korisnickoIme;
	public static String lozinka;
	public static String ime;
	public static String prezime;
	public static String pol;
	public static String telefon;
	public static String adresa;
	public static double potrosenIznos;
	public static boolean pravoNaPopust;
	public static List<ZakazanTretman> sviZakazaniTretmani;
	public static ZakazanTretman zakazanTretman1;
	public static ZakazanTretman zakazanTretman2;
	public static Tretman tretman;
	public static LocalTime vreme;
	public static LocalDate datum1;
	public static LocalDate datum2;
	public static Zaposleni kozmeticar;
	
	@BeforeEach
	void init() {
		sviKlijenti = new ArrayList<>();
		KarticaLojalnosti karticaLojalnosti = new KarticaLojalnosti(0, false);
		klijent1 = new Klijent("korisnickoIme1", "lozinka1", "ime1", "prezime1", "pol1", "telefon1", "adresa1", karticaLojalnosti);
		klijent2 = new Klijent("korisnickoIme2", "lozinka2", "ime2", "prezime2", "pol2", "telefon2", "adresa2", karticaLojalnosti);
		sviKlijenti.add(klijent1);
		klijentOperacije = new KlijentOperacije(sviKlijenti);
		
		korisnickoIme = "nekoKorisnickoIme";
		lozinka = "nekaLozinka";
		ime = "nekoIme";
		prezime = "nekoPrezime";
		pol = "zenski";
		telefon = "342423";
		adresa = "nedge";
		potrosenIznos = 100;
		pravoNaPopust = false;
		
		tretman = new Tretman(0, "gel lak", "45", 1500, "manikir");
		DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
		
		datum1 = LocalDate.parse("16.07.2023.", formatterDate);
		vreme = LocalTime.parse("13:00", formatterTime);
		datum2 = LocalDate.parse("17.07.2023.", formatterDate);
 		sviZakazaniTretmani = new ArrayList<>();
 		kozmeticar = new Zaposleni("kozmeticar", "kozmeticar", "kozmeticar", "kozmeticar", null, null, null, Uloga.KOZMETICAR, null, 0, 0, 0);
		zakazanTretman1 = new ZakazanTretman(0, tretman, 1500, datum1, vreme, kozmeticar, Stanje.ZAKAZAN, klijent1, null);
		zakazanTretman2 = new ZakazanTretman(1, tretman, 1500, datum2, vreme, kozmeticar, Stanje.ZAKAZAN, klijent2, null);
		sviZakazaniTretmani.add(zakazanTretman1);
		sviZakazaniTretmani.add(zakazanTretman2);
	}
	
	@Test
	void testDodajKlijentaValidan() {
		try {
			sviKlijenti = klijentOperacije.dodajKlijenta(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, potrosenIznos, pravoNaPopust);
		}catch(Exception e) {
			sviKlijenti = null;
		}
		
		assertNotNull(sviKlijenti);
	}
	
	@Test
	void testDodajKlijentaNijeValidanLozinka() {
		assertThrows(Exception.class, 
				() ->  {
					klijentOperacije.dodajKlijenta(korisnickoIme, null, ime, prezime, pol, telefon, adresa, potrosenIznos, pravoNaPopust);
				});
	}
	
	@Test
	void testDodajKlijentaNijeValidanIme() {
		assertThrows(Exception.class, 
				() ->  {
					klijentOperacije.dodajKlijenta(korisnickoIme, lozinka, null, prezime, pol, telefon, adresa, potrosenIznos, pravoNaPopust);
				});
	}
	
	@Test
	void testDodajKlijentaNijeValidanPrezime() {
		assertThrows(Exception.class, 
				() ->  {
					klijentOperacije.dodajKlijenta(korisnickoIme, lozinka, ime, null, pol, telefon, adresa, potrosenIznos, pravoNaPopust);
				});
	}
	
	@Test
	void testDodajKlijentaNijeValidanPol() {
		assertThrows(Exception.class, 
				() ->  {
					klijentOperacije.dodajKlijenta(korisnickoIme, lozinka, ime, prezime, null, telefon, adresa, potrosenIznos, pravoNaPopust);
				});
	}
	
	@Test
	void testDodajKlijentaNijeValidanTelefon() {
		assertThrows(Exception.class, 
				() ->  {
					klijentOperacije.dodajKlijenta(korisnickoIme, lozinka, ime, prezime, pol, null, adresa, potrosenIznos, pravoNaPopust);
				});
	}
	
	@Test
	void testDodajKlijentaNijeValidanAdresa() {
		assertThrows(Exception.class, 
				() ->  {
					klijentOperacije.dodajKlijenta(korisnickoIme, lozinka, ime, prezime, pol, telefon, null, potrosenIznos, pravoNaPopust);
				});
	}
	
	@Test
	void testDodajKlijentaNijeValidanIznos() {
		assertThrows(Exception.class, 
				() ->  {
					klijentOperacije.dodajKlijenta(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, -1000, pravoNaPopust);
				});
	}
	
	@Test
	void testIzmeniKlijenta() {
		try {
			sviKlijenti = klijentOperacije.izmeniKlijenta(klijent1.getKorisnickoIme(), lozinka, ime, prezime, pol, telefon, adresa, potrosenIznos, pravoNaPopust);
		} catch (Exception e) {
			sviKlijenti = null;
		}
		
		assertNotNull(sviKlijenti);
	}

	@Test
	void testObrisiKlijenta() {
		List<Klijent> sviKlijentiObrisan = new ArrayList<>();
		sviKlijentiObrisan.addAll(sviKlijenti);
		klijentOperacije.obrisiKlijenta(klijent1.getKorisnickoIme());
		assertNotEquals(sviKlijenti, sviKlijentiObrisan);
	}

	@Test
	void testZakaziTretman() {
		List<ZakazanTretman> sviZakazaniTretmaniZakazan = new ArrayList<>();
		try {
			sviZakazaniTretmaniZakazan = klijentOperacije.zakaziTretman(sviZakazaniTretmani, 0, UcitajPodatke.getSviTretmani(), "16.07.2023.", "14:00", "zikaZikic", UcitajPodatke.getSviZaposleni(), klijent1.getKorisnickoIme(), "online");
		}catch(Exception e) {
			sviZakazaniTretmaniZakazan = null;
		}
		
		assertNotEquals(sviZakazaniTretmani, sviZakazaniTretmaniZakazan);
	}

	@Test
	void testOtkaziTretman() {
		List<ZakazanTretman> sviZakazaniTretmaniOtkazan = new ArrayList<>();
		ZakazanTretman zakazanTretman = new ZakazanTretman(0, tretman, 1500, datum1, vreme, kozmeticar, Stanje.ZAKAZAN, klijent1, null);
		sviZakazaniTretmaniOtkazan.add(zakazanTretman);
		sviZakazaniTretmaniOtkazan.add(zakazanTretman2);
		try {
			sviZakazaniTretmani = klijentOperacije.otkaziTretman(sviZakazaniTretmani, 0);
		} catch (Exception e) {
			sviZakazaniTretmani = null;
		}
		
		assertNotEquals(sviZakazaniTretmani, sviZakazaniTretmaniOtkazan);
	}

	@Test
	void testIspisiZakazaneTretmaneKlijenta() {
		List<ZakazanTretman> zakazaniTretmaniKlijenta = new ArrayList<>();
		for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
			if(zakazanTretman.getKlijent().getKorisnickoIme().equals(klijent1.getKorisnickoIme())) {
				zakazaniTretmaniKlijenta.add(zakazanTretman);
			}
		}
		
		sviZakazaniTretmani = klijentOperacije.ispisiZakazaneTretmaneKlijenta(zakazaniTretmaniKlijenta, klijent1.getKorisnickoIme());
		
		assertEquals(sviZakazaniTretmani, zakazaniTretmaniKlijenta);
	}
}
