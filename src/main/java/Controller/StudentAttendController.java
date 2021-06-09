package Controller;

import DAO.*;
import Main.App;
import POJO.*;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class StudentAttendController implements Initializable {
    @FXML
    private Label subjectNum;
    @FXML
    private Label curSemRegisLabel;
    @FXML
    private Label currentTime;
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
    @FXML
    TableColumn<Course, Integer> col_registerSlot;
    @FXML
    TableColumn col_check = new TableColumn("checked");
    ObservableList<Course> list;
    FilteredList<Course> filterList;
    private Currentsemester curSem;
    private LocalDate currentDay;
    private CourseRegistration currentCourseRegistration;
    private List<Course> courseList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list = FXCollections.observableArrayList(item -> new Observable[] {item.checkedProperty()});
        filterList = new FilteredList<>(list);
        col_id.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSubjectBySubjectId().getId()));
        col_courseName.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getSubjectBySubjectId().getName()));
        col_credits.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSubjectBySubjectId().getCredits()).asObject());
        col_headTeacher.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getTeacherName()));
        col_room.setCellValueFactory(new PropertyValueFactory<>("room"));
        col_day.setCellValueFactory(new PropertyValueFactory<>("day"));
        col_period.setCellValueFactory(new PropertyValueFactory<>("period"));
        col_maxSlot.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getMaxSlot()).asObject());
        col_registerSlot.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getRegisterSlot()).asObject());
        col_check.setCellValueFactory(new PropertyValueFactory<Course, Boolean>("checked"));
        col_check.setCellFactory(CheckBoxTableCell.forTableColumn(col_check));
        col_check.setEditable(true);
        table.setEditable(true);
        table.setItems(list);

        //Current Semester, Current Course Registration
        curSem = CurrentSemesterDAO.getCurrentSemester();
        currentDay = LocalDate.now();
        List<CourseRegistration> courseRegistrationList = CourseRegistrationDAO.getCourseRegistrationList();
        for (CourseRegistration item : courseRegistrationList) {
            if (currentDay.isAfter(item.getDateStart().toLocalDate()) && currentDay.isBefore(item.getDateEnd().toLocalDate())) {
                currentCourseRegistration = item;
                break;
            }
        }
        if (currentCourseRegistration != null) {
            curSemRegisLabel.setText("ĐỢT " + currentCourseRegistration.getId()
                    + " HK" + + currentCourseRegistration.getSemesterId()
                    + "/" + currentCourseRegistration.getYear() % 100
                    + "-" + (currentCourseRegistration.getYear() + 1) % 100);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            currentTime.setText("Thời gian: " + formatter.format(currentCourseRegistration.getDateStart())
                    + " - " + formatter.format(currentCourseRegistration.getDateEnd()));

            //Current Course to register, check selected checkbox if student had registered
            courseList = CourseDAO.getCourseListBySemester(curSem.getId(), curSem.getYear());
            for (Course element : courseList) {
                if (CourseattendDAO.getCourseattendByID(App.getCurrentStudent().getId(), element.getId()) != null)
                    element.setChecked(true);
                else element.setChecked(false);
                list.add(element);
            }
            list.addListener((ListChangeListener<Course>) c -> {
                while (c.next()) {
                    if (c.wasUpdated()) {
                        Course updateCourse = courseList.get(c.getFrom());
                        if (updateCourse.isChecked()) {
                            Alert confirm = new Alert(Alert.AlertType.WARNING);
                            confirm.setTitle("Warning");
                            confirm.setHeaderText(null);
                            if (CourseattendDAO.getCourseattendListCount(App.getCurrentStudent().getId(), curSem) == 8)
                            {
                                confirm.setContentText("Bạn chỉ có thể đăng ký tối đa 8 môn!");
                                confirm.showAndWait();
                                updateCourse.setChecked(false);
                                return;
                            }
                            else if (updateCourse.getMaxSlot() == updateCourse.getRegisterSlot()) {
                                confirm.setContentText("Học phần đã full!");
                                confirm.showAndWait();
                                updateCourse.setChecked(false);
                                return;
                            }
                            Semester sem = SemesterDAO.getSemesterByID(curSem.getYear(), curSem.getId());
                            List<Courseattend> attendList = CourseattendDAO.getCourseattendList(App.getCurrentStudent().getId(), sem);
                            for (Courseattend item : attendList)
                            {
                                if (updateCourse.getDay().equals(item.getCourseByCourseId().getDay()) && updateCourse.getPeriod().equals(item.getCourseByCourseId().getPeriod()))
                                {
                                    confirm.setContentText("Bạn không thể đăng ký 2 môn trùng ngày và giờ!");
                                    confirm.showAndWait();
                                    updateCourse.setChecked(false);
                                    return;
                                }
                            }
                            CourseattendDAO.addCourseattend(new Courseattend(App.getCurrentStudent().getId(), updateCourse.getId()));
                        }
                        else CourseattendDAO.removeCourseattendByID(App.getCurrentStudent().getId(), updateCourse.getId());
                        updateCourse.setRegisterSlot(CourseDAO.getCourseStudentCount(updateCourse.getId()));
                        CourseDAO.updateCourse(updateCourse);
                        table.refresh();
                        subjectNum.setText(CourseattendDAO.getCourseattendListCount(App.getCurrentStudent().getId(), curSem) + "");
                    }
                }
            });
            subjectNum.setText(CourseattendDAO.getCourseattendListCount(App.getCurrentStudent().getId(), curSem) + "");
        }
        else {
            curSemRegisLabel.setText("CHƯA TỚI ĐỢT ĐKHP");
            currentTime.setText("");
        }
        //Tableview
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
