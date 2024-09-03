/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeli;

import domen.Klijent;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ninic
 */
public class ModelTabeleKlijentiRezervacije extends AbstractTableModel{
    
    
    List<Klijent> lista;
    
    String[] kolone = {"Id","Ime", "Prezime","Broj telefona","DatRodj","broj rezervacija"};
    
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    public ModelTabeleKlijentiRezervacije (List<Klijent> lista) {

        this.lista = lista;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Klijent br = lista.get(rowIndex);

       
            switch (columnIndex) {
            case 0:
                return br.getKlijentId();
            case 1:
                return br.getIme();
            case 2:
                return br.getPrezime();
            case 3:
                return br.getBrTel();
            case 4:
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy.");
                return simpleDateFormat.format(br.getDatRodj());
            case 5:
               return br.getBrojRezervacija();
               
                
            default:
                return null;

        

        }

    }

    @Override
    public String getColumnName(int column) {

        return kolone[column];
    }

    public List<Klijent> getLista() {
        return lista;
    }

    public void setLista(List<Klijent> lista) {
        this.lista = lista;
    }

    



    
}
