package com.mikefonseta.animalplanet.Entity;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Prodotto {

    private int id;
    private SimpleStringProperty nome;
    private SimpleStringProperty categoria;
    private SimpleFloatProperty prezzoDiAcquisto,prezzoDiVendita;
    private SimpleIntegerProperty ricarico;
    private boolean sfuso;

    public Prodotto(int id, String nome, String categoria, float prezzoDiAcquisto, float prezzoDiVendita,boolean sfuso) {
        this.id = id;
        this.nome = new SimpleStringProperty(nome);
        this.categoria = new SimpleStringProperty(categoria);
        this.prezzoDiAcquisto = new SimpleFloatProperty(prezzoDiAcquisto);
        this.prezzoDiVendita = new SimpleFloatProperty(prezzoDiVendita);
        this.ricarico = new SimpleIntegerProperty((int) (((prezzoDiVendita / prezzoDiAcquisto) - 1)*100));
        this.sfuso = sfuso;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome.get();
    }

    public SimpleStringProperty nomeProperty() {
        return nome;
    }

    public String getCategoria() {
        return categoria.get();
    }

    public SimpleStringProperty categoriaProperty() {
        return categoria;
    }

    public float getPrezzoDiAcquisto() {
        return prezzoDiAcquisto.get();
    }

    public SimpleFloatProperty prezzoDiAcquistoProperty() {
        return prezzoDiAcquisto;
    }

    public float getPrezzoDiVendita() {
        return prezzoDiVendita.get();
    }

    public SimpleFloatProperty prezzoDiVenditaProperty() {
        return prezzoDiVendita;
    }

    public int getRicarico() {
        return ricarico.get();
    }

    public SimpleIntegerProperty ricaricoProperty() {
        return ricarico;
    }

    public boolean isSfuso() {
        return sfuso;
    }

    public void setCategoria(String nome) {
        this.categoria = new SimpleStringProperty(nome);
    }
}
