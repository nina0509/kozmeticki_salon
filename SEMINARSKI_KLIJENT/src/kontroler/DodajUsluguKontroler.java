/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import domen.TipUsluge;
import domen.Usluga;
import forme.DodajUsluguForma;
import forme.TipForme;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JOptionPane;
import komunikacija.Komunikacija;
import kontroler.glavni.GlavniKontroler;

/**
 *
 * @author ninic
 */
public class DodajUsluguKontroler {

    private final DodajUsluguForma duf;

    public DodajUsluguKontroler(DodajUsluguForma duf) {
        this.duf = duf;
        addActionListener();

    }

    public void otvoriFormu(TipForme t) {

        duf.setVisible(true);
        podesiFormu(t);
        ucitajPodatkeZaComboBox();
        if(t==TipForme.IZBRISI)JOptionPane.showMessageDialog(duf, "Sistem je ucitao uslugu", "Uspeh", JOptionPane.INFORMATION_MESSAGE);


    }

    private void ucitajPodatkeZaComboBox() {

        List<TipUsluge> tipovi = komunikacija.Komunikacija.getInstance().vratiSveTipoveUsluga();
        for (TipUsluge t : tipovi) {
            duf.getjComboBoxTipUsluge().addItem(t);
        }

    }

    private void addActionListener() {
        duf.dodajAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                dodaj(e);

            }

            private void dodaj(ActionEvent e) {

                try {
                    String naziv = duf.getjTextFieldNaziv().getText().trim();
                    int trajanje = Integer.parseInt(duf.getjTextFieldTrajanje().getText().trim());
                    int cena = Integer.parseInt(duf.getjTextFieldCena().getText().trim());
                    TipUsluge tip = (TipUsluge) duf.getjComboBoxTipUsluge().getSelectedItem();

                    Usluga u = new Usluga(-1, naziv, trajanje, cena, tip);

                    try {
                        Komunikacija.getInstance().sacuvajUslugu(u);
                        JOptionPane.showMessageDialog(duf, "Sistem je zapamtio uslugu", "Uspesno", JOptionPane.INFORMATION_MESSAGE);
                        GlavniKontroler.getInstance().osveziTabeluUsluga();
                        duf.dispose();

                    } catch (Exception ex) {

                        JOptionPane.showMessageDialog(duf, "Sistem ne moze da zapamti uslugu", "Greska", JOptionPane.ERROR_MESSAGE);

                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(duf, "Sistem ne moze da zapamti uslugu", "Greska", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
        );

        duf.obrisiAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int id = Integer.parseInt(duf.getjTextFieldId().getText().trim());
                    String naziv = duf.getjTextFieldNaziv().getText().trim();
                    int trajanje = Integer.parseInt(duf.getjTextFieldTrajanje().getText().trim());
                    int cena = Integer.parseInt(duf.getjTextFieldCena().getText().trim());
                    TipUsluge tip = (TipUsluge) duf.getjComboBoxTipUsluge().getSelectedItem();

                    Usluga u = new Usluga(id, naziv, trajanje, cena, tip);
                    try {
                        Komunikacija.getInstance().izbrisiUsugu(u);
                        JOptionPane.showMessageDialog(duf, "Sistem je izbrisao podatke o usluzi", "Uspesno", JOptionPane.INFORMATION_MESSAGE);
                        GlavniKontroler.getInstance().osveziTabeluUsluga();
                        duf.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(duf, "Sistem ne moze da izbrise uslugu", "Greska", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(duf, "Sistem ne moze da izbrise uslugu", "Greska", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

    }

    private void podesiFormu(TipForme t) {

        if (t == TipForme.DODAJ) {

            duf.getjButtonObrisi().setVisible(false);
            duf.getjButtonDodaj().setVisible(true);

            duf.getjLabelId().setVisible(false);
            duf.getjTextFieldId().setVisible(false);
        } else if (t == TipForme.IZBRISI) {

            duf.getjButtonObrisi().setVisible(true);
            duf.getjButtonDodaj().setVisible(false);

            duf.getjLabelId().setVisible(true);
            duf.getjTextFieldId().setVisible(true);

            Usluga u = (Usluga) GlavniKontroler.getInstance().getParam("Usluga");

            duf.getjTextFieldId().setText(u.getUslugaId() + "");
            duf.getjTextFieldId().setEnabled(false);

            duf.getjTextFieldNaziv().setText(u.getNaziv());
            duf.getjTextFieldNaziv().setEnabled(false);

            duf.getjTextFieldTrajanje().setText(u.getTrajanje() + "");
            duf.getjTextFieldTrajanje().setEnabled(false);

            duf.getjTextFieldCena().setText(u.getCena() + "");
            duf.getjTextFieldCena().setEnabled(false);

            duf.getjComboBoxTipUsluge().setSelectedItem(u.getTip());
            duf.getjComboBoxTipUsluge().setEnabled(false);

            duf.getjComboBoxTipUsluge().setRenderer(new DefaultListCellRenderer() {
                @Override
                public void paint(Graphics g) {
                    setForeground(Color.BLACK);
                    super.paint(g);
                }

            });

        }

    }

}
