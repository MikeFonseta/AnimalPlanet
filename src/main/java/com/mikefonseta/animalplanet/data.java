package com.mikefonseta.animalplanet;

import com.mikefonseta.animalplanet.Entity.Prodotto;
import com.mikefonseta.animalplanet.Entity.ProdottoScontrino;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class data {

    private static ObservableList<Prodotto> prodotti = FXCollections.observableArrayList();
    private static Prodotto modifyProduct = null;
    private static ObservableList<String> categorie = FXCollections.observableArrayList();
    private static boolean isHiding = false;

    private static ObservableList<ProdottoScontrino> scontrino = FXCollections.observableArrayList();

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

    public static ObservableList<ProdottoScontrino> getScontrino() {return scontrino;}
    public static void setScontrino(ObservableList<ProdottoScontrino> scontrino) {data.scontrino = scontrino;}
}

