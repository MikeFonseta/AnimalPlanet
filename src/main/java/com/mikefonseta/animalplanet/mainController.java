package com.mikefonseta.animalplanet;

import com.mikefonseta.animalplanet.Database.Product;
import com.mikefonseta.animalplanet.Entity.Prodotto;
import com.mikefonseta.animalplanet.Entity.ProdottoListaScontrino;
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
                    if(data.getScontrino().stream().anyMatch(item -> prodotto.getId() == item.getId()))
                    {
                        int index = IntStream.range(0, data.getScontrino().size())
                                .filter(i -> data.getScontrino().get(i).getId() == prodotto.getId())
                                .findFirst()
                                .orElse(-1);
                        data.getScontrino().get(index).setNum_pezzi(data.getScontrino().get(index).getNum_pezzi() + 1);
                        data.getScontrino().get(index).setPrezzo_scontrino(data.getScontrino().get(index).getPrezzo_singolo()*data.getScontrino().get(index).getNum_pezzi());
                        data.setTotaleIntScontrino(data.getTotaleIntScontrino()+prodotto.getPrezzoDiVendita());
                        totaleScontrino.setText("Totale: " + data.getTotaleIntScontrino()+"€");
                    }else {
                        data.getScontrino().add(new ProdottoListaScontrino(prodotto.getId(), prodotto.getNome(), 1, prodotto.getPrezzoDiVendita()));
                        data.setTotaleIntScontrino(data.getTotaleIntScontrino()+prodotto.getPrezzoDiVendita());
                        totaleScontrino.setText("Totale: " + data.getTotaleIntScontrino()+"€");
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

        scontrino.setItems(data.getScontrino());
        totaleScontrino.setText("Totale: " + data.getTotaleIntScontrino()+"€");
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
                            prodotto.setNum_pezzi(prodotto.getNum_pezzi()+1);
                            prodotto.setPrezzo_scontrino(prodotto.getPrezzo_singolo()*prodotto.getNum_pezzi());
                            data.setTotaleIntScontrino(data.getTotaleIntScontrino()+prodotto.getPrezzo_singolo());
                            totaleScontrino.setText("Totale: " + data.getTotaleIntScontrino()+"€");
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
                            if(prodotto.getNum_pezzi() > 1) {
                                prodotto.setNum_pezzi(prodotto.getNum_pezzi() - 1);
                                prodotto.setPrezzo_scontrino(prodotto.getPrezzo_singolo() * prodotto.getNum_pezzi());
                                data.setTotaleIntScontrino(data.getTotaleIntScontrino()-prodotto.getPrezzo_singolo());
                                totaleScontrino.setText("Totale: " + data.getTotaleIntScontrino()+"€");
                            }
                            else{
                                data.getScontrino().remove(prodotto);
                                data.setTotaleIntScontrino(data.getTotaleIntScontrino()-prodotto.getPrezzo_singolo());
                                totaleScontrino.setText("Totale: " + data.getTotaleIntScontrino()+"€");
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addProduct.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {

        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setResizable(false);
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
                    Stage stage = (Stage) listaProdotti.getScene().getWindow();
                    stage.close();
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Errore durante la connessione al database", ButtonType.OK);
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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("modifyProduct.fxml"));
            Parent root1 = null;
            try {
                root1 = (Parent) fxmlLoader.load();
                modifyProdcutController modifyProdcutController = fxmlLoader.getController();
                modifyProdcutController.loadProduct(data.getProdotti().get(listaProdotti.getSelectionModel().getSelectedIndex()));
            } catch (IOException e) {

            }
            modifyProdcutController modifyController;
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
            stage.setResizable(false);
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
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Errore durante la connessione al database", ButtonType.OK);
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
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Errore durante la connessione al database", ButtonType.OK);
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
        data.getScontrino().clear();
        data.setTotaleIntScontrino(0);
        totaleScontrino.setText("Totale: " + data.getTotaleIntScontrino()+"€");
    }


    public void procedi(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addScontrino.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException e) {

        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.show();
        stage.setResizable(false);

    }

}