package com.mikefonseta.animalplanet.Entity;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class ScontrinoStatistiche {

    private Scontrino scontrino;
    private List<ProdottoSingoloScontrino> compScontrino;

    public ScontrinoStatistiche(Scontrino scontrino, List<ProdottoSingoloScontrino> compScontrino) {
        this.scontrino = scontrino;
        this.compScontrino = compScontrino;
    }

    public Scontrino getScontrino() {
        return scontrino;
    }

    public List<ProdottoSingoloScontrino> getCompScontrino() {
        return compScontrino;
    }

    public void setCompScontrino(List<ProdottoSingoloScontrino> compScontrino) {
        this.compScontrino = compScontrino;
    }

    public int getId_scontrinoS() {
        return scontrino.getId_scontrinoS();
    }

    public SimpleIntegerProperty id_scontrinoSProperty() {
        return new SimpleIntegerProperty(scontrino.getId_scontrinoS());
    }

    public void setId_scontrinoS(int id_scontrinoS) {
        this.getScontrino().setId_scontrinoS(id_scontrinoS);
    }

    public String getCreazione_ordineS() {
        return scontrino.getCreazione_ordineS();
    }

    public SimpleStringProperty creazione_ordineSProperty() {
        return new SimpleStringProperty(scontrino.getCreazione_ordineS());
    }

    public void setCreazione_ordineS(String creazione_ordineS) {
        this.scontrino.setCreazione_ordineS(creazione_ordineS);
    }

    public double getProfitto() {
        return scontrino.getProfitto();
    }

    public SimpleDoubleProperty profittoProperty() {
        return new SimpleDoubleProperty(scontrino.getProfitto());
    }

    public void setProfitto(double profitto) {
        this.scontrino.setProfitto(profitto);
    }

    public int getRicarico() {
        return scontrino.getRicarico();
    }

    public SimpleIntegerProperty ricaricoProperty() {
        return new SimpleIntegerProperty(scontrino.getRicarico());
    }

    public void setRicarico(int ricarico) {
        this.scontrino.setRicarico(ricarico);
    }

    public double getScontoS() {
        return scontrino.getScontoS();
    }

    public SimpleDoubleProperty scontoSProperty() {
        return new SimpleDoubleProperty(scontrino.getScontoS());
    }

    public void setScontoS(double scontoS) {
        this.scontrino.setScontoS(scontoS);
    }

    public double getTotaleS() {
        return scontrino.getTotaleS();
    }

    public SimpleDoubleProperty totaleSProperty() {
        return new SimpleDoubleProperty(scontrino.getTotaleS());
    }

    public void setTotaleS(double totaleS) {
        this.scontrino.setTotaleS(totaleS);
    }
}
