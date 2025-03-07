package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entiteti.Klijent;
import entiteti.TipTretmana;
import entiteti.Tretman;
import entiteti.ZakazanTretman;
import entiteti.Zaposleni;
import gui.Login;
import obradaPodataka.KlijentOperacije;
import obradaPodataka.KozmetickiSalonOperacije;
import obradaPodataka.TipTretmanaOperacije;
import obradaPodataka.TretmanOperacije;
import obradaPodataka.ZakazanTretmanOperacije;
import obradaPodataka.ZaposleniOperacije;

public class Main {	
	public static void main(String[] args) {
		List<Klijent> sviKlijenti = new ArrayList<>();
		List<Zaposleni> sviZaposleni = new ArrayList<>();
		List<TipTretmana> sviTipoviTretmana = new ArrayList<>();
		List<Tretman> sviTretmani = new ArrayList<>();
		List<ZakazanTretman> sviZakazaniTretmani = new ArrayList<>();
		
		KozmetickiSalonOperacije kozmetickiSalonOperacije = new KozmetickiSalonOperacije();	
		ZakazanTretmanOperacije zakazanKozmetickiTretmanOp = new ZakazanTretmanOperacije(sviZakazaniTretmani);
		ZaposleniOperacije zaposleniOperacije = new ZaposleniOperacije(sviZaposleni);
		KlijentOperacije klijentOperacije = new KlijentOperacije(sviKlijenti);
		TretmanOperacije tretmanOperacije = new TretmanOperacije(sviTretmani);
		TipTretmanaOperacije tipTretmanaOperacije = new TipTretmanaOperacije(sviTipoviTretmana);
		
		try {
			sviKlijenti = klijentOperacije.ucitajKlijenteIzFajl();
			sviTipoviTretmana = tipTretmanaOperacije.ucitajTipoveTretmanaIzFajla();
			sviTretmani = tretmanOperacije.ucitajTretmaneIzFajla();
			sviZaposleni = zaposleniOperacije.ucitajZaposleneIzFajla();
			sviZakazaniTretmani = zakazanKozmetickiTretmanOp.ucitajZakazaneTretmaneIzFajla();
			kozmetickiSalonOperacije.ucitajKozmetickiSalonIzFajla();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Login login = new Login();
	}
}
