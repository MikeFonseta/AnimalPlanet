package com.mikefonseta.animalplanet.Entity;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProdottoListaScontrino {

    private int id;
    private SimpleStringProperty nome_scontrino;
    private SimpleIntegerProperty num_pezzi;
    private SimpleFloatProperty prezzo_scontrino;
    private float prezzo_singolo;

    public ProdottoListaScontrino(int id, String nome, int numPezzi, float prezzoDiVendita) {
        this.id = id;
        this.nome_scontrino = new SimpleStringProperty(nome);
        this.num_pezzi = new SimpleIntegerProperty(numPezzi);
        this.prezzo_singolo = prezzoDiVendita;
        this.prezzo_scontrino = new SimpleFloatProperty(prezzoDiVendita);
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

    public int getNum_pezzi() {
        return num_pezzi.get();
    }

    public SimpleIntegerProperty num_pezziProperty() {
        return num_pezzi;
    }

    public void setNum_pezzi(int num_pezzi) {
        this.num_pezzi.set(num_pezzi);
    }

    public float getPrezzo_scontrino() {
        return prezzo_scontrino.get();
    }

    public SimpleFloatProperty prezzo_scontrinoProperty() {
        return prezzo_scontrino;
    }

    public void setPrezzo_scontrino(float prezzo_scontrino) {
        this.prezzo_scontrino.set(prezzo_scontrino);
    }

    public float getPrezzo_singolo() {
        return prezzo_singolo;
    }

    public void setPrezzo_singolo(float prezzo_singolo) {
        this.prezzo_singolo = prezzo_singolo;
    }
}
