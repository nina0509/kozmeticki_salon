/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Klijent;

/**
 *
 * @author ninic
 */
public class IzbrisiKlijentaSO extends OpstaSO {

    @Override
    protected void preduslovi(Object param) throws Exception {

        if (param == null || !(param instanceof Klijent)) {
            throw new Exception("Sistem ne moze da izbrise klijenta");
        }

    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        broker.izbrisi((Klijent) param);
    }

}
