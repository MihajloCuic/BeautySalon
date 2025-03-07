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
import obradaPodataka.ZakazanTretmanOperacije;
import util.Stanje;

class ZakazanTretmanTest {
	public static List<ZakazanTretman> sviZakazaniTretmani;
	public static ZakazanTretman zakazanTretman;
	public static ZakazanTretmanOperacije zakazanTretmanOperacije;
	public static LocalDate datumTretmana;
	public static LocalTime vremeTretmana;
	public static Zaposleni kozmeticar;
	public static Klijent klijent;
	public static Tretman tretman;
	public static DateTimeFormatter formatterDate;
	public static DateTimeFormatter formatterTime;
	
	
	@BeforeEach
	void init() {
		sviZakazaniTretmani = new ArrayList<>();
		tretman = new Tretman(0, "gel lak", "45", 1500, "manikir");
		formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		LocalDate datum = LocalDate.parse("16.07.2023.", formatterDate);
		formatterTime = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime vreme = LocalTime.parse("14:00", formatterTime);
		kozmeticar = new Zaposleni("kozmeticar", null, null, null, null, null, null, null, null, 0, 0, 0, null);
		KarticaLojalnosti karticaLojalnosti = new KarticaLojalnosti(0, false);
		klijent = new Klijent("klijent", null, null, null, null, null, null, karticaLojalnosti);
		zakazanTretman = new ZakazanTretman(0, tretman, 1500, datum, vreme, kozmeticar, Stanje.ZAKAZAN, klijent, null);
		sviZakazaniTretmani.add(zakazanTretman);
		zakazanTretmanOperacije = new ZakazanTretmanOperacije(sviZakazaniTretmani);
		
		datumTretmana = LocalDate.parse("17.07.2023.", formatterDate);
		vremeTretmana = LocalTime.parse("13:00", formatterTime);
	}
	
	@Test
	void testDodajZakazanTretmanvalidan() {
		List<ZakazanTretman> sviZakazaniTretmaniZakazan = new ArrayList<>();
		sviZakazaniTretmaniZakazan.addAll(sviZakazaniTretmani);
		try {
			zakazanTretmanOperacije.dodajZakazanTretman(tretman, datumTretmana, vremeTretmana, kozmeticar, klijent, null);
		} catch (Exception e) {
			sviZakazaniTretmaniZakazan = null;
			sviZakazaniTretmani = null;
		}
		
		assertNotEquals(sviZakazaniTretmaniZakazan, sviZakazaniTretmani);
	}
	
	@Test
	void testDodajZakazanTretmanNijeValidanTermin() {
		LocalDate datum = LocalDate.parse("16.07.2023.", formatterDate);
		LocalTime vreme = LocalTime.parse("14:00", formatterTime);
		assertThrows(Exception.class,
				() -> {
					zakazanTretmanOperacije.dodajZakazanTretman(tretman, datum, vreme, kozmeticar, klijent, null);
				});
	}

	@Test
	void testIzmeniZakazanTretman() {
		List<ZakazanTretman> sviZakazaniTretmaniIzmena = new ArrayList<>();
		LocalDate datum = LocalDate.parse("16.07.2023.", formatterDate);
		LocalTime vreme = LocalTime.parse("14:00", formatterTime);
		ZakazanTretman zakazanTret = new ZakazanTretman(0, tretman, 1500, datum, vreme, kozmeticar, Stanje.ZAKAZAN, klijent, null);
		sviZakazaniTretmaniIzmena.add(zakazanTret);
		try {
			zakazanTretmanOperacije.izmeniZakazanTretman(0, tretman, 1500, datumTretmana, vremeTretmana, kozmeticar, Stanje.ZAKAZAN, klijent, null);
		}catch(Exception e) {
			sviZakazaniTretmaniIzmena = null;
			sviZakazaniTretmani = null;
		}
		
		assertNotEquals(sviZakazaniTretmaniIzmena, sviZakazaniTretmani);
	}

	@Test
	void testObrisiZakazanTretman() {
		List<ZakazanTretman> sviZakazaniTretmaniObrisan = new ArrayList<>();
		sviZakazaniTretmaniObrisan.addAll(sviZakazaniTretmani);
		zakazanTretmanOperacije.obrisiZakazanTretman(0);
		assertNotEquals(sviZakazaniTretmaniObrisan, sviZakazaniTretmani);
	}

}
