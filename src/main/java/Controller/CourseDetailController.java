package Controller;

import DAO.CourseattendDAO;
import Main.App;
import POJO.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

public class CourseDetailController implements Initializable {
    @FXML
    private TextField searchBar;
    @FXML
    private Label courseInfo;
    @FXML
    private Label teacherInfo;
    @FXML
    TableView<Courseattend> table;
    @FXML
    TableColumn<Courseattend, String> col_studentId;
    @FXML
    TableColumn<Courseattend, String> col_studentName;
    @FXML
    TableColumn<Courseattend, Timestamp> col_dateRegistered;
    Course currentCourse;
    ObservableList<Courseattend> list = FXCollections.observableArrayList();
    FilteredList<Courseattend> filterList = new FilteredList<>(list);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        col_studentName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getStudentByStudentId().getFirstName()
                + " " + data.getValue().getStudentByStudentId().getLastName()));
        col_dateRegistered.setCellValueFactory(new PropertyValueFactory<>("dateRegisterd"));
    }

    public void setCurrentCourse(Course course) {
        this.currentCourse = course;
        courseInfo.setText(currentCourse.getSubjectBySubjectId().getId() + " - " + currentCourse.getSubjectBySubjectId().getName());
        teacherInfo.setText(currentCourse.getTeacherName());
        List<Courseattend> attendList =  CourseattendDAO.getCourseattendByCourseID(currentCourse.getId());
        for (Courseattend item : attendList)
            list.add(item);
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
        App.changeScene("Course");
    }

    @FXML
    public void onSearch() {
        String data = searchBar.getText().toLowerCase();
        filterList.setPredicate(
                item -> {
                    if (data == null || data.isEmpty()) return true;
                    if (item.getStudentId().toLowerCase().contains(data)) return true;
                    if ((item.getStudentByStudentId().getFirstName()
                            + " " + item.getStudentByStudentId().getLastName()).toLowerCase().contains(data))
                        return true;
                    return false;
                }
        );
        table.setItems(filterList);
    }
}


