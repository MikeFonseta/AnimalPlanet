package com.mikefonseta.animalplanet.Entity;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Scontrino {

    private SimpleIntegerProperty id_scontrino;
    private SimpleStringProperty creazione_ordine;
    private SimpleFloatProperty sconto;
    private SimpleFloatProperty totale;

    public Scontrino(int id_scontrino, String creazione_ordine, float sconto, float totale) {
        this.id_scontrino = new SimpleIntegerProperty(id_scontrino);
        this.creazione_ordine = new SimpleStringProperty(creazione_ordine.toString());
        this.sconto = new SimpleFloatProperty(sconto);
        this.totale = new SimpleFloatProperty(totale);
    }

    public int getId_scontrino() {
        return id_scontrino.get();
    }

    public SimpleIntegerProperty id_scontrinoProperty() {
        return id_scontrino;
    }

    public void setId_scontrino(int id_scontrino) {
        this.id_scontrino.set(id_scontrino);
    }

    public String getCreazione_ordine() {
        return creazione_ordine.get();
    }

    public SimpleStringProperty creazione_ordineProperty() {
        return creazione_ordine;
    }

    public void setCreazione_ordine(String creazione_ordine) {
        this.creazione_ordine.set(creazione_ordine);
    }

    public float getSconto() {
        return sconto.get();
    }

    public SimpleFloatProperty scontoProperty() {
        return sconto;
    }

    public void setSconto(float sconto) {
        this.sconto.set(sconto);
    }

    public float getTotale() {
        return totale.get();
    }

    public SimpleFloatProperty totaleProperty() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale.set(totale);
    }
}
