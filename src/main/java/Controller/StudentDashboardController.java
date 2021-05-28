package Controller;

import POJO.Student;
import javafx.fxml.FXML;
import Main.App;
import javafx.scene.control.Label;

public class StudentDashboardController {
    @FXML private Label identity;

    void initData(Student student) {
        identity.setText("Xin chào, " + student.getLastName());
    }
    public void exit()
    {
        App.exit();
    }

    @FXML
    public void minimize() {
        App.minimize();
    }

}
