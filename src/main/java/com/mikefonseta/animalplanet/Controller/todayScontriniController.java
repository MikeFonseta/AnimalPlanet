package com.mikefonseta.animalplanet.Controller;

import com.mikefonseta.animalplanet.Database.Product;
import com.mikefonseta.animalplanet.Database.Receipt;
import com.mikefonseta.animalplanet.Entity.ProdottoSingoloScontrino;
import com.mikefonseta.animalplanet.Entity.Scontrino;
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

public class todayScontriniController implements Initializable {

    @FXML
    private TableView<Scontrino> todayScontrini;
    @FXML
    public TableColumn<Scontrino, Integer> id_scontrinoS;
    @FXML
    public TableColumn<Scontrino, String> creazione_ordineS;
    @FXML
    public TableColumn<Scontrino, Float> scontoS;
    @FXML
    public TableColumn<Scontrino, Float> totaleS;


    @FXML
    private TableView<ProdottoSingoloScontrino> singoloScontrino;
    @FXML
    public TableColumn<ProdottoSingoloScontrino, String> nome_prodottoSC;
    @FXML
    public TableColumn<ProdottoSingoloScontrino, String> categoriaSC;
    @FXML
    public TableColumn<ProdottoSingoloScontrino, Float> num_pezziSC;
    @FXML
    public TableColumn<ProdottoSingoloScontrino, Float> prezzoSC;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_scontrinoS.setCellValueFactory(new PropertyValueFactory<>("id_scontrinoS"));
        creazione_ordineS.setCellValueFactory(new PropertyValueFactory<>("creazione_ordineS"));
        scontoS.setCellValueFactory(new PropertyValueFactory<>("scontoS"));
        totaleS.setCellValueFactory(new PropertyValueFactory<>("totaleS"));

        nome_prodottoSC.setCellValueFactory(new PropertyValueFactory<>("nome_prodottoSC"));
        categoriaSC.setCellValueFactory(new PropertyValueFactory<>("categoriaSC"));
        num_pezziSC.setCellValueFactory(new PropertyValueFactory<>("num_pezziSC"));
        prezzoSC.setCellValueFactory(new PropertyValueFactory<>("prezzoSC"));

        addButtonToSingoloScontrino();

        singoloScontrino.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        todayScontrini.setRowFactory(tv -> {
            TableRow<Scontrino> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Scontrino scontrino = row.getItem();
                    try {
                        singoloScontrino.getItems().clear();
                        singoloScontrino.setItems(Receipt.getSingoloScontrino(scontrino.getId_scontrinoS()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });

        try {
            todayScontrini.setItems(Receipt.getTodayScontrini());
        } catch (SQLException e) {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 13\n", ButtonType.OK);
            e.printStackTrace();
            alert1.setTitle("");
            alert1.setHeaderText("");
            alert1.showAndWait();
        }
    }

    private void addButtonToSingoloScontrino() {
        TableColumn addBtn = new TableColumn("");
        TableColumn removeBtn = new TableColumn("");

        Callback<TableColumn<ProdottoSingoloScontrino, Void>, TableCell<ProdottoSingoloScontrino, Void>> cellFactoryAdd = new Callback<TableColumn<ProdottoSingoloScontrino, Void>, TableCell<ProdottoSingoloScontrino, Void>>() {
            @Override
            public TableCell<ProdottoSingoloScontrino, Void> call(final TableColumn<ProdottoSingoloScontrino, Void> param) {
                final TableCell<ProdottoSingoloScontrino, Void> cell = new TableCell<ProdottoSingoloScontrino, Void>() {

                    private final Button add_btn = new Button("+");
                    {
                        add_btn.setOnAction((ActionEvent event) -> {
                            ProdottoSingoloScontrino prodotto = getTableView().getItems().get(getIndex());
                            prodotto.getId_scontrino();
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

        Callback<TableColumn<ProdottoSingoloScontrino, Void>, TableCell<ProdottoSingoloScontrino, Void>> cellFactoryRemove = new Callback<TableColumn<ProdottoSingoloScontrino, Void>, TableCell<ProdottoSingoloScontrino, Void>>() {
            @Override
            public TableCell<ProdottoSingoloScontrino, Void> call(final TableColumn<ProdottoSingoloScontrino, Void> param) {
                final TableCell<ProdottoSingoloScontrino, Void> cell = new TableCell<ProdottoSingoloScontrino, Void>() {

                    private final Button remove_btn = new Button("-");

                    {
                        remove_btn.setOnAction((ActionEvent event) -> {
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
        singoloScontrino.getColumns().addAll(addBtn,removeBtn);
    }

    public void deleteScontrino(){
        if(todayScontrini.getSelectionModel().getSelectedIndex() != -1) {
            ButtonType yes = new ButtonType("SI", ButtonBar.ButtonData.BACK_PREVIOUS);
            ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.NEXT_FORWARD);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Sicuro di voler eliminare lo scontrino con id "+
                    data.getTodayScontrini().get(todayScontrini.getSelectionModel().getSelectedIndex()).getId_scontrinoS() +"?", yes, no);
            alert.setTitle("");
            alert.setHeaderText("");
            alert.showAndWait();

            if (alert.getResult() == yes) {
                int id = data.getTodayScontrini().get(todayScontrini.getSelectionModel().getSelectedIndex()).getId_scontrinoS();
                try {
                    if(Receipt.deleteScontrino(id) == 1)
                    {
                        singoloScontrino.getItems().clear();
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION,  "Scontrino eliminato correttamente", ButtonType.OK);
                        alert1.setTitle("");
                        alert1.setHeaderText("");
                        alert1.showAndWait();
                    }
                } catch (SQLException e) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "Eliminazione non riuscita\nCodice errore: 15", ButtonType.OK);
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

    public void modifyScontrino(){
        if(todayScontrini.getSelectionModel().getSelectedIndex() != -1) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/modifyScontrino.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                modifyScontrinoController modifyScontrinoController = fxmlLoader.getController();
                modifyScontrinoController.setInfo(data.getTodayScontrini().get(todayScontrini.getSelectionModel().getSelectedIndex()));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
                stage.setResizable(false);
            } catch (IOException e) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Codice errore: 15" + e.getCause(), ButtonType.OK);
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

}
