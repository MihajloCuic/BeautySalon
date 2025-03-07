package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import entiteti.TipTretmana;
import entiteti.Zaposleni;
import obradaPodataka.UcitajPodatke;

import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class DodavanjeTretmanaKozmeticaru {

	private JFrame frame;
	private JTable table;
	private JButton btnDodeliTretmane;
	private JButton btnOdustani;
	private List<String> tretmani;
	/**
	 * Create the application.
	 */
	public List<String> DodavanjeTretmanaKozmeticaru(Zaposleni menadzer, Zaposleni kozmeticar) {
		return initialize(menadzer, kozmeticar);
	}

	/**
	 * Initialize the contents of the frame.
	 * @return 
	 */
	private List<String> initialize(Zaposleni menadzer, Zaposleni kozmeticar) {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 370);
		frame.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(74, 65, 292, 187);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			getData(),
			new String[] {
				"ID", "Naziv"
			}
		));
		scrollPane.setViewportView(table);
		
		btnDodeliTretmane = new JButton("Dodeli tretmane");
		btnDodeliTretmane.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<TipTretmana> sviTipoviTretmana = UcitajPodatke.getSviTipoviTretmana();
				tretmani = new ArrayList<>();
				int[] izabraniRedovi = table.getSelectedRows();
				for(int red: izabraniRedovi) {
					tretmani.add(sviTipoviTretmana.get(red).getNaziv());
				}
				kozmeticar.setTretman(tretmani);
				frame.dispose();
			}
		});
		btnDodeliTretmane.setBounds(84, 270, 124, 39);
		btnDodeliTretmane.setFocusable(false);
		frame.getContentPane().add(btnDodeliTretmane);
		
		btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnOdustani.setBounds(228, 270, 124, 39);
		btnOdustani.setFocusable(false);
		frame.getContentPane().add(btnOdustani);
		frame.setVisible(true);
		
		return tretmani;
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
