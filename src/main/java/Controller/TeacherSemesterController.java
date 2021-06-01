package Controller;

//import DAO.SemesterDAO;
import DAO.SemesterDAO;
import Main.App;
import POJO.Semester;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TeacherSemesterController implements Initializable {
    @FXML private TextField searchBar;
    @FXML TableView<Semester> table;
    @FXML TableColumn<Semester, Integer> col_id;
    @FXML TableColumn<Semester, Integer> col_year;
    @FXML TableColumn<Semester, String> col_dateStart;
    @FXML TableColumn<Semester, String> col_dateEnd;
    ObservableList<Semester> list = FXCollections.observableArrayList();
    FilteredList<Semester> filterList = new FilteredList<>(list);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Semester> semesterList = SemesterDAO.getSemesterList();
        for (Semester sem : semesterList) {
            list.add(sem);
            System.out.println(sem);
        }
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        //col_id.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSemesterId().getId()).asObject());
        //col_year.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getSemesterId().getYear()).asObject());
        //col_year.setCellValueFactory(new PropertyValueFactory<>("year"));
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
        App.changeScene("TeacherClass");
    }
    @FXML
    public void onSearch() {
        String data = searchBar.getText().toLowerCase();
        filterList.setPredicate(
                semester -> {
                    if (data == null || data.isEmpty()) return true;
                    if (String.valueOf(semester.getSemesterId().getId()).contains(data)) return true;
                    if (String.valueOf(semester.getSemesterId().getYear()).contains(data)) return true;
                    //if ((semester.getYear() + " - " + String.valueOf(semester.getId()).contains(data)).contains(data)) return true;
                    return false;
                }
        );
        table.setItems(filterList);
    }
    @FXML
    public void onAdd() {
        //Dialog<Teacher> addDialog = new Dialog<Teacher>();
    }
    @FXML
    public void onRemove() {

    }
}
