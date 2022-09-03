package com.mikefonseta.animalplanet.Entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import static com.mikefonseta.animalplanet.data.makePrecise;

public class Prodotto {

    private int id;
    private SimpleStringProperty nome;
    private SimpleStringProperty categoria;
    private SimpleDoubleProperty prezzoDiAcquisto;
    private SimpleDoubleProperty prezzoDiVendita;
    private SimpleIntegerProperty ricarico;
    private boolean sfuso;

    public Prodotto(int id, String nome, String categoria, double prezzoDiAcquisto, double prezzoDiVendita,boolean sfuso) {
        this.id = id;
        this.nome = new SimpleStringProperty(nome);
        this.categoria = new SimpleStringProperty(categoria);
        this.prezzoDiAcquisto = new SimpleDoubleProperty(makePrecise(prezzoDiAcquisto,2));
        this.prezzoDiVendita = new SimpleDoubleProperty(makePrecise(prezzoDiVendita,2));
        this.ricarico = new SimpleIntegerProperty((int) (((makePrecise(prezzoDiVendita,2) / makePrecise(prezzoDiAcquisto,2)) - 1)*100));
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

    public double getPrezzoDiAcquisto() {
        return prezzoDiAcquisto.get();
    }

    public SimpleDoubleProperty prezzoDiAcquistoProperty() {
        return prezzoDiAcquisto;
    }

    public double getPrezzoDiVendita() {
        return prezzoDiVendita.get();
    }

    public SimpleDoubleProperty prezzoDiVenditaProperty() {
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
