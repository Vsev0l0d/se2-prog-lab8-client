package GUI.Controllers;

import Client.ClientModule;
import Interfaces.CommandReceiver;
import com.google.inject.Guice;
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

public class RegistrationController {
    private CommandReceiver commandReceiver;

    public void setCommandReceiver(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @FXML
    private Button regUserBtn;
    @FXML
    private Button authUserWindowBtn;
    @FXML
    private TextField newLoginField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField repNewPasswordField;


    @FXML
    void regUserAction(ActionEvent event) throws IOException {
        if (!newLoginField.getText().isEmpty() && !newPasswordField.getText().isEmpty() && !repNewPasswordField.getText().isEmpty()) {
            if (newPasswordField.getText().equals(repNewPasswordField.getText())) {
                try {
                    commandReceiver.register(newLoginField.getText(), newPasswordField.getText());
                } catch (ClassNotFoundException | InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            } else showAlert("Введенные пароли не совпадают!");
        } else showAlert("Одно из требуемых полей пустое!");
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

    public void showAlert(String alertMessage) throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");

        alert.setHeaderText(null);
        alert.setContentText(alertMessage);

        alert.showAndWait();
    }

    public void showSuccessMessage(String alertMessage) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Успех!");

        alert.setHeaderText(null);
        alert.setContentText(alertMessage);

        alert.showAndWait();
    }
}
