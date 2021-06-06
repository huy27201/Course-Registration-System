package Controller;

import DAO.CourseDAO;
import DAO.CurrentSemesterDAO;
import Main.App;
import POJO.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CourseResultController implements Initializable {
    @FXML
    TableView<Courseattend> table;
    @FXML
    TableColumn<Courseattend, String> col_id;
    @FXML
    TableColumn<Courseattend, String> col_courseName;
    @FXML
    TableColumn<Courseattend, Integer> col_credits;
    @FXML
    TableColumn<Courseattend, String> col_headTeacher;
    @FXML
    TableColumn<Courseattend, String> col_room;
    @FXML
    TableColumn<Courseattend, String> col_day;
    @FXML
    TableColumn<Courseattend, String> col_period;
    ObservableList<Courseattend> list = FXCollections.observableArrayList();
    FilteredList<Courseattend> filterList = new FilteredList<>(list);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        List<Courseattend> list = CourseDAO.getCourseListBySemester(curSem);
//        for (Course element : courseList)
//            list.add(element);
//        col_id.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSubjectBySubjectId().getId()));
//        col_courseName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSubjectBySubjectId().getName()));
//        col_credits.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSubjectBySubjectId().getCredits()).asObject());
//        col_headTeacher.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTeacherName()));
//        col_room.setCellValueFactory(new PropertyValueFactory<>("room"));
//        col_day.setCellValueFactory(new PropertyValueFactory<>("day"));
//        col_period.setCellValueFactory(new PropertyValueFactory<>("period"));
//        table.setItems(list);

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

}
