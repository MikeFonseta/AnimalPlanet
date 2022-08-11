package com.mikefonseta.animalplanet.Controller;

import com.mikefonseta.animalplanet.Database.Product;
import com.mikefonseta.animalplanet.Database.Receipt;
import com.mikefonseta.animalplanet.Entity.Prodotto;
import com.mikefonseta.animalplanet.Entity.ProdottoListaScontrino;
import com.mikefonseta.animalplanet.data;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class mainController implements Initializable {

    //Prodotti
    @FXML
    private ChoiceBox<String> categorie;
    @FXML
    private TextField ricerca;
    @FXML
    private TableView<Prodotto> listaProdotti;
    @FXML
    public TableColumn<Prodotto, String> nome;
    @FXML
    public TableColumn<Prodotto, String> categoria;
    @FXML
    public TableColumn<Prodotto, Float> prezzo_di_acquisto;
    @FXML
    public TableColumn<Prodotto, Float> prezzo_di_vendita;
    @FXML
    public TableColumn<Prodotto, String> ricarico;

    //Scontrino
    @FXML
    private TableView scontrino;
    @FXML
    public TableColumn<Prodotto, String> nome_scontrino;
    @FXML
    public TableColumn<Prodotto, Integer> num_pezzi;
    @FXML
    public TableColumn<Prodotto, Float> prezzo_scontrino;
    @FXML
    public Label totaleScontrino;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        prezzo_di_acquisto.setCellValueFactory(new PropertyValueFactory<>("prezzoDiAcquisto"));
        prezzo_di_vendita.setCellValueFactory(new PropertyValueFactory<>("prezzoDiVendita"));
        ricarico.setCellValueFactory(new PropertyValueFactory<>("ricarico"));

        nome_scontrino.setCellValueFactory(new PropertyValueFactory<>("nome_scontrino"));
        num_pezzi.setCellValueFactory(new PropertyValueFactory<>("num_pezzi"));
        prezzo_scontrino.setCellValueFactory(new PropertyValueFactory<>("prezzo_scontrino"));

        addButtonToScontrino();

        listaProdotti.setRowFactory(tv -> {
            TableRow<Prodotto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Prodotto prodotto = row.getItem();
                    if(!prodotto.isSfuso()) {
                        if (data.getListaProdottiScontrino().stream().anyMatch(item -> prodotto.getId() == item.getId())) {
                            int index = IntStream.range(0, data.getListaProdottiScontrino().size())
                                    .filter(i -> data.getListaProdottiScontrino().get(i).getId() == prodotto.getId())
                                    .findFirst()
                                    .orElse(-1);
                            data.getListaProdottiScontrino().get(index).setNum_pezzi(data.getListaProdottiScontrino().get(index).getNum_pezzi() + 1);
                            data.getListaProdottiScontrino().get(index).setPrezzo_scontrino(data.getListaProdottiScontrino().get(index).getPrezzo_singolo() * data.getListaProdottiScontrino().get(index).getNum_pezzi());
                            data.setTotaleScontrino(data.getTotaleScontrino() + prodotto.getPrezzoDiVendita());
                            totaleScontrino.setText("Totale: " + data.getTotaleScontrino() + "€");
                        } else {
                            data.getListaProdottiScontrino().add(new ProdottoListaScontrino(prodotto.getId(), prodotto.getNome(),prodotto.getCategoria(), 1, prodotto.getPrezzoDiVendita(), false));
                            data.setTotaleScontrino(data.getTotaleScontrino() + prodotto.getPrezzoDiVendita());
                            totaleScontrino.setText("Totale: " + data.getTotaleScontrino() + "€");
                        }
                    }else{
                        addSfusoToCart(new ProdottoListaScontrino(prodotto.getId(),prodotto.getNome(), prodotto.getCategoria(), 1,prodotto.getPrezzoDiVendita(),prodotto.isSfuso()), false);
                    }
                }
            });
            return row ;
        });

        try {
            data.setCategorie(Product.getCategorie());
            data.setProdotti(Product.getProducts());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        data.getCategorie().add(0,"");
        categorie.setItems(data.getCategorie());
        listaProdotti.setItems(data.getProdotti());

        scontrino.setItems(data.getListaProdottiScontrino());
        totaleScontrino.setText("Totale: " + data.getTotaleScontrino()+"€");
    }

    private void addButtonToScontrino() {
        TableColumn addBtn = new TableColumn("");
        TableColumn removeBtn = new TableColumn("");

        Callback<TableColumn<ProdottoListaScontrino, Void>, TableCell<ProdottoListaScontrino, Void>> cellFactoryAdd = new Callback<TableColumn<ProdottoListaScontrino, Void>, TableCell<ProdottoListaScontrino, Void>>() {
            @Override
            public TableCell<ProdottoListaScontrino, Void> call(final TableColumn<ProdottoListaScontrino, Void> param) {
                final TableCell<ProdottoListaScontrino, Void> cell = new TableCell<ProdottoListaScontrino, Void>() {

                    private final Button add_btn = new Button("+");

                    {
                        add_btn.setOnAction((ActionEvent event) -> {
                            ProdottoListaScontrino prodotto = getTableView().getItems().get(getIndex());
                            if(!prodotto.isSfuso()) {
                                prodotto.setNum_pezzi(prodotto.getNum_pezzi() + 1);
                                prodotto.setPrezzo_scontrino(prodotto.getPrezzo_singolo() * prodotto.getNum_pezzi());
                                data.setTotaleScontrino(data.getTotaleScontrino() + prodotto.getPrezzo_singolo());
                                totaleScontrino.setText("Totale: " + data.getTotaleScontrino() + "€");
                            }else
                            {
                                addSfusoToCart(prodotto,true);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(add_btn);
                        }
                    }
                };
                return cell;
            }
        };

        Callback<TableColumn<ProdottoListaScontrino, Void>, TableCell<ProdottoListaScontrino, Void>> cellFactoryRemove = new Callback<TableColumn<ProdottoListaScontrino, Void>, TableCell<ProdottoListaScontrino, Void>>() {
            @Override
            public TableCell<ProdottoListaScontrino, Void> call(final TableColumn<ProdottoListaScontrino, Void> param) {
                final TableCell<ProdottoListaScontrino, Void> cell = new TableCell<ProdottoListaScontrino, Void>() {

                    private final Button remove_btn = new Button("-");

                    {
                        remove_btn.setOnAction((ActionEvent event) -> {
                            ProdottoListaScontrino prodotto = getTableView().getItems().get(getIndex());
                            if(!prodotto.isSfuso()) {
                                if(prodotto.getNum_pezzi() > 1) {
                                    prodotto.setNum_pezzi(prodotto.getNum_pezzi() - 1);
                                    prodotto.setPrezzo_scontrino(prodotto.getPrezzo_singolo() * prodotto.getNum_pezzi());
                                } else {
                                    data.getListaProdottiScontrino().remove(prodotto);
                                }
                                data.setTotaleScontrino(data.getTotaleScontrino() - prodotto.getPrezzo_singolo());
                                totaleScontrino.setText("Totale: " + data.getTotaleScontrino() + "€");
                            }else{
                                addSfusoToCart(prodotto, true);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(remove_btn);
                        }
                    }
                };
                return cell;
            }
        };

        addBtn.setMaxWidth(40);
        addBtn.setMinWidth(40);
        addBtn.setPrefWidth(40);
        removeBtn.setMaxWidth(40);
        removeBtn.setMinWidth(40);
        removeBtn.setPrefWidth(40);

        addBtn.setCellFactory(cellFactoryAdd);
        removeBtn.setCellFactory(cellFactoryRemove);
        scontrino.getColumns().addAll(addBtn,removeBtn);
    }

    public void addProduct() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addProduct.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);
        } catch (IOException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 2", ButtonType.OK);
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
    }

    public void deleteProduct()
    {
        if(listaProdotti.getSelectionModel().getSelectedIndex() != -1) {
            ButtonType yes = new ButtonType("SI", ButtonBar.ButtonData.BACK_PREVIOUS);
            ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.NEXT_FORWARD);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Sicuro di voler eliminare "+
                    data.getProdotti().get(listaProdotti.getSelectionModel().getSelectedIndex()).getNome() +"?", yes, no);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();

            if (alert.getResult() == yes) {
                int id = data.getProdotti().get(listaProdotti.getSelectionModel().getSelectedIndex()).getId();
                String name = data.getProdotti().get(listaProdotti.getSelectionModel().getSelectedIndex()).getNome();
                try {
                    if(Product.deleteProduct(id) == 1)
                    {
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION, name + " eliminato correttamente", ButtonType.OK);
                        alert1.setTitle("");
                        alert1.setHeaderText("");
                        alert1.showAndWait();
                    }
                } catch (SQLException e) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "Eliminazione non riuscita\nCodice errore: 3", ButtonType.OK);
                    alert1.setTitle("");
                    alert1.setHeaderText("");
                    alert1.showAndWait();
                }
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nessun elemento selezionato", ButtonType.OK);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();
        }
    }

    public void modifyProduct() {
        if(listaProdotti.getSelectionModel().getSelectedIndex() != -1) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/modifyProduct.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                modifyProdcutController modifyProdcutController = fxmlLoader.getController();
                modifyProdcutController.loadProduct(data.getProdotti().get(listaProdotti.getSelectionModel().getSelectedIndex()));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                stage.setResizable(false);
            } catch (IOException e) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 4", ButtonType.OK);
                alert1.setTitle("");
                alert1.setHeaderText("");
                alert1.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nessun elemento selezionato", ButtonType.OK);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();
        }
    }

    public void search(){
        try {
            data.setProdotti(Product.search(ricerca.getText(), categorie.getValue()));
        } catch (SQLException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 5", ButtonType.OK);
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
        listaProdotti.setItems(data.getProdotti());
    }

    public void svuotaListaProdotti(){
        ricerca.setText("");
        categorie.setValue("");
        try {
            data.setProdotti(Product.getProducts());
        } catch (SQLException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 6", ButtonType.OK);
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
        listaProdotti.setItems(data.getProdotti());
    }

    public void hide(){
        if(!data.isIsHiding()) {
            prezzo_di_vendita.setVisible(false);
            prezzo_di_acquisto.setVisible(false);
            ricarico.setVisible(false);
            data.setIsHiding(true);
        }
        else{
            prezzo_di_vendita.setVisible(true);
            prezzo_di_acquisto.setVisible(true);
            ricarico.setVisible(true);
            data.setIsHiding(false);
        }
    }

    public void svuotaScontrino(){
        data.getListaProdottiScontrino().clear();
        data.setTotaleScontrino(0);
        totaleScontrino.setText("Totale: " + data.getTotaleScontrino()+"€");
    }

    public void procedi(){
        if(data.getListaProdottiScontrino() != null && data.getListaProdottiScontrino().size() > 0) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addScontrino.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                stage.setResizable(false);
            } catch (IOException e) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 7", ButtonType.OK);
                alert1.setTitle("");
                alert1.setHeaderText("");
                alert1.showAndWait();
            }
        }else
        {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Scontrino vuoto", ButtonType.OK);
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }

    }

    public void addSfusoToCart(ProdottoListaScontrino prodotto, boolean modify) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addSfusoValue.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            addSfusoController addSfusoController = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.setResizable(false);
            data.setProdottoSfuso(prodotto);
            data.setModifyProdottoSfuso(modify);
            addSfusoController.updateInfo();
            addSfusoController.setLabel(totaleScontrino);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 8\n"+e.getCause(), ButtonType.OK);
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
    }

    public void getTodayScontrini() throws SQLException {
        Receipt.getTodayScontrini();
    }
}