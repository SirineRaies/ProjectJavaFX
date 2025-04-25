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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.projectjavafx.Models.Offre;
import org.example.projectjavafx.Services.OffreService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardOffresController implements Initializable {
        @FXML
        private TableView<Offre> table_offres;
        @FXML
        private TableColumn<Offre, Long> col_id;
        @FXML
        private TableColumn<Offre, Long> col_prodID;
        @FXML
        private TableColumn<Offre, String> col_offre;
        @FXML
        private TableColumn<Offre, String> col_contenu;
        @FXML
        private TextField tf_id;
        @FXML
        private TextField tf_prodID;
        @FXML
        private TextField tf_offre;
        @FXML
        private TextField tf_contenu;
        @FXML
        private Button btn_insert;
        @FXML
        private Button btn_update;
        @FXML
        private Button btn_delete;
        @FXML
        private Button btn_Offres;
        @FXML
        private Button btn_Produit;
        @FXML
        private Button btn_Commandes;
        @FXML
        private Button btn_ratings;
        @FXML
        private Button btn_userspage;

        private final OffreService offreService = new OffreService();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_prodID.setCellValueFactory(new PropertyValueFactory<>("produit_Id"));
            col_offre.setCellValueFactory(new PropertyValueFactory<>("offre"));
            col_contenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));

            loadOffreData();

            table_offres.setOnMouseClicked(this::selectOffre);
            btn_insert.setOnAction(this::addOffre);
            btn_update.setOnAction(this::updateOffre);
            btn_delete.setOnAction(this::deleteOffre);

        }

        private void selectOffre(javafx.scene.input.MouseEvent mouseEvent) {
            Offre selectedOffre = table_offres.getSelectionModel().getSelectedItem();
            if (selectedOffre != null) {
                tf_id.setText(String.valueOf(selectedOffre.getId()));
                tf_prodID.setText(String.valueOf(selectedOffre.getProduit_Id()));
                tf_offre.setText(selectedOffre.getOffre());
                tf_contenu.setText(selectedOffre.getContenu());
            }
        }

        private void addOffre(ActionEvent actionEvent) {
            String title = tf_offre.getText();
            String contenu = tf_contenu.getText();
            long prodID = Long.parseLong(tf_prodID.getText());

            try {
                Offre offre = new Offre(0, title, contenu, prodID);
                offreService.ajouter(offre);
                loadOffreData();
                showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Offre ajoutée avec succès.");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de l'offre : " + e.getMessage());
            }
        }

        private void updateOffre(ActionEvent actionEvent) {
            Offre selectedOffre = table_offres.getSelectionModel().getSelectedItem();
            if (selectedOffre != null) {
                String title = tf_offre.getText();
                String contenu = tf_contenu.getText();
                long prodID = Long.parseLong(tf_prodID.getText());

                selectedOffre.setOffre(title);
                selectedOffre.setContenu(contenu);
                selectedOffre.setProduit_Id(prodID);

                try {
                    offreService.modifier(selectedOffre);
                    loadOffreData();
                    showAlert(Alert.AlertType.CONFIRMATION, "Succès", "Offre mise à jour avec succès.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la mise à jour de l'offre : " + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Aucune Offre sélectionnée", "Veuillez sélectionner une offre à mettre à jour.");
            }
        }

        private void deleteOffre(ActionEvent actionEvent) {
            Offre selectedOffre = table_offres.getSelectionModel().getSelectedItem();
            if (selectedOffre != null) {
                try {
                    offreService.supprimer(selectedOffre);
                    loadOffreData();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Offre supprimée avec succès.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de l'offre : " + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Aucune Offre sélectionnée", "Veuillez sélectionner une offre à supprimer.");
            }
        }

        private void loadOffreData() {
            ObservableList<Offre> offres = FXCollections.observableArrayList();
            try {
                offres.addAll(offreService.afficher());
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des offres : " + e.getMessage());
            }
            table_offres.setItems(offres);
        }
        private ObservableList<Offre> getOffreList() {
            ObservableList<Offre> Offre = FXCollections.observableArrayList();
            try {
                Offre.addAll(offreService.afficher());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Offre;
        }
        private void showAlert(Alert.AlertType alertType, String title, String content) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.showAndWait();
        }


    @FXML
    void go_to_Ratings(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/DashboardRatings.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) btn_ratings.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

