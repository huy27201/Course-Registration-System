package Controller;

import DAO.AccountDAO;
import DAO.StudentDAO;
import Main.App;
import POJO.Account;
import POJO.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML private TextField userField;
    @FXML private TextField passwordField;
    @FXML private Label warning;
    public LoginController()
    {
    }

    @FXML
    public void onSubmit() {
        boolean check = false;
        String username = userField.getText();
        String password = passwordField.getText();
        List<Account> accList = AccountDAO.getAccountList();
        for (Account acc : accList) {
            if (username.equals(acc.getAccountId()) && password.equals(acc.getPassword())) {
                try {
                    if (acc.getRole().equals("GV")) {
                        Thread.sleep(300);
                        App.changeScene("TeacherDashboard");
                    }
                    else if (acc.getRole().equals("SV")) {
                        Thread.sleep(300);
                        App.changeScene("StudentDashboard");
                    }
//                    Student st = StudentDAO.getStudentByUsername(username);
                    //TeacherDashboardController.setIdentity(st.getLastName());
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
        if (k.getCode().equals(KeyCode.ENTER)) {
            onSubmit();
        }
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
