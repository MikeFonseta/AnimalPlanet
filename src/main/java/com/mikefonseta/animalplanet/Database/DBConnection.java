package com.mikefonseta.animalplanet.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static DBConnection instance = null;
    private Connection connection = null;

    private DBConnection()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:animalplanet.db");

            initTables();
        }
        catch ( Exception e )
        {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        //System.out.println("Opened database successfully");
    }

    public Connection getConnection() {
        return connection;
    }

    private void initTables() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Prodotto (\n" +
                "\tid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\tnome TEXT NOT NULL,\n" +
                "\tcategoria TEXT NOT NULL DEFAULT 'Nessuna' NOT NULL,\n" +
                "\tprezzo_di_acquisto DECIMAL(10,2) NOT NULL,\n" +
                "\tprezzo_di_vendita DECIMAL(10,2) NOT NULL,\n" +
                "\tsfuso BOOLEAN default 0,\n" +
                "  \tCONSTRAINT fkCategoria FOREIGN KEY(categoria) REFERENCES Categoria(nome_categoria)\n" +
                ");");

        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Categoria(\n" +
                "\tnome_categoria TEXT NOT NULL PRIMARY KEY, UNIQUE(nome_categoria)\n" +
                ");");
        statement.executeUpdate("INSERT OR IGNORE INTO Categoria(nome_categoria) VALUES('Nessuna')");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS Scontrino(\n" +
                "    id_scontrino INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    creazione_ordine DATE DEFAULT (STRFTIME('%H:%M %d/%m/%Y', DATETIME('now'))),\n" +
                "    sconto DECIMAL(10,2) NOT NULL,\n" +
                "    totale DECIMAL(10,2) NOT NULL\n" +
                ");");
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS CompScontrino(\n" +
                "    nome_prodotto TEXT NOT NULL,\n" +
                "    categoria TEXT NOT NULL DEFAULT 'Nessuna' NOT NULL,\n" +
                "    num_pezzi INTEGER NOT NULL,\n" +
                "    prezzo DECIMAL(10,2) NOT NULL,\n" +
                "    sfuso BOOLEAN default 0,\n" +
                "    id_scontrino INTEGER NOT NULL,\n" +
                "    CONSTRAINT  fkCOScontrino FOREIGN KEY(id_scontrino) REFERENCES Scontrino(id_scontrino) ON DELETE CASCADE\n" +
                ");");
    }


    public static DBConnection getInstance() throws SQLException {
        if (instance == null)
        {
            instance = new DBConnection();
        }
        else
        if (instance.getConnection().isClosed())
        {
            instance = new DBConnection();
        }

        return instance;
    }

}