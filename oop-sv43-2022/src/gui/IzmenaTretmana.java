package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import entiteti.TipTretmana;
import entiteti.Tretman;
import entiteti.Zaposleni;
import obradaPodataka.TretmanOperacije;
import obradaPodataka.UcitajPodatke;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;

public class IzmenaTretmana {

	private JFrame frame;
	private JTable table;
	/**
	 * Create the application.
	 */
	public IzmenaTretmana(Zaposleni zaposleni) {
		initialize(zaposleni);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Zaposleni zaposleni) {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 72, 349, 206);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			getData(),
			new String[] {
				"ID", "Naziv", "Tip tretmana", "Cena", "Trajanje tretmana"
			}
		));
		
		JButton btnDodaj = new JButton("Dodaj");
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tretman tretman = new Tretman(-1, null, null, -1, null);
				FormaTretmani formaTretmani = new FormaTretmani(zaposleni, tretman, true);
				frame.dispose();
			}
		});
		btnDodaj.setBounds(447, 80, 89, 23);
		frame.getContentPane().add(btnDodaj);
		
		JButton btnIzmeni = new JButton("Izmeni");
		btnIzmeni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				if(red == -1) {
					JOptionPane.showMessageDialog(null, "Greska, morate izabrati u tabeli tretman koji zelite da izmenite");
				}else {
					int idTretmana = Integer.parseInt((String) table.getValueAt(red, 0));
					for(Tretman tretman: UcitajPodatke.getSviTretmani()) {
						if(tretman.getId() == idTretmana) {
							FormaTretmani formaTretman = new FormaTretmani(zaposleni, tretman, false);
							frame.dispose();
						}
					}
				}
				
			}
		});
		btnIzmeni.setBounds(447, 124, 89, 23);
		frame.getContentPane().add(btnIzmeni);
		
		JButton btnObrisi = new JButton("Obrisi");
		btnObrisi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int red = table.getSelectedRow();
				int idTretmana = Integer.parseInt((String) table.getValueAt(red, 0));
				List<Tretman> sviTretmani = UcitajPodatke.getSviTretmani();
				TretmanOperacije tretmanOperacije = new TretmanOperacije(sviTretmani);
				sviTretmani = tretmanOperacije.obrisiTretman(idTretmana);
				UcitajPodatke.setSviTretmani(sviTretmani);
				try {
					tretmanOperacije.sacuvajTretmaneFajl();
				} catch (IOException e1) {
					System.out.println(e1.getMessage());;
				}
				table.setModel(new DefaultTableModel(
						getData(),
						new String[] {
							"ID", "Naziv", "Tip tretmana", "Cena", "Trajanje tretmana"
						}));
			}
		});
		btnObrisi.setBounds(447, 172, 89, 23);
		frame.getContentPane().add(btnObrisi);
		
		JButton btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PocetnaZaposleni pocetnaZaposleni = new PocetnaZaposleni(zaposleni);
				frame.dispose();
			}
		});
		btnOdustani.setBounds(447, 220, 89, 23);
		frame.getContentPane().add(btnOdustani);
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		frame.dispose();
		frame.setVisible(true);
	}

	public Object[][] getData() {
		List<Tretman> sviTretmani = UcitajPodatke.getSviTretmani();
		Object[][] data = new Object[sviTretmani.size()][5];
		for(int i = 0; i <sviTretmani.size(); i++) {
			data[i][0] = Integer.toString(sviTretmani.get(i).getId());
			data[i][1] = sviTretmani.get(i).getNaziv();
			data[i][2] = sviTretmani.get(i).getTipTretmana();
			data[i][3] = Double.toString(sviTretmani.get(i).getCena());
			data[i][4] = sviTretmani.get(i).getTrajanjeTretmana() + "min";
		}
		return data;
	}
}
