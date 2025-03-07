package obradaPodataka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entiteti.Tretman;

public class TretmanOperacije {
	private final String TRETMANI_PUTANJA = "datoteke/tretmani.csv";
	private List<Tretman> sviTretmani;
	
	public TretmanOperacije(List<Tretman> sviTretmani) {
		this.sviTretmani = sviTretmani;
	}
	
	public int dodeliId() {
		int poslednjiId;
		if(sviTretmani.size() != 0) {
			poslednjiId = sviTretmani.get(sviTretmani.size() - 1).getId();
		}else {
			poslednjiId = -1;
		}
		
		return poslednjiId + 1;
	}
	
	public void ispisiTretmane() {
		for(Tretman tretman: sviTretmani) {
			System.out.println(tretman);
		}
	}
	
	public List<Tretman> dodajTretman(String naziv, String trajanjeTretmana ,double cena, String tipTretmana) throws Exception{
		int id = dodeliId();
		
		for(Tretman tretman: sviTretmani) {
			if(tretman.getId() == id) {
				throw new Exception("Greska, vec postoji tretman sa zadatim id-jem");
			}
		}
		if(naziv.equals("") || naziv == null) {
			throw new Exception("Greska, prosledjeno je prazno polje korisnicko ime");
		}
		if(cena <= 0) {
			throw new Exception("Greska, cena tretmana ne moze biti manja ili jednaka nuli");
		}
		if(trajanjeTretmana.equals("") || trajanjeTretmana == null) {
			throw new Exception("Greska, trajanje tretmana ne moze biti prazno polje");
		}
		if(tipTretmana.equals("") || tipTretmana == null) {
			throw new Exception("Greska, tip tretmana ne moze biti prazno polje");
		}
		
		Tretman tretman = new Tretman(id, naziv, trajanjeTretmana, cena, tipTretmana);
		sviTretmani.add(tretman);
		return sviTretmani;
	}
	
	public List<Tretman> izmeniTretman(int id, String naziv, String trajanjeTretmana, double cena, String tipTretmana) throws Exception{
		boolean izmena = false;
		
		if(naziv.equals("") || naziv == null) {
			throw new Exception("Greska, prosledjeno je prazno polje korisnicko ime");
		}
		if(cena <= 0) {
			throw new Exception("Greska, cena tretmana ne moze biti manja ili jednaka nuli");
		}
		if(trajanjeTretmana.equals("") || trajanjeTretmana == null) {
			throw new Exception("Greska, trajanje tretmana ne moze biti prazno polje");
		}
		if(tipTretmana.equals("") || tipTretmana == null) {
			throw new Exception("Greska, tip tretmana ne moze biti prazno polje");
		}
		
		for(Tretman t: sviTretmani) {
			if(t.getId() == id) {
				t.setNaziv(naziv);
				t.setTrajanjeTretmana(trajanjeTretmana);
				t.setCena(cena);
				t.setTipTretmana(tipTretmana);
				
				izmena = true;
				break;
			}
		}
		
		if(izmena == false) {
			return null;
		}
		
		return sviTretmani;
	}
	
	public List<Tretman> obrisiTretman(int id) {
		boolean obrisan = false;
		
		for(Tretman t: sviTretmani) {
			if(t.getId() == id) {
				sviTretmani.remove(t);
				
				obrisan = true;
				break;
			}
		}
		
		if(obrisan == false) {
			return null;
		}
		
		return sviTretmani;
	}
	
	public List<Tretman> ucitajTretmaneIzFajla() throws IOException {
		List<String[]> sviTretmaniFajl = CSVReaderWriter.read(TRETMANI_PUTANJA);
		List<Tretman> sviTretmani = new ArrayList<>();
		
		for(String[] red: sviTretmaniFajl) {
			int id = Integer.parseInt(red[0]);
			String naziv = red[1];
			
			String trajanjeTretmana = red[2];
			double cena = Double.parseDouble(red[3]);
			String tipTretmana = red[4];
			
			Tretman tretman = new Tretman(id, naziv, trajanjeTretmana, cena, tipTretmana);
			sviTretmani.add(tretman);
		}
		UcitajPodatke.setSviTretmani(sviTretmani);
		return sviTretmani;
	}
	
	public void sacuvajTretmaneFajl() throws IOException {
		List<String[]> sviZaposleniFajl = new ArrayList<>();
		
		for(Tretman tretman: sviTretmani) {
			String[] temp = new String[5];
			temp[0] = Integer.toString(tretman.getId());
			temp[1] = tretman.getNaziv();
			temp[2] = tretman.getTrajanjeTretmana();
			temp[3] = Double.toString(tretman.getCena());
			temp[4] = tretman.getTipTretmana();
			
			sviZaposleniFajl.add(temp);
		}
		CSVReaderWriter.write(TRETMANI_PUTANJA, sviZaposleniFajl);
	}
}
