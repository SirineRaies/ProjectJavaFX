package org.example.projectjavafx.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {

    @FXML
    private Pane button_signup;
    @FXML
    private Pane button_login;

    @FXML
    public void Go_To_Signup(MouseEvent event) {
        try {
                    // Charger le fichier FXML de la page SignUp
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Pane) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } catch (IOException e) {
                    System.err.println("Erreur lors du chargement de la page SignUp.fxml : " + e.getMessage());
                    e.printStackTrace();
                }
            }
    @FXML
    public void Go_To_Login(MouseEvent event) {
        try {
            // Charger le fichier FXML de la page Login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();

            // Obtenir la scène et changer de page
            Stage stage = (Stage) ((Pane) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page Login.fxml : " + e.getMessage());
            e.printStackTrace();
        }
    }


}



