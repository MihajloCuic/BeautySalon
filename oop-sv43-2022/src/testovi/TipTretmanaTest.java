package testovi;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entiteti.TipTretmana;
import obradaPodataka.TipTretmanaOperacije;

class TipTretmanaTest {
	public static List<TipTretmana> sviTipoviTretmana;
	public static TipTretmana tipTretmana;
	public static int id;
	public static String nazivTretmana;
	public static TipTretmanaOperacije tipTretmanaOperacije;
	public static String putanja;
	
	@BeforeEach
	void init() {
		sviTipoviTretmana = new ArrayList<>();
		tipTretmana = new TipTretmana(0, "masaza");
		tipTretmanaOperacije = new TipTretmanaOperacije(sviTipoviTretmana);
		nazivTretmana = "manikir";
		id = 0;
	}
	
	@Test
	void testDodajTipTretmanaValidan() {
		try {
			sviTipoviTretmana = tipTretmanaOperacije.dodajTipTretmana(nazivTretmana);
		} catch (Exception e) {
			sviTipoviTretmana = null;
		}
		assertNotNull(sviTipoviTretmana);
	}
	
	@Test
	void testDodajTipTretmanaNijeValidan() {
		assertThrows(Exception.class,
				() -> {
					tipTretmanaOperacije.dodajTipTretmana(null);
				});
	}

	@Test
	void testIzmeniTipTretmanaValidan() {
		sviTipoviTretmana.add(tipTretmana);
		try {
			sviTipoviTretmana = tipTretmanaOperacije.izmeniTipTretmana(0, nazivTretmana);
		}catch(Exception e) {
			sviTipoviTretmana = null;
		}
		assertNotNull(sviTipoviTretmana);
	}
	
	@Test
	void testIzmeniTipTretmanaNijeValidan() {
		sviTipoviTretmana.add(tipTretmana);
		assertThrows(Exception.class,
				() -> {
					tipTretmanaOperacije.izmeniTipTretmana(0, null);
				});
	}

	@Test
	void testObrisiTipTretmana() {
		List<TipTretmana> sviTipoviTretmanaObrisani = new ArrayList<>();
		sviTipoviTretmanaObrisani.addAll(sviTipoviTretmana);
		sviTipoviTretmanaObrisani = tipTretmanaOperacije.obrisiTipTretmana(null, id);
		assertNotEquals(sviTipoviTretmana, sviTipoviTretmanaObrisani);
	}
	
}
