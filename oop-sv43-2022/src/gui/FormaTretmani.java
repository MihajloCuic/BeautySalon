package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import entiteti.TipTretmana;
import entiteti.Tretman;
import entiteti.Zaposleni;
import obradaPodataka.TretmanOperacije;
import obradaPodataka.UcitajPodatke;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;

public class FormaTretmani {

	private JFrame frame;
	private JTextField txtNaziv;
	private JTextField txtCena;
	private JTextField txtTrajanjeTretmana;
	private boolean dodaj = false;
	/**
	 * Create the application.
	 */
	public FormaTretmani(Zaposleni zaposleni, Tretman tretman, boolean dodaj) {
		initialize(zaposleni, tretman);
		this.dodaj = dodaj;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Zaposleni zaposleni, Tretman tretman) {
		frame = new JFrame();
		frame.setBounds(100, 100, 360, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTretman = new JLabel("Tretman");
		lblTretman.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTretman.setHorizontalAlignment(SwingConstants.CENTER);
		lblTretman.setBounds(0, 0, 344, 45);
		frame.getContentPane().add(lblTretman);
		
		JLabel lblNaziv = new JLabel("Naziv:");
		lblNaziv.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNaziv.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNaziv.setBounds(10, 79, 120, 24);
		frame.getContentPane().add(lblNaziv);
		
		JLabel lblTipTretmana = new JLabel("Tip tretmana:");
		lblTipTretmana.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipTretmana.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTipTretmana.setBounds(10, 114, 120, 24);
		frame.getContentPane().add(lblTipTretmana);
		
		JLabel lblCena = new JLabel("Cena:");
		lblCena.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCena.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCena.setBounds(10, 149, 120, 24);
		frame.getContentPane().add(lblCena);
		
		JLabel lblTrajanjeTretmana = new JLabel("Trajanje tretmana:");
		lblTrajanjeTretmana.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTrajanjeTretmana.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTrajanjeTretmana.setBounds(10, 184, 120, 24);
		frame.getContentPane().add(lblTrajanjeTretmana);
		
		txtNaziv = new JTextField(tretman.getNaziv());
		txtNaziv.setBounds(152, 83, 86, 20);
		frame.getContentPane().add(txtNaziv);
		txtNaziv.setColumns(10);
		
		txtCena = new JTextField(Double.toString(tretman.getCena()));
		txtCena.setColumns(10);
		txtCena.setBounds(152, 153, 86, 20);
		frame.getContentPane().add(txtCena);
		
		txtTrajanjeTretmana = new JTextField(tretman.getTrajanjeTretmana());
		txtTrajanjeTretmana.setColumns(10);
		txtTrajanjeTretmana.setBounds(152, 188, 86, 20);
		frame.getContentPane().add(txtTrajanjeTretmana);
		
		JComboBox cbTipTretmana = new JComboBox();
		cbTipTretmana.setBounds(152, 117, 86, 22);
		frame.getContentPane().add(cbTipTretmana);
		
		for(TipTretmana tipTretmana: UcitajPodatke.getSviTipoviTretmana()) {
			cbTipTretmana.addItem(tipTretmana.getNaziv());
		}
		
		if(tretman.getTipTretmana() != null) {
			for(int i = 0; i < cbTipTretmana.getItemCount(); i++) {
				if(tretman.getTipTretmana().equals((String)cbTipTretmana.getItemAt(i))) {
					cbTipTretmana.setSelectedIndex(i);
					break;
				}
			}
		}
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Tretman> sviTretmani = UcitajPodatke.getSviTretmani();
				TretmanOperacije tretmanOperacije = new TretmanOperacije(sviTretmani);
				if(dodaj) {
					try {
						sviTretmani = tretmanOperacije.dodajTretman(txtNaziv.getText(), txtTrajanjeTretmana.getText(), Double.parseDouble(txtCena.getText()), (String) cbTipTretmana.getSelectedItem());
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}else {
					try {
						sviTretmani = tretmanOperacije.izmeniTretman(tretman.getId(), txtNaziv.getText(), txtTrajanjeTretmana.getText(), Double.parseDouble(txtCena.getText()), (String) cbTipTretmana.getSelectedItem());
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				UcitajPodatke.setSviTretmani(sviTretmani);
				try {
					tretmanOperacije.sacuvajTretmaneFajl();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				IzmenaTretmana izmenaTretmana = new IzmenaTretmana(zaposleni);
				frame.dispose();
			}
		});
		btnSubmit.setBounds(51, 245, 89, 23);
		frame.getContentPane().add(btnSubmit);
		
		JButton btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IzmenaTretmana izmenaTretmana = new IzmenaTretmana(zaposleni);
				frame.dispose();
			}
		});
		btnOdustani.setBounds(164, 245, 89, 23);
		frame.getContentPane().add(btnOdustani);
		frame.dispose();
		frame.setVisible(true);
	}
}
