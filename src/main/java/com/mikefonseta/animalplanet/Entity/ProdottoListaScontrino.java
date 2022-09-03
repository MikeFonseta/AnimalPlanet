package com.mikefonseta.animalplanet.Entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

import static com.mikefonseta.animalplanet.data.makePrecise;

public class ProdottoListaScontrino {

    private int id;
    private SimpleStringProperty nome_scontrino;
    private SimpleDoubleProperty num_pezzi;
    private SimpleDoubleProperty prezzo_scontrino;
    private boolean isSfuso;
    private double prezzo_singolo;
    private double prezzo_di_acquisto;
    private String categoria;

    public ProdottoListaScontrino(int id, String nome, String categoria, double numPezzi, double prezzoDiVendita, double prezzo_di_acquisto,boolean isSfuso) {
        this.id = id;
        this.nome_scontrino = new SimpleStringProperty(nome);
        this.num_pezzi = new SimpleDoubleProperty(makePrecise(numPezzi,2));
        this.prezzo_singolo = makePrecise(prezzoDiVendita,2);
        this.prezzo_scontrino = new SimpleDoubleProperty(makePrecise(prezzoDiVendita*numPezzi,2));
        this.categoria = categoria;
        this.isSfuso = isSfuso;
        this.prezzo_di_acquisto = makePrecise(prezzo_di_acquisto,2);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome_scontrino() {
        return nome_scontrino.get();
    }

    public SimpleStringProperty nome_scontrinoProperty() {
        return nome_scontrino;
    }

    public void setNome_scontrino(String nome_scontrino) {
        this.nome_scontrino.set(nome_scontrino);
    }

    public double getNum_pezzi() {
        return num_pezzi.get();
    }

    public SimpleDoubleProperty num_pezziProperty() {
        return num_pezzi;
    }

    public void setNum_pezzi(double num_pezzi) {
        this.num_pezzi.set(makePrecise(num_pezzi,2));
    }

    public double getPrezzo_scontrino() {
        return prezzo_scontrino.get();
    }

    public SimpleDoubleProperty prezzo_scontrinoProperty() {
        return prezzo_scontrino;
    }

    public void setPrezzo_scontrino(double prezzo_scontrino) {this.prezzo_scontrino.set(makePrecise(prezzo_scontrino,2));}

    public boolean isSfuso() {
        return isSfuso;
    }

    public void setSfuso(boolean sfuso) {
        isSfuso = sfuso;
    }

    public double getPrezzo_singolo() {
        return makePrecise(prezzo_singolo,2);
    }

    public void setPrezzo_singolo(double prezzo_singolo) {
        this.prezzo_singolo = prezzo_singolo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrezzo_di_acquisto() {
        return makePrecise(prezzo_di_acquisto,2);
    }

    public void setPrezzo_di_acquisto(double prezzo_di_acquisto) {
        this.prezzo_di_acquisto = prezzo_di_acquisto;
    }
}
