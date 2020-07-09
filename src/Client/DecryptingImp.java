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
import java.util.List;
import java.util.stream.Collectors;

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
            if (serializedMessage.getMessage() != null) ctrl.showInfo(translate(serializedMessage.getMessage()));
            else {
                if (serializedMessage.getLinkedList().isEmpty()){
                    ctrl.showInfo("коллекция пуста");
                } else ctrl.showInfo(serializedMessage.getLinkedList().stream().map(StudyGroup::toString).collect(Collectors.joining()));
            }
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
                } else ctrl.showAlert("Не удается зайти");
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
            try {
                commandReceiver.getCollection("regular");
                Thread.sleep(3000);
            } catch (InterruptedException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private String translate(String message){
        switch (message.toLowerCase()) {
            case "выполнено": return "";
            case "элемент добавлен": return "";
            case "элемент не добавлен": return "";
            case "элемент обновлен": return "";
            case "элемент не обновлен": return "";
            case "элемент создан другим пользователем": return "";
            case "элемента с таким ID нет в коллекции": return "";
            case "некорректный аргумент": return "";
            case "элемент удален": return "";
            case "свои элементы коллекции удалены": return "";
            case "таких элементов не найдено": return "";
            case "элемент не прошел валидацию на стороне сервера": return "";
        }

        if (message.contains("removeElements")){
            message = message.replace("removeElements", "удалены элементы с ID");
        } else if (message.contains("ошибка при удалении из бд элемента с id=")){
            message = message.replace("Ошибка при удалении из бд элемента с id=", "err");
        } else if (message.contains("%data")){
            message = message.replace("%type", "Тип коллекции –");
            message = message.replace("%data", "Дата инициализации коллекции –");
            message = message.replace("%size", "Количество элементов в коллекции –");
        }
        return message;
    }
}
