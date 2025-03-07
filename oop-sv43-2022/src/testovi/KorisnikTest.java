package testovi;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entiteti.Klijent;
import entiteti.Zaposleni;
import obradaPodataka.KorisnikOperacije;
import util.StrucnaSprema;
import util.Uloga;

class KorisnikTest {
	public static KorisnikOperacije korisnikOperacije = new KorisnikOperacije();
	public static List<Klijent> sviKlijenti;
	public static List<Zaposleni> sviZaposleni;
	public static Klijent klijent;
	public static Zaposleni zaposleni;
	public static Object korisnik;
	
	@BeforeEach
	void init() {
		sviKlijenti = new ArrayList<>();
		sviZaposleni = new ArrayList<>();
		
		klijent = new Klijent("peraPeric", "peraPeric", "pera", "peric", "muski", "123456789", "negde", null);
		zaposleni = new Zaposleni("mirkoMirkovic", "mirkoMirkovic", "mirko", "mirkovic", "muski", "123456789", "daleko", Uloga.MENADZER, StrucnaSprema.VISA, 10, 0, 0, null);
		
		sviKlijenti.add(klijent);
		sviZaposleni.add(zaposleni);
	}
	
	@Test
	void testLoginZaposleniValidan() {
		korisnik = korisnikOperacije.login(sviKlijenti, sviZaposleni, "peraPeric", "peraPeric");
		assertNotNull(korisnik);
	}
	
	@Test
	void testLoginZaposleniNijeValidan() {
		korisnik = korisnikOperacije.login(sviKlijenti, sviZaposleni, "peraPeric", "perica");
		assertNull(korisnik);
	}
	
	@Test
	void testLoginKlijentValidan() {
		korisnik = korisnikOperacije.login(sviKlijenti, sviZaposleni, "mirkoMirkovic", "mirkoMirkovic");
		assertNotNull(korisnik);
	}
	
	@Test
	void testLoginKlijentNijeValidan() {
		korisnik = korisnikOperacije.login(sviKlijenti, sviZaposleni, "mirkoMirkovic", "mirko");
		assertNull(korisnik);
	}
}
