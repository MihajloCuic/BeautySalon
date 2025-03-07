package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import entiteti.TipTretmana;
import entiteti.Zaposleni;
import obradaPodataka.TipTretmanaOperacije;
import obradaPodataka.UcitajPodatke;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class IzmenaTipovaTretmana {

	private JFrame frame;
	private JTable table;
	private JTextField txtNaziv;
	private int idTretmana;
	private boolean dodaj = false;
	private boolean izmena = false;
	/**
	 * Create the application.
	 */
	public IzmenaTipovaTretmana(Zaposleni zaposleni) {
		initialize(zaposleni);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Zaposleni zaposleni) {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 370);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(44, 42, 279, 271);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			getData(),
			new String[] {
				"ID", "Naziv"
			}
		));
		
		JButton btnPotvrdi = new JButton("potvrdi");
		btnPotvrdi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String naziv = txtNaziv.getText();
				List<TipTretmana> sviTipoviTretmana = UcitajPodatke.getSviTipoviTretmana();
				TipTretmanaOperacije tipTretmanaOperacije = new TipTretmanaOperacije(sviTipoviTretmana);
				if(izmena) {
					try {
						sviTipoviTretmana = tipTretmanaOperacije.izmeniTipTretmana(idTretmana, naziv);
						UcitajPodatke.setSviTipoviTretmana(sviTipoviTretmana);
						tipTretmanaOperacije.sacuvajTipTretmanaFajl();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					JOptionPane.showMessageDialog(null, "TipTretmana uspesno izmenjen");
				}else if(dodaj) {
					try {
						sviTipoviTretmana = tipTretmanaOperacije.dodajTipTretmana(naziv);
						UcitajPodatke.setSviTipoviTretmana(sviTipoviTretmana);
						tipTretmanaOperacije.sacuvajTipTretmanaFajl();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
				table.setModel(new DefaultTableModel(
						getData(),
						new String[] {
								"ID", "Naziv"
						}));
				
				txtNaziv.setText("");
				txtNaziv.setVisible(false);
				btnPotvrdi.setVisible(false);
			}
		});
		btnPotvrdi.setBounds(427, 290, 78, 23);
		btnPotvrdi.setVisible(false);
		frame.getContentPane().add(btnPotvrdi);
		
		JButton btnNewButton = new JButton("Dodaj");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dodaj = true;
				txtNaziv.setVisible(true);
				btnPotvrdi.setVisible(true);
			}
		});
		btnNewButton.setBounds(413, 51, 103, 31);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnIzmeni = new JButton("Izmeni");
		btnIzmeni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				izmena = true;
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Greska, morate izabrati u tabeli koji tip tretmana zelite da menjate");
				}else {
					idTretmana = Integer.parseInt((String) table.getValueAt(red, 0));
					txtNaziv.setVisible(true);
					btnPotvrdi.setVisible(true);
				}
			}
		});
		btnIzmeni.setBounds(413, 98, 103, 31);
		frame.getContentPane().add(btnIzmeni);
		
		JButton btnObrisi = new JButton("Obrisi");
		btnObrisi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TipTretmanaOperacije tipTretmanaOperacije = new TipTretmanaOperacije(UcitajPodatke.getSviTipoviTretmana());
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Greska, morate izabrati u tabeli koji tip tretmana brisete");
				}else {
					idTretmana = Integer.parseInt((String) table.getValueAt(red, 0));
					List<TipTretmana> sviTipoviTretmana = tipTretmanaOperacije.obrisiTipTretmana(UcitajPodatke.getSviTretmani(), idTretmana);
					UcitajPodatke.setSviTipoviTretmana(sviTipoviTretmana);
					table.setModel(new DefaultTableModel(
							getData(),
							new String[] {
									"ID", "Naziv"
							}
							));
					try {
						tipTretmanaOperacije.sacuvajTipTretmanaFajl();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnObrisi.setBounds(413, 150, 103, 31);
		frame.getContentPane().add(btnObrisi);
		
		JButton btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PocetnaZaposleni pocetnaZaposleni = new PocetnaZaposleni(zaposleni);
				frame.dispose();
			}
		});
		btnOdustani.setBounds(413, 202, 103, 31);
		frame.getContentPane().add(btnOdustani);
		
		txtNaziv = new JTextField();
		txtNaziv.setBounds(416, 259, 100, 20);
		frame.getContentPane().add(txtNaziv);
		txtNaziv.setColumns(10);
		txtNaziv.setVisible(false);
		
		frame.setVisible(true);
	}
	
	public Object[][] getData() {
		List<TipTretmana> sviTipoviTretmana = UcitajPodatke.getSviTipoviTretmana();
		Object[][] data = new Object[sviTipoviTretmana.size()][2];
		
		for(int i = 0; i < sviTipoviTretmana.size(); i++) {
			data[i][0] = Integer.toString(sviTipoviTretmana.get(i).getId());
			data[i][1] = sviTipoviTretmana.get(i).getNaziv();
		}
		
		return data;
	}
}
