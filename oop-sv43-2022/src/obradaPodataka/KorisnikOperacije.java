package obradaPodataka;

import java.util.List;

import entiteti.Klijent;
import entiteti.Korisnik;
import entiteti.Zaposleni;

public class KorisnikOperacije {
	public Korisnik login(List<Klijent> sviKlijenti, List<Zaposleni> sviZaposleni, String korisnickoIme, String lozinka) {
		for(Zaposleni zaposleni: sviZaposleni) {
			if(!zaposleni.getKorisnickoIme().equals(korisnickoIme)) {
				continue;
			}
			if(!zaposleni.getLozinka().equals(lozinka)) {
				continue;
			}
			return zaposleni;
		}
		
		for(Klijent klijent: sviKlijenti) {
			if(!klijent.getKorisnickoIme().equals(korisnickoIme)) {
				continue;
			}
			if(!klijent.getLozinka().equals(lozinka)) {
				continue;
			}
			return klijent;
		}
		
		return null;
	}
}
