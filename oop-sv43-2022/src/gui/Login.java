package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entiteti.Klijent;
import entiteti.Korisnik;
import entiteti.Zaposleni;
import obradaPodataka.KorisnikOperacije;
import obradaPodataka.UcitajPodatke;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {
	private JFrame frame;
	private JPanel contentPane;
	private JTextField txtKorisnickoIme;
	private JPasswordField txtLozinka;

	/**
	 * Create the frame.
	 */
	public Login() {
		initialize();
	}
	
	public void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 466, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		frame.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNaslov = new JLabel("Kozmeticki Salon");
		lblNaslov.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNaslov.setHorizontalAlignment(SwingConstants.CENTER);
		lblNaslov.setBounds(0, 24, 450, 59);
		contentPane.add(lblNaslov);
		
		JLabel lblKorisnickoIme = new JLabel("Korisnicko ime:");
		lblKorisnickoIme.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblKorisnickoIme.setHorizontalAlignment(SwingConstants.CENTER);
		lblKorisnickoIme.setBounds(146, 119, 148, 14);
		contentPane.add(lblKorisnickoIme);
		
		JLabel lblLozinka = new JLabel("Lozinka:");
		lblLozinka.setHorizontalAlignment(SwingConstants.CENTER);
		lblLozinka.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLozinka.setBounds(146, 175, 148, 14);
		contentPane.add(lblLozinka);
		
		JLabel lblPogresnaLozinka = new JLabel("");
		lblPogresnaLozinka.setHorizontalAlignment(SwingConstants.CENTER);
		lblPogresnaLozinka.setForeground(Color.RED);
		lblPogresnaLozinka.setBounds(0, 231, 450, 14);
		contentPane.add(lblPogresnaLozinka);
		
		txtKorisnickoIme = new JTextField();
		txtKorisnickoIme.setBounds(146, 144, 148, 20);
		contentPane.add(txtKorisnickoIme);
		txtKorisnickoIme.setColumns(10);
		
		txtLozinka = new JPasswordField();
		txtLozinka.setBounds(146, 200, 148, 20);
		contentPane.add(txtLozinka);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setFocusable(false);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KorisnikOperacije korisnikOperacije = new KorisnikOperacije();
				Korisnik obj = korisnikOperacije.login(UcitajPodatke.getSviKlijenti(), UcitajPodatke.getSviZaposleni(), txtKorisnickoIme.getText(), String.valueOf(txtLozinka.getPassword()));
				if(obj instanceof Klijent) {
					Klijent klijent = (Klijent) obj;
					PocetnaKlijent pocetnaKlijent = new PocetnaKlijent(klijent);
					frame.dispose();
					
				}else if(obj instanceof Zaposleni) {
					Zaposleni zaposleni = (Zaposleni) obj;
					PocetnaZaposleni pocetnaZaposleni = new PocetnaZaposleni(zaposleni);
					frame.dispose();
				}else {
					txtKorisnickoIme.setText("");
					txtLozinka.setText("");
					lblPogresnaLozinka.setText("Pogrešno korisničko ime ili lozinka!");
				}
			}
		});
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLogin.setBounds(156, 256, 123, 23);
		contentPane.add(btnLogin);
		
		JButton btnRegistracija = new JButton("Registracija");
		btnRegistracija.setFocusable(false);
		btnRegistracija.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Zaposleni radnik = new Zaposleni(null, null, null, null,"",null,null,null,null, 0,0,0);
				Registracija registracija = new Registracija(radnik, false);
				frame.dispose();
			}
		});

		btnRegistracija.setBounds(156, 290, 123, 23);
		contentPane.add(btnRegistracija);
		
		frame.setVisible(true);
	}
}
