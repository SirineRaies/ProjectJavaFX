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
import org.example.projectjavafx.Models.Produit;
import org.example.projectjavafx.Services.ProduitService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardProduitsController implements Initializable {
        @FXML
        private TableView<Produit> table_produits;
        @FXML
        private TableColumn<Produit, Long> col_id;
        @FXML
        private TableColumn<Produit, String> col_nomPro;
        @FXML
        private TableColumn<Produit, String> col_prixPro;
        @FXML
        private TextField tf_id;
        @FXML
        private TextField tf_nompro;
        @FXML
        private TextField tf_prixpro;
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
        private Button btn_ratings;
        @FXML
        private Button btn_userspage;

        private final ProduitService produitService = new ProduitService();

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            col_nomPro.setCellValueFactory(new PropertyValueFactory<>("nompro"));
            col_prixPro.setCellValueFactory(new PropertyValueFactory<>("prixpro"));

            loadProduitsData();

            table_produits.setOnMouseClicked(this::selectProduit);
            btn_insert.setOnAction(this::addProduit);
            btn_update.setOnAction(this::updateProduit);
            btn_delete.setOnAction(this::deleteProduit);
        }

        private void selectProduit(MouseEvent mouseEvent) {
            Produit selectedproduits = table_produits.getSelectionModel().getSelectedItem();
            if (selectedproduits != null) {
                tf_id.setText(String.valueOf(selectedproduits.getId()));
                tf_nompro.setText(String.valueOf(selectedproduits.getNompro()));
                tf_prixpro.setText(String.valueOf(selectedproduits.getPrixpro()));
            }
        }

        private void addProduit(ActionEvent actionEvent) {
            try {
                String nompro = tf_nompro.getText();
                float prixpro = Float.parseFloat(tf_prixpro.getText());


                Produit produit = new Produit(nompro, prixpro);
                ProduitService.ajouter(produit);
                loadProduitsData();
                showAlert(Alert.AlertType.CONFIRMATION, "Succés", "Produit ajouté avec succés.");
            } catch (SQLException | NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur d'ajouter un produit : " + e.getMessage());
            }
        }

        private void updateProduit(ActionEvent actionEvent) {
            Produit selectedProduit = table_produits.getSelectionModel().getSelectedItem();
            if (selectedProduit != null) {
                try {
                    String nompro = tf_nompro.getText();
                    float prixpro = Float.parseFloat(tf_prixpro.getText());

                    selectedProduit.setNompro(nompro);
                    selectedProduit.setPrixpro(prixpro);

                    ProduitService.modifier(selectedProduit);
                    loadProduitsData();
                    showAlert(Alert.AlertType.CONFIRMATION, "Succés", "Produit est mis à jour avec succés.");
                } catch (SQLException | NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de mis à jour du produit: " + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Aucun produit est séléctionnée", "S'il vous plait séléctionner un produit à modifier.");
            }
        }

        private void deleteProduit(ActionEvent actionEvent) {
            Produit selectedProduit = table_produits.getSelectionModel().getSelectedItem();
            if (selectedProduit != null) {
                try {
                    ProduitService.supprimer(selectedProduit);
                    loadProduitsData();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Produit supprimé avec succés .");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression du produit : " + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Aucun produit n'est sélectionné", " S'il vous plait séléctionner un produit à supprimer.");
            }
        }

        private void loadProduitsData() {
            ObservableList<Produit> produits = FXCollections.observableArrayList();
            try {
                produits.addAll(produitService.afficher());
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de charger les données des produits: " + e.getMessage());
            }
            table_produits.setItems(produits);
        }

        private void showAlert(Alert.AlertType alertType, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setContentText(message);
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

