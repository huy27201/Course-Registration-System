package Controller;

import DAO.AccountDAO;
import DAO.TeacherDAO;
import Main.App;
import POJO.Account;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML private TextField userField;
    @FXML private TextField passwordField;
    @FXML private Label warning;
    FXMLLoader fxmlLoader;
    @FXML
    public void onSubmit(Event event) {
        boolean check = false;
        String username = userField.getText();
        String password = passwordField.getText();
        List<Account> accList = AccountDAO.getAccountList();
        for (Account acc : accList) {
            if (username.equals(acc.getAccountId()) && password.equals(acc.getPassword())) {
                try {
                    Thread.sleep(300);
                    if (acc.getRole().equals("GV"))
                        fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/TeacherDashboard.fxml"));
                    else if (acc.getRole().equals("SV"))
                        fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/StudentDashboard.fxml"));
                    Parent root = fxmlLoader.load();
                    TeacherDashboardController dashboardController = fxmlLoader.getController();
                    dashboardController.setCurrentTeacher(TeacherDAO.getTeacherByUsername(username));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException | InterruptedException ioException) {
                    ioException.printStackTrace();
                }
                check = true;
            }
        }
        if (check == false) {
            try {
                Thread.sleep(300);
                warning.setVisible(true);
            } catch (InterruptedException ioException) {
                ioException.printStackTrace();
            }
        }
    }
    @FXML
    public void submitKeyPressed(KeyEvent k) {
        if (k.getCode().equals(KeyCode.ENTER))
            onSubmit(k);
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
