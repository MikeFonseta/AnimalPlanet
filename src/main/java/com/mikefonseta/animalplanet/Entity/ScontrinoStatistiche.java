package com.mikefonseta.animalplanet.Entity;

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
}
