/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import domen.Klijent;
import forme.DodajKlijentaForma;
import forme.TipForme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import kontroler.glavni.GlavniKontroler;

/**
 *
 * @author ninic
 */
public class DodajKlijentaKontroler {

    private final DodajKlijentaForma dkf;

    public DodajKlijentaKontroler(DodajKlijentaForma dkf) {
        this.dkf = dkf;
        addActionListener();

    }

    public void otvoriFormu(TipForme t) {

        podesiFormu(t);
        dkf.setVisible(true);
        if(t==TipForme.IZMENI)JOptionPane.showMessageDialog(dkf, "Sistem je ucitao klijenta", "Uspeh", JOptionPane.INFORMATION_MESSAGE);


    }

    private void addActionListener() {
        
        //cuvanje Klijenta
        dkf.dodajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dodaj(e);

            }

            private void dodaj(ActionEvent e) {

                try {
                    String ime = dkf.getjTextFieldIme().getText().trim();
                    String prezime = dkf.getjTextFieldPrezime().getText().trim();
                    String brTel = dkf.getjTextFieldBrTel().getText().trim();
                    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
                    Date datum = date.parse(dkf.getjTextFieldDatum().getText().trim());

                    Klijent k = new Klijent(-1, ime, prezime, brTel, datum);
                     k.setBrTel(k.getBrTel().replace(" ", ""));
                    try {
                        Komunikacija.getInstance().sacuvajKlijenta(k);
                        JOptionPane.showMessageDialog(dkf, "Sistem je zapamtio klijenta", "Uspesno", JOptionPane.INFORMATION_MESSAGE);
                        GlavniKontroler.getInstance().osveziTabeluKlijenata();
                        dkf.dispose();

                    } catch (Exception ex) {

                        JOptionPane.showMessageDialog(dkf, "Sistem ne moze da zapamti klijenta", "Greska", JOptionPane.ERROR_MESSAGE);

                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dkf, "Sistem ne moze da zapamti klijenta", "Greska", JOptionPane.ERROR_MESSAGE);
                }

            }

        }
        );

        //azuriranje
        dkf.azurirajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dodaj(e);

            }

            private void dodaj(ActionEvent e) {

                try {
                    int id = Integer.parseInt(dkf.getjTextFieldId().getText().trim());
                    String ime = dkf.getjTextFieldIme().getText().trim();
                    String prezime = dkf.getjTextFieldPrezime().getText().trim();
                    String brTel = dkf.getjTextFieldBrTel().getText().trim();
                    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
                    Date datum = date.parse(dkf.getjTextFieldDatum().getText().trim());
                    
                    Klijent k = new Klijent(id, ime, prezime, brTel, datum);
                    k.setBrTel(k.getBrTel().replace(" ", ""));
                    try {

                        Komunikacija.getInstance().sacuvajKlijenta(k);
                        JOptionPane.showMessageDialog(dkf, "Sistem je zapamtio klijenta", "Uspesno", JOptionPane.INFORMATION_MESSAGE);
                        GlavniKontroler.getInstance().osveziTabeluKlijenata();
                        dkf.dispose();

                    } catch (Exception ex) {

                        JOptionPane.showMessageDialog(dkf, "Sistem ne moze da zapamti klijenta", "Greska", JOptionPane.ERROR_MESSAGE);

                    }

                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(dkf, "Sistem ne moze da zapamti klijenta", "Greska", JOptionPane.ERROR_MESSAGE);
                }

            }

        }
        );

        //Obrisi klijenta
        dkf.obrisiAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int id = Integer.parseInt(dkf.getjTextFieldId().getText().trim());
                    String ime = dkf.getjTextFieldIme().getText().trim();
                    String prezime = dkf.getjTextFieldPrezime().getText().trim();
                    String brTel = dkf.getjTextFieldBrTel().getText().trim();
                    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
                    Date datum = date.parse(dkf.getjTextFieldDatum().getText().trim());

                    Klijent k = new Klijent(id, ime, prezime, brTel, datum);
                    try {
                        Komunikacija.getInstance().izbrisiKlijenta(k);
                        JOptionPane.showMessageDialog(dkf, "Sistem je izbrisao podatke o klijentu", "Uspesno", JOptionPane.INFORMATION_MESSAGE);
                        GlavniKontroler.getInstance().osveziTabeluKlijenata();
                        dkf.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(dkf, "Sistem ne moze da izbrise klijenta", "Greska", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dkf, "Sistem ne moze da izbrise klijenta", "Greska", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

    }

    private void podesiFormu(TipForme t) {

        if (t == TipForme.DODAJ) {
            dkf.getjButtonAzuriraj().setVisible(false);
            dkf.getjButtonObrisi().setVisible(false);
            dkf.getjButtonDodajKlijenta().setVisible(true);

            dkf.getjLabelid().setVisible(false);
            dkf.getjTextFieldId().setVisible(false);
        } else if (t == TipForme.IZMENI) {

            dkf.getjButtonAzuriraj().setVisible(true);
            dkf.getjButtonObrisi().setVisible(true);
            dkf.getjButtonDodajKlijenta().setVisible(false);

            dkf.getjLabelid().setVisible(true);
            dkf.getjTextFieldId().setVisible(true);

            Klijent k = (Klijent) GlavniKontroler.getInstance().getParam("Klijent");

            dkf.getjTextFieldId().setText(k.getKlijentId() + "");
            dkf.getjTextFieldId().setEnabled(false);
            dkf.getjTextFieldIme().setText(k.getIme());
            dkf.getjTextFieldPrezime().setText(k.getPrezime());
            dkf.getjTextFieldBrTel().setText(k.getBrTel());
            dkf.getjTextFieldDatum().setText(k.getDatRodj().toString());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String date = dateFormat.format(k.getDatRodj());
            dkf.getjTextFieldDatum().setText(date);


        }

    }
}
