package org.main.Controller;

import javafx.fxml.FXML;
import org.main.App;

public class StudentDashboardController {
    @FXML
    public void exit()
    {
        App.exit();
    }

    @FXML
    public void minimize() {
        App.minimize();
    }
}
