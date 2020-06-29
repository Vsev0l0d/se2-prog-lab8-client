import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.*;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUI/Views/HiPanel.fxml"));
        primaryStage.setTitle("StudyGroupProject. Авторизация.");
        primaryStage.setScene(new Scene(root, 350, 405));
        primaryStage.show();
        
        URL music = getClass().getResource("GUI/Music/HiMark.mp3");
        Media media = new Media(music.toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        Thread.sleep(100);
        mediaPlayer.play();
    }
}