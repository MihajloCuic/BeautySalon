package gui;

import javax.swing.JFrame;

import entiteti.KozmetickiSalon;
import entiteti.Zaposleni;
import obradaPodataka.KozmetickiSalonOperacije;
import obradaPodataka.UcitajPodatke;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class IzmenaKozmetickogSalona {

	private JFrame frame;
	private JTextField txtImeSalona;
	private JTextField txtPocetakRadnogVremena;
	private JTextField txtKrajRadnogVremena;
	private JTextField txtIznosZaPopust;
	/**
	 * Create the application.
	 */
	public IzmenaKozmetickogSalona(Zaposleni zaposleni) {
		initialize(zaposleni);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Zaposleni zaposleni) {
		frame = new JFrame();
		frame.setBounds(100, 100, 580, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblImeSalona = new JLabel("Ime:");
		lblImeSalona.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblImeSalona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblImeSalona.setBounds(0, 35, 140, 29);
		frame.getContentPane().add(lblImeSalona);
		
		JLabel lblPocetakRadnogVremena = new JLabel("Pocetak radnog vremena:");
		lblPocetakRadnogVremena.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPocetakRadnogVremena.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPocetakRadnogVremena.setBounds(0, 90, 221, 29);
		frame.getContentPane().add(lblPocetakRadnogVremena);
		
		JLabel lblKrajRadnogVremena = new JLabel("Kraj radnog vremena:");
		lblKrajRadnogVremena.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKrajRadnogVremena.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblKrajRadnogVremena.setBounds(0, 145, 221, 29);
		frame.getContentPane().add(lblKrajRadnogVremena);
		
		txtImeSalona = new JTextField(UcitajPodatke.getKozmetickiSalon().getNaziv());
		txtImeSalona.setBounds(150, 42, 227, 20);
		frame.getContentPane().add(txtImeSalona);
		txtImeSalona.setColumns(10);
		
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		txtPocetakRadnogVremena = new JTextField(UcitajPodatke.getKozmetickiSalon().getPocetakRadnogVremena().format(timeFormatter));
		txtPocetakRadnogVremena.setColumns(10);
		txtPocetakRadnogVremena.setBounds(231, 97, 146, 20);
		frame.getContentPane().add(txtPocetakRadnogVremena);
		
		txtKrajRadnogVremena = new JTextField(UcitajPodatke.getKozmetickiSalon().getKrajRadnogVremena().format(timeFormatter));
		txtKrajRadnogVremena.setColumns(10);
		txtKrajRadnogVremena.setBounds(231, 152, 146, 20);
		frame.getContentPane().add(txtKrajRadnogVremena);
		
		txtIznosZaPopust = new JTextField(Double.toString(UcitajPodatke.getKozmetickiSalon().getIznosZaPopust()));
		txtIznosZaPopust.setBounds(304, 198, 73, 20);
		frame.getContentPane().add(txtIznosZaPopust);
		txtIznosZaPopust.setColumns(10);
		
		JButton btnIzmeni = new JButton("Izmeni");
		btnIzmeni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String naziv = txtImeSalona.getText();
				LocalTime pocetakRadnogVremena = LocalTime.parse(txtPocetakRadnogVremena.getText(), timeFormatter);
				LocalTime krajRadnogVremena = LocalTime.parse(txtKrajRadnogVremena.getText(), timeFormatter);
				double iznosZaPopust = Double.parseDouble(txtIznosZaPopust.getText());
				
				KozmetickiSalon kozmetickiSalon = new KozmetickiSalon(naziv, pocetakRadnogVremena, krajRadnogVremena, iznosZaPopust);
				UcitajPodatke.setKozmetickiSalon(kozmetickiSalon);
				KozmetickiSalonOperacije kozmetickiSalonOperacije = new KozmetickiSalonOperacije();
				try {
					kozmetickiSalonOperacije.sacuvajKozmetickiTretmanFajl(kozmetickiSalon);
					JOptionPane.showMessageDialog(null, "Uspesna izmena kozmetickog Salona");
					PocetnaZaposleni pocetnaZaposleni = new PocetnaZaposleni(zaposleni);
					frame.dispose();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Neuspesna izmena!");
					PocetnaZaposleni pocetnaZaposleni = new PocetnaZaposleni(zaposleni);
					frame.dispose();
				}
			}
		});
		btnIzmeni.setBounds(421, 61, 106, 38);
		frame.getContentPane().add(btnIzmeni);
		
		JButton btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PocetnaZaposleni zaposleniPocetna = new PocetnaZaposleni(zaposleni);
				frame.dispose();
			}
		});
		btnOdustani.setBounds(421, 124, 106, 38);
		frame.getContentPane().add(btnOdustani);
		
		JLabel lblMinimalnaSumaZa = new JLabel("Minimalna suma za karticu lojalnosti:");
		lblMinimalnaSumaZa.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMinimalnaSumaZa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMinimalnaSumaZa.setBounds(0, 191, 299, 29);
		frame.getContentPane().add(lblMinimalnaSumaZa);
		
		frame.setVisible(true);
	}
}
