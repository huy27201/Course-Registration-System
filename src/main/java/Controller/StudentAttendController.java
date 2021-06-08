package Controller;

import DAO.CourseDAO;
import DAO.CurrentSemesterDAO;
import Main.App;
import POJO.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class StudentAttendController implements Initializable {
    @FXML
    private Label curSemLabel;
    @FXML
    private TextField searchBar;
    @FXML
    TableView<Course> table;
    @FXML
    TableColumn<Course, String> col_id;
    @FXML
    TableColumn<Course, String> col_courseName;
    @FXML
    TableColumn<Course, Integer> col_credits;
    @FXML
    TableColumn<Course, String> col_headTeacher;
    @FXML
    TableColumn<Course, String> col_room;
    @FXML
    TableColumn<Course, String> col_day;
    @FXML
    TableColumn<Course, String> col_period;
    @FXML
    TableColumn<Course, Integer> col_maxSlot;
//    @FXML
//    TableColumn<Course, String> col_numRegister;
    @FXML
    TableColumn col_check = new TableColumn("checked");
    ObservableList<Course> list = FXCollections.observableArrayList();
    FilteredList<Course> filterList = new FilteredList<>(list);
    private Currentsemester curSem;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        curSem = CurrentSemesterDAO.getCurrentSemester();
        curSemLabel.setText(curSem.getId() + "/" + curSem.getYear() + "-" + (curSem.getYear() + 1));
        List<Course> courseList = CourseDAO.getCourseListBySemester(curSem);
        for (Course element : courseList)
            list.add(element);
        col_id.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSubjectBySubjectId().getId()));
        col_courseName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSubjectBySubjectId().getName()));
        col_credits.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSubjectBySubjectId().getCredits()).asObject());
        col_headTeacher.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTeacherName()));
        col_room.setCellValueFactory(new PropertyValueFactory<>("room"));
        col_day.setCellValueFactory(new PropertyValueFactory<>("day"));
        col_period.setCellValueFactory(new PropertyValueFactory<>("period"));
        col_maxSlot.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMaxSlot()).asObject());
        col_check.setCellValueFactory(
                (Callback<TableColumn.CellDataFeatures<Course, Boolean>, ObservableValue<Boolean>>) param -> param.getValue().checkedProperty());
        col_check.setCellFactory(CheckBoxTableCell.forTableColumn(col_check));
        table.setItems(list);
    }

    @FXML
    public void exit() {
        App.exit();
    }

    @FXML
    public void minimize() {
        App.minimize();
    }

    @FXML
    public void onDashboard() throws IOException {
        App.changeScene("StudentDashboard");
    }

    @FXML
    public void logoutClicked() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("Login");
    }

    @FXML
    public void onReturn() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("StudentDashboard");
    }

    @FXML
    public void onSearch() {
        String data = searchBar.getText().toLowerCase();
        filterList.setPredicate(
                course -> {
                    if (data == null || data.isEmpty()) return true;
                    if (course.getTeacherName().toLowerCase().contains(data)) return true;
                    if (course.getDay().toLowerCase().contains(data)) return true;
                    if (course.getPeriod().toLowerCase().contains(data)) return true;
                    if (course.getRoom().toLowerCase().contains(data)) return true;
                    if (course.getSubjectBySubjectId().getId().toLowerCase().contains(data)) return true;
                    if ((course.getSubjectBySubjectId().getName().toLowerCase()).contains(data)) return true;
                    return false;
                }
        );
        table.setItems(filterList);
    }


}
