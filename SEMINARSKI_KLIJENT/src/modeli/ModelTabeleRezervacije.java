/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeli;

import domen.Rezervacija;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ninic
 */
public class ModelTabeleRezervacije extends AbstractTableModel {

    List<Rezervacija> lista;
    String[] kolone = {"ID", "datum", "ukupna cena", "pojavio se", "ime klijenta", "datum rodjenja klijenta"};

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    public ModelTabeleRezervacije(List<Rezervacija> lista) {

        this.lista = lista;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Rezervacija r = lista.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return r.getRezervacijaId();
            case 1:
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy.");
                return simpleDateFormat.format(r.getDatum());
            case 3:
                if (r.isPojavljivanje()) {
                    return "da";
                } else {
                    return "ne";
                }
            case 2:
                return r.getUkupnaCena();
            case 4:
                return r.getKlijent().getIme() + " " + r.getKlijent().getPrezime();
            case 5:
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd.MM.yyyy.");
                return simpleDateFormat1.format(r.getKlijent().getDatRodj());

            default:
                return null;

        }

    }

    @Override
    public String getColumnName(int column) {

        return kolone[column];
    }

    public List<Rezervacija> getLista() {
        return lista;
    }

    public void setLista(List<Rezervacija> lista) {
        this.lista = lista;
    }

    //lokalna pretraga stara
    public void pretrazi(String ime, String datum) {

        List<Rezervacija> nova = lista.stream()
                .filter(k -> (ime == null || ime.isBlank() || k.getKlijent().getIme().toLowerCase().contains(ime.toLowerCase()) | k.getKlijent().getPrezime().toLowerCase().contains(ime.toLowerCase())))
                .filter(k -> (datum == null || datum.isBlank() || (new SimpleDateFormat("dd.MM.yyyy")).format(k.getDatum()).contains(datum.toLowerCase())))
                .collect(Collectors.toList());
        lista = nova;
        fireTableDataChanged();

    }

}
