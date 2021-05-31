package Controller;

import Main.App;
import POJO.Teacher;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;

import java.io.IOException;

public class TeacherStudentController {
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
    public void onAdd() {
        Dialog<Teacher> addDialog = new Dialog<Teacher>();
    }
    @FXML
    public void onRemove() {

    }
}
