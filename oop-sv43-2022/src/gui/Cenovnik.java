package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entiteti.Klijent;
import entiteti.Zaposleni;
import obradaPodataka.KozmetickiSalonOperacije;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.event.ActionEvent;

public class Cenovnik {

	private JFrame frame;
	private JTable table;
	/**
	 * Create the application.
	 */
	public Cenovnik() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 261, 416);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCenovnik = new JLabel("Cenovnik");
		lblCenovnik.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblCenovnik.setHorizontalAlignment(SwingConstants.CENTER);
		lblCenovnik.setBounds(0, 0, 245, 52);
		frame.getContentPane().add(lblCenovnik);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 72, 245, 305);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setRowSelectionAllowed(false);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			getData(),
			new String[] {
				"Naziv", "Cena"
			}
		));
		frame.setVisible(true);
	}
	
	public Object[][] getData() {
		KozmetickiSalonOperacije kozmetickiSalonOperacije = new KozmetickiSalonOperacije();
		HashMap<String, Double> cenovnik = kozmetickiSalonOperacije.UcitajCenovnik();
		Object[][] data = new Object[cenovnik.size()][2];
		int i = 0;
		for(String item: cenovnik.keySet()) {
			data[i][0] = item;
			data[i][1] = cenovnik.get(item) + "din";
			i++;
		}
		return data;
	}
}
