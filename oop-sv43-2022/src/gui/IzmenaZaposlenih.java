package gui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import entiteti.Zaposleni;
import obradaPodataka.UcitajPodatke;
import obradaPodataka.ZaposleniOperacije;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class IzmenaZaposlenih {

	private JFrame frame;
	private JTable table;
	/**
	 * Create the application.
	 */
	public IzmenaZaposlenih(Zaposleni zaposleni) {
		initialize(zaposleni);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Zaposleni zaposleni) {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 420);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 46, 556, 296);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			getData(),
			new String[] {
				"Korisnicko ime", "Lozinka", "Ime", "Prezime", "Strucna sprema", "Godine staza", "Plata", "Bonus"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(5).setPreferredWidth(85);
		table.getColumnModel().getColumn(6).setResizable(false);
		table.getColumnModel().getColumn(6).setPreferredWidth(60);
		table.getColumnModel().getColumn(7).setResizable(false);
		table.getColumnModel().getColumn(7).setPreferredWidth(60);
		scrollPane.setViewportView(table);
		
		JButton btnDodajZaposlenog = new JButton("Dodaj");
		btnDodajZaposlenog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Zaposleni radnik = new Zaposleni(null, null, null, null,"",null,null,null,null, 0,0,0);
				FormaZaposleni formaZaposleni = new FormaZaposleni(zaposleni, radnik);
				frame.dispose();
			}
		});
		btnDodajZaposlenog.setBounds(625, 62, 118, 46);
		btnDodajZaposlenog.setFocusable(false);
		frame.getContentPane().add(btnDodajZaposlenog);
		
		JButton btnIzmeniZaposlenog = new JButton("Izmeni");
		btnIzmeniZaposlenog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Greska, odaberite zaposlenog za izmenu!");
				}else {
					String korisnickoIme = table.getValueAt(red, 0).toString();
					for(Zaposleni radnik: UcitajPodatke.getSviZaposleni()) {
						if(radnik.getKorisnickoIme().equals(korisnickoIme)) {
							FormaZaposleni formaZaposleni = new FormaZaposleni(zaposleni, radnik);
							frame.dispose();
						}
					}
				}
			}
		});
		btnIzmeniZaposlenog.setBounds(625, 136, 118, 46);
		btnIzmeniZaposlenog.setFocusable(false);
		frame.getContentPane().add(btnIzmeniZaposlenog);
		
		JButton btnObrisiZaposlenog = new JButton("Obrisi");
		btnObrisiZaposlenog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Greska, odaberite zaposlenog za izmenu!");
				}else {
					String korisnickoIme = table.getValueAt(red, 0).toString();
					ZaposleniOperacije zaposleniOperacije = new ZaposleniOperacije(UcitajPodatke.getSviZaposleni());
					List<Zaposleni> sviZaposleni = zaposleniOperacije.obrisiZaposlenog(korisnickoIme);
					UcitajPodatke.setSviZaposleni(sviZaposleni);
					try {
						zaposleniOperacije.sacuvajZaposleneFajl();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					table.setModel(new DefaultTableModel(
							getData(),
							new String[] {
									"Korisnicko ime", "Lozinka", "Ime", "Prezime", "Strucna sprema", "Godine staza", "Plata", "Bonus"
							}
						));
				}
			}
		});
		btnObrisiZaposlenog.setBounds(625, 207, 118, 46);
		btnObrisiZaposlenog.setFocusable(false);
		frame.getContentPane().add(btnObrisiZaposlenog);
		
		JButton btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PocetnaZaposleni pocetnaZaposleni = new PocetnaZaposleni(zaposleni);
				frame.dispose();
			}
		});
		btnOdustani.setBounds(625, 278, 118, 46);
		btnOdustani.setFocusable(false);
		frame.getContentPane().add(btnOdustani);
		frame.setVisible(true);
	}
	
	public Object[][] getData() {
		List<Zaposleni> sviZaposleni = UcitajPodatke.getSviZaposleni();
		Object[][] data = new Object[sviZaposleni.size()][8];
		for(int i = 0; i < sviZaposleni.size(); i++) {
			data[i][0] = sviZaposleni.get(i).getKorisnickoIme();
			data[i][1] = sviZaposleni.get(i).getLozinka();
			data[i][2] = sviZaposleni.get(i).getIme();
			data[i][3] = sviZaposleni.get(i).getPrezime();
			data[i][4] = sviZaposleni.get(i).getStrucnaSprema().name();
			data[i][5] = Integer.toString(sviZaposleni.get(i).getGodineStaza());
			data[i][6] = Double.toString(sviZaposleni.get(i).getPlata());
			data[i][7] = Double.toString(sviZaposleni.get(i).getBonus());
		}
		return data;
	}
}
