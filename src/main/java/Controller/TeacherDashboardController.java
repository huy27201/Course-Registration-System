package Controller;
import POJO.Teacher;
import javafx.fxml.FXML;
import Main.App;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import java.io.IOException;

public class TeacherDashboardController {
    //@FXML private Label identity;


    @FXML
    public void exit() {
        App.exit();
    }
    @FXML
    public void minimize() {
        App.minimize();
    }
    @FXML
    public void onSubject() {
        try {
            Thread.sleep(300);
            App.changeScene("");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }
    @FXML
    public void onAccount() {
        try {
            Thread.sleep(300);
            App.changeScene("TeacherAccount");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void onClassName() {
        try {
            Thread.sleep(300);
            App.changeScene("TeacherAccount");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void onCourseManagement() {
        try {
            Thread.sleep(300);
            App.changeScene("TeacherAccount");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void onCourseRegistration() {
        try {
            Thread.sleep(300);
            App.changeScene("TeacherAccount");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void onSemester() {
        try {
            Thread.sleep(300);
            App.changeScene("TeacherAccount");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }
    @FXML
    public void logoutClicked() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("Login");
    }
}
