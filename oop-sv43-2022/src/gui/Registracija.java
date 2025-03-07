package gui;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import entiteti.Klijent;
import entiteti.Zaposleni;
import obradaPodataka.KlijentOperacije;
import obradaPodataka.UcitajPodatke;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class Registracija {

	private JFrame frame;
	private JTextField txtIme;
	private JTextField txtPrezime;
	private JTextField txtKorisnickoIme;
	private JTextField txtAdresa;
	private JTextField txtTelefon;
	private JPasswordField txtLozinka;

	/**
	 * Create the application.
	 */
	public Registracija(Zaposleni zaposleni, boolean menadzerRegistruje) {
		initialize(zaposleni, menadzerRegistruje);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Zaposleni zaposleni, boolean menadzerRegistruje) {
		frame = new JFrame();
		frame.setBounds(100, 100, 350, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblRegistracija = new JLabel("Registracija");
		lblRegistracija.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblRegistracija.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistracija.setBounds(0, 0, 334, 48);
		frame.getContentPane().add(lblRegistracija);
		
		JLabel lblIme = new JLabel("Ime:");
		lblIme.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIme.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIme.setBounds(27, 99, 93, 14);
		frame.getContentPane().add(lblIme);
		
		JLabel lblPrezime = new JLabel("Prezime:");
		lblPrezime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrezime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrezime.setBounds(27, 124, 93, 14);
		frame.getContentPane().add(lblPrezime);
		
		JLabel lblKorisnickoIme = new JLabel("Korisnicko ime:");
		lblKorisnickoIme.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKorisnickoIme.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblKorisnickoIme.setBounds(10, 149, 110, 14);
		frame.getContentPane().add(lblKorisnickoIme);
		
		JLabel lblLozinka = new JLabel("Lozinka:");
		lblLozinka.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLozinka.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblLozinka.setBounds(27, 174, 93, 14);
		frame.getContentPane().add(lblLozinka);
		
		JLabel lblPol = new JLabel("Pol:");
		lblPol.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPol.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPol.setBounds(27, 228, 93, 14);
		frame.getContentPane().add(lblPol);
		
		JLabel lblAdresa = new JLabel("Adresa:");
		lblAdresa.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdresa.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAdresa.setBounds(27, 284, 93, 14);
		frame.getContentPane().add(lblAdresa);
		
		JLabel lblTelefon = new JLabel("Telefon:");
		lblTelefon.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefon.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTelefon.setBounds(27, 309, 93, 14);
		frame.getContentPane().add(lblTelefon);
		
		txtIme = new JTextField();
		txtIme.setBounds(130, 98, 90, 20);
		frame.getContentPane().add(txtIme);
		txtIme.setColumns(10);
		
		txtPrezime = new JTextField();
		txtPrezime.setColumns(10);
		txtPrezime.setBounds(130, 123, 90, 20);
		frame.getContentPane().add(txtPrezime);
		
		txtKorisnickoIme = new JTextField();
		txtKorisnickoIme.setColumns(10);
		txtKorisnickoIme.setBounds(130, 148, 90, 20);
		frame.getContentPane().add(txtKorisnickoIme);
		
		txtAdresa = new JTextField();
		txtAdresa.setColumns(10);
		txtAdresa.setBounds(130, 283, 90, 20);
		frame.getContentPane().add(txtAdresa);
		
		txtTelefon = new JTextField();
		txtTelefon.setColumns(10);
		txtTelefon.setBounds(130, 308, 90, 20);
		frame.getContentPane().add(txtTelefon);
		
		JRadioButton rdbtnMusko = new JRadioButton("Musko");
		rdbtnMusko.setBounds(137, 211, 83, 23);
		frame.getContentPane().add(rdbtnMusko);
		
		JRadioButton rdbtnZensko = new JRadioButton("Zensko");
		rdbtnZensko.setBounds(137, 240, 83, 23);
		frame.getContentPane().add(rdbtnZensko);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnZensko);
		buttonGroup.add(rdbtnMusko);
		
		txtLozinka = new JPasswordField();
		txtLozinka.setBounds(130, 173, 90, 20);
		frame.getContentPane().add(txtLozinka);
		
		JButton btnSubmit =  new JButton("Submit");
		btnSubmit.setFocusable(false);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pol;
				if(rdbtnMusko.isSelected()) {
					pol = "muski";
				}
				else if(rdbtnZensko.isSelected()) {
					pol = "zenski";
				}else {
					pol = "neodredjen";
				}
				KlijentOperacije klijentOperacije = new KlijentOperacije(UcitajPodatke.getSviKlijenti());
				try {
					klijentOperacije.dodajKlijenta(txtKorisnickoIme.getText(), String.valueOf(txtLozinka.getPassword()), txtIme.getText(), txtPrezime.getText(), pol, txtTelefon.getText(), txtAdresa.getText(), 0, false);
					klijentOperacije.sacuvajKlijenteFajl();
					UcitajPodatke.setSviKlijenti(UcitajPodatke.getSviKlijenti());
					if(menadzerRegistruje) {
						PocetnaZaposleni pocetnaZaposleni = new PocetnaZaposleni(zaposleni);
						frame.dispose();
					}else {
						Login login = new Login();
						frame.dispose();
					}
				} catch (Exception e1) {
					String greska = e1.getMessage();
					switch(greska) {
					case "KorisnickoIme":
						lblKorisnickoIme.setForeground(Color.red);
						break;
					case "Lozinka":
						lblLozinka.setForeground(Color.red);
						break;
					case "Ime":
						lblIme.setForeground(Color.red);
						break;
					case "Prezime":
						lblPrezime.setForeground(Color.red);
						break;
					case "Pol":
						lblPol.setForeground(Color.red);
						break;
					case "Adresa":
						lblAdresa.setForeground(Color.red);
						break;
					case "Telefon":
						lblTelefon.setForeground(Color.red);
						break;
					default:
						lblKorisnickoIme.setText("Greska, nije doslo do izmene!");
						lblKorisnickoIme.setForeground(Color.red);
						break;
					}
				}
			}
		});
		
		btnSubmit.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnSubmit.setBounds(29, 367, 110, 23);
		frame.getContentPane().add(btnSubmit);
		
		JButton btnOdustani = new JButton("Odustani");
		btnOdustani.setFocusable(false);
		btnOdustani.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				frame.dispose();
			}
		});
		btnOdustani.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnOdustani.setBounds(181, 367, 110, 23);
		frame.getContentPane().add(btnOdustani);
		frame.setVisible(true);
	}
}
