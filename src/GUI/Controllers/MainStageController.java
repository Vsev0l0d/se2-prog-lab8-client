package GUI.Controllers;

import BasicClasses.*;
import Interfaces.CommandReceiver;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.LinkedList;
import java.util.List;

public class MainStageController {
    private ObservableList<StudyGroup> observableList = FXCollections.observableArrayList();
    private List<List<Integer>> idElementsAllUsers;
    private CommandReceiver commandReceiver;

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

    @FXML
    public void initialize() {
        fillTable();
//        observableList.add(new StudyGroup("a", new Coordinates(12,11), 11, FormOfEducation.DISTANCE_EDUCATION, Semester.FIFTH, new Person("11", 11, Color.BLUE, Color.BLACK, Country.CHINA)));
//        observableList.add(new StudyGroup("ss", new Coordinates(11,12), 11, FormOfEducation.DISTANCE_EDUCATION, Semester.FIFTH, new Person("11", 11, Color.BLUE, Color.BLACK, Country.CHINA)));
    }

    public void fillTable() {
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

        tableView.setItems(observableList);
    }

    public void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка!");

        alert.setHeaderText(null);
        alert.setContentText(alertMessage);

        alert.showAndWait();
    }

    public void setCollection(LinkedList<StudyGroup> linkedList, List<List<Integer>> idElementsAllUsers) {
        observableList = FXCollections.observableArrayList(linkedList);
        this.idElementsAllUsers = idElementsAllUsers;
        // колекция с сервера, но в табл не отображается, вероятнее всего нужно обновить картинку
    }

    public void setCommandReceiver(CommandReceiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }
}
