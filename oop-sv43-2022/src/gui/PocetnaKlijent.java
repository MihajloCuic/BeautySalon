package gui;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JFrame;

import entiteti.Klijent;
import entiteti.ZakazanTretman;
import entiteti.Zaposleni;
import obradaPodataka.KlijentOperacije;
import obradaPodataka.UcitajPodatke;
import obradaPodataka.ZakazanTretmanOperacije;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;

public class PocetnaKlijent {

	private JFrame frame;
	private JTable table;
	/**
	 * Create the application.
	 */
	public PocetnaKlijent(Klijent klijent) {
		initialize(klijent);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Klijent klijent) {
		frame = new JFrame();
		frame.setBounds(100, 100, 700, 323);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 133, 684, 128);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			getData(klijent),
			new String[] {
				"ID", "Tretman", "Datum", "Vreme", "Kozmeticar", "Cena", "Status"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnZakazi = new JButton("Zakazi tretman");
		
		btnZakazi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ZakaziTretman zakaziTretman = new ZakaziTretman(klijent, null, "klijent");
				frame.dispose();
			}
		});
		btnZakazi.setFocusable(false);
		btnZakazi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnZakazi.setBounds(117, 11, 147, 50);
		frame.getContentPane().add(btnZakazi);
		
		JButton btnOtkazi = new JButton("Otkazi tretman");
		btnOtkazi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = table.getSelectedRow();
				List<ZakazanTretman> sviZakazaniTretmani = UcitajPodatke.getSviZakazaniTretmani();
				KlijentOperacije klijentOperacije = new KlijentOperacije(UcitajPodatke.getSviKlijenti());
				List<ZakazanTretman> zakazaniTretmaniKlijenta = klijentOperacije.ispisiZakazaneTretmaneKlijenta(sviZakazaniTretmani, klijent.getKorisnickoIme());
				int idTretmana = zakazaniTretmaniKlijenta.get(selectedIndex).getId();
				try {
					UcitajPodatke.setSviZakazaniTretmani(klijentOperacije.otkaziTretman(sviZakazaniTretmani, idTretmana));
					ZakazanTretmanOperacije zakazanTretmanOperacije = new ZakazanTretmanOperacije(UcitajPodatke.getSviZakazaniTretmani());
					zakazanTretmanOperacije.sacuvajZakazaneTretmaneFajl();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Greska, tretman ne moze da se otkaze");
				}
				
				table.setModel(new DefaultTableModel(
						getData(klijent),
						new String[] {
							"ID", "Tretman", "Datum", "Vreme", "Kozmeticar", "Cena", "Status"
						}
					));
			}
		});
		btnOtkazi.setFocusable(false);
		btnOtkazi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOtkazi.setBounds(117, 72, 147, 50);
		frame.getContentPane().add(btnOtkazi);
		
		JLabel lblKarticaLojalnosti = new JLabel("Kartica lojalnosti");
		lblKarticaLojalnosti.setHorizontalAlignment(SwingConstants.CENTER);
		lblKarticaLojalnosti.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblKarticaLojalnosti.setBounds(419, 11, 265, 27);
		frame.getContentPane().add(lblKarticaLojalnosti);
		
		JLabel lblPravoNaPopust = new JLabel("");
		lblPravoNaPopust.setHorizontalAlignment(SwingConstants.CENTER);
		lblPravoNaPopust.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPravoNaPopust.setBounds(419, 49, 255, 27);
		frame.getContentPane().add(lblPravoNaPopust);
		
		JLabel lblPotrosenIznos = new JLabel("");
		lblPotrosenIznos.setHorizontalAlignment(SwingConstants.CENTER);
		lblPotrosenIznos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPotrosenIznos.setBounds(419, 86, 255, 27);
		frame.getContentPane().add(lblPotrosenIznos);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNalog = new JMenu("Nalog");
		menuBar.add(mnNalog);
		
		JMenuItem mntmIzmena = new JMenuItem("Izmena naloga");
		mntmIzmena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Zaposleni radnik = new Zaposleni(null, null, null, null,"",null,null,null,null, 0,0,0);
				FormaIzmene formaIzmene = new FormaIzmene(klijent, radnik, false);
				frame.dispose();
			}
		});
		mnNalog.add(mntmIzmena);
		
		JMenuItem mntmKarticaLojalnosti = new JMenuItem("Kartica Lojalnosti");
		mntmKarticaLojalnosti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(klijent.getKarticaLojalnosti().getPravoNaPopust()) {
					lblPravoNaPopust.setText("Korisnik ima pravo na 10% popusta");
				}
				else {
					lblPravoNaPopust.setText("Korisnik nema pravo na popust");
				}
				
				lblPotrosenIznos.setText("Potrosen iznos: " + Double.toString(klijent.getKarticaLojalnosti().getPotrosenIznos()) + "din");
			}
		});
		mnNalog.add(mntmKarticaLojalnosti);
		
		JMenu mnKraj = new JMenu("Izlaz");
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

		frame.setVisible(true);
	}
	
	Object[][] getData(Klijent klijent) {
		List<ZakazanTretman> sviZakazaniTretmani = UcitajPodatke.getSviZakazaniTretmani();
		KlijentOperacije klijentOperacije = new KlijentOperacije(UcitajPodatke.getSviKlijenti());
		List<ZakazanTretman> zakazaniTretmaniKlijenta = klijentOperacije.ispisiZakazaneTretmaneKlijenta(sviZakazaniTretmani, klijent.getKorisnickoIme());
		if(zakazaniTretmaniKlijenta == null) {
			return null;
		}
		Object[][] data = new Object[zakazaniTretmaniKlijenta.size()][7];
		for(int i = 0; i < zakazaniTretmaniKlijenta.size(); i++) {
				data[i][0] = Integer.toString(zakazaniTretmaniKlijenta.get(i).getId());
				data[i][1] = zakazaniTretmaniKlijenta.get(i).getTretman().getNaziv();
				
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				data[i][2] = zakazaniTretmaniKlijenta.get(i).getDatumTretmana().format(dateFormatter);
				
				DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
				data[i][3] = zakazaniTretmaniKlijenta.get(i).getVremeTretmana().format(timeFormatter);
				
				data[i][4] = zakazaniTretmaniKlijenta.get(i).getKozmeticar().getKorisnickoIme();
				data[i][5] = Double.toString(zakazaniTretmaniKlijenta.get(i).getCena());
				data[i][6] = zakazaniTretmaniKlijenta.get(i).getStanje().toString();
		}
		return data;
	}
}
