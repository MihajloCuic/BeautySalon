package gui;

import javax.swing.JFrame;

import entiteti.Klijent;
import entiteti.Zaposleni;
import obradaPodataka.KlijentOperacije;
import obradaPodataka.UcitajPodatke;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class FormaIzmene {

	private JFrame frame;
	private JTextField txtIme;
	private JTextField txtPrezime;
	private JTextField txtAdresa;
	private JTextField txtTelefon;
	private JPasswordField txtLozinka;

	/**
	 * Create the application.
	 */
	public FormaIzmene(Klijent klijent, Zaposleni zaposleni, boolean menadzerMenja) {
		initialize(klijent, zaposleni, menadzerMenja);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Klijent klijent, Zaposleni zaposleni, boolean menadzerMenja) {
		frame = new JFrame();
		frame.setBounds(100, 100, 330, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblIzmena = new JLabel("Izmena");
		lblIzmena.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblIzmena.setHorizontalAlignment(SwingConstants.CENTER);
		lblIzmena.setBounds(0, 0, 314, 43);
		frame.getContentPane().add(lblIzmena);
		
		JLabel lblKorisnickoIme = new JLabel("");
		lblKorisnickoIme.setHorizontalAlignment(SwingConstants.CENTER);
		lblKorisnickoIme.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblKorisnickoIme.setBounds(91, 54, 122, 14);
		frame.getContentPane().add(lblKorisnickoIme);
		
		JLabel lblLozinka = new JLabel("Lozinka:");
		lblLozinka.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLozinka.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLozinka.setBounds(10, 103, 122, 14);
		frame.getContentPane().add(lblLozinka);
		
		JLabel lblIme = new JLabel("Ime:");
		lblIme.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIme.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIme.setBounds(10, 128, 122, 14);
		frame.getContentPane().add(lblIme);
		
		JLabel lblPrezime = new JLabel("Prezime:");
		lblPrezime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrezime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrezime.setBounds(10, 153, 122, 14);
		frame.getContentPane().add(lblPrezime);
		
		JLabel lblPol = new JLabel("Pol:");
		lblPol.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPol.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPol.setBounds(10, 198, 122, 14);
		frame.getContentPane().add(lblPol);
		
		JLabel lblAdresa = new JLabel("Adresa:");
		lblAdresa.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdresa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAdresa.setBounds(10, 244, 122, 14);
		frame.getContentPane().add(lblAdresa);
		
		JLabel lblTelefon = new JLabel("Telefon:");
		lblTelefon.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelefon.setBounds(10, 269, 122, 14);
		frame.getContentPane().add(lblTelefon);
		
		txtIme = new JTextField();
		txtIme.setText(klijent.getIme());
		txtIme.setColumns(10);
		txtIme.setBounds(142, 127, 86, 20);
		frame.getContentPane().add(txtIme);
		
		txtPrezime = new JTextField();
		txtPrezime.setText(klijent.getPrezime());
		txtPrezime.setColumns(10);
		txtPrezime.setBounds(142, 152, 86, 20);
		frame.getContentPane().add(txtPrezime);
		
		txtAdresa = new JTextField();
		txtAdresa.setText(klijent.getAdresa());
		txtAdresa.setColumns(10);
		txtAdresa.setBounds(142, 243, 86, 20);
		frame.getContentPane().add(txtAdresa);
		
		txtTelefon = new JTextField();
		txtTelefon.setText(klijent.getTelefon());
		txtTelefon.setColumns(10);
		txtTelefon.setBounds(142, 268, 86, 20);
		frame.getContentPane().add(txtTelefon);
		
		txtLozinka = new JPasswordField();
		txtLozinka.setText(klijent.getLozinka());
		txtLozinka.setBounds(142, 102, 86, 20);
		frame.getContentPane().add(txtLozinka);
		
		JRadioButton rdbtnMusko = new JRadioButton("Musko");
		rdbtnMusko.setBounds(142, 189, 86, 23);
		frame.getContentPane().add(rdbtnMusko);
		
		JRadioButton rdbtnZensko = new JRadioButton("Zensko");
		rdbtnZensko.setBounds(142, 213, 86, 23);
		frame.getContentPane().add(rdbtnZensko);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnZensko);
		buttonGroup.add(rdbtnMusko);
		
		if(klijent.getPol().equals("muski")) {
			rdbtnMusko.setSelected(true);
		}else if(klijent.getPol().equals("zenski")) {
			rdbtnZensko.setSelected(true);
		}
		
		JButton btnIzmena = new JButton("Izmeni");
		btnIzmena.setFocusable(false);
		btnIzmena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KlijentOperacije klijentOperacije = new KlijentOperacije(UcitajPodatke.getSviKlijenti());
				String pol = "";
				if(rdbtnMusko.isSelected()) {
					pol += "muski";
				}else if(rdbtnZensko.isSelected()) {
					pol += "zenski";
				}
				try {
					List<Klijent> sviKlijenti = klijentOperacije.izmeniKlijenta(klijent.getKorisnickoIme(), String.valueOf(txtLozinka.getPassword()), txtIme.getText(), txtPrezime.getText(), pol, txtTelefon.getText(), txtAdresa.getText(), klijent.getKarticaLojalnosti().getPotrosenIznos(), klijent.getKarticaLojalnosti().getPravoNaPopust());
					UcitajPodatke.setSviKlijenti(sviKlijenti);
					klijentOperacije.sacuvajKlijenteFajl();
					for(Klijent k: sviKlijenti) {
						if(klijent.getKorisnickoIme().equals(k.getKorisnickoIme())) {
							if(menadzerMenja) {
								JOptionPane.showMessageDialog(null, "Uspesna izmena!");
								PocetnaZaposleni pocetnaZaposleni = new PocetnaZaposleni(zaposleni);
								frame.dispose();
							}else {
								JOptionPane.showMessageDialog(null, "Uspesna izmena!");
								PocetnaKlijent pocetnaKlijent = new PocetnaKlijent(k);
								frame.dispose();
							}
						}
					}
				} catch (Exception e1) {
					String greska = e1.getMessage();
					switch(greska) {
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
		btnIzmena.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnIzmena.setBounds(103, 328, 89, 23);
		frame.getContentPane().add(btnIzmena);
		
		JButton btnOdustani = new JButton("Odustani");
		btnOdustani.setFocusable(false);
		btnOdustani.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menadzerMenja) {
					PocetnaZaposleni pocetnaZaposleni = new PocetnaZaposleni(zaposleni);
					frame.dispose();
				}else {
					PocetnaKlijent pocetnaKlijent = new PocetnaKlijent(klijent);
					frame.dispose();
				}
			}
		});
		btnOdustani.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOdustani.setBounds(103, 362, 89, 23);
		frame.getContentPane().add(btnOdustani);
		frame.setVisible(true);
	}

}
