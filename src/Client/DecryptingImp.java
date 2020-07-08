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
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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
            FXMLLoader loader = new FXMLLoader(DecryptingImp.class.getResource("/GUI/Views/MainStage.fxml"));
            Parent sceneFXML = loader.load();
            MainStageController ctrl = (loader.getController());
            ctrl.showInfo(serializedMessage.getMessage());
        }
        if (o instanceof SerializedResAuth) {
            SerializedResAuth serializedResAuth = (SerializedResAuth) o;

            if (serializedResAuth.getType().equals("auth")) {
                FXMLLoader loader = new FXMLLoader(DecryptingImp.class.getResource("/GUI/Views/HiPanel.fxml"));
                Parent sceneFXML = loader.load();
                HiPanelController ctrl = (loader.getController());
                ctrl.setCommandReceiver(commandReceiver);
                if (serializedResAuth.getRes()) {
                    try {
                        ctrl.changeToMain(commandReceiver.getPrimaryStage(), commandReceiver);
                        commandReceiver.getCollection("return_collection_init");
                    } catch (ClassNotFoundException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else ctrl.showAlert("Вы не зарегистрированы!");
            }

            if (serializedResAuth.getType().equals("reg")) {
                FXMLLoader loader = new FXMLLoader(DecryptingImp.class.getResource("/GUI/Views/Register.fxml"));
                Parent sceneFXML = loader.load();
                RegistrationController ctrl = (loader.getController());
                ctrl.setCommandReceiver(commandReceiver);
                if (serializedResAuth.getRes()) {
                    try {
                        ctrl.changeToMain(commandReceiver.getPrimaryStage(), commandReceiver);
                        ctrl.showSuccessMessage("Пользователь успешно зарегистрирован");
                        commandReceiver.getCollection("return_collection_init");
                    } catch (ClassNotFoundException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else ctrl.showAlert("Пользователь с таким логином уже существует!");
            }
        }

        if (o instanceof SerializedCollection) {
            SerializedCollection serializedCollection = (SerializedCollection) o;
            LinkedList<StudyGroup> linkedList = serializedCollection.getLinkedList();
            List<List<Integer>> idElementsAllUsers = serializedCollection.getIdElementsAllUsers();
            if (serializedCollection.getRequireType().equals("init")) {

                new Thread(()->{
                   while (!Thread.currentThread().isInterrupted()) {
                        try {
                            commandReceiver.getCollection("regular");
                            Thread.sleep(3000);
                       } catch (InterruptedException | ClassNotFoundException e) {
                           e.printStackTrace();
                        }
                    }
                }).start();

                MainStageController mainStageController = commandReceiver.getMainStageController();

                mainStageController.setCommandReceiver(commandReceiver);
                mainStageController.setCollection(linkedList, idElementsAllUsers);

            } else {
                MainStageController mainStageController = commandReceiver.getMainStageController();
                mainStageController.setCollection(linkedList, idElementsAllUsers);
                mainStageController.updateTable();
                mainStageController.visual();
            }
        }
    }

    @Override
    public void requireCollection() {
        while (true) {
            System.out.println("asd");
            try {
                commandReceiver.getCollection("regular");
                Thread.sleep(3000);
            } catch (InterruptedException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
