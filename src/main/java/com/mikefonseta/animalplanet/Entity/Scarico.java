package com.mikefonseta.animalplanet.Entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Scarico {

    private SimpleIntegerProperty id;
    private SimpleStringProperty fornitore;
    private SimpleStringProperty data_scarico;
    private SimpleDoubleProperty importo;

    public Scarico(int id, String fornitore, double importo,String data_scarico) {
        this.id = new SimpleIntegerProperty(id);
        this.fornitore = new SimpleStringProperty(fornitore);
        this.data_scarico = new SimpleStringProperty(data_scarico);
        this.importo = new SimpleDoubleProperty(importo);
    }

    public double getImporto() {
        return importo.get();
    }

    public SimpleDoubleProperty importoProperty() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo.set(importo);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFornitore() {
        return fornitore.get();
    }

    public SimpleStringProperty fornitoreProperty() {
        return fornitore;
    }

    public void setFornitore(String fornitore) {
        this.fornitore.set(fornitore);
    }

    public String getData_scarico() {
        return data_scarico.get();
    }

    public SimpleStringProperty data_scaricoProperty() {
        return data_scarico;
    }

    public void setData_scarico(String data_scarico) {
        this.data_scarico.set(data_scarico);
    }
}
