/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import com.sun.java.accessibility.util.AWTEventMonitor;
import domen.Menadzer;
import forme.LoginForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import komunikacija.Komunikacija;
import kontroler.glavni.GlavniKontroler;

/**
 *
 * @author ninic
 */
public class LoginKontroler {

    private final LoginForma lf;

    public LoginKontroler(LoginForma lf) {
        this.lf = lf;
        addActionListeners();
    }

    private void addActionListeners() {

        lf.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                prijava(e);

            }

            private void prijava(ActionEvent e) {

                String username = lf.getjTextFieldUsername().getText().trim();
                String password = String.valueOf(lf.getjPasswordField1().getPassword());
                
                Komunikacija.getInstance().konekcija();
                
                
                Menadzer ulogovani = Komunikacija.getInstance().login(username, password);

                if (ulogovani == null) {
                    JOptionPane.showMessageDialog(lf, "Neuspesna prijava", "Greska", JOptionPane.ERROR_MESSAGE);

                } else {
                    
                    GlavniKontroler.getInstance().setUlogovani(ulogovani);
                    JOptionPane.showMessageDialog(lf, "Uspesno ste se prijavili", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    GlavniKontroler.getInstance().otvoriGlavnuFormu();
                    lf.dispose();

                }
              

            }
        }
        );

    }

    public void otvoriFormu() {

        lf.setVisible(true);
    }

}
