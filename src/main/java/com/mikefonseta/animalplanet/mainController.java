package com.mikefonseta.animalplanet;

import com.mikefonseta.animalplanet.Database.Product;
import com.mikefonseta.animalplanet.Entity.Prodotto;
import com.mikefonseta.animalplanet.Entity.ProdottoScontrino;
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


    @FXML
    private TableView scontrino;
    @FXML
    public TableColumn<Prodotto, String> nome_scontrino;
    @FXML
    public TableColumn<Prodotto, Integer> num_pezzi;
    @FXML
    public TableColumn<Prodotto, Float> prezzo_scontrino;

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
                    }else {
                        data.getScontrino().add(new ProdottoScontrino(prodotto.getId(), prodotto.getNome(), 1, prodotto.getPrezzoDiVendita()));
                    }
                    for (ProdottoScontrino prodottoScontrino: data.getScontrino())
                    {
                        System.out.println("Prodotto: " + prodottoScontrino.getNome_scontrino());
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
        categorie.setItems(data.getCategorie());
        categorie.setValue("Nessuna");
        listaProdotti.setItems(data.getProdotti());

        scontrino.setItems(data.getScontrino());
    }

    private void addButtonToScontrino() {
        TableColumn addBtn = new TableColumn("");
        TableColumn removeBtn = new TableColumn("");

        Callback<TableColumn<ProdottoScontrino, Void>, TableCell<ProdottoScontrino, Void>> cellFactoryAdd = new Callback<TableColumn<ProdottoScontrino, Void>, TableCell<ProdottoScontrino, Void>>() {
            @Override
            public TableCell<ProdottoScontrino, Void> call(final TableColumn<ProdottoScontrino, Void> param) {
                final TableCell<ProdottoScontrino, Void> cell = new TableCell<ProdottoScontrino, Void>() {

                    private final Button add_btn = new Button("+");

                    {
                        add_btn.setOnAction((ActionEvent event) -> {
                            ProdottoScontrino prodotto = getTableView().getItems().get(getIndex());
                            prodotto.setNum_pezzi(prodotto.getNum_pezzi()+1);
                            prodotto.setPrezzo_scontrino(prodotto.getPrezzo_singolo()*prodotto.getNum_pezzi());
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

        Callback<TableColumn<ProdottoScontrino, Void>, TableCell<ProdottoScontrino, Void>> cellFactoryRemove = new Callback<TableColumn<ProdottoScontrino, Void>, TableCell<ProdottoScontrino, Void>>() {
            @Override
            public TableCell<ProdottoScontrino, Void> call(final TableColumn<ProdottoScontrino, Void> param) {
                final TableCell<ProdottoScontrino, Void> cell = new TableCell<ProdottoScontrino, Void>() {

                    private final Button remove_btn = new Button("-");

                    {
                        remove_btn.setOnAction((ActionEvent event) -> {
                            ProdottoScontrino prodotto = getTableView().getItems().get(getIndex());
                            if(prodotto.getNum_pezzi() > 1) {
                                prodotto.setNum_pezzi(prodotto.getNum_pezzi() - 1);
                                prodotto.setPrezzo_scontrino(prodotto.getPrezzo_singolo() * prodotto.getNum_pezzi());
                            }
                            else{
                                data.getScontrino().remove(prodotto);
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
            data.setProdotti(Product.search(ricerca.getText(),categorie.getValue()));
        } catch (SQLException e) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION, "Errore durante la connessione al database", ButtonType.OK);
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
        listaProdotti.setItems(data.getProdotti());
    }

    public void svuota(){
        ricerca.setText("");
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

    public void add_scontrino(){
        data.getScontrino().get(scontrino.getSelectionModel().getSelectedIndex()).setNum_pezzi(data.getScontrino().get(scontrino.getSelectionModel().getSelectedIndex()).getNum_pezzi() + 1);
    }

    public void setRemove_scontrino(){

    }
}