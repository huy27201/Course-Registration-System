package Controller;

import DAO.CourseDAO;
import DAO.CourseRegistrationDAO;
import DAO.CurrentSemesterDAO;
import DAO.SemesterDAO;
import Main.App;
import POJO.Course;
import POJO.Currentsemester;
import POJO.Semester;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TeacherSemesterController implements Initializable {
    @FXML
    private TextField searchBar;
    @FXML
    TableView<Semester> table;
    @FXML
    TableColumn<Semester, Integer> col_id;
    @FXML
    TableColumn<Semester, Integer> col_year;
    @FXML
    TableColumn<Semester, Date> col_dateStart;
    @FXML
    TableColumn<Semester, Date> col_dateEnd;
    @FXML
    Label currentSemesterLabel;
    ObservableList<Semester> list = FXCollections.observableArrayList();
    FilteredList<Semester> filterList = new FilteredList<>(list);
    private Currentsemester curSem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Semester> semesterList = SemesterDAO.getSemesterList();
        for (Semester sem : semesterList)
            list.add(sem);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
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
                semester -> {
                    if (data == null || data.isEmpty()) return true;
                    if (String.valueOf(semester.getId()).contains(data)) return true;
                    if (String.valueOf(semester.getYear()).contains(data)) return true;
                    if ((semester.getYear() + " - " + semester.getId()).contains(data)) return true;
                    return false;
                }
        );
        table.setItems(filterList);
    }

    @FXML
    public void onAdd(Event event) throws IOException {
        table.setItems(list);
        Dialog<Semester> dialog = newDialog();
        Optional<Semester> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (SemesterDAO.addSemester(result.get()))
                table.getItems().add(result.get());
        }
    }

    @FXML
    public void onRemove() {
        table.setItems(list);
        if (table.getSelectionModel().getSelectedItem() != null) {
            Semester selectedSem = table.getSelectionModel().getSelectedItem();
            if (selectedSem.getYear() == curSem.getYear() && selectedSem.getId() == curSem.getId()) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Waring");
                warning.setContentText("B???n kh??ng th??? x??a h???c k?? hi???n t???i!!");
                warning.setHeaderText(null);
                warning.showAndWait();
            }
            else {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Delete");
                confirm.setContentText("B???n c?? ch???c ch???n mu???n x??a h???c k??? n??y kh??ng?");
                confirm.setHeaderText(null);
                Optional<ButtonType> option = confirm.showAndWait();
                if (option.get() == ButtonType.OK) {
                    List<Course> cList = CourseDAO.getCourseListBySemester(selectedSem.getId(), selectedSem.getYear());
                    if (cList.size() > 0) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setContentText("Vui l??ng x??a c??c h???c ph???n c?? trong h???c k??? n??y!!");
                        alert.setHeaderText(null);
                        alert.showAndWait();
                    }
                    else {
                        if (CourseRegistrationDAO.getCourseRegistrationBySemester(selectedSem.getId(), selectedSem.getYear()) != null) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Warning");
                            alert.setContentText("Vui l??ng x??a c??c k??? ????ng k?? h???c ph???n c?? trong h???c k??? n??y!!");
                            alert.setHeaderText(null);
                            alert.showAndWait();
                        }
                        else {
                            if (SemesterDAO.removeSemesterByID(selectedSem.getYear(), selectedSem.getId()))
                                table.getItems().remove(selectedSem);
                        }
                    }

                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Vui l??ng ch???n h???c k??? c???n x??a!!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    @FXML
    void setCurrent(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Semester sem = table.getSelectionModel().getSelectedItem();
            curSem = new Currentsemester(sem.getId(), sem.getYear());
            CurrentSemesterDAO.removeCurrrentSemester();
            CurrentSemesterDAO.addCurrrentSemester(curSem);
            currentSemesterLabel.setText(curSem.getId() + "/" + curSem.getYear() + "-" + (curSem.getYear() + 1));
        }
    }

    public Dialog<Semester> newDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/SemesterDialog.fxml"));
        DialogPane content = fxmlLoader.load();
        Dialog<Semester> dialog = new Dialog<>();
        dialog.setTitle("Th??ng tin h???c k???");
        dialog.setDialogPane(content);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/assets/img/SchoolLogo.png").toString()));
        SemesterDialogController sdc = fxmlLoader.getController();
        Button btn = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
        btn.addEventFilter(ActionEvent.ACTION, event -> {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning");
            warning.setHeaderText(null);
            if (sdc.getDateStart() == null || sdc.getDateEnd() == null) {
                warning.setContentText("Vui l??ng nh???p h???t th??ng tin!");
                warning.showAndWait();
                event.consume();
            }
            else if (sdc.getDateStart().toLocalDate().isAfter(sdc.getDateEnd().toLocalDate())) {
                warning.setContentText("Ng??y k???t th??c ph???i sau ng??y b???t ?????u!");
                warning.showAndWait();
                event.consume();
            }
            else {
                List<Semester> semesterList = SemesterDAO.getSemesterList();
                for (Semester sem : semesterList)
                    if (!sdc.getDateStart().toLocalDate().isAfter(sem.getDateEnd().toLocalDate())) {
                       if (!sdc.getDateEnd().toLocalDate().isBefore(sem.getDateStart().toLocalDate())) {
                           warning.setContentText("Th???i gian c???a h???c k??? kh??ng ???????c tr??ng v???i th???i gian c???a h???c k??? kh??c!");
                           warning.showAndWait();
                           event.consume();
                           break;
                       }
                    }
            }
        });
        dialog.setResultConverter(button -> {
            if (button == ButtonType.APPLY)
                return new Semester(sdc.getId(), sdc.getYear(), sdc.getDateStart(), sdc.getDateEnd());
            return null;
        });
        return dialog;
    }
}
