package GUI.Controllers;

import BasicClasses.*;
import Interfaces.CommandReceiver;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.*;

public class MainStageController implements Initializable {
    private ObservableList<StudyGroup> observableList = FXCollections.observableArrayList();
    private List<List<Integer>> idElementsAllUsers = new ArrayList<>();
    private CommandReceiver commandReceiver;

    @FXML
    public FlowPane groupMap;
    @FXML
    public Pane executeCommand;
    @FXML
    public Pane aboutFagots;
    @FXML
    private Button toTableBtn;
    @FXML
    private Button toGroupMapBtn;
    @FXML
    private Button executeCommandBtn;
    @FXML
    private Button aboutFagotsBtn;
    @FXML
    private TableView<StudyGroup> tableView;

    @FXML
    private TableColumn<StudyGroup, Integer> idColumn;
    @FXML
    private TableColumn<StudyGroup, String> nameColumn;
    @FXML
    private TableColumn<StudyGroup, Integer> xColumn;
    @FXML
    private TableColumn<StudyGroup, Float> yColumn;
    @FXML
    private TableColumn<StudyGroup, java.time.ZonedDateTime> creationDateColumn;
    @FXML
    private TableColumn<StudyGroup, Integer> studentsCountColumn;
    @FXML
    private TableColumn<StudyGroup, FormOfEducation> formOfEducationColumn;
    @FXML
    private TableColumn<StudyGroup, Semester> semesterEnumColumn;
    @FXML
    private TableColumn<StudyGroup, String> adminNameColumn;
    @FXML
    private TableColumn<StudyGroup, Integer> heightColumn;
    @FXML
    private TableColumn<StudyGroup, String> eyeColorColumn;
    @FXML
    private TableColumn<StudyGroup, String> hairColorColumn;
    @FXML
    private TableColumn<StudyGroup, String> nationalityColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<Integer> objects = new ArrayList<>();
        objects.add(0);
        idElementsAllUsers.add(objects);
        observableList.add(new StudyGroup("a", new Coordinates(100,11), 16, FormOfEducation.DISTANCE_EDUCATION, Semester.FIFTH, new Person("11", 11, Color.BLUE, Color.BLACK, Country.CHINA)));
        observableList.add(new StudyGroup("a", new Coordinates(11,100), 1, FormOfEducation.DISTANCE_EDUCATION, Semester.FIFTH, new Person("11", 11, Color.BLUE, Color.BLACK, Country.CHINA)));
        observableList.add(new StudyGroup("a", new Coordinates(300,51), 1, FormOfEducation.DISTANCE_EDUCATION, Semester.FIFTH, new Person("11", 11, Color.BLUE, Color.BLACK, Country.CHINA)));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        xColumn.setCellValueFactory(studyGroup -> new SimpleIntegerProperty((studyGroup.getValue().getCoordinates().getX())).asObject());
        yColumn.setCellValueFactory(studyGroup -> new SimpleFloatProperty((studyGroup.getValue().getCoordinates().getY())).asObject());
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        studentsCountColumn.setCellValueFactory(new PropertyValueFactory<>("studentsCount"));
        formOfEducationColumn.setCellValueFactory(new PropertyValueFactory<>("formOfEducation"));
        semesterEnumColumn.setCellValueFactory(new PropertyValueFactory<>("semesterEnum"));
        adminNameColumn.setCellValueFactory(studyGroup -> new SimpleStringProperty((studyGroup.getValue().getGroupAdmin().getName())));
        heightColumn.setCellValueFactory(studyGroup -> new SimpleIntegerProperty((studyGroup.getValue().getGroupAdmin().getHeight())).asObject());
        eyeColorColumn.setCellValueFactory(studyGroup -> new SimpleStringProperty((studyGroup.getValue().getGroupAdmin().getEyeColor().toString())));
        hairColorColumn.setCellValueFactory(studyGroup -> new SimpleStringProperty((studyGroup.getValue().getGroupAdmin().getHairColor().toString())));
        nationalityColumn.setCellValueFactory(studyGroup -> new SimpleStringProperty((studyGroup.getValue().getGroupAdmin().getNationality().toString())));
        fillTable();
        visual();
    }

    public void fillTable() {
        tableView.setItems(observableList);
    }

    public void visual() {
        ArrayList<Circle> circles = new ArrayList<>();
        HashMap<Circle, StudyGroup> circleStudyGroupHashMap = new HashMap<>();
        HashMap<javafx.scene.paint.Color, List<Integer>> colorListHashMap = new HashMap<>();
        for (List<Integer> list : idElementsAllUsers){
            javafx.scene.paint.Color color = javafx.scene.paint.Color.color(Math.random(), Math.random(), Math.random());
            colorListHashMap.put(color, list);
        }

        for (StudyGroup studyGroup : observableList) {
            double size = Math.sqrt(studyGroup.getStudentsCount()*100);
            if (size > 100) size = 100;
            if (size < 15) size = 15;
            Circle circle = new Circle();
            circle.setRadius(size);
            circle.setLayoutX(studyGroup.getCoordinates().getX());
            circle.setLayoutY(studyGroup.getCoordinates().getY());

            for (Map.Entry<javafx.scene.paint.Color, List<Integer>> entry : colorListHashMap.entrySet()){
                if (entry.getValue().contains(studyGroup.getId())){
                    circle.setFill(entry.getKey());
                }
            }
            circle.setStroke(javafx.scene.paint.Color.color(0, 0, 0));
            circle.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {
                    if (t.getSource() instanceof Circle) {
                        Circle selectedCircle = ((Circle) (t.getSource()));
                        StudyGroup selectedStudyGroup = circleStudyGroupHashMap.get(selectedCircle);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("info");
                        alert.setContentText(selectedStudyGroup.toString());
                        ButtonType close = new ButtonType("close");
                        ButtonType edit = new ButtonType("update");
                        ButtonType delete = new ButtonType("removeById");
                        alert.getButtonTypes().clear();
                        alert.getButtonTypes().addAll(delete, edit, close);
                        Optional<ButtonType> option = alert.showAndWait();
                        if (option.get() == close)
                            alert.close();
                        else if (option.get() == edit) {
                            // update
                        } else if (option.get() == delete) {
                            // removeById
                        }
                    }
                }
            });
            circles.add(circle);
            circleStudyGroupHashMap.put(circle,studyGroup);
        }
        groupMap.getChildren().setAll(circles);
    }

    public void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");

        alert.setHeaderText(null);
        alert.setContentText(alertMessage);

        alert.showAndWait();
    }

    public void setCollection(LinkedList<StudyGroup> linkedList, List<List<Integer>> idElementsAllUsers) {
        observableList.clear();
        observableList.addAll(linkedList);
        this.idElementsAllUsers = idElementsAllUsers;
    }

    public void setCommandReceiver(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    public void showTable(ActionEvent actionEvent) {
        groupMap.setVisible(false);
        executeCommand.setVisible(false);
        aboutFagots.setVisible(false);
        tableView.setVisible(true);
    }

    public void showGroupMap(ActionEvent actionEvent) {
        executeCommand.setVisible(false);
        aboutFagots.setVisible(false);
        tableView.setVisible(false);
        groupMap.setVisible(true);
    }

    public void showExecuteCommand(ActionEvent actionEvent) {
        groupMap.setVisible(false);
        aboutFagots.setVisible(false);
        tableView.setVisible(false);
        executeCommand.setVisible(true);
    }

    public void showAboutFagots(ActionEvent actionEvent) {
        groupMap.setVisible(false);
        tableView.setVisible(false);
        executeCommand.setVisible(false);
        aboutFagots.setVisible(true);
    }
}
