package com.mikefonseta.animalplanet.Database;

import com.mikefonseta.animalplanet.Entity.*;
import com.mikefonseta.animalplanet.data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mikefonseta.animalplanet.data.makePrecise;

public class Statistics {

    public static int dayStatistics(String day) throws SQLException {

        ObservableList<ScontrinoStatistiche> scontrini = FXCollections.observableArrayList();

        if(day == null) { day = "now"; }

        String sql = "SELECT * FROM Scontrino WHERE STRFTIME('%Y-%m-%d', creazione_ordine) = STRFTIME('%Y-%m-%d', DATETIME('"+day+"'))";
        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        int result = 0;

        while (rs.next()) {
            result = 1;
            scontrini.add(new ScontrinoStatistiche(new Scontrino(rs.getInt("id_scontrino"), rs.getString("creazione_ordine"),0,0,
                    rs.getFloat("sconto"), rs.getFloat("totale")),null));

        }

        data.setDayStatistics(scontrini);
        rs.close();
        statement.close();
        conn.close();

        for(ScontrinoStatistiche s: data.getDayStatistics()){
            s.setCompScontrino(Receipt.getSingoloScontrino(s.getScontrino().getId_scontrinoS()));
        }

        float totale = 0;
        float totaleAcquisto = 0;
        for(ScontrinoStatistiche s: data.getDayStatistics()){
            totale += s.getScontrino().getTotaleS();
            for(ProdottoSingoloScontrino p: s.getCompScontrino()){
                 totaleAcquisto += p.getPrezzo_di_acquisto()*p.getNum_pezziSC();
            }
        }

        if(totale != 0) {
            data.setIncassoDay(makePrecise(totale,2));
            data.setNettoDay(makePrecise(totale - totaleAcquisto,2));
            data.setRicaricoDay((int) makePrecise(((totale / totaleAcquisto) - 1) * 100, 2));
        }else{
            data.setIncassoDay(0);
            data.setNettoDay(0);
            data.setRicaricoDay(0);
            result = 1;
        }
        //System.out.println("INCASSO: " + totale + "\nNETTO: " + (totale-totaleAcquisto) + "\nRICARICO: " + (int) (((totale / totaleAcquisto) - 1)*100));
        return result;
    }

    public static int weeklyStatistics(String week) throws SQLException {

        ObservableList<ScontrinoStatistiche> scontrini = FXCollections.observableArrayList();

        if(week == null) { week = "now"; }

        String sql = "SELECT * FROM Scontrino WHERE strftime('%W', creazione_ordine) == strftime('%W', '"+week+"')";
        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        int result = 0;
        while (rs.next()) {
            result = 1;
            scontrini.add(new ScontrinoStatistiche(new Scontrino(rs.getInt("id_scontrino"), rs.getString("creazione_ordine"),0,0,
                    rs.getFloat("sconto"), rs.getFloat("totale")),null));

        }

        data.setWeeklytStatistics(scontrini);
        rs.close();
        statement.close();
        conn.close();

        for(ScontrinoStatistiche s: data.getWeeklytStatistics()){
            s.setCompScontrino(Receipt.getSingoloScontrino(s.getScontrino().getId_scontrinoS()));
        }

        float totale = 0;
        float totaleAcquisto = 0;
        for(ScontrinoStatistiche s: data.getWeeklytStatistics()){
            totale += s.getScontrino().getTotaleS();
            for(ProdottoSingoloScontrino p: s.getCompScontrino()){
                totaleAcquisto += p.getPrezzo_di_acquisto()*p.getNum_pezziSC();
            }
        }

        if(totale != 0) {
            data.setIncassoWeekly(makePrecise(totale,2));
            data.setNettoWeekly(makePrecise(totale - totaleAcquisto,2));
            data.setRicaricoWeekly((int) makePrecise(((totale / totaleAcquisto) - 1) * 100, 2));
        }else{
            data.setIncassoWeekly(0);
            data.setNettoWeekly(0);
            data.setRicaricoWeekly(0);
        }
        //System.out.println("INCASSO: " + totale + "\nNETTO: " + (totale-totaleAcquisto) + "\nRICARICO: " + (int) (((totale / totaleAcquisto) - 1)*100));

        return result;
    }

    public static int monthlyStatistics(String month) throws SQLException {

        ObservableList<ScontrinoStatistiche> scontrini = FXCollections.observableArrayList();

        if(month == null) { month = "now"; }

        String sql = "SELECT * FROM Scontrino WHERE strftime('%m', creazione_ordine) == strftime('%m', '"+month+"')";

        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        int result = 0;
        while (rs.next()) {
            result = 1;
            scontrini.add(new ScontrinoStatistiche(new Scontrino(rs.getInt("id_scontrino"), rs.getString("creazione_ordine"),0,0,
                    rs.getFloat("sconto"), rs.getFloat("totale")),null));

        }

        data.setMonthlyStatistics(scontrini);
        rs.close();
        statement.close();
        conn.close();

        for(ScontrinoStatistiche s: data.getMonthlyStatistics()){
            s.setCompScontrino(Receipt.getSingoloScontrino(s.getScontrino().getId_scontrinoS()));
        }

        float totale = 0;
        float totaleAcquisto = 0;
        for(ScontrinoStatistiche s: data.getMonthlyStatistics()){
            totale += s.getScontrino().getTotaleS();
            for(ProdottoSingoloScontrino p: s.getCompScontrino()){
                totaleAcquisto += p.getPrezzo_di_acquisto()*p.getNum_pezziSC();
            }
        }
        data.setIncassoMonthly(makePrecise(totale,2));
        data.setNettoMonthly(makePrecise(totale - totaleAcquisto,2));
        data.setRicaricoMonthly((int) makePrecise(((totale / totaleAcquisto) - 1) * 100, 2));

        return result;
    }

    public static List<ScontrinoGrafico> getScontriniStats() throws SQLException {

        ArrayList<ScontrinoStatistiche> scontrini = new ArrayList<>();
        ArrayList<ScontrinoGrafico> result = new ArrayList<>();

        double spese = getSpese();

        String sql = "SELECT * FROM Scontrino ORDER BY creazione_ordine ASC LIMIT 20 ";
        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {

            scontrini.add(new ScontrinoStatistiche(new Scontrino(rs.getInt("id_scontrino"), rs.getString("creazione_ordine"),0,0,
                    makePrecise(rs.getDouble("sconto"),2), makePrecise(rs.getDouble("totale"),2)),null));
        }

        for(ScontrinoStatistiche s: scontrini){
            s.setCompScontrino(Receipt.getSingoloScontrino(s.getScontrino().getId_scontrinoS()));
        }


        float totale = 0;
        float totaleAcquisto = 0;
        String date = "";
        for(int i = 0; i < scontrini.size(); i++){
            date = scontrini.get(i).getScontrino().getCreazione_ordineS().substring(0,7);
            totale += scontrini.get(i).getScontrino().getTotaleS();
            for(ProdottoSingoloScontrino p: scontrini.get(i).getCompScontrino()){
                totaleAcquisto += p.getPrezzo_di_acquisto()*p.getNum_pezziSC();
            }

            while(i + 1 < scontrini.size() && scontrini.get(i + 1).getScontrino().getCreazione_ordineS().substring(0,7).equals(date)){
                totale += scontrini.get(i + 1).getScontrino().getTotaleS();
                for(ProdottoSingoloScontrino p: scontrini.get(i + 1).getCompScontrino()){
                    totaleAcquisto += p.getPrezzo_di_acquisto()*p.getNum_pezziSC();
                }
                i++;
            }
            result.add(new ScontrinoGrafico(date, totale-totaleAcquisto-spese));
            totale = 0;
            totaleAcquisto = 0;
        }

        rs.close();
        statement.close();
        conn.close();

        return result;
    }

    public static ObservableList<ProdottoSingoloScontrino> getProdottiVenduti(String month) throws SQLException {

        List<ScontrinoStatistiche> scontrini = new ArrayList<>();
        ObservableList<ProdottoSingoloScontrino> result = FXCollections.observableArrayList();

        if(month == null) {
            scontrini = data.getMonthlyStatistics();
        }
        else {

            String sql = "SELECT * FROM Scontrino WHERE strftime('%m', creazione_ordine) == strftime('%m', '" + month + "')";

            Connection conn = DBConnection.getInstance().getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                scontrini.add(new ScontrinoStatistiche(new Scontrino(rs.getInt("id_scontrino"), rs.getString("creazione_ordine"), 0, 0,
                        rs.getFloat("sconto"), rs.getFloat("totale")), null));

            }

            rs.close();
            statement.close();
            conn.close();

            for (ScontrinoStatistiche s : scontrini) {
                s.setCompScontrino(Receipt.getSingoloScontrino(s.getScontrino().getId_scontrinoS()));
            }
        }

        if(scontrini != null) {
            for (ScontrinoStatistiche s : scontrini) {
                for (ProdottoSingoloScontrino p : s.getCompScontrino()) {
                    Optional<ProdottoSingoloScontrino> pFind = result.stream().filter(o -> o.getNome_prodottoSC().equals(p.getNome_prodottoSC())).findAny();
                    if(pFind.isEmpty()){
                        result.add(p);
                    }else{
                        result.get(result.indexOf(pFind.get())).setNum_pezziSC(result.get(result.indexOf(pFind.get())).getNum_pezziSC()+p.getNum_pezziSC());
                        result.get(result.indexOf(pFind.get())).setPrezzoSC(result.get(result.indexOf(pFind.get())).getPrezzoSC() * result.get(result.indexOf(pFind.get())).getNum_pezziSC());
                        result.get(result.indexOf(pFind.get())).setNettoSC(result.get(result.indexOf(pFind.get())).getNettoSC() * result.get(result.indexOf(pFind.get())).getNum_pezziSC());
                    }
                }
            }
        }

        return result;
    }

    public static double getSpese() throws SQLException {

        double spese = 0;

        String sql = "SELECT * FROM Info";

        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {

            spese = rs.getDouble("spese");
        }
        data.setSpese(spese);
        rs.close();
        statement.close();
        conn.close();

        return spese;
    }

    public static int updateSpese(double newValue) throws SQLException {

        int result = 0;
        String sql = "UPDATE Info SET spese="+newValue+" WHERE spese="+data.getSpese()+"";
        Connection conn = DBConnection.getInstance().getConnection();
        Statement statement  = conn.createStatement();
        result = statement.executeUpdate(sql);
        if(result == 0){
            sql = "INSERT INTO Info(spese) VALUES(0)";
            statement.executeUpdate(sql);
        }
        data.setSpese(newValue);
        statement.close();
        conn.close();

        return result;
    }
}
