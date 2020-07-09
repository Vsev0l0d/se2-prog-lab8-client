package GUI.Controllers;

import Interfaces.CommandReceiver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HiPanelController {
    private CommandReceiver commandReceiver;
    private Stage primaryStage;
    private AnchorPane pane;
    private Scene scene;

    public void setCommandReceiver(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private Button authUserBtn;
    @FXML
    private Button regUserWindowBtn;
    @FXML
    private TextField userLoginField;
    @FXML
    private PasswordField userPasswordField;

    @FXML
    void displayRegWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/GUI/Views/Register.fxml"));
            pane = (AnchorPane) loader.load();
            scene = new Scene(pane, 350, 390);
            primaryStage.setScene(scene);
            primaryStage.show();

            RegistrationController controller = loader.getController();
            controller.setCommandReceiver(commandReceiver);
            controller.setPrimaryStage(primaryStage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void authUserAction(ActionEvent event) {
        if (!userLoginField.getText().isEmpty() && !userPasswordField.getText().isEmpty()) {
            try {
                commandReceiver.setPrimaryStage(primaryStage);
                commandReceiver.tryAuth(userLoginField.getText().trim(), userPasswordField.getText().trim());
            } catch (ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        } else showAlert("Вы не ввели логин или пароль!");
    }


    public void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");

        alert.setHeaderText(null);
        alert.setContentText(alertMessage);

        alert.showAndWait();
    }

    public void changeToMain(Stage primaryStage, CommandReceiver commandReceiver) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GUI/Views/MainStage.fxml"));
        pane = loader.load();
        scene = new Scene(pane, 1198, 494);
        primaryStage.setScene(scene);
        primaryStage.setTitle("StudyGroupProject");
        primaryStage.show();

        MainStageController controller = loader.getController();
        commandReceiver.setMainStageController(controller);
        controller.setCommandReceiver(commandReceiver);
        controller.setPrimaryStage(primaryStage);
    }
}