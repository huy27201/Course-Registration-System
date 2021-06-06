package Controller;

import DAO.SubjectDAO;
import POJO.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CourseDialogController implements Initializable {
    @FXML
    private ComboBox<Subject> subjectName;
    @FXML
    private TextField headTeacher;
    @FXML
    private ComboBox<String> day;
    @FXML
    private ComboBox<String> period;
    @FXML
    private TextField room;
    @FXML
    private TextField maxSlot;
    @FXML
    private Spinner<Integer> year;
    @FXML
    private Spinner<Integer> semesterId;

    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1);
        semesterId.setValueFactory(value);
        value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1990, 2021, 2021);
        year.setValueFactory(value);
        day.getItems().addAll("Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật");
        day.setValue("Thứ 2");
        period.getItems().addAll("7:30 - 9:30", "9:30 - 11:30", "13:30 - 15:30", "15:30 - 17:30");
        period.setValue("7:30 - 9:30");
        ObservableList<Subject> list = FXCollections.observableArrayList();
        List<Subject> subList = SubjectDAO.getSubjectList();
        for (Subject sub : subList)
            list.add(sub);
        subjectName.setItems(list);
        subjectName.setConverter(new StringConverter<Subject>() {
            @Override
            public String toString(Subject sub) {
                if (sub != null)
                    return sub.getName();
                return "";
            }
            @Override
            public Subject fromString(String string) {
                return subjectName.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    @FXML
    public Subject getSubjectName() {
        if (subjectName.getValue() != null)
            return SubjectDAO.getSubjectByID(subjectName.getValue().getId());
        return null;
    }

    @FXML
    public int getSemesterId() {
        return semesterId.getValue();
    }

    @FXML
    public int getYear() {
        return year.getValue();
    }

    @FXML
    public String getHeadTeacher() {
        return headTeacher.getText();
    }

    @FXML
    public String getDay() {
        return day.getValue();
    }

    @FXML
    public String getPeriod() {
        return period.getValue();
    }

    @FXML
    public String getRoom() {
        return room.getText();
    }

    @FXML
    public Integer getMaxSlot() {
        try {
            int res = Integer.parseInt(maxSlot.getText());
            return res;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @FXML
    public void setDisable(boolean b) {
        semesterId.setDisable(true);
        year.setDisable(true);
    }

    @FXML
    public void setYear(int year) {
        SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1990, 2021, year);
        this.year.setValueFactory(value);
    }

    @FXML
    public void setSemesterId(int semesterId) {
        SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, semesterId);
        this.semesterId.setValueFactory(value);
    }
}
