package com.mikefonseta.animalplanet.Entity;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProdottoListaScontrino {

    private int id;
    private SimpleStringProperty nome_scontrino;
    private SimpleFloatProperty num_pezzi;
    private SimpleFloatProperty prezzo_scontrino;
    private boolean isSfuso;
    private float prezzo_singolo;
    private String categoria;

    public ProdottoListaScontrino(int id, String nome, String categoria, float numPezzi, float prezzoDiVendita, boolean isSfuso) {
        this.id = id;
        this.nome_scontrino = new SimpleStringProperty(nome);
        this.num_pezzi = new SimpleFloatProperty(numPezzi);
        this.prezzo_singolo = prezzoDiVendita;
        this.prezzo_scontrino = new SimpleFloatProperty(prezzoDiVendita*numPezzi);
        this.categoria = categoria;
        this.isSfuso = isSfuso;
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

    public float getNum_pezzi() {
        return num_pezzi.get();
    }

    public SimpleFloatProperty num_pezziProperty() {
        return num_pezzi;
    }

    public void setNum_pezzi(float num_pezzi) {
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

    public boolean isSfuso() {
        return isSfuso;
    }

    public void setSfuso(boolean sfuso) {
        isSfuso = sfuso;
    }

    public float getPrezzo_singolo() {
        return prezzo_singolo;
    }

    public void setPrezzo_singolo(float prezzo_singolo) {
        this.prezzo_singolo = prezzo_singolo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
