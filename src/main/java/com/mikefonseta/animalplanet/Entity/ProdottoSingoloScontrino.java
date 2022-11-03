package com.mikefonseta.animalplanet.Entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import static com.mikefonseta.animalplanet.data.makePrecise;

public class ProdottoSingoloScontrino {

    private SimpleStringProperty nome_prodottoSC;
    private SimpleDoubleProperty num_pezziSC;
    private SimpleDoubleProperty prezzoSC;
    private SimpleDoubleProperty nettoSC;
    private SimpleIntegerProperty ricaricoSC;
    private boolean isSfusoSC;
    private int id_scontrino;
    private double prezzo_di_acquisto;
    private SimpleStringProperty categoriaSC;


    public ProdottoSingoloScontrino(String nome, String categoria, double numPezzi, double prezzo, double prezzo_di_acquisto, boolean isSfuso, int id_scontrino) {
        this.nome_prodottoSC = new SimpleStringProperty(nome);
        this.num_pezziSC = new SimpleDoubleProperty(numPezzi);
        this.prezzoSC = new SimpleDoubleProperty(makePrecise(prezzo*numPezzi,2));
        this.categoriaSC = new SimpleStringProperty(categoria);
        this.nettoSC = new SimpleDoubleProperty(makePrecise(prezzoSC.get()-prezzo_di_acquisto,2));
        this.ricaricoSC = new SimpleIntegerProperty((int) (((makePrecise(prezzoSC.get(),2) / makePrecise(prezzo_di_acquisto,2)) - 1)*100));
        this.isSfusoSC = isSfuso;
        this.id_scontrino = id_scontrino;
        this.prezzo_di_acquisto = prezzo_di_acquisto;
    }

    public String getNome_prodottoSC() {
        return nome_prodottoSC.get();
    }

    public SimpleStringProperty nome_prodottoSCProperty() {
        return nome_prodottoSC;
    }

    public void setNome_prodottoSC(String nome_prodottoSC) {
        this.nome_prodottoSC.set(nome_prodottoSC);
    }

    public double getNum_pezziSC() {
        return num_pezziSC.get();
    }

    public SimpleDoubleProperty num_pezziSCProperty() {
        return num_pezziSC;
    }

    public void setNum_pezziSC(double num_pezziSC) {
        this.num_pezziSC.set(num_pezziSC);
    }

    public double getPrezzoSC() {
        return prezzoSC.get();
    }

    public SimpleDoubleProperty prezzoSCProperty() {
        return prezzoSC;
    }

    public void setPrezzoSC(double prezzoSC) {
        this.prezzoSC.set(prezzoSC);
    }

    public boolean isSfusoSC() {
        return isSfusoSC;
    }

    public void setSfusoSC(boolean sfusoSC) {
        isSfusoSC = sfusoSC;
    }

    public String getCategoriaSC() {
        return categoriaSC.get();
    }

    public SimpleStringProperty categoriaSCProperty() {
        return categoriaSC;
    }

    public void setCategoriaSC(String categoriaSC) {
        this.categoriaSC.set(categoriaSC);
    }

    public int getId_scontrino() {
        return id_scontrino;
    }

    public void setId_scontrino(int id_scontrino) {
        this.id_scontrino = id_scontrino;
    }

    public double getPrezzo_di_acquisto() {
        return prezzo_di_acquisto;
    }

    public void setPrezzo_di_acquisto(double prezzo_di_acquisto) {
        this.prezzo_di_acquisto = prezzo_di_acquisto;
    }

    public double getNettoSC() {
        return nettoSC.get();
    }

    public SimpleDoubleProperty nettoSCProperty() {
        return nettoSC;
    }

    public void setNettoSC(double nettoSC) {
        this.nettoSC.set(nettoSC);
    }

    public int getRicaricoSC() {
        return ricaricoSC.get();
    }

    public SimpleIntegerProperty ricaricoSCProperty() {
        return ricaricoSC;
    }

    public void setRicaricoSC(int ricaricoSC) {
        this.ricaricoSC.set(ricaricoSC);
    }
}
