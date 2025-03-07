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
import obradaPodataka.UcitajPodatke;
import obradaPodataka.ZaposleniOperacije;
import util.Stanje;
import util.StrucnaSprema;
import util.Uloga;

class ZaposleniTest {
	public static List<Zaposleni> sviZaposleni;
	public static Zaposleni zaposleni;
	public static ZaposleniOperacije zaposleniOperacije;
	public static String korisnickoIme;
	public static String lozinka;
	public static String ime;
	public static String prezime;
	public static String pol;
	public static String telefon;
	public static String adresa;
	public static List<ZakazanTretman> sviZakazaniTretmani;
	public static Tretman tretman;
	public static LocalTime vreme;
	public static LocalDate datum1;
	public static Zaposleni kozmeticar1;
	public static Zaposleni kozmeticar2;
	public static Klijent klijent1;
	public static List<Klijent> sviKlijenti;
	public static ZakazanTretman zakazanTretman1;
	public static ZakazanTretman zakazanTretman2;
	
	@BeforeEach
	void init() {
		sviZaposleni = new ArrayList<>();
		zaposleni = new Zaposleni("korisnickoIme", "lozinka", "ime", "prezime", "pol", "telefon", "adresa", Uloga.MENADZER, StrucnaSprema.VISA, 5, 60000, 5000);
		sviZaposleni.add(zaposleni);
		zaposleniOperacije = new ZaposleniOperacije(sviZaposleni);
		korisnickoIme = "neko korisnicko ime";
		lozinka = "neka lozinka";
		ime = "neko ime";
		prezime = "neko prezime";
		pol = "zenski";
		telefon = "124456441";
		adresa = "tamo daleko";
		
		tretman = new Tretman(0, "gel lak", "45", 1500, "manikir");
		KarticaLojalnosti kl = new KarticaLojalnosti(0, false);
		klijent1 = new Klijent("klijent1", "klijen1", null, null, null, null, null, kl);
		sviKlijenti = new ArrayList<>();
		sviKlijenti.add(klijent1);
		kozmeticar1 = new Zaposleni(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, Uloga.KOZMETICAR, StrucnaSprema.VISA, 15, 50000, 2000, null);
		kozmeticar2 = new Zaposleni("kozmeticar2", "kozmeticar2", null, null, null, null, null, Uloga.KOZMETICAR, StrucnaSprema.OSNOVNA, 20, 45000, 0, null);
		sviZakazaniTretmani = new ArrayList<>();
		DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
		DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		vreme = LocalTime.parse("13:00", formatterTime);
		datum1 = LocalDate.parse("16.07.2023.", formatterDate);
		LocalDate datum2 = LocalDate.parse("17.07.2023.", formatterDate);
		zakazanTretman1 = new ZakazanTretman(0, tretman, 1500, datum1, vreme, kozmeticar1, Stanje.ZAKAZAN, klijent1, zaposleni);
		zakazanTretman2 = new ZakazanTretman(0, tretman, 1500, datum2, vreme, kozmeticar2, Stanje.ZAKAZAN, klijent1, zaposleni);
		sviZakazaniTretmani.add(zakazanTretman1);
		sviZakazaniTretmani.add(zakazanTretman2);
	}

	@Test
	void testDodajZaposlenogValidan() {
		List<Zaposleni> sviZaposleniDodan = new ArrayList<>();
		sviZaposleniDodan.addAll(sviZaposleni);
		try {
			zaposleniOperacije.dodajZaposlenog(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, Uloga.RECEPCIONER, StrucnaSprema.FAKULTET, 15, null);
		}catch(Exception e) {
			sviZaposleniDodan = null;
			sviZaposleni = null;
		}
		
		assertNotEquals(sviZaposleniDodan, sviZaposleni);
	}
	
	@Test
	void testIzmeniZaposlenogNijeValidanLozinka() {
		assertThrows(Exception.class,
				() -> {
					zaposleniOperacije.izmeniZaposlenog(korisnickoIme, null, ime, prezime, pol, telefon, adresa, Uloga.KOZMETICAR, StrucnaSprema.VISA, 5, 60000, 5000, null);
				});
	}
	
	@Test
	void testIzmeniZaposlenogNijeValidanIme() {
		assertThrows(Exception.class,
				() -> {
					zaposleniOperacije.izmeniZaposlenog(korisnickoIme, lozinka, null, prezime, pol, telefon, adresa, Uloga.KOZMETICAR, StrucnaSprema.VISA, 5, 60000, 5000, null);
				});
	}
	
	@Test
	void testIzmeniZaposlenogNijeValidanPrezime() {
		assertThrows(Exception.class,
				() -> {
					zaposleniOperacije.izmeniZaposlenog(korisnickoIme, lozinka, ime, null, pol, telefon, adresa, Uloga.KOZMETICAR, StrucnaSprema.VISA, 5, 60000, 5000, null);
				});
	}
	
	@Test
	void testIzmeniZaposlenogNijeValidanPol() {
		assertThrows(Exception.class,
				() -> {
					zaposleniOperacije.izmeniZaposlenog(korisnickoIme, lozinka, ime, prezime, null, telefon, adresa, Uloga.KOZMETICAR, StrucnaSprema.VISA, 5, 60000, 5000, null);
				});
	}
	
	@Test
	void testIzmeniZaposlenogNijeValidanTelefon() {
		assertThrows(Exception.class,
				() -> {
					zaposleniOperacije.izmeniZaposlenog(korisnickoIme, lozinka, ime, prezime, pol, null, adresa, Uloga.KOZMETICAR, StrucnaSprema.VISA, 5, 60000, 5000, null);
				});
	}
	
	@Test
	void testIzmeniZaposlenogNijeValidanAdresa() {
		assertThrows(Exception.class,
				() -> {
					zaposleniOperacije.izmeniZaposlenog(korisnickoIme, lozinka, ime, prezime, pol, telefon, null, Uloga.KOZMETICAR, StrucnaSprema.VISA, 5, 60000, 5000, null);
				});
	}
	
	@Test
	void testIzmeniZaposlenogNijeValidanUloga() {
		assertThrows(Exception.class,
				() -> {
					zaposleniOperacije.izmeniZaposlenog(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, Uloga.valueOf("NESTO"), StrucnaSprema.VISA, 5, 60000, 5000, null);
				});
	}
	
	@Test
	void testIzmeniZaposlenogNijeValidanGodineStaza() {
		assertThrows(Exception.class,
				() -> {
					zaposleniOperacije.izmeniZaposlenog(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, Uloga.KOZMETICAR, StrucnaSprema.VISA, -1, 60000, 5000, null);
				});
	}
	
	@Test
	void testIzmeniZaposlenogNijeValidanPlata() {
		assertThrows(Exception.class,
				() -> {
					zaposleniOperacije.izmeniZaposlenog(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, Uloga.KOZMETICAR, StrucnaSprema.VISA, 5, 0, 5000, null);
				});
	}
	
	@Test
	void testIzmeniZaposlenogNijeValidanBonus() {
		assertThrows(Exception.class,
				() -> {
					zaposleniOperacije.izmeniZaposlenog(korisnickoIme, lozinka, ime, prezime, pol, telefon, adresa, Uloga.KOZMETICAR, StrucnaSprema.VISA, 5, 0, -5000, null);
				});
	}

	@Test
	void testIzmeniZaposlenog() {
		List<Zaposleni> sviZaposleniIzmena = new ArrayList<>();
		Zaposleni zap = new Zaposleni("korisnickoIme", "lozinka", "ime", "prezime", "pol", "telefon", "adresa", Uloga.MENADZER, StrucnaSprema.VISA, 5, 60000, 5000, null);
		sviZaposleniIzmena.add(zap);
		try {
			zaposleniOperacije.izmeniZaposlenog(zaposleni.getKorisnickoIme(), lozinka, ime, prezime, pol, telefon, adresa, Uloga.MENADZER, StrucnaSprema.VISA, 5, 60000, 5000, null);
		} catch (Exception e) {
			sviZaposleniIzmena = null;
			sviZaposleni = null;
		}
		
		assertNotEquals(sviZaposleniIzmena, sviZaposleni);
	}

	@Test
	void testObrisiZaposlenog() {
		List<Zaposleni> sviZaposleniObrisan = new ArrayList<>();
		sviZaposleniObrisan.addAll(sviZaposleni);
		zaposleniOperacije.obrisiZaposlenog(zaposleni.getKorisnickoIme());
		
		assertNotEquals(sviZaposleniObrisan, sviZaposleni);
	}

	@Test
	void testZakaziTretman() {
		List<ZakazanTretman> sviZakazaniTretmaniZakazan = new ArrayList<>();
		sviZakazaniTretmaniZakazan.addAll(sviZakazaniTretmani);
		try {
			sviZakazaniTretmaniZakazan = zaposleniOperacije.zakaziTretman(sviZakazaniTretmani, 0, UcitajPodatke.getSviTretmani(), "21.07.2023.", "16:00", kozmeticar1.getKorisnickoIme(), klijent1.getKorisnickoIme(), sviKlijenti, zaposleni.getKorisnickoIme());
		} catch (Exception e) {
			sviZakazaniTretmaniZakazan = null;
		}
		
		assertNotEquals(sviZakazaniTretmani, sviZakazaniTretmaniZakazan);
	}

	@Test
	void testOtkaziTretman() {
		List<ZakazanTretman> sviZakazaniTretmaniOtkazan = new ArrayList<>();
		ZakazanTretman zakazanTretman = new ZakazanTretman(0, tretman, 1500, datum1, vreme, kozmeticar1, Stanje.ZAKAZAN, klijent1, zaposleni);
		sviZakazaniTretmaniOtkazan.add(zakazanTretman);
		sviZakazaniTretmaniOtkazan.add(zakazanTretman2);
		try {
			sviZakazaniTretmani = zaposleniOperacije.otkaziTretman(sviZakazaniTretmani, 0);
		} catch (Exception e) {
			sviZakazaniTretmani = null;
		}
		
		assertNotEquals(sviZakazaniTretmani, sviZakazaniTretmaniOtkazan);
	}

	@Test
	void testPrikaziRasporedKozmeticara() {
		List<ZakazanTretman> terminiKozmeticara = new ArrayList<>();
		for(ZakazanTretman zakazanTretman: sviZakazaniTretmani) {
			if(zakazanTretman.getKozmeticar().getKorisnickoIme().equals(kozmeticar1.getKorisnickoIme())) {
				terminiKozmeticara.add(zakazanTretman);
			}
		}
		
		sviZakazaniTretmani = zaposleniOperacije.prikaziRasporedKozmeticara(sviZakazaniTretmani, kozmeticar1.getKorisnickoIme());	
		assertEquals(sviZakazaniTretmani, terminiKozmeticara);
	}
}
