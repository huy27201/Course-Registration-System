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

public class CourseController implements Initializable {
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
    TableColumn<Course, String> col_maxSlot;
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
    public void onAdd() throws IOException {
        Dialog<Course> dialog = newDialog();
        Optional<Course> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (CourseDAO.addCourse(result.get()))
                table.getItems().add(result.get());
        }
    }

    @FXML
    public void onRemove() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION);
            confirmExit.setTitle("Delete");
            confirmExit.setContentText("Bạn có chắc chắn muốn xóa học phần này không? Danh sách sinh viên đăng ký học phần này có thể bị xóa theo!!");
            confirmExit.setHeaderText(null);
            Optional<ButtonType> option = confirmExit.showAndWait();
            if (option.get() == ButtonType.OK) {
                Course selectedItem = table.getSelectionModel().getSelectedItem();
                if (CourseDAO.removeCourseByID(new CoursePK(selectedItem.getId(), selectedItem.getSemesterId(), selectedItem.getYear())))
                    table.getItems().remove(selectedItem);
            }
        } else {
            Alert confirmExit = new Alert(Alert.AlertType.WARNING);
            confirmExit.setTitle("Warning");
            confirmExit.setContentText("Vui lòng chọn học phần cần xóa!!");
            confirmExit.setHeaderText(null);
            confirmExit.showAndWait();
        }
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

    public Dialog<Course> newDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/CourseDialog.fxml"));
        DialogPane content = fxmlLoader.load();
        Dialog<Course> dialog = new Dialog<>();
        dialog.setTitle("Thông tin học phần");
        dialog.setDialogPane(content);
        CourseDialogController cdc = fxmlLoader.getController();
        cdc.setSemesterId(curSem.getId());
        cdc.setYear(curSem.getYear());
        Button btn = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
        btn.addEventFilter(ActionEvent.ACTION, event -> {
            if (cdc == null || cdc.getSubjectName() == null || cdc.getRoom().equals("") == true || cdc.getHeadTeacher().equals("") == true || cdc.getMaxSlot() == null) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setContentText("Vui lòng nhập hết thông tin!");
                warning.setHeaderText(null);
                warning.showAndWait();
                event.consume();
            }
        });
        dialog.setResultConverter(button -> {
            if (button == ButtonType.APPLY)
                return new Course(cdc.getSemesterId(), cdc.getYear(), cdc.getHeadTeacher(), cdc.getSubjectName(), cdc.getRoom(), cdc.getDay(), cdc.getPeriod(), cdc.getMaxSlot());
            return null;
        });
        return dialog;
    }
}
