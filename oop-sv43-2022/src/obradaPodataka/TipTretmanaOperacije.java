package obradaPodataka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entiteti.TipTretmana;
import entiteti.Tretman;

public class TipTretmanaOperacije {
	private final String TIP_TRETMANA_PUTANJA = "datoteke/tipoviKozmetickogTretmana.csv";
	private List<TipTretmana> sviTipoviTretmana;
	
	public TipTretmanaOperacije(List<TipTretmana> sviTipoviTretmana) {
		this.sviTipoviTretmana = sviTipoviTretmana;
	}
	
	public int dodeliId() {
		int poslednjiId = -1;
		if(sviTipoviTretmana.size() != 0) {
			poslednjiId = sviTipoviTretmana.get(sviTipoviTretmana.size()-1).getId();
		}
		return poslednjiId + 1;
	}
	
	public void ispisiTipoveTretmana() {
		for(TipTretmana tipTretmana: sviTipoviTretmana) {
			System.out.println(tipTretmana);
		}
	}
	
	public List<TipTretmana> dodajTipTretmana(String naziv) throws Exception{
		int id = dodeliId();
		if(naziv.equals("") || naziv == null) {
			throw new Exception("Greska, prosledjeno je prazno polje naziv");
		}
		for(TipTretmana tipTretmana: sviTipoviTretmana) {
			if(tipTretmana.getId() ==  id) {
				throw new Exception("Greska, vec postoji tip tretmana sa datim id-jem");
			}
		}
		
		TipTretmana tipTretmana = new TipTretmana(id, naziv);
		
		sviTipoviTretmana.add(tipTretmana);
		return sviTipoviTretmana;
	}
	
	public List<TipTretmana> izmeniTipTretmana(int id, String naziv) throws Exception{
		boolean izmena = false;
		
		if(naziv.equals("") || naziv == null) {
			throw new Exception("Greska, prosledjeno je prazno polje naziv");
		}
		
		for(TipTretmana t: sviTipoviTretmana) {
			if(t.getId() == id) {
				t.setNaziv(naziv);
				
				izmena = true;
				break;
			}
		}
		
		if(izmena == false) {
			return null;
		}
		
		return sviTipoviTretmana;
	}
	
	public List<TipTretmana> obrisiTipTretmana(List<Tretman> sviTretmani, int id) {
		boolean obrisan = false;
		
		for(TipTretmana t: sviTipoviTretmana) {
			if(t.getId() == id) {
				for(Tretman tretman: sviTretmani) {
					if(tretman.getTipTretmana().equals(t.getNaziv())) {
						sviTretmani.remove(tretman);
						break;
					}
				}
				sviTipoviTretmana.remove(t);
				
				obrisan = true;
				break;
			}
		}
		
		if(obrisan == false) {
			return null;
		}
		
		return sviTipoviTretmana;
	}
	
	public List<TipTretmana> ucitajTipoveTretmanaIzFajla() throws IOException {
		List<String[]> ucitaniTipoviTretmana = CSVReaderWriter.read(TIP_TRETMANA_PUTANJA);
		List<TipTretmana> sviTipoviTretmana = new ArrayList<>();
		
		for(String[] red: ucitaniTipoviTretmana) {
			int id = Integer.parseInt(red[0]);
			String naziv = red[1];
			
			TipTretmana tipTretmana = new TipTretmana(id, naziv);
			sviTipoviTretmana.add(tipTretmana);
		}
		UcitajPodatke.setSviTipoviTretmana(sviTipoviTretmana);
		return sviTipoviTretmana;
	}
	
	public void sacuvajTipTretmanaFajl() throws IOException {
		List<String[]> sviTipoviTretmanaFajl = new ArrayList<>();
		
		for(TipTretmana tipTretmana: sviTipoviTretmana) {
			String[] temp = new String[3];
			temp[0] = Integer.toString(tipTretmana.getId());
			temp[1] = tipTretmana.getNaziv();
			
			sviTipoviTretmanaFajl.add(temp);
		}
		
		CSVReaderWriter.write(TIP_TRETMANA_PUTANJA, sviTipoviTretmanaFajl);
	}
}
