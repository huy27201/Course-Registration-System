package Controller;

import javafx.fxml.FXML;
import Main.App;

public class StudentDashboardController {
    @FXML private String identity;

    @FXML public String getIdentity() {
        return identity;
    }
    @FXML public void setIdentity(String identity) {
        this.identity = identity;
    }
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
