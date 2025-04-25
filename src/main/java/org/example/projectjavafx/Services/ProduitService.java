package org.example.projectjavafx.Services;

import org.example.projectjavafx.Models.Produit;
import org.example.projectjavafx.Utils.MyConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProduitService {

    private static Connection connection;

    public ProduitService() {
        connection = MyConnection.getInstance().getCnx();
    }

    // Methode pour ajouter un produit
    public static void ajouter(Produit produit) throws SQLException {
        String sql = "INSERT INTO produits (nompro, prixpro) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, produit.getNompro());
        preparedStatement.setFloat(2, produit.getPrixpro());
        preparedStatement.executeUpdate();
    }

    // Methode pour mettre à jour un produit
    public static void modifier(Produit produit) throws SQLException {
        String sql = "UPDATE produits SET nompro = ?, prixpro = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, produit.getNompro());
        preparedStatement.setFloat(2, produit.getPrixpro());
        preparedStatement.setLong(3, produit.getId());
        preparedStatement.executeUpdate();
    }

    // Methode pour supprimer un produit
    public static void supprimer(Produit produit) throws SQLException {
        String sql = "DELETE FROM produits WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, produit.getId());
        preparedStatement.executeUpdate();
    }

    // Methode pour afficher tous les produits
    public List<Produit> afficher() throws SQLException {
        String sql = "SELECT * FROM produits";
        List<Produit> produits = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Produit produit = new Produit();
                produit.setId(rs.getLong("id"));
                produit.setNompro(rs.getString("nompro"));
                produit.setPrixpro(rs.getFloat("prixpro"));
                produits.add(produit);
            }
        }
        return produits;
    }

    // Methode pour récupérer les produits ajoutés les 7 derniers jours
    public Map<LocalDate, Integer> getProduitCreationLast7Days() throws SQLException {
        Map<LocalDate, Integer> produits = new HashMap<>();

        String sql = "SELECT DATE(created_at) as creation_date, COUNT(*) as creation_count " +
                "FROM produits " +
                "WHERE created_at >= CURDATE() - INTERVAL 7 DAY " +
                "GROUP BY DATE(created_at)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            LocalDate date = resultSet.getDate("creation_date").toLocalDate();
            int count = resultSet.getInt("creation_count");
            produits.put(date, count);
        }

        // Ensure all days in the last 7 days are represented, even if there were no produits
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 7; i++) {
            LocalDate day = today.minusDays(i);
            produits.putIfAbsent(day, 0);
        }

        return produits;
    }
}
