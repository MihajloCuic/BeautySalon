package entiteti;

import obradaPodataka.UcitajPodatke;
import util.StrucnaSprema;

public class ComboBox {
	private Object selectedItem;
	
	public ComboBox() {}
	
	public Object getSelectedItem() {
		return selectedItem;
	}
	
	public void setSelectedItem(Object selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public String IzdvojTipTretmana() {
		for(Tretman tretman: UcitajPodatke.getSviTretmani()) {
			if(tretman.getId() == izdvojId()) {
				return tretman.getTipTretmana();
			}
		}
		return null;
	}
	
	public int izdvojId() {
		String str = (String) selectedItem;
		int id = Integer.parseInt(str.substring(0, 1));
		return id;
	}
	
	public String comboBoxToString() {
		return (String) selectedItem;
	}
	
	public StrucnaSprema comboBoxToStrucnaSprema() {
		return StrucnaSprema.valueOf((String) selectedItem);
	}
}
