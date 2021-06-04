package Controller;

import DAO.SubjectDAO;
import POJO.Subject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class CourseDialogController implements Initializable {
    @FXML
    private ComboBox<String> subjectName;
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
//        SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
//        id.setValueFactory(value);
//        value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1990, 2021, 2021);
//        year.setValueFactory(value);
//        value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1);
//        semesterId.setValueFactory(value);
    }

    @FXML
    public Subject getSubjectName() {
        Subject sub = SubjectDAO.getSubjectByID(subjectName.getValue());
        return sub;
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
