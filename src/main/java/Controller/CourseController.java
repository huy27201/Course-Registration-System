package Controller;

import DAO.CourseDAO;
import DAO.CurrentSemesterDAO;
import Main.App;
import POJO.Course;
import POJO.Currentsemester;
import POJO.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CourseController implements Initializable {
    @FXML private TextField searchBar;
    @FXML TableView<Course> table;
    @FXML TableColumn<Course, Integer> col_id;
    //@FXML TableColumn<Course, Integer> col_semesterId;
    //@FXML TableColumn<Course, Integer> col_year;
    @FXML TableColumn<Course, String> col_room;
    @FXML TableColumn<Course, String> col_day;
    @FXML TableColumn<Course, String> col_period;
    @FXML TableColumn<Course, String> col_maxSlot;
    ObservableList<Course> list = FXCollections.observableArrayList();
    FilteredList<Course> filterList = new FilteredList<>(list);
    private Currentsemester curSem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        curSem = CurrentSemesterDAO.getCurrentSemester();
        List<Course> courseList = CourseDAO.getCourseListBySemester(curSem);
        for (Course element : courseList)
            list.add(element);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        //col_semesterId.setCellValueFactory(new PropertyValueFactory<>("semesterId"));
        //col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_room.setCellValueFactory(new PropertyValueFactory<>("room"));
        col_day.setCellValueFactory(new PropertyValueFactory<>("day"));
        col_period.setCellValueFactory(new PropertyValueFactory<>("period"));
        col_maxSlot.setCellValueFactory(new PropertyValueFactory<>("maxSlot"));
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
        App.changeScene("TeacherDashboard");
    }
    @FXML
    public void logoutClicked() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("Login");
    }
    @FXML
    public void onReturn() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("TeacherDashboard");
    }
    @FXML
    public void onAdd() {
        Dialog<Teacher> addDialog = new Dialog<Teacher>();
    }
    @FXML
    public void onRemove() {

    }
    @FXML
    public void onSearch() {

    }
}
