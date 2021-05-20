package org.main.Controller;
import javafx.fxml.FXML;
import org.main.App;
import java.io.IOException;

public class TeacherDashboardController {
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
            App.changeScene("Login");
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
}
