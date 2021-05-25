package Controller;
import javafx.fxml.FXML;
import Main.App;
import javafx.scene.control.Button;

import java.io.IOException;

public class TeacherAccountController {
    @FXML private Button addButton;
    @FXML private Button delButton;

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
}
