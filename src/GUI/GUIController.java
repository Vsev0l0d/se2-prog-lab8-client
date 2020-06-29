package GUI;

import Client.ClientModule;
import Commands.Utils.HashEncrypterImp;
import Interfaces.CommandReceiver;
import Interfaces.HashEncrypter;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIController {
    private final HashEncrypter hashEncrypter = new HashEncrypterImp();

    @FXML
    private Button authUserBtn;
    @FXML
    private Button regUserWindowBtn;
    @FXML
    private Button regUserBtn;
    @FXML
    private Button authUserWindowBtn;
    @FXML
    private TextField userLoginField;
    @FXML
    private PasswordField userPasswordField;

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

    @FXML
    void authUserAction(ActionEvent event) {
        if (!userLoginField.getText().isEmpty() && !userPasswordField.getText().isEmpty()) {
            Injector injector = Guice.createInjector(new ClientModule());
            CommandReceiver commandReceiver = injector.getInstance(CommandReceiver.class);

            try {
                commandReceiver.tryAuth(userLoginField.getText().trim(), hashEncrypter.encryptString(userPasswordField.getText().trim()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else showAlert("Вы не ввели логин или пароль!");


    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");

        alert.setHeaderText(null);
        alert.setContentText(alertMessage);

        alert.showAndWait();
    }


}