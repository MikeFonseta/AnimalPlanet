package com.mikefonseta.animalplanet.Entity;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProdottoSingoloScontrino {

    private SimpleStringProperty nome_prodottoSC;
    private SimpleFloatProperty num_pezziSC;
    private SimpleFloatProperty prezzoSC;
    private boolean isSfusoSC;
    private int id_scontrino;
    private SimpleStringProperty categoriaSC;


    public ProdottoSingoloScontrino(String nome, String categoria, float numPezzi, float prezzo, boolean isSfuso, int id_scontrino) {
        this.nome_prodottoSC = new SimpleStringProperty(nome);
        this.num_pezziSC = new SimpleFloatProperty(numPezzi);
        this.prezzoSC = new SimpleFloatProperty(prezzo*numPezzi);
        this.categoriaSC = new SimpleStringProperty(categoria);
        this.isSfusoSC = isSfuso;
        this.id_scontrino = id_scontrino;
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

    public float getNum_pezziSC() {
        return num_pezziSC.get();
    }

    public SimpleFloatProperty num_pezziSCProperty() {
        return num_pezziSC;
    }

    public void setNum_pezziSC(float num_pezziSC) {
        this.num_pezziSC.set(num_pezziSC);
    }

    public float getPrezzoSC() {
        return prezzoSC.get();
    }

    public SimpleFloatProperty prezzoSCProperty() {
        return prezzoSC;
    }

    public void setPrezzoSC(float prezzoSC) {
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
}
