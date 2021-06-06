package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class CourseRegistrationDialogController implements Initializable {
    @FXML
    private Spinner<Integer> id;
    @FXML
    private Spinner<Integer> year;
    @FXML
    private Spinner<Integer> semesterId;
    @FXML
    private DatePicker dateStart;
    @FXML
    private DatePicker dateEnd;

    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
        id.setValueFactory(value);
        value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1990, 2025, 2021);
        year.setValueFactory(value);
        value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1);
        semesterId.setValueFactory(value);
    }

    @FXML
    public int getId() {
        return id.getValue();
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
    public Date getDateStart() {
        if (dateStart.getValue() == null) return null;
        return Date.valueOf(dateStart.getValue());
    }

    @FXML
    public Date getDateEnd() {
        if (dateEnd.getValue() == null) return null;
        return Date.valueOf(dateEnd.getValue());
    }

    @FXML
    public void setDisable(boolean b) {
        id.setDisable(true);
        year.setDisable(true);
    }

    @FXML
    public void setYear(int year) {
        SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1990, 2025, year);
        this.year.setValueFactory(value);
    }

    @FXML
    public void setSemesterId(int semesterId) {
        SpinnerValueFactory<Integer> value = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, semesterId);
        this.semesterId.setValueFactory(value);
    }
}
