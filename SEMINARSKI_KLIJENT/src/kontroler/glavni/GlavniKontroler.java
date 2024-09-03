/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler.glavni;

import domen.Menadzer;
import forme.DodajKlijentaForma;
import forme.DodajRezervacijuForma;
import forme.DodajUsluguForma;
import forme.GlavnaForma;
import forme.LoginForma;
import forme.PregledKlijenataForma;
import forme.PregledRezervacijaForma;
import forme.PregledUslugaForma;
import forme.TipForme;
import java.util.HashMap;
import java.util.Map;
import kontroler.DodajKlijentaKontroler;
import kontroler.DodajRezervacijuKontroler;
import kontroler.DodajUsluguKontroler;
import kontroler.GlavnaFormaKontroler;
import kontroler.LoginKontroler;
import kontroler.PregledKlijenataKontroler;
import kontroler.PregledRezervacijaKontroler;
import kontroler.PregledUslugaKontroler;

/**
 *
 * @author ninic
 */
public class GlavniKontroler {

    private static GlavniKontroler instance;
    private LoginKontroler loginKontroler;
    private GlavnaFormaKontroler glavnaFormaKontroler;
    private PregledKlijenataKontroler pregledKlijenataKontroler;
    private DodajKlijentaKontroler dodajKlijentaKontroler;
    private PregledUslugaKontroler pregledUslugaKontroler;
    private PregledRezervacijaKontroler pregledRezervacijaKontroler;
    private DodajUsluguKontroler dodajUsluguKontroler;
    private DodajRezervacijuKontroler dodajRezervacijuKontroler;
    private Menadzer ulogovani;
    private Map<String, Object> parametri;

    private GlavniKontroler() {
        parametri = new HashMap<>();
    }

    public static GlavniKontroler getInstance() {

        if (instance == null) {
            instance = new GlavniKontroler();
        }
        return instance;
    }

    public void otvoriLoginFormu() {

        loginKontroler = new LoginKontroler(new LoginForma());
        loginKontroler.otvoriFormu();

    }

    public void otvoriGlavnuFormu() {

        glavnaFormaKontroler = new GlavnaFormaKontroler(new GlavnaForma());
        glavnaFormaKontroler.otvoriFormu();

    }

    public void otvoriPregledKlijenataFormu() {

        pregledKlijenataKontroler = new PregledKlijenataKontroler(new PregledKlijenataForma());
        pregledKlijenataKontroler.otvoriFormu();

    }

    public Menadzer getUlogovani() {
        return ulogovani;
    }

    public void setUlogovani(Menadzer ulogovani) {
        this.ulogovani = ulogovani;
    }

    public void otvoriDodajKlijentaFormu() {

        dodajKlijentaKontroler = new DodajKlijentaKontroler(new DodajKlijentaForma());
        dodajKlijentaKontroler.otvoriFormu(TipForme.DODAJ);

    }

    public void osveziTabeluKlijenata() {

        if (pregledKlijenataKontroler != null) {
            pregledKlijenataKontroler.osveziTabeluKlijenata();
        }

    }

    public void otvoriDodajUsluguFormu() {
        dodajUsluguKontroler = new DodajUsluguKontroler(new DodajUsluguForma());
        dodajUsluguKontroler.otvoriFormu(TipForme.DODAJ);

    }

    public void otvoriIzbrisiUsluguFormu() {
        dodajUsluguKontroler = new DodajUsluguKontroler(new DodajUsluguForma());
        dodajUsluguKontroler.otvoriFormu(TipForme.IZBRISI);

    }

    public void otvoriPregledUslugaFormu() {
        pregledUslugaKontroler = new PregledUslugaKontroler(new PregledUslugaForma());
        pregledUslugaKontroler.otvoriFormu();
    }

    public void osveziTabeluUsluga() {
        if (pregledUslugaKontroler != null) {
            pregledUslugaKontroler.osveziTabeluUsluga();
        }
    }

    public void setParam(String s, Object o) {
        parametri.put(s, o);
    }

    public Object getParam(String s) {
        return parametri.get(s);
    }

    public void otvoriIzmeniKlijentaFormu() {
        dodajKlijentaKontroler = new DodajKlijentaKontroler(new DodajKlijentaForma());
        dodajKlijentaKontroler.otvoriFormu(TipForme.IZMENI);
        
        
    }

    public void otvoriPregledRezervacijaFormu() {

        pregledRezervacijaKontroler = new PregledRezervacijaKontroler(new PregledRezervacijaForma());
        pregledRezervacijaKontroler.otvoriFormu();

    }

    public void otvoriDodajRezervacijuFormu() {

        dodajRezervacijuKontroler = new DodajRezervacijuKontroler(new DodajRezervacijuForma());
        dodajRezervacijuKontroler.otvoriFormu(TipForme.DODAJ);

    }

    public void otvoriAzurirajRezervacijuFormu() {

        dodajRezervacijuKontroler = new DodajRezervacijuKontroler(new DodajRezervacijuForma());
        dodajRezervacijuKontroler.otvoriFormu(TipForme.IZMENI);

    }

    public void osveziTabeluRezervacija() {
        if (pregledRezervacijaKontroler != null) {
            pregledRezervacijaKontroler.osveziTabeluRezervacija();
        }

    }

    public void logout() {

        this.ulogovani = null;
        loginKontroler.otvoriFormu();

    }

}
