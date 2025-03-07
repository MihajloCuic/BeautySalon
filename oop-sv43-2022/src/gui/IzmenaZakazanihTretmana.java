package gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;

import entiteti.ComboBox;
import entiteti.Klijent;
import entiteti.Tretman;
import entiteti.ZakazanTretman;
import entiteti.Zaposleni;
import obradaPodataka.KlijentOperacije;
import obradaPodataka.UcitajPodatke;
import obradaPodataka.ZakazanTretmanOperacije;
import obradaPodataka.ZaposleniOperacije;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;

public class IzmenaZakazanihTretmana {

	private JFrame frame;
	private JTable table;
	/**
	 * Create the application.
	 */
	public IzmenaZakazanihTretmana(Zaposleni zaposleni, ZakazanTretman zakazanTretman) {
		initialize(zaposleni, zakazanTretman);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Zaposleni zaposleni, ZakazanTretman zakazanTretman) {
		frame = new JFrame();
		frame.setBounds(100, 100, 590, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblZakaziTretman = new JLabel("Zakazivanje Tretmana");
		lblZakaziTretman.setHorizontalAlignment(SwingConstants.CENTER);
		lblZakaziTretman.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblZakaziTretman.setBounds(0, 0, 575, 65);
		frame.getContentPane().add(lblZakaziTretman);
		
		JLabel lblTretman = new JLabel("Odaberite tretman:");
		lblTretman.setHorizontalAlignment(SwingConstants.CENTER);
		lblTretman.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTretman.setBounds(0, 100, 247, 27);
		frame.getContentPane().add(lblTretman);
		
		JLabel lblKozmeticar = new JLabel("Odaberite kozmeticara:");
		lblKozmeticar.setHorizontalAlignment(SwingConstants.CENTER);
		lblKozmeticar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblKozmeticar.setBounds(0, 189, 247, 27);
		frame.getContentPane().add(lblKozmeticar);
		
		JLabel lblDatumTretmana = new JLabel("Odaberite datum tretmana:");
		lblDatumTretmana.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatumTretmana.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDatumTretmana.setBounds(0, 265, 247, 27);
		frame.getContentPane().add(lblDatumTretmana);
		
		JLabel lblVremeTretmana = new JLabel("Odaberite vreme tretmana:");
		lblVremeTretmana.setHorizontalAlignment(SwingConstants.CENTER);
		lblVremeTretmana.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVremeTretmana.setBounds(298, 100, 277, 27);
		frame.getContentPane().add(lblVremeTretmana);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(55, 303, 130, 20);
		frame.getContentPane().add(dateChooser);
		frame.setVisible(true);
		
		ComboBox comboBoxKozmeticar = new ComboBox();
		JComboBox cbKozmeticar = new JComboBox();

		//Uzimanje trenutno selektovanog kozmeticara
		cbKozmeticar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxKozmeticar.setSelectedItem(cbKozmeticar.getSelectedItem());
			}
		});
		cbKozmeticar.setBounds(55, 221, 130, 22);
		frame.getContentPane().add(cbKozmeticar);
		
		ComboBox comboBoxTretmana = new ComboBox();
		JComboBox cbTretman = new JComboBox();
		//Popunjavanje comboBox-a tretmana
		for(Tretman tretman: UcitajPodatke.getSviTretmani()) {
			cbTretman.addItem(tretman.getId() + " | " + tretman.getNaziv());
		}
		//Popunjavanje comboBox-a kozmeticara na osnovu izabranog tretmana
		cbTretman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxTretmana.setSelectedItem(cbTretman.getSelectedItem());
				cbKozmeticar.removeAllItems();
				for(Zaposleni z: UcitajPodatke.getSviZaposleni()) {
					if(z.getUloga().uloga != 2) {
						continue;
					}
					if(!z.getTretman().contains(comboBoxTretmana.IzdvojTipTretmana())) {
						continue;
					}
					cbKozmeticar.addItem(z.getKorisnickoIme());
				}
			}
		});
		cbTretman.setBounds(55, 123, 130, 22);
		frame.getContentPane().add(cbTretman);
		
		JButton btnIzmeni = new JButton("Izmeni");
		btnIzmeni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				
				String vreme = model.getValueAt(table.getSelectedRow(), 0).toString();
				LocalTime vremeTretmana;
				if(vreme != null) {
					DateTimeFormatter vremeFormat = DateTimeFormatter.ofPattern("HH:mm");
					vremeTretmana = LocalTime.parse(vreme, vremeFormat);
				}else {
					vremeTretmana = zakazanTretman.getVremeTretmana();
				}
				
				LocalDate izabraniDatum = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				
				Tretman tretman = null;
				int idTretmana = comboBoxTretmana.izdvojId();
				for(Tretman tret: UcitajPodatke.getSviTretmani()) {
					if(tret.getId() == idTretmana) {
						tretman = tret;
						break;
					}
				}
				
				try {
					List<ZakazanTretman> sviZakazaniTretmani = UcitajPodatke.getSviZakazaniTretmani();
					ZakazanTretmanOperacije zakazanTretmanOperacije = new ZakazanTretmanOperacije(UcitajPodatke.getSviZakazaniTretmani());
					sviZakazaniTretmani = zakazanTretmanOperacije.izmeniZakazanTretman(zakazanTretman.getId(), tretman, zakazanTretman.getCena(), izabraniDatum, vremeTretmana, zakazanTretman.getKozmeticar(), zakazanTretman.getStanje(), zakazanTretman.getKlijent(), zaposleni);						
					UcitajPodatke.setSviZakazaniTretmani(sviZakazaniTretmani);
					zakazanTretmanOperacije.sacuvajZakazaneTretmaneFajl();
					JOptionPane.showMessageDialog(null, "Tretman je uspesno izmenjen");

					PocetnaZaposleni pocetnaZaposleni = new PocetnaZaposleni(zaposleni);
					frame.dispose();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		btnIzmeni.setFocusable(false);
		btnIzmeni.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnIzmeni.setBounds(21, 393, 109, 37);
		frame.getContentPane().add(btnIzmeni);
		
		JButton btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PocetnaZaposleni pocetnaZaposleni = new PocetnaZaposleni(zaposleni);
				frame.dispose();
			}
		});
		btnOdustani.setFocusable(false);
		btnOdustani.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOdustani.setBounds(156, 393, 109, 37);
		frame.getContentPane().add(btnOdustani);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(352, 138, 177, 292);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Vreme"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnPrikaziVreme = new JButton("Prikazi Vreme");
		btnPrikaziVreme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int sviRedovi = model.getRowCount();
				for(int i = sviRedovi - 1; i >= 0; i--) {
					model.removeRow(i);
				}
				List<String> slobodnoVreme = vreme(dateChooser.getDate(), comboBoxKozmeticar.comboBoxToString());
				for(String v: slobodnoVreme) {
					model.addRow(new Object[] {v});
				}
			}
		});
		btnPrikaziVreme.setFocusable(false);
		btnPrikaziVreme.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnPrikaziVreme.setBounds(63, 334, 110, 23);
		frame.getContentPane().add(btnPrikaziVreme);
		
		JButton btnCenovnik = new JButton("Cenovnik");
		btnCenovnik.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Cenovnik cenovnik = new Cenovnik();
			}
		});
		btnCenovnik.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnCenovnik.setFocusable(false);
		btnCenovnik.setBounds(65, 156, 110, 23);
		frame.getContentPane().add(btnCenovnik);
		
		ComboBox cb = new ComboBox();
		for(int i = 0; i <cbTretman.getItemCount(); i++) {
			cb.setSelectedItem(cbTretman.getItemAt(i));
			if(cb.izdvojId() == zakazanTretman.getTretman().getId()) {
				cbTretman.setSelectedItem(cbTretman.getItemAt(i));
				break;
			}
		}
		
		cbKozmeticar.addItem(zakazanTretman.getKozmeticar().getKorisnickoIme());
		for(int i = 0; i < cbKozmeticar.getItemCount(); i++) {
			cb.setSelectedItem(cbKozmeticar.getItemAt(i));
			if(cb.comboBoxToString().equals(zakazanTretman.getKozmeticar().getKorisnickoIme())) {
				cbKozmeticar.setSelectedItem(cbKozmeticar.getItemAt(i));
				break;
			}
		}
		
		Date date = Date.from(zakazanTretman.getDatumTretmana().atStartOfDay(ZoneId.systemDefault()).toInstant());
		dateChooser.setDate(date);
		
		frame.setVisible(true);
	}
	
	public List<String> vreme(Date izabraniDatum, String kozmeticarKI) {
		LocalDate datum = izabraniDatum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime pocetakRadnogVremena = LocalTime.parse("08:00");
		LocalTime krajRadnogVremena = LocalTime.parse("20:00");
		LocalTime vreme = pocetakRadnogVremena;
		List<String> slobodnoVreme = new ArrayList<>();
		
		List<LocalTime> zauzeto = new ArrayList<>();
		for(ZakazanTretman zakazanTretman: UcitajPodatke.getSviZakazaniTretmani()) {
			if(zakazanTretman.getDatumTretmana().equals(datum) && zakazanTretman.getKozmeticar().getKorisnickoIme().equals(kozmeticarKI)) {
				int trajanjeTretmana = Integer.parseInt(zakazanTretman.getTretman().getTrajanjeTretmana());
				if(trajanjeTretmana > 60) {
					LocalTime pocetakTretmana = zakazanTretman.getVremeTretmana();
					LocalTime krajTretmana = pocetakTretmana.plusMinutes(trajanjeTretmana+60-trajanjeTretmana%60); //zaokruzujemo kraj tretmana na sledeci sat
					while(pocetakTretmana.isBefore(krajTretmana)) {				
						zauzeto.add(pocetakTretmana);
						pocetakTretmana = pocetakTretmana.plusHours(1);
					}
				}else {
					zauzeto.add(zakazanTretman.getVremeTretmana());
				}
			}
		}
		
		while(vreme.isBefore(krajRadnogVremena)) {
			if(!zauzeto.contains(vreme)) {
				slobodnoVreme.add(vreme.format(formatter));
			}
			vreme = vreme.plusHours(1);
		}
		
		return slobodnoVreme;
	}
}
