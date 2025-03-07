package gui;

import javax.swing.JFrame;

import entiteti.ComboBox;
import entiteti.Klijent;
import entiteti.TipTretmana;
import entiteti.Tretman;
import entiteti.ZakazanTretman;
import entiteti.Zaposleni;
import obradaPodataka.KlijentOperacije;
import obradaPodataka.UcitajPodatke;
import obradaPodataka.ZakazanTretmanOperacije;
import obradaPodataka.ZaposleniOperacije;
import util.Uloga;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JLabel;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class PocetnaZaposleni {

	private JFrame frame;
	private JTable table;
	private boolean zakazi = false;
	private boolean obrisi = false;
	private boolean izmeni = false;
	private JTextField txtCenaDonjaGranica;
	private JTextField txtCenaGornjaGranica;
	/**
	 * Create the application.
	 */
	public PocetnaZaposleni(Zaposleni zaposleni) {
		initialize(zaposleni);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Zaposleni zaposleni) {
		frame = new JFrame();
		frame.setBounds(100, 100, 531, 499);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnZaposleni = new JMenu("Zaposleni");
		menuBar.add(mnZaposleni);
		
		JMenuItem mntmOperacijeZaposleni = new JMenuItem("Izmena Zaposlenih");
		mntmOperacijeZaposleni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IzmenaZaposlenih izmenaZaposlenih = new IzmenaZaposlenih(zaposleni);
				frame.dispose();
			}
		});
		mnZaposleni.add(mntmOperacijeZaposleni);
		
		JMenu mnKlijenti = new JMenu("Klijenti");
		menuBar.add(mnKlijenti);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setSelectedItem("Izaberite klijenta");
		if(zaposleni.getUloga().uloga == 2) {
			comboBox.addItem("IZVRSEN");
			comboBox.addItem("NIJESEPOJAVIO");
		}else {
			for(Klijent klijent: UcitajPodatke.getSviKlijenti()) {
				comboBox.addItem(klijent.getKorisnickoIme());
			}
		}
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String item = (String) comboBox.getSelectedItem();
				if(zaposleni.getUloga().uloga == 2) {
					int red = table.getSelectedRow();
					if(red == -1) {
						JOptionPane.showMessageDialog(null, "Izaberite u tabeli tretman koji zelite da izvrsite");
					}else {
						int idZakazanogTretmana = Integer.parseInt((String) table.getValueAt(red, 0));
						List<ZakazanTretman> sviZakazaniTretmani = UcitajPodatke.getSviZakazaniTretmani();
						ZaposleniOperacije zaposleniOperacije = new ZaposleniOperacije(UcitajPodatke.getSviZaposleni());
						try {
							sviZakazaniTretmani = zaposleniOperacije.izvrsiTretman(UcitajPodatke.getSviZakazaniTretmani(), idZakazanogTretmana, item);
							UcitajPodatke.setSviZakazaniTretmani(sviZakazaniTretmani);
							ZakazanTretmanOperacije zakazanTretmanOperacije = new ZakazanTretmanOperacije(sviZakazaniTretmani);
							zakazanTretmanOperacije.sacuvajZakazaneTretmaneFajl();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
						table.setModel(new DefaultTableModel(
								getData(zaposleni, false, 0, 0, 0, 0),
								new String[] {
									"ID", "Naziv", "Datum", "Vreme", "Kozmeticar", "Klijent", "Cena", "Status"
								}));
						comboBox.setVisible(false);
					}
				}else {
					List<Klijent> sviKlijenti = UcitajPodatke.getSviKlijenti();
					for(Klijent klijent: sviKlijenti) {
						if(klijent.getKorisnickoIme().equals(item)) {
							if(zakazi) {
								ZakaziTretman zakaziTretman = new ZakaziTretman(klijent, zaposleni, "zaposleni");
								zakazi = false;
								frame.dispose();
							}else if(izmeni){
								FormaIzmene formaIzmene = new FormaIzmene(klijent, zaposleni, true);
								izmeni = false;
								frame.dispose();
							}else if(obrisi) {
								KlijentOperacije klijentOperacije = new KlijentOperacije(sviKlijenti);
								sviKlijenti = klijentOperacije.obrisiKlijenta(item);
								UcitajPodatke.setSviKlijenti(sviKlijenti);
								comboBox.removeAllItems();
								for(int i = 0; i < sviKlijenti.size(); i++) {
									comboBox.addItem(sviKlijenti.get(i).getKorisnickoIme());
								}
								try {
									klijentOperacije.sacuvajKlijenteFajl();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								JOptionPane.showMessageDialog(null, "Korisnik je obrisan");
								comboBox.setVisible(false);
								obrisi = false;
							}
						}
					}
				}
			}
		});
		comboBox.setVisible(false);
		comboBox.setBounds(83, 84, 123, 22);
		frame.getContentPane().add(comboBox);
		
		JMenuItem mntmIzmeniKlijenta = new JMenuItem("Izmeni klijenta");
		mntmIzmeniKlijenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				izmeni = true;
				comboBox.setVisible(true);			
			}
		});
		mnKlijenti.add(mntmIzmeniKlijenta);
		
		JMenuItem mntmDodajKlijenta = new JMenuItem("Dodaj klijenta");
		mntmDodajKlijenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Registracija registracija = new Registracija(zaposleni, true);
				frame.dispose();
			}
		});
		mnKlijenti.add(mntmDodajKlijenta);
		
		JMenuItem mntmObrisiKlijenta = new JMenuItem("Obrisi klijenta");
		mntmObrisiKlijenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obrisi = true;
				comboBox.setVisible(true);
			}
		});
		mnKlijenti.add(mntmObrisiKlijenta);
		
		JMenu mnTretmani = new JMenu("Tretmani");
		menuBar.add(mnTretmani);
		
		JMenuItem mntmTipTretmana = new JMenuItem("Tipovi tretmana");
		mntmTipTretmana.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IzmenaTipovaTretmana izmenaTipovaTretmana = new IzmenaTipovaTretmana(zaposleni);
				frame.dispose();
			}
		});
		mnTretmani.add(mntmTipTretmana);
		
		JMenuItem mntmTretman = new JMenuItem("Tretmani");
		mntmTretman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IzmenaTretmana izmenaTretmana = new IzmenaTretmana(zaposleni);
				frame.dispose();
			}
		});
		mnTretmani.add(mntmTretman);
		
		JMenuItem mntmZakazaniTretmani = new JMenuItem("Izmeni zakazan tretman");
		mntmZakazaniTretmani.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "U tabeli dole izaberite zakazan tretman koji zelite da izmenite!");
				}
				else {
					int idZakazanogTretmana = Integer.parseInt((String) table.getValueAt(red, 0));
					for(ZakazanTretman zakazanTretman: UcitajPodatke.getSviZakazaniTretmani()) {
						if(zakazanTretman.getId() == idZakazanogTretmana) {
							IzmenaZakazanihTretmana izmenaZakazanihTretmana = new IzmenaZakazanihTretmana(zaposleni, zakazanTretman);
							frame.dispose();
							break;
						}
					}
				}	
			}
		});
		mnTretmani.add(mntmZakazaniTretmani);
		
		JMenu mnIzvestaji = new JMenu("Izvestaji");
		menuBar.add(mnIzvestaji);
		
		JMenuItem mntmIzvestaji = new JMenuItem("Prikazi izvestaje");
		mntmIzvestaji.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IzvestajiGUI izvestaji = new IzvestajiGUI(zaposleni);
				frame.dispose();
			}
		});
		mnIzvestaji.add(mntmIzvestaji);
		
		JMenu mnSalon = new JMenu("Kozmeticki Salon");
		menuBar.add(mnSalon);
		
		JMenuItem mntmIzmenaKozmetickogSalona = new JMenuItem("Izmena Kozmetickog Salona");
		mntmIzmenaKozmetickogSalona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IzmenaKozmetickogSalona izmenaKozmetickogSalona = new IzmenaKozmetickogSalona(zaposleni);
				frame.dispose();
			}
		});
		mnSalon.add(mntmIzmenaKozmetickogSalona);
		
		JMenu mnKraj = new JMenu("Kraj");
		menuBar.add(mnKraj);
		
		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				frame.dispose();
			}
		});
		mnKraj.add(mntmLogout);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnKraj.add(mntmExit);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 248, 515, 194);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			getData(zaposleni, false, 0, 0, 0, 0),
			new String[] {
				"ID", "Naziv", "Datum", "Vreme", "Kozmeticar", "Klijent", "Cena", "Status"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(85);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(6).setResizable(false);
		table.getColumnModel().getColumn(6).setPreferredWidth(60);
		table.getColumnModel().getColumn(7).setResizable(false);
		table.getColumnModel().getColumn(7).setPreferredWidth(120);
		scrollPane.setViewportView(table);		
		
		JButton btnZakaziTretman = new JButton();
		if(zaposleni.getUloga().uloga == 2) {
			btnZakaziTretman.setText("Izvrsi Tretman");
		}else {
			btnZakaziTretman.setText("Zakazi Tretman");
		}
		btnZakaziTretman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(zaposleni.getUloga().uloga != 2) {
					zakazi = true;
				}
				comboBox.setVisible(true);
			}
		});
		btnZakaziTretman.setBounds(83, 29, 123, 44);
		btnZakaziTretman.setFocusable(false);
		frame.getContentPane().add(btnZakaziTretman);
		
		JButton btnOtkaziTretman = new JButton("Otkazi Tretman");
		btnOtkaziTretman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				int idZakazanogTretmana = Integer.parseInt((String) table.getValueAt(red, 0));
				ZaposleniOperacije zaposleniOperacije = new ZaposleniOperacije(UcitajPodatke.getSviZaposleni());
				try {
					List<ZakazanTretman> sviZakazaniTretmani = UcitajPodatke.getSviZakazaniTretmani();
					sviZakazaniTretmani = zaposleniOperacije.otkaziTretman(UcitajPodatke.getSviZakazaniTretmani(), idZakazanogTretmana);
					UcitajPodatke.setSviZakazaniTretmani(sviZakazaniTretmani);
					ZakazanTretmanOperacije zakazanTretmanOperacije = new ZakazanTretmanOperacije(sviZakazaniTretmani);
					zakazanTretmanOperacije.sacuvajZakazaneTretmaneFajl();
					table.setModel(new DefaultTableModel(
							getData(zaposleni, false, 0, 0, 0, 0),
							new String[] {
								"ID", "Naziv", "Datum", "Vreme", "Kozmeticar", "Klijent", "Cena", "Status"
							}));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnOtkaziTretman.setBounds(303, 29, 123, 44);
		btnOtkaziTretman.setFocusable(false);
		frame.getContentPane().add(btnOtkaziTretman);
		
		ComboBox cbTretmanObj = new ComboBox();
		ComboBox cbTipTretmanaObj = new ComboBox();
		
		JComboBox cbTipTretmana = new JComboBox();
		cbTipTretmana.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbTipTretmanaObj.setSelectedItem(cbTipTretmana.getSelectedItem());
			}
		});
		cbTipTretmana.setBounds(202, 166, 109, 22);
		frame.getContentPane().add(cbTipTretmana);
		cbTipTretmana.setVisible(false);
		
		JComboBox cbTretman = new JComboBox();
		cbTretman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbTretmanObj.setSelectedItem(cbTretman.getSelectedItem());
			}
		});
		cbTretman.setBounds(202, 192, 109, 22);
		frame.getContentPane().add(cbTretman);
		cbTretman.setVisible(false);
		
		txtCenaDonjaGranica = new JTextField();
		txtCenaDonjaGranica.setBounds(156, 219, 86, 20);
		frame.getContentPane().add(txtCenaDonjaGranica);
		txtCenaDonjaGranica.setColumns(10);
		txtCenaDonjaGranica.setVisible(false);
		txtCenaDonjaGranica.setText("0");
		
		JLabel lblOd = new JLabel("Od:");
		lblOd.setBounds(134, 223, 28, 14);
		frame.getContentPane().add(lblOd);
		lblOd.setVisible(false);
		
		JLabel lblDo = new JLabel("Do:");
		lblDo.setBounds(252, 223, 28, 14);
		frame.getContentPane().add(lblDo);
		lblDo.setVisible(false);
		
		txtCenaGornjaGranica = new JTextField();
		txtCenaGornjaGranica.setColumns(10);
		txtCenaGornjaGranica.setBounds(276, 219, 86, 20);
		frame.getContentPane().add(txtCenaGornjaGranica);
		txtCenaGornjaGranica.setVisible(false);
		txtCenaGornjaGranica.setText("1000000");
		
		JButton btnFiltriraj = new JButton("Filtriraj");
		btnFiltriraj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idTipaTretmana;
				int idTretmana;
				double minCena = Double.parseDouble(txtCenaDonjaGranica.getText());
				double maxCena = Double.parseDouble(txtCenaGornjaGranica.getText());
				if(maxCena < minCena) {
					JOptionPane.showMessageDialog(null, "Greska, maksimalna cena ne moze biti manja od minimalne");
					return;
				}
				if(cbTipTretmanaObj.getSelectedItem() != null && cbTipTretmana.getSelectedIndex() != 0) {
					idTipaTretmana = cbTipTretmanaObj.izdvojId();
				}else {
					idTipaTretmana = -1;
				}
				if(cbTretmanObj.getSelectedItem() != null && cbTretman.getSelectedIndex() != 0) {
					idTretmana = cbTretmanObj.izdvojId();
				}else {
					idTretmana = -1;
				}
				table.setModel(new DefaultTableModel(
						getData(zaposleni, true, idTretmana, idTipaTretmana, minCena, maxCena),
						new String[] {
								"ID", "Naziv", "Datum", "Vreme", "Kozmeticar", "Klijent", "Cena", "Status"
							}));
			}
		});
		btnFiltriraj.setBounds(371, 193, 123, 21);
		frame.getContentPane().add(btnFiltriraj);
		btnFiltriraj.setVisible(false);
		
		JCheckBox chckbxCena = new JCheckBox("Po opsegu cene");
		chckbxCena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lblOd.isVisible()) {
					lblOd.setVisible(false);
					lblDo.setVisible(false);
					txtCenaDonjaGranica.setVisible(false);
					txtCenaGornjaGranica.setVisible(false);
				}else {
					lblOd.setVisible(true);
					lblDo.setVisible(true);
					txtCenaDonjaGranica.setVisible(true);
					txtCenaGornjaGranica.setVisible(true);		
				}
				btnFiltriraj.setVisible(true);
			}
		});
		chckbxCena.setBounds(10, 218, 116, 23);
		frame.getContentPane().add(chckbxCena);
		
		JCheckBox chckbxTretman = new JCheckBox("Po kozmetickom tretmanu");
		chckbxTretman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cbTretman.isVisible()) {
					cbTretman.setVisible(false);
					cbTretman.removeAllItems();
				}else {
					cbTretman.setVisible(true);
					cbTretman.addItem("Izaberite tretman");
					for(Tretman tretman: UcitajPodatke.getSviTretmani()) {
						String temp = Integer.toString(tretman.getId()) + " | " + tretman.getNaziv();
						cbTretman.addItem(temp);
					}
				}
				btnFiltriraj.setVisible(true);
			}
		});
		chckbxTretman.setBounds(10, 192, 186, 23);
		frame.getContentPane().add(chckbxTretman);
		
		JCheckBox chckbxTipTretmana = new JCheckBox("Po tipu tretmana");
		chckbxTipTretmana.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cbTipTretmana.isVisible()) {
					cbTipTretmana.setVisible(false);
					cbTipTretmana.removeAllItems();
				}else {
					cbTipTretmana.setVisible(true);
					cbTipTretmana.addItem("Izaberite tip tretmana");
					for(TipTretmana tipTretmana: UcitajPodatke.getSviTipoviTretmana()) {
						String temp = Integer.toString(tipTretmana.getId()) + " | " + tipTretmana.getNaziv();
						cbTipTretmana.addItem(temp);
					}
				}
				btnFiltriraj.setVisible(true);
			}
		});
		chckbxTipTretmana.setBounds(10, 166, 152, 23);
		frame.getContentPane().add(chckbxTipTretmana);
		
		JLabel lblFiltriraj = new JLabel("Filtriranje:");
		lblFiltriraj.setBounds(10, 140, 97, 14);
		frame.getContentPane().add(lblFiltriraj);
		
		JButton btnOdfiltriraj = new JButton("Odfiltriraj");
		btnOdfiltriraj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(new DefaultTableModel(
						getData(zaposleni, false, 0, 0, 0, 0),
						new String[] {
							"ID", "Naziv", "Datum", "Vreme", "Kozmeticar", "Klijent", "Cena", "Status"
						}));
			}
		});
		btnOdfiltriraj.setBounds(372, 218, 122, 23);
		frame.getContentPane().add(btnOdfiltriraj);
		
		if(zaposleni.getUloga().uloga == 2) {
			mnZaposleni.setEnabled(false);
			mnKlijenti.setEnabled(false);
			mnTretmani.setEnabled(false);
			mnIzvestaji.setEnabled(false);
			mnSalon.setEnabled(false);
			chckbxTipTretmana.setVisible(false);
			chckbxTretman.setVisible(false);
			chckbxCena.setVisible(false);
			btnOdfiltriraj.setVisible(false);
			lblFiltriraj.setVisible(false);
		}else if(zaposleni.getUloga().uloga == 3) {
			mnZaposleni.setEnabled(false);
			mnKlijenti.setEnabled(false);
			mnTretmani.setEnabled(true);
			mnIzvestaji.setEnabled(false);
			mnSalon.setEnabled(false);
			mntmTipTretmana.setEnabled(false);
			mntmTretman.setEnabled(false);
		}
		
		frame.setVisible(true);
	}
	
	public Object[][] getData(Zaposleni zaposleni, boolean filtriranje, int idTretmana, int idTipaTretmana, double minCena, double maxCena) {
		List<ZakazanTretman> sviZakazaniTretmani = UcitajPodatke.getSviZakazaniTretmani();
		ZaposleniOperacije zaposleniOperacije = new ZaposleniOperacije(UcitajPodatke.getSviZaposleni());
		if(filtriranje) {
			List<ZakazanTretman> sviFiltriraniTretmani = zaposleniOperacije.filtrirajZakazaneTretmane(sviZakazaniTretmani, idTretmana, idTipaTretmana, minCena, maxCena);
			Object[][] data = new Object[sviFiltriraniTretmani.size()][8];
			for(int i = 0; i < sviFiltriraniTretmani.size(); i++) {
				data[i][0] = Integer.toString(sviFiltriraniTretmani.get(i).getId());
				data[i][1] = sviFiltriraniTretmani.get(i).getTretman().getNaziv();
				
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				data[i][2] = sviFiltriraniTretmani.get(i).getDatumTretmana().format(dateFormatter);
				
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
				data[i][3] = sviFiltriraniTretmani.get(i).getVremeTretmana().format(timeFormatter);
				data[i][4] = sviFiltriraniTretmani.get(i).getKozmeticar().getKorisnickoIme();
				data[i][5] = sviFiltriraniTretmani.get(i).getKlijent().getKorisnickoIme();
				data[i][6] = Double.toString(sviFiltriraniTretmani.get(i).getCena());
				data[i][7] = sviFiltriraniTretmani.get(i).getStanje().toString();
			}
			return data;
		}
		if(zaposleni.getUloga().uloga == 2) {
			List<ZakazanTretman> sviZakazaniTretmaniKozmeticara = zaposleniOperacije.prikaziRasporedKozmeticara(sviZakazaniTretmani, zaposleni.getKorisnickoIme());
			Object[][] data = new Object[sviZakazaniTretmaniKozmeticara.size()][8];
			for(int i = 0; i < sviZakazaniTretmaniKozmeticara.size(); i++) {
				data[i][0] = Integer.toString(sviZakazaniTretmaniKozmeticara.get(i).getId());
				data[i][1] = sviZakazaniTretmaniKozmeticara.get(i).getTretman().getNaziv();
				
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				data[i][2] = sviZakazaniTretmaniKozmeticara.get(i).getDatumTretmana().format(dateFormatter);
				
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
				data[i][3] = sviZakazaniTretmaniKozmeticara.get(i).getVremeTretmana().format(timeFormatter);
				data[i][4] = zaposleni.getKorisnickoIme();
				data[i][5] = sviZakazaniTretmaniKozmeticara.get(i).getKlijent().getKorisnickoIme();
				data[i][6] = Double.toString(sviZakazaniTretmaniKozmeticara.get(i).getCena());
				data[i][7] = sviZakazaniTretmaniKozmeticara.get(i).getStanje().toString();
			}
			return data;
		}else {
			Object[][] data = new Object[sviZakazaniTretmani.size()][8];
			for(int i = 0; i < sviZakazaniTretmani.size(); i++) {
				data[i][0] = Integer.toString(sviZakazaniTretmani.get(i).getId());
				data[i][1] = sviZakazaniTretmani.get(i).getTretman().getNaziv();
				
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				data[i][2] = sviZakazaniTretmani.get(i).getDatumTretmana().format(dateFormatter);
				
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
				data[i][3] = sviZakazaniTretmani.get(i).getVremeTretmana().format(timeFormatter);
				
				data[i][4] = sviZakazaniTretmani.get(i).getKozmeticar().getKorisnickoIme();
				data[i][5] = sviZakazaniTretmani.get(i).getKlijent().getKorisnickoIme();
				data[i][6] = Double.toString(sviZakazaniTretmani.get(i).getCena());
				data[i][7] = sviZakazaniTretmani.get(i).getStanje().toString();
			}
			return data;
		}
	}
}
