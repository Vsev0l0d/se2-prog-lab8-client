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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {
    private CommandReceiver commandReceiver;
    private AnchorPane pane;
    private Scene scene;
    private Stage primaryStage;

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
                    commandReceiver.setPrimaryStage(primaryStage);
                    commandReceiver.register(newLoginField.getText(), newPasswordField.getText());
                } catch (ClassNotFoundException | InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            } else showAlert("Введенные пароли не совпадают!");
        } else showAlert("Одно из требуемых полей пустое!");
    }

    @FXML
    void displayAuthWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/Views/HiPanel.fxml"));
            pane = (AnchorPane) loader.load();
            scene = new Scene(pane, 350, 350);
            primaryStage.setScene(scene);
            primaryStage.show();

            HiPanelController controller = loader.getController();
            controller.setCommandReceiver(commandReceiver);
            controller.setPrimaryStage(primaryStage);
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

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void changeToMain(Stage primaryStage, CommandReceiver commandReceiver) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/Views/MainStage.fxml"));
        pane = loader.load();
        scene = new Scene(pane, 1198, 494);
        primaryStage.setScene(scene);
        primaryStage.setTitle("StudyGroupProject. Окно работы.");
        primaryStage.show();

        MainStageController controller = loader.getController();
        commandReceiver.setMainStageController(controller);
        controller.setCommandReceiver(commandReceiver);
        controller.setPrimaryStage(primaryStage);
    }
}
