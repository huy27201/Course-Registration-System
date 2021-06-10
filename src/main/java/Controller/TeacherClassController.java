package Controller;

import DAO.ClassDAO;
import Main.App;
import POJO.Classname;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TeacherClassController implements Initializable {
    @FXML
    private TextField searchBar;
    @FXML
    TableView<Classname> table;
    @FXML
    TableColumn<Classname, String> col_id;
    @FXML
    TableColumn<Classname, Integer> col_total;
    @FXML
    TableColumn<Classname, Integer> col_male;
    @FXML
    TableColumn<Classname, Integer> col_female;
    ObservableList<Classname> list = FXCollections.observableArrayList();
    FilteredList<Classname> filterList = new FilteredList<>(list);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Classname> classList = ClassDAO.getClassList();
        for (Classname item : classList)
            list.add(item);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_male.setCellValueFactory(new PropertyValueFactory<>("maleCount"));
        col_female.setCellValueFactory(new PropertyValueFactory<>("femaleCount"));
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
    public void onStudentClass(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Classname item = table.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/TeacherStudent.fxml"));
            Parent root = fxmlLoader.load();
            TeacherStudentController controller = fxmlLoader.getController();
            controller.setCurrentClass(item);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void onRemove() {
        table.setItems(list);
        if (table.getSelectionModel().getSelectedItem() != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Delete");
            confirm.setContentText("Bạn có chắc chắn muốn xóa lớp này không?");
            confirm.setHeaderText(null);
            Optional<ButtonType> option = confirm.showAndWait();
            if (option.get() == ButtonType.OK) {
                Classname selectedItem = table.getSelectionModel().getSelectedItem();
                if (selectedItem.getTotal() > 0 ) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("Vui lòng xóa các sinh viên trong lớp học này!!");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
                else {
                    if (ClassDAO.removeClassByID(selectedItem.getId())) {
                        table.getItems().remove(selectedItem);
                    }
                }

            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Vui lòng chọn lớp học cần xóa!!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
    @FXML
    public void onSearch() {
        String data = searchBar.getText().toLowerCase();
        filterList.setPredicate(
                item -> {
                    if (data == null || data.isEmpty()) return true;
                    if (item.getId().toLowerCase().contains(data)) return true;
                    return false;
                }
        );
        table.setItems(filterList);
    }
    @FXML
    public void onAdd() {
        table.setItems(list);
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setTitle("Class");
        dialog.setContentText("Nhập tên lớp học");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/assets/img/SchoolLogo.png").toString()));
        Optional<String> result = dialog.showAndWait();
        String id;
        if (result.isPresent()) {
            id = result.get();
            Classname newClass = new Classname(id);
            if (ClassDAO.addClass(newClass))
                table.getItems().add(newClass);
        }
    }
}
