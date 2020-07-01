package Client;

import BasicClasses.StudyGroup;
import Commands.SerializedCollection;
import Commands.SerializedCommands.*;
import Commands.SerializedResAuth;
import GUI.Controllers.HiPanelController;
import GUI.Controllers.MainStageController;
import GUI.Controllers.RegistrationController;
import Interfaces.CommandReceiver;
import Interfaces.Decrypting;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.LinkedList;

@Singleton
public class DecryptingImp implements Decrypting {
    private final CommandReceiver commandReceiver;

    @Inject
    public DecryptingImp(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    public void decrypt(Object o) throws IOException {
        if (o instanceof SerializedMessage) {
            SerializedMessage serializedMessage = (SerializedMessage) o;
            System.out.println(serializedMessage.getMessage());
        }
        if (o instanceof SerializedResAuth) {
            SerializedResAuth serializedResAuth = (SerializedResAuth) o;

            if (serializedResAuth.getType().equals("auth")) {
                FXMLLoader loader = new FXMLLoader(DecryptingImp.class.getResource("/GUI/Views/HiPanel.fxml"));
                Parent sceneFXML = loader.load();
                HiPanelController ctrl = (loader.getController());
                if (serializedResAuth.getRes()) {
                    try {
                        commandReceiver.getCollection();
                    } catch (ClassNotFoundException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    ctrl.displayMainStageWindow();
                } else ctrl.showAlert("Вы не зарегистрированы!");
            }

            if (serializedResAuth.getType().equals("reg")) {
                FXMLLoader loader = new FXMLLoader(DecryptingImp.class.getResource("/GUI/Views/Register.fxml"));
                Parent sceneFXML = loader.load();
                RegistrationController ctrl = (loader.getController());
                if (serializedResAuth.getRes()) {
                    ctrl.showSuccessMessage("Пользователь успешно зарегистрирован");
                } else ctrl.showAlert("Пользователь с таким логином уже существует!");
            }
        }

        if (o instanceof SerializedCollection) {
            SerializedCollection serializedCollection = (SerializedCollection) o;
            LinkedList<StudyGroup> linkedList = serializedCollection.getLinkedList();

            FXMLLoader loader = new FXMLLoader(DecryptingImp.class.getResource("/GUI/Views/MainStage.fxml"));
            Parent sceneFXML = loader.load();
            MainStageController ctrl = (loader.getController());
            ctrl.setObservableList(linkedList);
        }
    }
}
