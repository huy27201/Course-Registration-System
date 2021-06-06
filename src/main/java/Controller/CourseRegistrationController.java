package Controller;

import DAO.CourseRegistrationDAO;
import DAO.CurrentSemesterDAO;
import Main.App;
import POJO.CourseRegistration;
import POJO.Currentsemester;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CourseRegistrationController implements Initializable {
    @FXML
    private TextField searchBar;
    @FXML
    TableView<CourseRegistration> table;
    @FXML
    TableColumn<CourseRegistration, Integer> col_id;
    @FXML
    TableColumn<CourseRegistration, Integer> col_semesterId;
    @FXML
    TableColumn<CourseRegistration, Integer> col_year;
    @FXML
    TableColumn<CourseRegistration, Date> col_dateStart;
    @FXML
    TableColumn<CourseRegistration, Date> col_dateEnd;
    @FXML
    Label currentSemesterLabel;
    ObservableList<CourseRegistration> list = FXCollections.observableArrayList();
    FilteredList<CourseRegistration> filterList = new FilteredList<>(list);
    private Currentsemester curSem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<CourseRegistration> courseRegistrationList = CourseRegistrationDAO.getCourseRegistrationList();
        for (CourseRegistration element : courseRegistrationList)
            list.add(element);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_semesterId.setCellValueFactory(new PropertyValueFactory<>("semesterId"));
        col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
        col_dateStart.setCellValueFactory(new PropertyValueFactory<>("dateStart"));
        col_dateEnd.setCellValueFactory(new PropertyValueFactory<>("dateEnd"));
        table.setItems(list);
        curSem = CurrentSemesterDAO.getCurrentSemester();
        currentSemesterLabel.setText(curSem.getId() + "/" + curSem.getYear() + "-" + (curSem.getYear() + 1));
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
    public void onSearch() {
        String data = searchBar.getText().toLowerCase();
        filterList.setPredicate(
                res -> {
                    if (data == null || data.isEmpty()) return true;
                    if (String.valueOf(res.getId()).contains(data)) return true;
                    if (String.valueOf(res.getYear()).contains(data)) return true;
                    if (String.valueOf(res.getSemesterId()).contains(data)) return true;
                    if ((res.getId() + " - " + res.getSemesterId() + " - " + res.getYear()).contains(data)) return true;
                    return false;
                }
        );
        table.setItems(filterList);
    }

    @FXML
    public void onAdd(Event event) throws IOException {
        Dialog<CourseRegistration> dialog = newDialog();
        Optional<CourseRegistration> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (CourseRegistrationDAO.addCourseRegistration(result.get()))
                table.getItems().add(result.get());
        }
    }

    @FXML
    public void onRemove() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION);
            confirmExit.setTitle("Delete");
            confirmExit.setContentText("Bạn có chắc chắn muốn xóa kỳ đăng ký học phần này không?");
            confirmExit.setHeaderText(null);
            Optional<ButtonType> option = confirmExit.showAndWait();
            if (option.get() == ButtonType.OK) {
                CourseRegistration selectedItem = table.getSelectionModel().getSelectedItem();
                if (CourseRegistrationDAO.removeCourseRegistrationByID(selectedItem.getId(), selectedItem.getSemesterId(), selectedItem.getYear()))
                    table.getItems().remove(selectedItem);
            }
        } else {
            Alert confirmExit = new Alert(Alert.AlertType.WARNING);
            confirmExit.setTitle("Warning");
            confirmExit.setContentText("Vui lòng chọn kỳ ĐKHP cần xóa!!");
            confirmExit.setHeaderText(null);
            confirmExit.showAndWait();
        }
    }

    public Dialog<CourseRegistration> newDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/CourseRegistrationDialog.fxml"));
        DialogPane content = fxmlLoader.load();
        Dialog<CourseRegistration> dialog = new Dialog<>();
        dialog.setTitle("Thông tin kỳ ĐKHP");
        dialog.setDialogPane(content);
        CourseRegistrationDialogController sdc = fxmlLoader.getController();
        sdc.setYear(curSem.getYear());
        sdc.setSemesterId(curSem.getId());
        Button btn = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
        btn.addEventFilter(ActionEvent.ACTION, event -> {
            if (sdc.getDateStart() == null || sdc.getDateEnd() == null) {
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
                return new CourseRegistration(sdc.getId(), sdc.getSemesterId(), sdc.getYear(), sdc.getDateStart(), sdc.getDateEnd());
            return null;
        });
        return dialog;
    }
}
