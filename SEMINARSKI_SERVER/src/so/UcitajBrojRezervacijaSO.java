/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;


import domen.Klijent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ninic
 */
public class UcitajBrojRezervacijaSO extends OpstaSO {

List<Klijent> klijenti=new ArrayList<>();
    @Override
    protected void preduslovi(Object param) throws Exception {

        
      

    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

    klijenti=broker.vratiSve(new Klijent(), kljuc);
   
    for(Klijent k: klijenti)
    {
        int br=broker.getCountRez(k);
        k.setBrojRezervacija(br);
      
    }
    

    }


    public List<Klijent> getKlijenti() {
        return klijenti;
    }

    public void setKlijenti(List<Klijent> klijenti) {
        this.klijenti = klijenti;
    }
    
    
    
    
}
