package Controller;

import javafx.fxml.FXML;
import Main.App;

public class StudentDashboardController {

    public void exit()
    {
        App.exit();
    }

    @FXML
    public void minimize() {
        App.minimize();
    }
}
