import Interfaces.ConsoleManager;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUI/Views/HiPanel.fxml"));
        primaryStage.setTitle("StudyGroupProject. Авторизация.");
        primaryStage.setScene(new Scene(root, 350, 405));
        primaryStage.show();
    }
}