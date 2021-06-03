package Controller;

import DAO.SemesterDAO;
import Main.App;
import POJO.Semester;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TeacherSemesterController implements Initializable {
    @FXML private TextField searchBar;
    @FXML TableView<Semester> table;
    @FXML TableColumn<Semester, Integer> col_id;
    @FXML TableColumn<Semester, Integer> col_year;
    @FXML TableColumn<Semester, Date> col_dateStart;
    @FXML TableColumn<Semester, Date> col_dateEnd;
    @FXML Label currentSemester;
    ObservableList<Semester> list = FXCollections.observableArrayList();
    FilteredList<Semester> filterList = new FilteredList<>(list);

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
        Dialog<Semester> dialog = newDialog();
        Optional<Semester> result = dialog.showAndWait();
        if (result.isPresent()) {
            if(SemesterDAO.addSemester(result.get()))
                table.getItems().add(result.get());
        }
    }
    @FXML
    public void onRemove() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION);
            confirmExit.setTitle("Delete");
            confirmExit.setContentText("Bạn có chắc chắn muốn xóa học kỳ này không? Các học phần và kì ĐKHP chứa môn học này có thể bị xóa theo!!");
            confirmExit.setHeaderText(null);
            Optional<ButtonType> option = confirmExit.showAndWait();
            if (option.get() == ButtonType.OK) {
                Semester selectedSem = table.getSelectionModel().getSelectedItem();
                table.getItems().remove(selectedSem);
                SemesterDAO.removeSemesterByID(selectedSem.getYear(), selectedSem.getId());
            }
        }
        else {
            Alert confirmExit = new Alert(Alert.AlertType.WARNING);
            confirmExit.setTitle("Warning");
            confirmExit.setContentText("Vui lòng chọn môn học cần xóa!!");
            confirmExit.setHeaderText(null);
            confirmExit.showAndWait();
        }
    }
    @FXML
    void setCurrent(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Semester sem = table.getSelectionModel().getSelectedItem();
            currentSemester.setText(sem.getId() + "/" + sem.getYear() + "-" + (sem.getYear() + 1));
        }
    }
    public Dialog<Semester> newDialog() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/SemesterDialog.fxml"));
        DialogPane content = fxmlLoader.load();
        Dialog<Semester> dialog = new Dialog<>();
        dialog.setTitle("Thông tin học kỳ");
        dialog.setDialogPane(content);
        SemesterDialogController sdc = fxmlLoader.getController();
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
                return new Semester(sdc.getId(), sdc.getYear(), sdc.getDateStart(), sdc.getDateEnd());
            return null;
        });
        return dialog;
    }
}
