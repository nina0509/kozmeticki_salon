/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import domen.Menadzer;
import forme.GlavnaForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import kontroler.glavni.GlavniKontroler;

/**
 *
 * @author ninic
 */
public class GlavnaFormaKontroler {

    private final GlavnaForma gf;

    public GlavnaFormaKontroler(GlavnaForma gf) {
        this.gf = gf;
        addActionListeners();
    }

    private void addActionListeners() {

        //Obrisi klijenta
        gf.logoutAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    komunikacija.Komunikacija.getInstance().logout(GlavniKontroler.getInstance().getUlogovani());
                    gf.dispose();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(gf, "Sistem ne moze da odjavi klijenta", "Greska", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

    }

    public void otvoriFormu() {

        Menadzer m = GlavniKontroler.getInstance().getUlogovani();
        gf.setVisible(true);
        gf.getjLabelUlogovani().setText(m.getIme() + " " + m.getPrezime());

    }
}
