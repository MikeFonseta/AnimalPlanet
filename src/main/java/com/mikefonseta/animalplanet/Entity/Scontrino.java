package com.mikefonseta.animalplanet.Entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Scontrino {

    private SimpleIntegerProperty id_scontrinoS;
    private SimpleStringProperty creazione_ordineS;
    private SimpleDoubleProperty scontoS;
    private SimpleDoubleProperty totaleS;

    public Scontrino(int id_scontrino, String creazione_ordine, double sconto, double totale) {
        this.id_scontrinoS = new SimpleIntegerProperty(id_scontrino);
        this.creazione_ordineS = new SimpleStringProperty(creazione_ordine.toString());
        this.scontoS = new SimpleDoubleProperty(sconto);
        this.totaleS = new SimpleDoubleProperty(totale);
    }

    public int getId_scontrinoS() {
        return id_scontrinoS.get();
    }

    public SimpleIntegerProperty id_scontrinoSProperty() {
        return id_scontrinoS;
    }

    public void setId_scontrinoS(int id_scontrinoS) {
        this.id_scontrinoS.set(id_scontrinoS);
    }

    public String getCreazione_ordineS() {
        return creazione_ordineS.get();
    }

    public SimpleStringProperty creazione_ordineSProperty() {
        return creazione_ordineS;
    }

    public void setCreazione_ordineS(String creazione_ordineS) {
        this.creazione_ordineS.set(creazione_ordineS);
    }

    public double getScontoS() {
        return scontoS.get();
    }

    public SimpleDoubleProperty scontoSProperty() {
        return scontoS;
    }

    public void setScontoS(double scontoS) {
        this.scontoS.set(scontoS);
    }

    public double getTotaleS() {
        return totaleS.get();
    }

    public SimpleDoubleProperty totaleSProperty() {
        return totaleS;
    }

    public void setTotaleS(double totaleS) {
        this.totaleS.set(totaleS);
    }
}
