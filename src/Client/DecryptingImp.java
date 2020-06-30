package Client;

import Commands.SerializedCommands.*;
import Commands.SerializedResAuth;
import GUI.Controllers.HiPanelController;
import Interfaces.Decrypting;
import com.google.inject.Singleton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

@Singleton
public class DecryptingImp implements Decrypting {
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
                    System.out.println("ass");
                } else ctrl.showAlert("Вы не зарегистрированы!");
            }

            if (serializedResAuth.getType().equals("reg")) {
                FXMLLoader loader = new FXMLLoader(DecryptingImp.class.getResource("/GUI/Views/Register.fxml"));
                Parent sceneFXML = loader.load();
                HiPanelController ctrl = (loader.getController());
                if (serializedResAuth.getRes()) {
                    System.out.println("asds");
                } else ctrl.showAlert("Пользователь с таким логином уже существует!");
            }
        }
    }
}
