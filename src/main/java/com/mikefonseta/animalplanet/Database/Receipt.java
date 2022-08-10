package com.mikefonseta.animalplanet.Database;

import com.mikefonseta.animalplanet.Entity.Prodotto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Receipt {

    public static ObservableList<Prodotto> getProducts() throws SQLException {

        ObservableList<Prodotto> products = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Prodotto";

        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {

            products.add(new Prodotto(rs.getInt("id"), rs.getString("nome"),
                    rs.getString("categoria"), rs.getFloat("prezzo_di_acquisto"),
                    rs.getFloat("prezzo_di_vendita"), rs.getBoolean("sfuso")));
        }

        rs.close();
        statement.close();
        conn.close();

        return products;
    }
}
