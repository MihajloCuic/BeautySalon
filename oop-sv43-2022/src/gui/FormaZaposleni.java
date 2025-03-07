package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import entiteti.ComboBox;
import entiteti.Zaposleni;
import obradaPodataka.UcitajPodatke;
import obradaPodataka.ZaposleniOperacije;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import util.Uloga;
import util.StrucnaSprema;

public class FormaZaposleni {

	private JFrame frame;
	private JTextField txtIme;
	private JTextField txtPrezime;
	private JTextField txtTelefon;
	private JTextField txtAdresa;
	private JTextField txtGodineStaza;
	private JTextField txtPlata;
	private JTextField txtBonus;
	private JPasswordField txtLozinka;
	private JComboBox cbTipoviTretmana;
	/**
	 * Create the application.
	 */
	public FormaZaposleni(Zaposleni menadzer, Zaposleni radnik) {
		initialize(menadzer, radnik);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Zaposleni menadzer, Zaposleni radnik) {
		frame = new JFrame();
		frame.setBounds(100, 100, 350, 635);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblForma = new JLabel("Forma");
		lblForma.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblForma.setHorizontalAlignment(SwingConstants.CENTER);
		lblForma.setBounds(0, 0, 312, 58);
		frame.getContentPane().add(lblForma);
		
		JLabel lblIme = new JLabel("Ime:");
		lblIme.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblIme.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIme.setBounds(0, 113, 131, 14);
		frame.getContentPane().add(lblIme);
		
		JLabel lblPrezime = new JLabel("Prezime:");
		lblPrezime.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPrezime.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrezime.setBounds(0, 138, 131, 14);
		frame.getContentPane().add(lblPrezime);
		
		JLabel lblLozinka = new JLabel("Lozinka:");
		lblLozinka.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblLozinka.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLozinka.setBounds(0, 163, 131, 14);
		frame.getContentPane().add(lblLozinka);
		
		JLabel lblTelefon = new JLabel("Telefon:");
		lblTelefon.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTelefon.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefon.setBounds(0, 188, 131, 14);
		frame.getContentPane().add(lblTelefon);
		
		JLabel lblAdresa = new JLabel("Adresa:");
		lblAdresa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAdresa.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdresa.setBounds(0, 213, 131, 14);
		frame.getContentPane().add(lblAdresa);
		
		JLabel lblPol = new JLabel("Pol:");
		lblPol.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPol.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPol.setBounds(0, 253, 131, 14);
		frame.getContentPane().add(lblPol);
		
		JLabel lblGodineStaza = new JLabel("Godine staza:");
		lblGodineStaza.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblGodineStaza.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGodineStaza.setBounds(0, 293, 131, 14);
		frame.getContentPane().add(lblGodineStaza);
		
		JLabel lblStrucnaSprema = new JLabel("Strucna sprema:");
		lblStrucnaSprema.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblStrucnaSprema.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStrucnaSprema.setBounds(0, 318, 131, 21);
		frame.getContentPane().add(lblStrucnaSprema);
		
		JLabel lblPlata = new JLabel("Plata:");
		lblPlata.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPlata.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlata.setBounds(0, 343, 131, 14);
		frame.getContentPane().add(lblPlata);
		
		JLabel lblBonus = new JLabel("Bonus:");
		lblBonus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBonus.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBonus.setBounds(0, 368, 131, 14);
		frame.getContentPane().add(lblBonus);
		
		txtIme = new JTextField(radnik.getIme());
		txtIme.setBounds(141, 113, 94, 20);
		frame.getContentPane().add(txtIme);
		txtIme.setColumns(10);
		
		txtPrezime = new JTextField(radnik.getPrezime());
		txtPrezime.setColumns(10);
		txtPrezime.setBounds(141, 138, 94, 20);
		frame.getContentPane().add(txtPrezime);
		
		txtTelefon = new JTextField(radnik.getTelefon());
		txtTelefon.setColumns(10);
		txtTelefon.setBounds(141, 188, 94, 20);
		frame.getContentPane().add(txtTelefon);
		
		txtAdresa = new JTextField(radnik.getAdresa());
		txtAdresa.setColumns(10);
		txtAdresa.setBounds(141, 213, 94, 20);
		frame.getContentPane().add(txtAdresa);
		
		txtGodineStaza = new JTextField(Integer.toString(radnik.getGodineStaza()));
		txtGodineStaza.setColumns(10);
		txtGodineStaza.setBounds(141, 293, 94, 20);
		frame.getContentPane().add(txtGodineStaza);
		
		txtPlata = new JTextField(Double.toString(radnik.getPlata()));
		txtPlata.setColumns(10);
		txtPlata.setBounds(141, 343, 94, 20);
		frame.getContentPane().add(txtPlata);
		
		txtBonus = new JTextField(Double.toString(radnik.getBonus()));
		txtBonus.setColumns(10);
		txtBonus.setBounds(141, 368, 94, 20);
		frame.getContentPane().add(txtBonus);
		
		txtLozinka = new JPasswordField(radnik.getLozinka());
		txtLozinka.setBounds(141, 163, 94, 20);
		frame.getContentPane().add(txtLozinka);
		
		JComboBox cbStrucnaSprema = new JComboBox();
		cbStrucnaSprema.setModel(new DefaultComboBoxModel(StrucnaSprema.values()));
		if(radnik.getStrucnaSprema() != null) {
			switch(radnik.getStrucnaSprema().name()) {
				case "OSNOVNA":
					cbStrucnaSprema.setSelectedIndex(0);
					break;
				case "SREDNJA":
					cbStrucnaSprema.setSelectedIndex(1);
					break;
				case "VISA":
					cbStrucnaSprema.setSelectedIndex(2);
					break;
				case "FAKULTET":
					cbStrucnaSprema.setSelectedIndex(3);
			}
		}
		cbStrucnaSprema.setBounds(141, 317, 94, 22);
		frame.getContentPane().add(cbStrucnaSprema);
		
		JRadioButton rdbtnMuski = new JRadioButton("Muski");
		rdbtnMuski.setBounds(137, 240, 90, 23);
		frame.getContentPane().add(rdbtnMuski);
		
		JRadioButton rdbtnZenski = new JRadioButton("Zenski");
		rdbtnZenski.setBounds(137, 266, 90, 23);
		frame.getContentPane().add(rdbtnZenski);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnZenski);
		buttonGroup.add(rdbtnMuski);
		
		if(radnik.getPol().equals("muski")) {
			rdbtnMuski.setSelected(true);
		} else if(radnik.getPol().equals("zenski")) {
			rdbtnZenski.setSelected(true);
		}
		
		JLabel lblUloga = new JLabel("Uloga:");
		lblUloga.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUloga.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUloga.setBounds(0, 393, 131, 21);
		frame.getContentPane().add(lblUloga);
		
		JComboBox cbUloga = new JComboBox();
		cbUloga.setModel(new DefaultComboBoxModel(Uloga.values()));
		if(radnik.getUloga() != null) {
			switch(radnik.getUloga().name()) {
				case "MENADZER":
					cbUloga.setSelectedIndex(0);
					break;
				case "KOZMETICAR":
					cbUloga.setSelectedIndex(1);
					break;
				case "RECEPCIONER":
					cbUloga.setSelectedIndex(2);
					break;
			}
		}
		cbUloga.setBounds(141, 392, 94, 22);
		frame.getContentPane().add(cbUloga);
		
		cbTipoviTretmana = new JComboBox();
		if(radnik.getTretman() != null) {
			for(String tretman: radnik.getTretman()) {
				cbTipoviTretmana.addItem(tretman);
			}
		}
		cbTipoviTretmana.setBounds(141, 425, 94, 22);
		frame.getContentPane().add(cbTipoviTretmana);
		
		JLabel lblTipoviTretmana = new JLabel("Tipovi tretmana:");
		lblTipoviTretmana.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipoviTretmana.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblTipoviTretmana.setBounds(0, 425, 131, 21);
		frame.getContentPane().add(lblTipoviTretmana);
		
		JButton btnIzracunajPlatu = new JButton("Izracunaj platu");
		btnIzracunajPlatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StrucnaSprema strucnaSprema = (StrucnaSprema) cbStrucnaSprema.getSelectedItem();
				ZaposleniOperacije zaposleniOperacije = new ZaposleniOperacije(UcitajPodatke.getSviZaposleni());
				double plata = zaposleniOperacije.izracunajPlatu(strucnaSprema, Double.parseDouble(txtBonus.getText()), Integer.parseInt(txtGodineStaza.getText()));
				txtPlata.setText(Double.toString(plata));
			}
		});
		btnIzracunajPlatu.setBounds(10, 477, 131, 38);
		btnIzracunajPlatu.setFocusable(false);
		frame.getContentPane().add(btnIzracunajPlatu);
		
		JComboBox cbTipBonusa = new JComboBox();
		cbTipBonusa.setModel(new DefaultComboBoxModel(new String[] {"tip", "zarada", "ucinkovitost"}));
		cbTipBonusa.setBounds(246, 367, 75, 22);
		frame.getContentPane().add(cbTipBonusa);
		
		JButton btnIzracunajBonus = new JButton("Izracunaj\r \nbonus");
		btnIzracunajBonus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radnik.getKorisnickoIme() == null) {
					JOptionPane.showMessageDialog(null, "Ne mozete dodeliti bonus zaposlenom koji jos nije upisan u sistem!");
				}else {
					String tipBonusa = (String) cbTipBonusa.getSelectedItem();
					LocalDate datum = LocalDate.now();
					int mesec = datum.getMonthValue();
					ZaposleniOperacije zaposleniOperacije = new ZaposleniOperacije(UcitajPodatke.getSviZaposleni());
					double bonus = zaposleniOperacije.obracunajBonusZaMesec(UcitajPodatke.getSviZakazaniTretmani(), radnik.getUloga(), mesec, tipBonusa);
					txtBonus.setText(Double.toString(bonus));
				}
			}
		});
		btnIzracunajBonus.setBounds(151, 477, 141, 38);
		btnIzracunajBonus.setFocusable(false);
		frame.getContentPane().add(btnIzracunajBonus);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ZaposleniOperacije zaposleniOperacije = new ZaposleniOperacije(UcitajPodatke.getSviZaposleni());
				if(radnik != null) {
					String pol = "";
					if(rdbtnMuski.isSelected()) {
						pol += "muski";
					}else if(rdbtnZenski.isSelected()) {
						pol += "zenski";
					}
					
					Uloga uloga = (Uloga) cbUloga.getSelectedItem();
					StrucnaSprema strucnaSprema = (StrucnaSprema) cbStrucnaSprema.getSelectedItem();
					List<String> tipoviTretmanaKozmeticara = new ArrayList<>();
					for(int i = 0; i < cbTipoviTretmana.getItemCount(); i++) {
						tipoviTretmanaKozmeticara.add((String) cbTipoviTretmana.getItemAt(i));
					}
					try {
						List<Zaposleni> sviZaposleni = UcitajPodatke.getSviZaposleni();
						String korisnickoIme;
						if(radnik.getKorisnickoIme() == null) {
							korisnickoIme = txtIme.getText() + txtPrezime.getText();
							sviZaposleni = zaposleniOperacije.dodajZaposlenog(korisnickoIme, String.valueOf(txtLozinka.getPassword()), txtIme.getText(), txtPrezime.getText(), pol, txtTelefon.getText(), txtAdresa.getText(), uloga, strucnaSprema, Integer.parseInt(txtGodineStaza.getText()), tipoviTretmanaKozmeticara);
						}else {
							korisnickoIme = radnik.getKorisnickoIme();
							sviZaposleni = zaposleniOperacije.izmeniZaposlenog(korisnickoIme, String.valueOf(txtLozinka.getPassword()), txtIme.getText(), txtPrezime.getText(), pol, txtTelefon.getText(), txtAdresa.getText(), uloga, strucnaSprema, Integer.parseInt(txtGodineStaza.getText()), Double.parseDouble(txtPlata.getText()), Double.parseDouble(txtBonus.getText()), tipoviTretmanaKozmeticara);
						}
						UcitajPodatke.setSviZaposleni(sviZaposleni);
						zaposleniOperacije.sacuvajZaposleneFajl();
						
						IzmenaZaposlenih izmenaZaposlenih = new IzmenaZaposlenih(menadzer);
						frame.dispose();
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnSubmit.setFocusable(false);
		btnSubmit.setBounds(10, 537, 131, 41);
		frame.getContentPane().add(btnSubmit);
		
		JButton btnOdustani = new JButton("Odustani");
		btnOdustani.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IzmenaZaposlenih izmenaZaposlenih = new IzmenaZaposlenih(menadzer);
				frame.dispose();
			}
		});
		btnOdustani.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnOdustani.setFocusable(false);
		btnOdustani.setBounds(151, 537, 141, 41);
		frame.getContentPane().add(btnOdustani);
		
		JButton btnDodajTretmaneKozmeticaru = new JButton("+");
		btnDodajTretmaneKozmeticaru.setFont(new Font("Tahoma", Font.BOLD, 12));
//		btnDodajTretmaneKozmeticaru.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				DodavanjeTretmanaKozmeticaru dodavanjeTretmanaKozmeticaru = new DodavanjeTretmanaKozmeticaru();
//				List<String> listaTretmana = dodavanjeTretmanaKozmeticaru.vratiTretmane();
//				for(String str: listaTretmana) {
//					cbTipoviTretmana.removeAllItems();
//					cbTipoviTretmana.addItem(str);
//				}
//			}
//		});
		btnDodajTretmaneKozmeticaru.setBounds(248, 425, 73, 23);
		btnDodajTretmaneKozmeticaru.setFocusable(false);
		frame.getContentPane().add(btnDodajTretmaneKozmeticaru);
		frame.setVisible(true);
	}
}
