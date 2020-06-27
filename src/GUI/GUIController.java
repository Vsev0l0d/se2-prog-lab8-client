package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIController {
    @FXML
    private Button authUserBtn;
    @FXML
    private Button regUserWindowBtn;
    @FXML
    private Button regUserBtn;
    @FXML
    private Button authUserWindowBtn;

    @FXML
    void authUserAction(ActionEvent event) {

    }

    @FXML
    void displayRegWindow(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI/Views/Register.fxml"));
            Stage stage = new Stage();
            stage.setTitle("StudyGroupProject. Регистрация.");
            stage.setScene(new Scene(root, 359, 417));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void displayAuthWindow(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI/Views/HiPanel.fxml"));
            Stage stage = new Stage();
            stage.setTitle("StudyGroupProject. Авторизация.");
            stage.setScene(new Scene(root, 350, 405));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void regUserAction(ActionEvent event) {

    }


}