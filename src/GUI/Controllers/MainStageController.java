package GUI.Controllers;

import BasicClasses.StudyGroup;
import Client.ClientModule;
import Interfaces.CommandReceiver;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.io.IOException;

public class MainStageController {
    @FXML
    private TableView<StudyGroup> tableView;
    @FXML
    private Button toTableBtn;
    @FXML
    private Button toGroupMapBtn;
    @FXML
    private Button executeCommandBtn;
    @FXML
    private Button aboutFagotsBtn;

    @FXML
    public void initialize() {
        Injector injector = Guice.createInjector(new ClientModule());
        CommandReceiver commandReceiver = injector.getInstance(CommandReceiver.class);

        try {
            commandReceiver.getCollection();
        } catch (ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");

        alert.setHeaderText(null);
        alert.setContentText(alertMessage);

        alert.showAndWait();
    }
}
