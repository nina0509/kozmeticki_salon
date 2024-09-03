/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import domen.Klijent;
import domen.Popust;
import domen.Rezervacija;
import domen.StavkaRezervacije;
import domen.TipUsluge;
import domen.Usluga;
import forme.DodajRezervacijuForma;
import forme.TipForme;
import modeli.ModelTabelePopust;
import modeli.ModelTabeleStavkeRezervacije;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import komunikacija.Komunikacija;
import kontroler.glavni.GlavniKontroler;

/**
 *
 * @author ninic
 */
public class DodajRezervacijuKontroler {

    private final DodajRezervacijuForma drf;

    public DodajRezervacijuKontroler(DodajRezervacijuForma drf) {
        this.drf = drf;

        addActionListener();

    }

    public void otvoriFormu(TipForme t) {

        ModelTabeleStavkeRezervacije mtsr = new ModelTabeleStavkeRezervacije(new ArrayList<>());
        drf.getjTableStavkeRez().setModel(mtsr);

        ModelTabelePopust mtp = new ModelTabelePopust(new ArrayList<>());
        drf.getjTablePopusti().setModel(mtp);
        ucitajPodatkeZaComboBoxeve();
        podesiFormu(t);
        
      

        drf.setVisible(true);
        if(t==TipForme.IZMENI)JOptionPane.showMessageDialog(drf, "Sistem je ucitao rezervaciju", "Uspeh", JOptionPane.INFORMATION_MESSAGE);


    }

    private void addActionListener() {

        //vreme kraja kad se promeni usluga ili vreme pocetka
        //cena kad se promeni usluga
        drf.ComboBoxActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    Usluga u = (Usluga) drf.getjComboBoxUsluga().getSelectedItem();
                    LocalTime vrPocetka = (LocalTime) drf.getjComboBoxVremePocetka().getSelectedItem();

                    long trajanje = u.getTrajanje();

                    LocalTime vremeZav = vrPocetka.plusMinutes(trajanje);

                    drf.getjComboBoxVremeZavrsetka().addItem(vremeZav);
                    drf.getjComboBoxVremeZavrsetka().setSelectedItem(vremeZav);

                    ModelTabelePopust mtp = (ModelTabelePopust) drf.getjTablePopusti().getModel();

                    Klijent k = (Klijent) drf.getjComboBoxKlijent().getSelectedItem();

                    Popust p = mtp.vratiPopust(k, u);
                    int cenaUsluge = u.getCena();

                    if (p != null) {
                        System.out.println(cenaUsluge * p.getPopust());
                        int popust = (int) ((double) (cenaUsluge * p.getPopust()) / 100);
                        cenaUsluge = cenaUsluge - popust;

                    }
                    drf.getjTextFieldCenaUsluge().setText(cenaUsluge + "");

                } catch (Exception ex) {

                }

            }

        }
        );

        //menjanje klijenta azuriranje popusta i cene
        drf.ComboBoxKlijentActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    Klijent k = (Klijent) drf.getjComboBoxKlijent().getSelectedItem();
                    List<Popust> popusti = Komunikacija.getInstance().vratiSvePopuste(k);
                    Usluga u = (Usluga) drf.getjComboBoxUsluga().getSelectedItem();
                    ModelTabelePopust mtp = (ModelTabelePopust) drf.getjTablePopusti().getModel();
                    mtp.setLista(popusti);
                    mtp.fireTableDataChanged();

                    Popust p = mtp.vratiPopust(k, u);
                    int cenaUsluge = u.getCena();

                    if (p != null) {
                        System.out.println(cenaUsluge * p.getPopust());
                        int popust = (int) ((double) (cenaUsluge * p.getPopust()) / 100);
                        cenaUsluge = cenaUsluge - popust;

                    }
                    drf.getjTextFieldCenaUsluge().setText(cenaUsluge + "");

                } catch (Exception ex) {
                   

                }

            }

        }
        );

        //promena tipa usluge menja usluge
        drf.ComboBoxTipUslugeActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    TipUsluge tip = (TipUsluge) drf.getjComboBoxTipUsluge().getSelectedItem();
                    List<Usluga> usluge = komunikacija.Komunikacija.getInstance().nadjiUsluge(null);
                    drf.getjComboBoxUsluga().removeAllItems();
                    for (Usluga u : usluge) {
                        if (u.getTip().equals(tip)) {
                            drf.getjComboBoxUsluga().addItem(u);
                        }
                    }

                } catch (Exception ex) {

                }
            }
        }
        );

        //kad promenis uslugu termini se provere
        drf.ComboBoxUslugaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    proveriTermine();
                    proveriVremeLokalno();

                } catch (Exception ex) {

                }

            }
        }
        );

        //dodavanje stavke rezervacije
        drf.dodajStavkuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    Usluga u = (Usluga) drf.getjComboBoxUsluga().getSelectedItem();

                    LocalTime vrPoc = (LocalTime) drf.getjComboBoxVremePocetka().getSelectedItem();

                    LocalTime vrZav = (LocalTime) drf.getjComboBoxVremeZavrsetka().getSelectedItem();

                    int cena = Integer.parseInt(drf.getjTextFieldCenaUsluge().getText());

                    StavkaRezervacije sr = new StavkaRezervacije(-1, null, vrPoc, vrZav, cena, u);
                    ((ModelTabeleStavkeRezervacije) drf.getjTableStavkeRez().getModel()).dodajStavku(sr);

                    int ukCena = Integer.parseInt(drf.getjTextFieldUkupnaCena().getText());
                    ukCena += sr.getCena();
                    drf.getjTextFieldUkupnaCena().setText(ukCena + "");
                    proveriVremeLokalno();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(drf, "Sistem ne moze da doda stavku rezervacije", "Greska", JOptionPane.ERROR_MESSAGE);

                }
            }
        }
        );

        //brisanje stavke rezervacije
        drf.obrisiStavkuAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int red = drf.getjTableStavkeRez().getSelectedRow();
                    if (red == -1) {
                        JOptionPane.showMessageDialog(drf, "Niste selektovali stavku rezervacije za brisanje!", "Greska", JOptionPane.ERROR_MESSAGE);

                    } else {

                        ModelTabeleStavkeRezervacije mtsr = (ModelTabeleStavkeRezervacije) drf.getjTableStavkeRez().getModel();
                        StavkaRezervacije sr = mtsr.getLista().get(red);
                        int ukCena = Integer.parseInt(drf.getjTextFieldUkupnaCena().getText());
                        ukCena -= sr.getCena();
                        drf.getjTextFieldUkupnaCena().setText(ukCena + "");
                        mtsr.izbrisiStavku(sr);
                        proveriVremeLokalno();

                    }

                } catch (Exception ex) {

                    JOptionPane.showMessageDialog(drf, "Sistem ne moze da obrise stavku rezervacije", "Greska", JOptionPane.ERROR_MESSAGE);

                }

            }
        }
        );

        //dodavanje rezervacije
        drf.dodajRezAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
                    Date datum = date.parse(drf.getjTextFieldDatum().getText().trim());
                    Klijent k = (Klijent) drf.getjComboBoxKlijent().getSelectedItem();
                    List<StavkaRezervacije> stavke = ((ModelTabeleStavkeRezervacije) drf.getjTableStavkeRez().getModel()).getLista();
                    int ukCena = Integer.parseInt(drf.getjTextFieldUkupnaCena().getText());
                    Rezervacija r = new Rezervacija(-1, datum, ukCena, true, k);
                    r.setStavke(stavke);
                    Komunikacija.getInstance().sacuvajRezervaciju(r);
                    JOptionPane.showMessageDialog(drf, "Sistem je zapamtio rezervaciju", "Uspesno", JOptionPane.INFORMATION_MESSAGE);

                    GlavniKontroler.getInstance().osveziTabeluRezervacija();
                    drf.dispose();

                } catch (Exception ex) {
                   
                    JOptionPane.showMessageDialog(drf, "Sistem ne moze da zapamti rezervaciju", "Greska", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
        );

        //datum slobodni termini
        drf.getjTextFieldDatum().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textChanged(drf.getjTextFieldDatum().getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textChanged(drf.getjTextFieldDatum().getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                textChanged(drf.getjTextFieldDatum().getText());
            }

            // Metoda koja se poziva kada se tekst promeni
            public void textChanged(String newText) {
                try {
                    proveriTermine();
                    proveriVremeLokalno();

                } catch (Exception ex) {

                }

            }

        });

        //BRISANJE REZERVACIJE
        drf.ObrisiRezAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    int id = Integer.parseInt(drf.getjTextFieldId().getText().trim());
                    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
                    Date datum = date.parse(drf.getjTextFieldDatum().getText().trim());
                    Klijent k = (Klijent) drf.getjComboBoxKlijent().getSelectedItem();
                    boolean dolazak = false;
                    if (drf.getjComboBoxDolazak().getSelectedIndex() == 0) {
                        dolazak = true;
                    }
                    List<StavkaRezervacije> stavke = ((ModelTabeleStavkeRezervacije) drf.getjTableStavkeRez().getModel()).getLista();
                    int ukCena = Integer.parseInt(drf.getjTextFieldUkupnaCena().getText());
                    Rezervacija r = new Rezervacija(id, datum, ukCena, dolazak, k);
                    r.setStavke(stavke);

                    Komunikacija.getInstance().izbrisiRezervaciju(r);
                    JOptionPane.showMessageDialog(drf, "Sistem je izbrisao podatke o rezervaciji", "Uspesno", JOptionPane.INFORMATION_MESSAGE);

                    GlavniKontroler.getInstance().osveziTabeluRezervacija();
                    drf.dispose();

                } catch (Exception ex) {
                 
                    JOptionPane.showMessageDialog(drf, "Sistem ne moze da izbrise rezervaciju", "Greska", JOptionPane.ERROR_MESSAGE);
                }

            }
        }
        );

        //AZURIRANJE REZERVACIJE
        drf.AzurirajRezAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int id = Integer.parseInt(drf.getjTextFieldId().getText().trim());
                    SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
                    Date datum = date.parse(drf.getjTextFieldDatum().getText().trim());
                    Klijent k = (Klijent) drf.getjComboBoxKlijent().getSelectedItem();
                    boolean dolazak = false;
                    if (drf.getjComboBoxDolazak().getSelectedIndex() == 0) {
                        dolazak = true;
                    }
                    List<StavkaRezervacije> stavke = ((ModelTabeleStavkeRezervacije) drf.getjTableStavkeRez().getModel()).getLista();
                    int ukCena = Integer.parseInt(drf.getjTextFieldUkupnaCena().getText());
                    Rezervacija r = new Rezervacija(id, datum, ukCena, dolazak, k);
                    r.setStavke(stavke);

                    try {

                        Komunikacija.getInstance().sacuvajRezervaciju(r);
                        JOptionPane.showMessageDialog(drf, "Sistem je zapamtio rezervaciju", "Uspesno", JOptionPane.INFORMATION_MESSAGE);
                        GlavniKontroler.getInstance().osveziTabeluRezervacija();
                        drf.dispose();

                    } catch (Exception ex) {

                        JOptionPane.showMessageDialog(drf, "Sistem ne moze da zapamti rezervaciju", "Greska", JOptionPane.ERROR_MESSAGE);

                    }

                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(drf, "Sistem ne moze da zapamti rezervaciju", "Greska", JOptionPane.ERROR_MESSAGE);
                }

            }

        }
        );

    }

    //slobodni termini
    private void proveriTermine() {

        try {
            drf.getjComboBoxVremePocetka().removeAllItems();
            popuniComboBoxVreme();
            Usluga u = (Usluga) drf.getjComboBoxUsluga().getSelectedItem();
            SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
            Date datum = date.parse(drf.getjTextFieldDatum().getText().trim());

            List<Rezervacija> rez = Komunikacija.getInstance().nadjiRezervacije(null);
           
            for (Rezervacija r : rez) {

                for (StavkaRezervacije s : r.getStavke()) {
                    if (s.getUsluga().equals(u) && r.getDatum().equals(datum)) {

                        LocalTime poc = s.getVremePocetka();
                        poc = poc.minusMinutes((long) s.getUsluga().getTrajanje());
                        poc = poc.plusMinutes(1);
                        LocalTime kraj = s.getVremeZavrsetka();
                        kraj = kraj.minusMinutes(1);

                        for (int i = 8; i <= 20; i++) {
                            for (int j = 0; j <= 45; j = j + 15) {
                                LocalTime vreme = LocalTime.of(i, j, 0);
             
                                if (!(vreme.isBefore(poc) || vreme.isAfter(kraj))) {
                                    drf.getjComboBoxVremePocetka().removeItem(vreme);
                                }
  
                            }
                        }

                    }

                }

                proveriVremeLokalno();

            }
            
            
        } catch (Exception e) {
        }

    }

    //lokalna provera vremena da li u stavkama koje jos nisu u bazi ima zauzeto
    private void proveriVremeLokalno() {
        List<StavkaRezervacije> stLokal = ((ModelTabeleStavkeRezervacije) drf.getjTableStavkeRez().getModel()).getLista();
        Usluga u = (Usluga) drf.getjComboBoxUsluga().getSelectedItem();
        for (StavkaRezervacije s : stLokal) {
            
                LocalTime poc = s.getVremePocetka();
                poc = poc.minusMinutes((long) s.getUsluga().getTrajanje());
                poc = poc.plusMinutes(1);
                LocalTime kraj = s.getVremeZavrsetka();
                kraj = kraj.minusMinutes(1);

                for (int i = 8; i <= 20; i++) {
                    for (int j = 0; j <= 45; j = j + 15) {
                        LocalTime vreme = LocalTime.of(i, j, 0);
                        if (!(vreme.isBefore(poc) || vreme.isAfter(kraj))) {
                            drf.getjComboBoxVremePocetka().removeItem(vreme);
                        }
                    }
                }

            }
        
      
        
         
        

        }
    

    //priprema forme u zavisnosti od tipa
    private void podesiFormu(TipForme t) {

        if (t == TipForme.DODAJ) {
            drf.getjComboBoxVremeZavrsetka().setEnabled(false);
            drf.getjTextFieldCenaUsluge().setEnabled(false);
            drf.getjTextFieldUkupnaCena().setEnabled(false);
            drf.getjTextFieldId().setEnabled(false);
            drf.getjButtonAzuriraj().setVisible(false);
            drf.getjButtonObrisi().setVisible(false);
            drf.getjLabelId().setVisible(false);
            drf.getjTextFieldId().setVisible(false);
            drf.getjComboBoxDolazak().setVisible(false);
            drf.getjLabelDolazak().setVisible(false);
        } else if (t == TipForme.IZMENI) {

            drf.getjButtonDodajRezervaciju().setVisible(false);
            drf.getjComboBoxVremeZavrsetka().setEnabled(false);
            drf.getjTextFieldCenaUsluge().setEnabled(false);
            drf.getjTextFieldUkupnaCena().setEnabled(false);
            drf.getjComboBoxKlijent().setEnabled(false);
            drf.getjLabelId().setVisible(true);
            drf.getjTextFieldId().setVisible(true);
            drf.getjTextFieldDatum().setEnabled(false);

            Rezervacija r = (Rezervacija) GlavniKontroler.getInstance().getParam("Rezervacija");
            drf.getjComboBoxKlijent().setSelectedItem(r.getKlijent());
            drf.getjTextFieldId().setText(r.getRezervacijaId() + "");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String date = dateFormat.format(r.getDatum());
            drf.getjTextFieldDatum().setText(date);
            drf.getjTextFieldUkupnaCena().setText(r.getUkupnaCena() + "");

            if (r.isPojavljivanje()) {
                drf.getjComboBoxDolazak().setSelectedIndex(0);
            } else {
                drf.getjComboBoxDolazak().setSelectedIndex(1);
            }

            ModelTabeleStavkeRezervacije mtsr = (ModelTabeleStavkeRezervacije) drf.getjTableStavkeRez().getModel();
            mtsr.setLista(r.getStavke());
            mtsr.fireTableDataChanged();

        }

        drf.getjComboBoxVremeZavrsetka().setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                setForeground(Color.BLACK);
                super.paint(g);
            }

        });

        drf.getjComboBoxKlijent().setRenderer(new DefaultListCellRenderer() {
            @Override
            public void paint(Graphics g) {
                setForeground(Color.BLACK);
                super.paint(g);
            }

        });

    }

    private void ucitajPodatkeZaComboBoxeve() {

        List<Klijent> klijenti = komunikacija.Komunikacija.getInstance().vratiSveKlijente();
        for (Klijent k : klijenti) {
            drf.getjComboBoxKlijent().addItem(k);
        }

        List<TipUsluge> tipUsluge = komunikacija.Komunikacija.getInstance().vratiSveTipoveUsluga();
        for (TipUsluge u : tipUsluge) {
            drf.getjComboBoxTipUsluge().addItem(u);
        }

        List<Usluga> usluge = komunikacija.Komunikacija.getInstance().vratiSveUsluge();
        for (Usluga u : usluge) {
            if (u.getTip().equals(tipUsluge.get(0))) {
                drf.getjComboBoxUsluga().addItem(u);
            }
            drf.getjComboBoxUsluga().addItem(u);
        }

        popuniComboBoxVreme();

    }

    private void popuniComboBoxVreme() {
        for (int i = 8; i <= 20; i++) {
            for (int j = 0; j <= 45; j = j + 15) {

                LocalTime vreme = LocalTime.of(i, j, 0);
                drf.getjComboBoxVremePocetka().addItem(vreme);

            }

        }
    }

}
