package com.mikefonseta.animalplanet;

import com.mikefonseta.animalplanet.Entity.Prodotto;
import com.mikefonseta.animalplanet.Entity.ProdottoListaScontrino;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class data {

    private static ObservableList<Prodotto> prodotti = FXCollections.observableArrayList();
    private static Prodotto modifyProduct = null;
    private static ObservableList<String> categorie = FXCollections.observableArrayList();
    private static boolean isHiding = false;

    private static ObservableList<ProdottoListaScontrino> scontrino = FXCollections.observableArrayList();
    private static float totaleScontrino = 0;

    private static ProdottoListaScontrino prodottoSfuso = null;
    private static boolean modifyProdottoSfuso = false;

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

    public static ObservableList<ProdottoListaScontrino> getScontrino() {return scontrino;}

    public static float getTotaleScontrino() { return totaleScontrino;}
    public static void setTotaleScontrino(float totaleScontrino) { data.totaleScontrino = totaleScontrino;}

    public static ProdottoListaScontrino getProdottoSfuso() { return prodottoSfuso;}
    public static void setProdottoSfuso(ProdottoListaScontrino prodottoSfuso) { data.prodottoSfuso = prodottoSfuso;}
    public static boolean isModifyProdottoSfuso() { return modifyProdottoSfuso;}
    public static void setModifyProdottoSfuso(boolean modifyProdottoSfuso) { data.modifyProdottoSfuso = modifyProdottoSfuso;}
}

