package Controller;

import DAO.AccountDAO;
import DAO.StudentDAO;
import DAO.TeacherDAO;
import Main.App;
import POJO.Account;
import POJO.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML private TextField userField;
    @FXML private TextField passwordField;
    @FXML private Label warning;
    @FXML private Stage stage;
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
//                        stage.initStyle(StageStyle.UNDECORATED);
//                        stage.setResizable(false);
//                        stage.setScene();
//                        stage.show();
//                        TeacherDashboardController tchController = loader.getController();
//                        tchController.initData(TeacherDAO.getTeacherA(username));
                    }
                    else if (acc.getRole().equals("SV")) {
                        Thread.sleep(300);
                        App.changeScene("StudentDashboard");
//                        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentDashboard"));
//                        StudentDashboardController stuController = loader.getController();
//                        stuController.initData(StudentDAO.getStudentByID(username));
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
