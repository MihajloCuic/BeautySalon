package testovi;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entiteti.Tretman;
import obradaPodataka.TretmanOperacije;

class TretmanTest {
	public static List<Tretman> sviTretmani;
	public static Tretman tretman;
	public static TretmanOperacije tretmanOperacije;
	public static String naziv;
	public static String trajanje;
	public static double cena;
	public static String tipTretmana;
	
	@BeforeEach
	void init() {
		sviTretmani = new ArrayList<>();
		tretman = new Tretman(0, "relaks masaza", "90", 2600, "masaza");
		sviTretmani.add(tretman);
		tretmanOperacije = new TretmanOperacije(sviTretmani);
		naziv = "gel lak";
		trajanje = "45";
		cena = 1500;
		tipTretmana = "manikir";
	}
	
	@Test
	void testDodajTretmanValidan() {
		try {
			sviTretmani = tretmanOperacije.dodajTretman(naziv, trajanje, cena, tipTretmana);
		} catch (Exception e) {
			sviTretmani = null;
		}
		
		assertNotNull(sviTretmani);
	}

	@Test
	void testDodajTretmanNijeValidanNaziv() {
		assertThrows(Exception.class,
				() -> {
					tretmanOperacije.dodajTretman(null, trajanje, cena, tipTretmana);
				});
	}
	
	@Test
	void testDodajTretmanNijeValidanTrajanje() {
		assertThrows(Exception.class,
				() -> {
					tretmanOperacije.dodajTretman(naziv, null, cena, tipTretmana);
				});
	}
	
	@Test
	void testDodajTretmanNijeValidanCena() {
		assertThrows(Exception.class,
				() -> {
					tretmanOperacije.dodajTretman(naziv, trajanje, -23, tipTretmana);
				});
	}
	
	@Test
	void testDodajTretmanNijeValidanTipTretmana() {
		assertThrows(Exception.class,
				() -> {
					tretmanOperacije.dodajTretman(naziv, trajanje, cena, null);
				});
	}
	
	@Test
	void testIzmeniTretmanValidan() {
		try {
			sviTretmani = tretmanOperacije.izmeniTretman(0, "spa pedikir", "80", 2000, "pedikir");
		} catch (Exception e) {
			sviTretmani = null;
		}
		
		assertNotNull(sviTretmani);
	}
	
	@Test
	void testIzmeniTretmanNijeValidanNaziv() {
		assertThrows(Exception.class,
				() -> {
					tretmanOperacije.izmeniTretman(0, "", trajanje, cena, tipTretmana);
				});
	}
	
	@Test
	void testIzmeniTretmanNijeValidanTrajanje() {
		assertThrows(Exception.class,
				() -> {
					tretmanOperacije.izmeniTretman(0, naziv, "", cena, tipTretmana);
				});
	}
	
	@Test
	void testIzmeniTretmanNijeValidanCena() {
		assertThrows(Exception.class,
				() -> {
					tretmanOperacije.izmeniTretman(0, naziv, trajanje, -100, tipTretmana);
				});
	}
	
	@Test
	void testIzmeniTretmanNijeValidanTipTretmana() {
		assertThrows(Exception.class,
				() -> {
					tretmanOperacije.izmeniTretman(0, naziv, trajanje, cena, "");
				});
	}

	@Test
	void testObrisiTretman() {
		List<Tretman> sviTretmaniObrisani = new ArrayList<>();
		sviTretmaniObrisani.addAll(sviTretmani);
		tretmanOperacije.obrisiTretman(0);
		assertNotEquals(sviTretmani, sviTretmaniObrisani);
	}

}
