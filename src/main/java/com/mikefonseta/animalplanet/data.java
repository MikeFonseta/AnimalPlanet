package com.mikefonseta.animalplanet;

import com.mikefonseta.animalplanet.Entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class data {

    private static ObservableList<Prodotto> prodotti = FXCollections.observableArrayList();
    private static ObservableList<Scarico> scarichi = FXCollections.observableArrayList();
    private static ObservableList<String> categorie = FXCollections.observableArrayList();
    private static ObservableList<String> fornitori = FXCollections.observableArrayList();
    private static double spese = 0;
    private static ObservableList<Scontrino> todayScontrini = FXCollections.observableArrayList();
    private static ObservableList<ProdottoSingoloScontrino> listaSingoloScontrino = FXCollections.observableArrayList();
    private static ObservableList<ScontrinoStatistiche> dayStatistics = FXCollections.observableArrayList();
    private static ObservableList<ScontrinoStatistiche> weeklyStatistics = FXCollections.observableArrayList();
    private static ObservableList<ScontrinoStatistiche> monthlyStatistics = FXCollections.observableArrayList();

    public static double getSpese() {
        return spese;
    }

    public static void setSpese(double spese) {
        data.spese = spese;
    }

    private static double totaleScontrino = 0;
    private static ProdottoListaScontrino prodottoSfuso = null;
    private static boolean modifyProdottoSfuso = false;
    private static boolean isHiding = false;
    private static Prodotto modifyProduct = null;
    private static Scarico modifyScarico = null;
    private static double incassoDay = 0, nettoDay = 0;
    private static int ricaricoDay = 0;

    private static double incassoWeekly = 0, nettoWeekly = 0;
    private static int ricaricoWeekly = 0;

    private static double incassoMonthly = 0, nettoMonthly = 0;
    private static int ricaricoMonthly = 0;

    public static double getIncassoDay() {
        return makePrecise(incassoDay,2);
    }
    public static void setIncassoDay(double incassoDay) {
        data.incassoDay = incassoDay;
    }
    public static double getNettoDay() {
        return makePrecise(nettoDay,2);
    }
    public static void setNettoDay(double nettoDay) {
        data.nettoDay = nettoDay;
    }
    public static int getRicaricoDay() {
        return ricaricoDay;
    }
    public static void setRicaricoDay(int ricaricoDay) {
        data.ricaricoDay = ricaricoDay;
    }
    public static double getIncassoWeekly() {
        return makePrecise(incassoWeekly,2);
    }
    public static void setIncassoWeekly(double incassoWeekly) {
        data.incassoWeekly = incassoWeekly;
    }
    public static double getNettoWeekly() {
        return makePrecise(nettoWeekly,2);
    }
    public static void setNettoWeekly(double nettoWeekly) {
        data.nettoWeekly = nettoWeekly;
    }
    public static int getRicaricoWeekly() {
        return ricaricoWeekly;
    }
    public static void setRicaricoWeekly(int ricaricoWeekly) {
        data.ricaricoWeekly = ricaricoWeekly;
    }
    public static double getIncassoMonthly() {
        return makePrecise(incassoMonthly,2);
    }
    public static void setIncassoMonthly(double incassoMonthly) {
        data.incassoMonthly = incassoMonthly;
    }
    public static double getNettoMonthly() {
        return makePrecise(nettoMonthly,2);
    }
    public static void setNettoMonthly(double nettoMonthly) {
        data.nettoMonthly = nettoMonthly;
    }
    public static int getRicaricoMonthly() {
        return ricaricoMonthly;
    }
    public static void setRicaricoMonthly(int ricaricoMonthly) {
        data.ricaricoMonthly = ricaricoMonthly;
    }

    private static ObservableList<ProdottoListaScontrino> listaProdottiScontrino = FXCollections.observableArrayList();
    public static ObservableList<ProdottoSingoloScontrino> getListaSingoloScontrino() {
        return listaSingoloScontrino;
    }

    public static ObservableList<ScontrinoStatistiche> getDayStatistics() {
        return dayStatistics;
    }
    public static void setDayStatistics(ObservableList<ScontrinoStatistiche> dayStatistics) {
        data.dayStatistics = dayStatistics;
    }

    public static ObservableList<ScontrinoStatistiche> getWeeklytStatistics() {
        return weeklyStatistics;
    }
    public static void setWeeklytStatistics(ObservableList<ScontrinoStatistiche> weeklyStatistics) {
        data.weeklyStatistics = weeklyStatistics;
    }

    public static ObservableList<ScontrinoStatistiche> getMonthlyStatistics() {
        return monthlyStatistics;
    }
    public static void setMonthlyStatistics(ObservableList<ScontrinoStatistiche> monthlyStatistics) {
        data.monthlyStatistics = monthlyStatistics;
    }

    public static ObservableList<Scontrino> getTodayScontrini() {
        return todayScontrini;
    }
    public static void setTodayScontrini(ObservableList<Scontrino> todayScontrini) {
        data.todayScontrini = todayScontrini;
    }

    public static ObservableList<Scarico> getScarichi() {
        return scarichi;
    }
    public static void setScarichi(ObservableList<Scarico> scarichi) {
        data.scarichi = scarichi;
    }

    public static ObservableList<String> getFornitori() {
        return fornitori;
    }
    public static void setFornitori(ObservableList<String> fornitori) {
        data.fornitori = fornitori;
    }
    public static ObservableList<Prodotto> getProdotti() {
        return prodotti;
    }
    public static void setProdotti(ObservableList<Prodotto> prodotti) {
        data.prodotti = prodotti;
    }

    public static Prodotto getModifyProduct() {
        return modifyProduct;
    }
    public static void setModifyProduct(Prodotto modifyProduct) {
        data.modifyProduct = modifyProduct;
    }
    public static ObservableList<String> getCategorie() {
        return categorie;
    }
    public static void setCategorie(ObservableList<String> categorie) {
        data.categorie = categorie;
    }
    public static boolean isIsHiding() {return isHiding;}
    public static void setIsHiding(boolean isHiding) {data.isHiding = isHiding;}

    public static ObservableList<ProdottoListaScontrino> getListaProdottiScontrino() {return listaProdottiScontrino;}

    public static double getTotaleScontrino() { return makePrecise(totaleScontrino,2);}
    public static void setTotaleScontrino(double totaleScontrino) { data.totaleScontrino = makePrecise(totaleScontrino,2);;}

    public static ProdottoListaScontrino getProdottoSfuso() { return prodottoSfuso;}
    public static void setProdottoSfuso(ProdottoListaScontrino prodottoSfuso) { data.prodottoSfuso = prodottoSfuso;}
    public static boolean isModifyProdottoSfuso() { return modifyProdottoSfuso;}
    public static void setModifyProdottoSfuso(boolean modifyProdottoSfuso) { data.modifyProdottoSfuso = modifyProdottoSfuso;}

    public static Scarico getModifyScarico() {
        return modifyScarico;
    }

    public static void setModifyScarico(Scarico modifyScarico) {
        data.modifyScarico = modifyScarico;
    }

    public final static double makePrecise(double value, int precision) {
        double pow = Math.pow(10, precision);
        long powValue = Math.round(pow * value);
        return powValue / pow;
    }
}

