package org.example.projectjavafx.Services;

import org.example.projectjavafx.Models.Rating;
import org.example.projectjavafx.Utils.MyConnection;
import javafx.scene.chart.XYChart;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RatingService {
    private static Connection connection;

    public RatingService() {
        connection = MyConnection.getInstance().getCnx();
    }

    // Méthode pour ajouter une évaluation
    public void ajouter(Rating rating) throws SQLException {
        String sql = "INSERT INTO ratings (commentaire,user_ID, rating) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, rating.getCommentaire());
            preparedStatement.setLong(2, rating.getUser_ID());
            preparedStatement.setFloat(3, rating.getRating());
            preparedStatement.executeUpdate();
        }
    }

    // Méthode pour mettre à jour une évaluation
    public static void modifier(Rating rating) throws SQLException {
        String sql = "UPDATE ratings SET commentaire = ?, user_ID = ?, rating = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, rating.getCommentaire());
            preparedStatement.setLong(2, rating.getUser_ID());
            preparedStatement.setFloat(3, rating.getRating());
            preparedStatement.setLong(4, rating.getId());
            preparedStatement.executeUpdate();
        }
    }

    // Méthode pour supprimer une évaluation
    public void supprimer(Rating rating) throws SQLException {
        String sql = "DELETE FROM ratings WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, rating.getId());
            preparedStatement.executeUpdate();
        }
    }

    // Méthode pour afficher toutes les évaluations
    public static List<Rating> afficher() throws SQLException {
        List<Rating> ratingsList = new ArrayList<>();
        String sql = "SELECT * FROM ratings";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Rating rating = new Rating();
                rating.setId(resultSet.getLong("id"));
                rating.setCommentaire(resultSet.getString("commentaire"));
                rating.setUser_ID(resultSet.getLong("user_id"));
                rating.setRating(resultSet.getFloat("rating"));
                ratingsList.add(rating);
            }
        }
        return ratingsList;
    }

    public int getTotalRatingCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM ratings";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0;
    }

    public int getNewRatingCount(int days) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ratings WHERE DATE(date) >= DATE(NOW()) - INTERVAL ? DAY";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, days);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }




}