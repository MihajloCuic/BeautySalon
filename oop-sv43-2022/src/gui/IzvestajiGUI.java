package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import entiteti.ComboBox;
import entiteti.Klijent;
import entiteti.Tretman;
import entiteti.ZakazanTretman;
import entiteti.Zaposleni;
import obradaPodataka.Izvestaji;
import obradaPodataka.KlijentOperacije;
import obradaPodataka.UcitajPodatke;
import util.Stanje;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import com.toedter.calendar.JDateChooser;

public class IzvestajiGUI {

	private JFrame frame;
	private JTable table;
	/**
	 * Create the application.
	 */
	public IzvestajiGUI(Zaposleni zaposleni) {
		initialize(zaposleni);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Zaposleni zaposleni) {
		frame = new JFrame();
		frame.setBounds(100, 100, 560, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 360, 371);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setToolTipText("");
		comboBox.setBounds(382, 35, 140, 22);
		frame.getContentPane().add(comboBox);
		
		JDateChooser dcPocetak = new JDateChooser();
		dcPocetak.setBounds(382, 108, 140, 20);
		frame.getContentPane().add(dcPocetak);
		
		JDateChooser dcKraj = new JDateChooser();
		dcKraj.setBounds(382, 178, 140, 20);
		frame.getContentPane().add(dcKraj);
		
		comboBox.addItem("po kozmeticaru");
		comboBox.addItem("po stanju");
		comboBox.addItem("po tretmanu");
		comboBox.addItem("kartice lojalnosti");
		
		JButton btnPrikazi = new JButton("Prikazi");
		btnPrikazi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date pocetak = dcPocetak.getDate();
				Date kraj = dcKraj.getDate();
				String izvestaj = (String) comboBox.getSelectedItem();
				if(!izvestaj.equals("kartice lojalnosti") && pocetak != null && kraj != null) {
					if(pocetak.after(kraj)) {
						JOptionPane.showMessageDialog(null, "Datum pocetka mora biti pre krajnjeg datuma");
					}else {
						switch(izvestaj) {
						case "po kozmeticaru":
							table.setModel(new DefaultTableModel(
									getData(izvestaj,pocetak,kraj),
									new String[] {
										"Korisnicko ime", "Broj izvrsenih tretmana", "ukupna zarada"
									}
								));
							break;
						case "po stanju":
							table.setModel(new DefaultTableModel(
									getData(izvestaj,pocetak,kraj),
									new String[] {
										"Stanje tretmana", "Broj slucajeva stanja"
									}
								));
							break;
						case "po tretmanu":
							table.setModel(new DefaultTableModel(
									getData(izvestaj,pocetak,kraj),
									new String[] {
										"ID", "Naziv", "Tip tretmana", "Trajanje tertmana", "Cena", "broj obavljenih tretmana", "ukupna zarada"
									}
								));
							break;
						default:
							JOptionPane.showMessageDialog(null, "Izaberite opciju iz gore date liste");
							break;
						}
					}
				}else if(izvestaj.equals("kartice lojalnosti")){
					table.setModel(new DefaultTableModel(
							getData(izvestaj,pocetak,kraj),
							new String[] {
								"Korisnicko ime", "Ukupno potrosen iznos"
							}
						));
				}else {
					JOptionPane.showMessageDialog(null, "Izaberite opseg datuma za izvestaj");
				}
			}
		});
		btnPrikazi.setBounds(392, 209, 119, 30);
		frame.getContentPane().add(btnPrikazi);
		
		JButton btnDodeliKarticeLojalnosti = new JButton("Dodeli kartice Lojalnosti");
		btnDodeliKarticeLojalnosti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Klijent klijent: getKlijente()) {
					for(Klijent k: UcitajPodatke.getSviKlijenti()) {
						if(klijent.getKorisnickoIme().equals(k.getKorisnickoIme())) {
							k.getKarticaLojalnosti().setPravoNaPopust(true);
						}
					}
				}
				KlijentOperacije klijentOperacije = new KlijentOperacije(UcitajPodatke.getSviKlijenti());
				JOptionPane.showMessageDialog(null, "Kartice lojalnosti su uspesno dodeljene");
				table.setModel(new DefaultTableModel(
						getData("kartice lojalnosti",null,null),
						new String[] {
							"Korisnicko ime", "Ukupno potrosen iznos"
						}
					));
				try {
					klijentOperacije.sacuvajKlijenteFajl();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Greska, klijenti nisu sacuvani u fajl");
				}
			}
		});
		btnDodeliKarticeLojalnosti.setBounds(370, 261, 164, 46);
		frame.getContentPane().add(btnDodeliKarticeLojalnosti);
		
		JButton btnPovratak = new JButton("Pocetni meni");
		btnPovratak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PocetnaZaposleni pocetnaZaposleni = new PocetnaZaposleni(zaposleni);
				frame.dispose();
			}
		});
		btnPovratak.setBounds(370, 318, 164, 42);
		frame.getContentPane().add(btnPovratak);
		
		JLabel lblPocetniDatum = new JLabel("Pocetni datum:");
		lblPocetniDatum.setHorizontalAlignment(SwingConstants.CENTER);
		lblPocetniDatum.setBounds(370, 75, 164, 22);
		frame.getContentPane().add(lblPocetniDatum);
		
		JLabel lblPocetniDatum_1 = new JLabel("Pocetni datum:");
		lblPocetniDatum_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPocetniDatum_1.setBounds(370, 145, 164, 22);
		frame.getContentPane().add(lblPocetniDatum_1);
		
		btnDodeliKarticeLojalnosti.setVisible(false);
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedIndex() == 3) {
					btnDodeliKarticeLojalnosti.setVisible(true);
				}
				else {
					btnDodeliKarticeLojalnosti.setVisible(false);
				}
			}
		});
		
		frame.setVisible(true);
	}
	public Object[][] getData(String izborIzvestaja, Date pocetniDatum, Date krajnjiDatum) {
		LocalDate datumPocetak = null;
		LocalDate datumKraj = null;
		if(pocetniDatum != null && krajnjiDatum != null) {
			datumPocetak = pocetniDatum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			datumKraj = krajnjiDatum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		Izvestaji izvestaji = new Izvestaji();
		Object[][] data;
		int i = 0;
		switch(izborIzvestaja) {
		case "po kozmeticaru":
			HashMap<Zaposleni, List<ZakazanTretman>> zakazaniTretmaniPoZaposlenom = izvestaji.kozmetickiTretmaniPoKozmeticaru(datumPocetak, datumKraj);
			data = new Object[zakazaniTretmaniPoZaposlenom.size()][3]; //kozmeticarKI, brojTretmanaIzvrsenih, ukupnaZarada
			
			for(Zaposleni z: zakazaniTretmaniPoZaposlenom.keySet()) {
				int br = 0;
				double zarada = 0;
				for(ZakazanTretman zakazanTretman: zakazaniTretmaniPoZaposlenom.get(z)) {
					br++;
					zarada += zakazanTretman.getCena();
				}
				data[i][0] = z.getKorisnickoIme();
				data[i][1] = Integer.toString(br);
				data[i][2] = Double.toString(zarada);
				i++;
			}
			break;
		case "po stanju":
			HashMap<String, Integer> zakazaniTretmaniPoStanju = izvestaji.kozmetickiTretmaniPoStanju(datumPocetak, datumKraj);
			data = new Object[zakazaniTretmaniPoStanju.size()][2]; //moguce stanje tretmana, broj pojave slucajeva stanja tretmana
			
			for(String stanje: zakazaniTretmaniPoStanju.keySet()) {
				data[i][0] = stanje;
				data[i][1] = Integer.toString(zakazaniTretmaniPoStanju.get(stanje));
				i++;
			}
			break;
		case "po tretmanu":
			HashMap<Tretman, List<ZakazanTretman>> kozmetickeUsluge = izvestaji.izvestajKozmetickeUsluge(datumPocetak, datumKraj);
			data = new Object[kozmetickeUsluge.size()][7]; //podaci o tretmanu, broj obavljenih tretmana, zarada
			for(Tretman tretman: kozmetickeUsluge.keySet()) {
				int br = 0;
				double zarada = 0;
				for(ZakazanTretman zakazanTretman: kozmetickeUsluge.get(tretman)) {
					br++;
					zarada += zakazanTretman.getCena();
				}
				data[i][0] = Integer.toString(tretman.getId());
				data[i][1] = tretman.getNaziv();
				data[i][2] = tretman.getTipTretmana();
				data[i][3] = tretman.getTrajanjeTretmana() + "min";
				data[i][4] = Double.toString(tretman.getCena());
				data[i][5] = Integer.toString(br);
				data[i][6] = Double.toString(zarada);
				i++;
			}
			break;
		case "kartice lojalnosti":
			List<Klijent> klijentiKojiIspunjavajuUslov = getKlijente();
			data = new Object[klijentiKojiIspunjavajuUslov.size()][2]; //klijentKI, potrosenIznos
			for(Klijent klijent: klijentiKojiIspunjavajuUslov) {
				data[i][0] = klijent.getKorisnickoIme();
				data[i][1] = Double.toString(klijent.getKarticaLojalnosti().getPotrosenIznos());
				i++;
			}
			break;
		default:
			data = null;
			break;
		}
		
		return data;
	}
	
	public List<Klijent> getKlijente() {
		Izvestaji izvestaji = new Izvestaji();
		List<Klijent> klijenti = izvestaji.izvestajKarticeLojalnosti();
		return klijenti;
	}
}
