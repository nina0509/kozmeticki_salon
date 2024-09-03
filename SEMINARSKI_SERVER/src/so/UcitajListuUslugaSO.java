/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Usluga;
import java.util.List;

/**
 *
 * @author ninic
 */
public class UcitajListuUslugaSO extends OpstaSO{

     List<Usluga> Usluge;
    
    @Override
    protected void preduslovi(Object param) throws Exception {

    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        String uslov = " JOIN tipusluge on tipusluge.tipId=usluga.tipId";
        Usluge = broker.vratiSve(new Usluga(), uslov);

    }

    public List<Usluga> getUsluge() {
        return Usluge;
    }

    public void setUsluge(List<Usluga> Usluge) {
        this.Usluge = Usluge;
    }
    
}
