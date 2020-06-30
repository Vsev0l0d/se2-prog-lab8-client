package Client;

import GUI.Controllers.HiPanelController;
import Interfaces.Session;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.util.NoSuchElementException;
import java.util.Scanner;

@Singleton
public class SessionImp implements Session {
    private SocketChannel socketChannel;
    private String hostName;
    private int port;

    @Inject
    public SessionImp() throws IOException {
        FileReader fileReader = null;

        FXMLLoader loader = new FXMLLoader(SessionImp.class.getResource("/GUI/Views/HiPanel.fxml"));
        Parent sceneFXML = loader.load();
        HiPanelController ctrl = (loader.getController());
        try {
            fileReader = new FileReader(System.getProperty("user.dir") + "/config.txt");
            Scanner scan = new Scanner(fileReader);
            String[] data = scan.nextLine().split(" ");
            hostName = data[0];
            port = Integer.parseInt(data[1]);
            fileReader.close();
        } catch (FileNotFoundException e) {
            ctrl.showAlert("Не найден файл config.txt, создайте его в директории " +
                    System.getProperty("user.dir") +" и напишите туда хост и порт через пробел");
            return;
        } catch (IOException e) {
            ctrl.showAlert("Ошибка при чтении из конфигурационного файла: " + e);
            return;
        } catch (NumberFormatException ex) {
            ctrl.showAlert("Порт должен быть целым числом");
            return;
        } catch (NoSuchElementException e) {
            ctrl.showAlert("Напишите в файл config.txt хост и порт через пробел");
            return;
        }


        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(hostName, port);
            socketChannel = SocketChannel.open(inetSocketAddress);
            socketChannel.configureBlocking(false);

        } catch (SocketException ex) {
            ctrl.showAlert("Не удалось подключиться к удаленному адресу...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeSession() throws IOException {
        if (socketChannel != null) { socketChannel.close(); }
    }

    @Override
    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
}
