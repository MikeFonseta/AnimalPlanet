package com.mikefonseta.animalplanet.Entity;

import java.util.List;

public class ScontrinoGrafico {

    private String data;
    private double profitto;

    public ScontrinoGrafico(String data, double profitto) {
        this.data = data;
        this.profitto = profitto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getProfitto() {
        return profitto;
    }

    public void setProfitto(double profitto) {
        this.profitto = profitto;
    }
}
