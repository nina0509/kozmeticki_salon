/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so;

import domen.Klijent;
import java.util.Date;
import java.util.List;
import repozitorijum.Repozitorijum;
import repozitorijum.db.DbRepozitorijum;
import repozitorijum.db.impl.DbRepozitorijumGenericki;

/**
 *
 * @author ninic
 */
public class ZapamtiKlijentaSO extends OpstaSO{
  protected final Repozitorijum broker;

    public ZapamtiKlijentaSO() {
       this.broker = new DbRepozitorijumGenericki();
    }
  
  
  
 
  @Override
    protected void preduslovi(Object param) throws Exception {

        Klijent k = (Klijent) param;
        if (k==null || k.getIme().isBlank() || k.getIme() == null || k.getPrezime().isBlank() || k.getPrezime() == null || k.getBrTel().isBlank() || k.getBrTel() == null || k.getDatRodj() == null) {
            throw new Exception("Sistem ne moze da zapamti klijenta");
        }
        
        String regex= "^(\\+\\d{1,3})?0?6[0-9]\\d{6,7}$";
        k.setBrTel(k.getBrTel().replace(" ", ""));
        if(!k.getBrTel().matches(regex) || k.getDatRodj().after(new Date()))
        {
         throw new Exception("Sistem ne moze da zapamti klijenta");

        }

    }

  @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

          
         Klijent k=(Klijent)param;
         k.setBrTel(k.getBrTel().replace(" ", ""));
         List<Klijent>klijenti = broker.vratiSve(new Klijent(), " WHERE klijent.klijentId="+k.getKlijentId());
         
         if(klijenti.isEmpty())
         {
          broker.sacuvaj((Klijent) param);
         }
         else
         {
         
          broker.izmeni((Klijent) param);
         }
         
       

    }
    
    

}
