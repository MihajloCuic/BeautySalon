package testovi;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entiteti.KozmetickiSalon;
import obradaPodataka.KozmetickiSalonOperacije;

class KozmetickiSalonTest {
	public static KozmetickiSalon kozmetickiSalon;
	public static String naziv;
	public static LocalTime pocetakRadnogVremena;
	public static LocalTime krajRadnogVremena;
	public static KozmetickiSalonOperacije kozmetickiSalonOperacije;
	
	@BeforeEach
	void init() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:MM");
		LocalTime pocetakVremena = LocalTime.parse("08:00", formatter);
		LocalTime krajVremena = LocalTime.parse("20:00", formatter);
		kozmetickiSalon = new KozmetickiSalon("ime salona", pocetakVremena, krajVremena, 10000);
		naziv = "kozmeticki salon";
		String pocetak = "07:00";
		String kraj = "16:00";
		pocetakRadnogVremena = LocalTime.parse(pocetak, formatter);
		krajRadnogVremena = LocalTime.parse(kraj, formatter);
		kozmetickiSalonOperacije = new KozmetickiSalonOperacije();
	}
	
	@Test
	void testIzmeniPodatkeKozmetickogSalonaValidan() {
		try {
			kozmetickiSalon = KozmetickiSalonOperacije.izmeniPodatkeKozmetickogSalona(kozmetickiSalon, naziv, pocetakRadnogVremena, krajRadnogVremena);
		} catch (Exception e) {
			kozmetickiSalon = null;
		}
		
		assertNotNull(kozmetickiSalon);
	}
	
	@Test
	void testIzmeniPodatkeKozmetickogSalonaNijeValidanNaziv() {
		assertThrows(Exception.class, 
				() -> {
					KozmetickiSalonOperacije.izmeniPodatkeKozmetickogSalona(kozmetickiSalon, null, pocetakRadnogVremena, krajRadnogVremena);
				});
	}
	
	@Test
	void testIzmeniPodatkeKozmetickogSalonaNijeValidanPocetakRadnogVremena() {
		assertThrows(Exception.class, 
				() -> {
					KozmetickiSalonOperacije.izmeniPodatkeKozmetickogSalona(kozmetickiSalon, naziv, null, krajRadnogVremena);
				});
	}
	
	@Test
	void testIzmeniPodatkeKozmetickogSalonaNijeValidanKrajRadnogVremena() {
		assertThrows(Exception.class, 
				() -> {
					KozmetickiSalonOperacije.izmeniPodatkeKozmetickogSalona(kozmetickiSalon, naziv, pocetakRadnogVremena, null);
				});
	}

}
