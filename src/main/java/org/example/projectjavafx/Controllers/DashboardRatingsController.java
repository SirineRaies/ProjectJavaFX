package org.example.projectjavafx.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.projectjavafx.Models.Rating;
import org.example.projectjavafx.Services.RatingService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardRatingsController implements Initializable {
    @FXML
    private TableView<Rating> table_ratings;
    @FXML
    private TableColumn<Rating, Long> col_id;
    @FXML
    private TableColumn<Rating, String> col_comment;
    @FXML
    private TableColumn<Rating, Long> col_user;
    @FXML
    private TableColumn<Rating, Float> col_rate;
    @FXML
    private TextField tf_id;
    @FXML
    private TextField tf_comment;
    @FXML
    private TextField tf_user;
    @FXML
    private TextField tf_rate;
    @FXML
    private Button btn_insert;
    @FXML
    private Button  btn_update;
    @FXML
    private Button  btn_delete;
    @FXML
    private Button btn_Offres;
    @FXML
    private Button btn_Produit;
    @FXML
    private Button btn_Commandes;
    @FXML
    private Button btn_userspage;

    private final RatingService ratingsService = new RatingService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_comment.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
        col_user.setCellValueFactory(new PropertyValueFactory<>("user_ID"));
        col_rate.setCellValueFactory(new PropertyValueFactory<>("rating"));

        loadRatingsData();

        table_ratings.setOnMouseClicked(this::selectRating);
        btn_insert.setOnAction(this::addRating);
        btn_update.setOnAction(this::updateRating);
        btn_delete.setOnAction(this::deleteRating);
    }

    private void selectRating(MouseEvent mouseEvent) {
        Rating selectedRating = table_ratings.getSelectionModel().getSelectedItem();
        if (selectedRating != null) {
            tf_id.setText(String.valueOf(selectedRating.getId()));
            tf_comment.setText(selectedRating.getCommentaire());
            tf_user.setText(String.valueOf(selectedRating.getUser_ID()));
            tf_rate.setText(String.valueOf(selectedRating.getRating()));
        }
    }

    private void addRating(ActionEvent actionEvent) {
        try {
            String comment = tf_comment.getText();
            long userId = Long.parseLong(tf_user.getText());
            float rate = Float.parseFloat(tf_rate.getText());

            Rating rating = new Rating(comment, rate,userId);
            ratingsService.ajouter(rating);
            loadRatingsData();
            showAlert(Alert.AlertType.CONFIRMATION, "Succés", "Rating aajouté avec succés.");
        } catch (SQLException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error adding rating: " + e.getMessage());
        }
    }

    private void updateRating(ActionEvent actionEvent) {
        Rating selectedRating = table_ratings.getSelectionModel().getSelectedItem();
        if (selectedRating != null) {
            try {
                String comment = tf_comment.getText();
                long userId = Long.parseLong(tf_user.getText());
                float rate = Float.parseFloat(tf_rate.getText());

                selectedRating.setCommentaire(comment);
                selectedRating.setUser_ID(userId);
                selectedRating.setRating(rate);
                if (rate < 0.0 || rate > 5.0) { // Exemple : le taux doit être entre 0.0 et 5.0
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Le taux doit être compris entre 0.0 et 5.0.");
                    return;
                }

                RatingService.modifier(selectedRating);
                loadRatingsData();
                showAlert(Alert.AlertType.CONFIRMATION, "Succés", "Rating est mis à jour avec succés.");
            } catch (SQLException | NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de mis à jour du Rating: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucun Rating est séléctionné", "S'il vou splait selectionner un Rating à modifier.");
        }
    }

    private void deleteRating(ActionEvent actionEvent) {
        Rating selectedRating = table_ratings.getSelectionModel().getSelectedItem();
        if (selectedRating != null) {
            try {
                ratingsService.supprimer(selectedRating);
                loadRatingsData();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Rating deleted successfully.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Error deleting rating: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Rating Selected", "Please select a rating to delete.");
        }
    }

    private void loadRatingsData() {
        ObservableList<Rating> ratings = FXCollections.observableArrayList();
        try {
            ratings.addAll(RatingService.afficher());
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error loading ratings data: " + e.getMessage());
        }
        table_ratings.setItems(ratings);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    void go_to_Produits(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DashboardProduits.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) btn_Produit.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void go_toUser(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DashboardAdmins.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) btn_userspage.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void go_to_Offres(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DashboardOffres.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) btn_Offres.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    void go_to_Commandes(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DashboardCommandes.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) btn_Commandes.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}