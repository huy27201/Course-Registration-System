package Controller;

import Main.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.io.IOException;
import java.util.Optional;

public class TeacherClassController {
    @FXML
    public void exit() {
        App.exit();
    }
    @FXML
    public void minimize() {
        App.minimize();
    }
    @FXML
    public void onAdd() {
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
    public void onStudentClass() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("TeacherStudent");
    }
    @FXML
    public void onRemove() {
        Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION);
        confirmExit.setTitle("Xóa lớp");
        confirmExit.setHeaderText(null);
        confirmExit.setContentText("Bạn có chắc chắc muốn xóa không? Mọi sinh viên trong lớp của bạn sẽ bị xóa hết!");
        Optional<ButtonType> option = confirmExit.showAndWait();
        if (option.get() == ButtonType.OK) {

        }
    }
}
