package GUI.Controllers;

import BasicClasses.*;
import Interfaces.CommandReceiver;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStageController implements Initializable {
    private ObservableList<StudyGroup> observableList = FXCollections.observableArrayList();
    private ObservableList<String> commandNames = FXCollections.observableArrayList();
    private List<List<Integer>> idElementsAllUsers = new ArrayList<>();
    private CommandReceiver commandReceiver;

    @FXML private Text hiText;
    @FXML private Pane groupMap;
    @FXML private Pane executeCommand;
    @FXML private Pane aboutFagots;
    @FXML private Pane tablePane;
    @FXML private TableView<StudyGroup> tableView;

    @FXML private TableColumn<StudyGroup, Integer> idColumn;
    @FXML private TableColumn<StudyGroup, String> nameColumn;
    @FXML private TableColumn<StudyGroup, Integer> xColumn;
    @FXML private TableColumn<StudyGroup, Float> yColumn;
    @FXML private TableColumn<StudyGroup, java.time.ZonedDateTime> creationDateColumn;
    @FXML private TableColumn<StudyGroup, Integer> studentsCountColumn;
    @FXML private TableColumn<StudyGroup, FormOfEducation> formOfEducationColumn;
    @FXML private TableColumn<StudyGroup, Semester> semesterEnumColumn;
    @FXML private TableColumn<StudyGroup, String> adminNameColumn;
    @FXML private TableColumn<StudyGroup, Integer> heightColumn;
    @FXML private TableColumn<StudyGroup, String> eyeColorColumn;
    @FXML private TableColumn<StudyGroup, String> hairColorColumn;
    @FXML private TableColumn<StudyGroup, String> nationalityColumn;

    @FXML private TextField idFilter;
    @FXML private TextField nameFilter;
    @FXML private TextField xFilter;
    @FXML private TextField yFilter;
    @FXML private TextField creationDateFilter;
    @FXML private TextField studentsCountFilter;
    @FXML private ChoiceBox<String> formOfEducationFilter;
    @FXML private ChoiceBox<String> semesterEnumFilter;
    @FXML private TextField adminNameFilter;
    @FXML private TextField heightFilter;
    @FXML private ChoiceBox<String> eyeColorFilter;
    @FXML private ChoiceBox<String> hairColorFilter;
    @FXML private ChoiceBox<String> nationalityFilter;

    @FXML private TextField idArgumentField;
    @FXML private TitledPane aboutGroupTitiledPane;
    @FXML private TitledPane aboutGroupAdminTitledPane;
    @FXML private TextField groupNameField;
    @FXML private TextField xTextField;
    @FXML private TextField yTextField;
    @FXML private TextField studentsCountField;
    @FXML private ComboBox<String> formOfEducationComboBox;
    @FXML private ComboBox<String> semesterComboBox;
    @FXML private TextField adminGroupField;
    @FXML private TextField adminHeightField;
    @FXML private ComboBox<String> adminEyeColorComboBox;
    @FXML private ComboBox<String> adminHairColorComboBox;
    @FXML private ComboBox<String> adminNationalityComboBox;
    @FXML private ComboBox<String> commandChoiseComboBox;
    @FXML private Button executeCommandBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // для тестов, пока падает буфер
        ArrayList<Integer> objects = new ArrayList<>();
        objects.add(0);
        idElementsAllUsers.add(objects);
        observableList.add(new StudyGroup("a", new Coordinates(100,11), 16, FormOfEducation.DISTANCE_EDUCATION, Semester.FIFTH, new Person("11", 11, Color.BLUE, Color.BLACK, Country.CHINA)));
        observableList.add(new StudyGroup("a", new Coordinates(11,100), 100, FormOfEducation.FULL_TIME_EDUCATION, Semester.FOURTH, new Person("11", 11, Color.RED, Color.BLACK, Country.CHINA)));
        observableList.add(new StudyGroup("a", new Coordinates(11,100), 30, FormOfEducation.DISTANCE_EDUCATION, Semester.FIFTH, new Person("11", 11, Color.BLUE, Color.BLACK, Country.USA)));
        observableList.add(new StudyGroup("a", new Coordinates(300,51), 1, null, Semester.FIFTH, new Person("11", 11, Color.BLUE, Color.BLACK, Country.CHINA)));

        formOfEducationFilter.setItems(FXCollections.observableArrayList(Stream.concat(Stream.of(FormOfEducation.values()).map(Enum::toString), Stream.of("null", "")).collect(Collectors.toList())));
        semesterEnumFilter.setItems(FXCollections.observableArrayList((Stream.concat(Stream.of(Semester.values()).map(Enum::toString), Stream.of("")).collect(Collectors.toList()))));
        eyeColorFilter.setItems(FXCollections.observableArrayList(Stream.concat(Stream.of(Color.values()).map(Enum::toString), Stream.of("")).collect(Collectors.toList())));
        hairColorFilter.setItems(FXCollections.observableArrayList(Stream.concat(Stream.of(Color.values()).map(Enum::toString), Stream.of("")).collect(Collectors.toList())));
        nationalityFilter.setItems(FXCollections.observableArrayList(Stream.concat(Stream.of(Country.values()).map(Enum::toString), Stream.of("")).collect(Collectors.toList())));

        formOfEducationComboBox.setItems(FXCollections.observableArrayList(Stream.concat(Stream.of(FormOfEducation.values()).map(Enum::toString), Stream.of("null")).collect(Collectors.toList())));
        semesterComboBox.setItems(FXCollections.observableArrayList(Stream.of(Semester.values()).map(Enum::toString).collect(Collectors.toList())));
        adminEyeColorComboBox.setItems(FXCollections.observableArrayList(Stream.of(Color.values()).map(Enum::toString).collect(Collectors.toList())));
        adminHairColorComboBox.setItems(FXCollections.observableArrayList(Stream.of(Color.values()).map(Enum::toString).collect(Collectors.toList())));
        adminNationalityComboBox.setItems(FXCollections.observableArrayList(Stream.of(Country.values()).map(Enum::toString).collect(Collectors.toList())));
        commandChoiseComboBox.setItems(commandNames);

        fillTable();
        updateTable();
        setListenersTextField();
    }

    private void fillTable() {
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
        tableView.setRowFactory(tv -> {
            TableRow<StudyGroup> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    if ((!row.isEmpty())) {
                        StudyGroup selectedStudyGgroup = row.getItem();
                        showInfoElement(selectedStudyGgroup);
                    } else {
                        // add
                    }
                }
            });
            return row;
        });
    }

    private void updateTable() {
        FilteredList<StudyGroup> filtered = new FilteredList<>(observableList, t -> true);
        idFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(studyGroup -> Integer.toString(studyGroup.getId()).equals(newValue)));
        nameFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(studyGroup -> studyGroup.getName().toLowerCase().contains(newValue.toLowerCase())));
        xFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(studyGroup -> studyGroup.getCoordinates().getX().toString().toLowerCase().equals(newValue.toLowerCase())));
        yFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(studyGroup -> studyGroup.getCoordinates().getX().toString().toLowerCase().equals(newValue.toLowerCase())));
        creationDateFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(studyGroup -> studyGroup.getCreationDate().toString().toLowerCase().equals(newValue.toLowerCase())));
        studentsCountFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(studyGroup -> studyGroup.getStudentsCount().toString().toLowerCase().contains(newValue.toLowerCase())));
        formOfEducationFilter.setOnAction(event -> filtered.setPredicate(studyGroup -> {
            String formOfEducation = studyGroup.getFormOfEducation() == null ? "null" : String.valueOf(studyGroup.getFormOfEducation());
            return formOfEducation.contains(formOfEducationFilter.getValue());
        }));
        semesterEnumFilter.setOnAction(event -> filtered.setPredicate(studyGroup -> studyGroup.getSemesterEnum().toString().contains(semesterEnumFilter.getValue())));
        adminNameFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(studyGroup -> studyGroup.getGroupAdmin().getName().toLowerCase().contains(newValue.toLowerCase())));
        heightFilter.textProperty().addListener((observable, oldValue, newValue) -> filtered.setPredicate(studyGroup -> Double.toString(studyGroup.getGroupAdmin().getHeight()).toLowerCase().equals(newValue.toLowerCase())));
        eyeColorFilter.setOnAction(event -> filtered.setPredicate(studyGroup -> studyGroup.getGroupAdmin().getEyeColor().toString().contains(eyeColorFilter.getValue())));
        hairColorFilter.setOnAction(event -> filtered.setPredicate(studyGroup -> studyGroup.getGroupAdmin().getHairColor().toString().contains(hairColorFilter.getValue())));
        nationalityFilter.setOnAction(event -> filtered.setPredicate(studyGroup -> studyGroup.getGroupAdmin().getNationality().toString().contains(nationalityFilter.getValue())));

        SortedList<StudyGroup> sorted = new SortedList<>(filtered);
        sorted.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sorted);
    }

    private void visual() {
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
            circle.setLayoutX(studyGroup.getCoordinates().getX() + 100);
            circle.setLayoutY(studyGroup.getCoordinates().getY() + 100);

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
                        showInfoElement(selectedStudyGroup);
                    }
                }
            });
            circles.add(circle);
            circleStudyGroupHashMap.put(circle,studyGroup);
        }
        groupMap.getChildren().clear();
        circles.sort(Comparator.comparing(Circle::getRadius));
        Collections.reverse(circles);
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
        commandNames.clear();
        commandNames.addAll(commandReceiver.getCommandsName());
        hiText.setText("Hi, " + commandReceiver.getLogin());
    }

    @FXML
    private void showTable(ActionEvent actionEvent) {
        groupMap.setVisible(false);
        executeCommand.setVisible(false);
        aboutFagots.setVisible(false);
        tablePane.setVisible(true);
    }

    @FXML
    private void showGroupMap(ActionEvent actionEvent) {
        visual();
        executeCommand.setVisible(false);
        aboutFagots.setVisible(false);
        tablePane.setVisible(false);
        groupMap.setVisible(true);
    }

    @FXML
    private void showExecuteCommand(ActionEvent actionEvent) {
        groupMap.setVisible(false);
        aboutFagots.setVisible(false);
        tablePane.setVisible(false);
        executeCommand.setVisible(true);
    }

    @FXML
    private void showAboutFagots(ActionEvent actionEvent) {
        groupMap.setVisible(false);
        tablePane.setVisible(false);
        executeCommand.setVisible(false);
        aboutFagots.setVisible(true);
    }

    @FXML
    private void commandProcessing(ActionEvent actionEvent) {
        String commandName = commandChoiseComboBox.getSelectionModel().getSelectedItem();

        if (commandName.matches("add|remove_lower|remove_greater")) { aboutGroupTitiledPane.setDisable(false); aboutGroupAdminTitledPane.setDisable(false); idArgumentField.setDisable(true); }
        else if (commandName.equals("count_by_group_admin")) { aboutGroupTitiledPane.setDisable(true); aboutGroupAdminTitledPane.setDisable(false); idArgumentField.setDisable(true); }
        else if (commandName.equals("update")){ aboutGroupTitiledPane.setDisable(false); aboutGroupAdminTitledPane.setDisable(false); idArgumentField.setDisable(false);}
        else if (commandName.matches("remove_by_id")){ aboutGroupTitiledPane.setDisable(true); aboutGroupAdminTitledPane.setDisable(true); idArgumentField.setDisable(false);}
        else { aboutGroupTitiledPane.setDisable(true); aboutGroupAdminTitledPane.setDisable(true); idArgumentField.setDisable(true);}

        if (commandName.equals("execute_script")){
            String path = selectScriptFile();
            if (path != null) {
                // отправить execute_script
            }
        }
        clearTitlePanes();
    }

    private void clearTitlePanes() {
        idArgumentField.clear();
        adminGroupField.clear();
        adminHeightField.clear();
        groupNameField.clear();
        studentsCountField.clear();
        xTextField.clear();
        yTextField.clear();
        formOfEducationComboBox.getSelectionModel().select(-1);
        semesterComboBox.getSelectionModel().select(-1);
        adminEyeColorComboBox.getSelectionModel().select(-1);
        adminHairColorComboBox.getSelectionModel().select(-1);
        adminNationalityComboBox.getSelectionModel().select(-1);
    }

    private String selectScriptFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("txt", "*.txt"));
        Stage stage = new Stage();
        stage.setTitle("SelectScriptFile");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            return file.getPath();
        }
        return null;
    }

    private void setListenersTextField(){
        idArgumentField.textProperty().addListener((observable, oldValue, newValue) -> dataValidation());
        adminGroupField.textProperty().addListener((observable, oldValue, newValue) -> dataValidation());
        adminHeightField.textProperty().addListener((observable, oldValue, newValue) -> dataValidation());
        groupNameField.textProperty().addListener((observable, oldValue, newValue) -> dataValidation());
        studentsCountField.textProperty().addListener((observable, oldValue, newValue) -> dataValidation());
        xTextField.textProperty().addListener((observable, oldValue, newValue) -> dataValidation());
        yTextField.textProperty().addListener((observable, oldValue, newValue) -> dataValidation());
    }

    @FXML
    private void dataValidation(){
        try {
            executeCommandBtn.setDisable(true);

            boolean isIdCorrect = true;
            boolean isAdminCorrect = true;
            boolean isGroupCorrect = true;
            if (!idArgumentField.isDisable()) isIdCorrect = Integer.parseInt(idArgumentField.getText()) > 0;

            if (!aboutGroupAdminTitledPane.isDisable())
                    isAdminCorrect = (adminGroupField.getText() != null && !adminGroupField.getText().equals("")) &&
                    (Integer.parseInt(adminHeightField.getText()) > 0) && (adminEyeColorComboBox.getSelectionModel().getSelectedItem() != null) &&
                    (adminHairColorComboBox.getSelectionModel().getSelectedItem() != null) && (adminNationalityComboBox.getSelectionModel().getSelectedItem() != null);

            if (!aboutGroupTitiledPane.isDisable())
                    isGroupCorrect = (groupNameField.getText() != null && !groupNameField.getText().equals("")) &&
                    (Integer.parseInt(studentsCountField.getText()) > 0) &&
                    (Integer.parseInt(xTextField.getText()) < 531) &&
                    (Float.parseFloat(yTextField.getText()) > -653f) &&
                    (formOfEducationComboBox.getSelectionModel().getSelectedItem() != null) &&
                    (semesterComboBox.getSelectionModel().getSelectedItem() != null);

            if (isAdminCorrect && isIdCorrect && isGroupCorrect) executeCommandBtn.setDisable(false);
        } catch (Exception ignored) {}
    }

    private void showInfoElement(StudyGroup studyGroup){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("info");
        alert.setContentText(studyGroup.toString());
        ButtonType close = new ButtonType("close");
        ButtonType edit = new ButtonType("update");
        ButtonType delete = new ButtonType("remove");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(delete, edit, close);
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == close)
            alert.close();
        else if (option.get() == edit) {
            groupMap.setVisible(false);
            executeCommand.setVisible(true);

            commandChoiseComboBox.setValue("update");
            idArgumentField.setDisable(true);
            idArgumentField.setText(studyGroup.getId().toString());
            groupNameField.setText(studyGroup.getName());
            xTextField.setText(studyGroup.getCoordinates().getX().toString());
            yTextField.setText(Float.toString(studyGroup.getCoordinates().getY()));
            studentsCountField.setText(studyGroup.getStudentsCount().toString());
            formOfEducationComboBox.setValue(studyGroup.getFormOfEducation() == null ? "null" : studyGroup.getFormOfEducation().toString());
            semesterComboBox.setValue(studyGroup.getSemesterEnum().toString());
            adminGroupField.setText(studyGroup.getGroupAdmin().getName());
            adminHeightField.setText(Integer.toString(studyGroup.getGroupAdmin().getHeight()));
            adminEyeColorComboBox.setValue(studyGroup.getGroupAdmin().getEyeColor().toString());
            adminHairColorComboBox.setValue(studyGroup.getGroupAdmin().getHairColor().toString());
            adminNationalityComboBox.setValue(studyGroup.getGroupAdmin().getNationality().toString());
        } else if (option.get() == delete) {
            // removeById
        }
    }
}
