package org.main.Controller;

import org.main.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import java.io.IOException;

public class LoginController {
    @FXML private Button btnLogin;
    @FXML private Label forgotLink;
    @FXML private TextField userField;
    @FXML private TextField passwordField;
    @FXML private Label warning;
    public LoginController()
    {
    }

    @FXML
    public void btnEnter(MouseEvent mouseEvent) {
        btnLogin.setStyle(  "-fx-background-color: #FFF;"
                            + " -fx-text-fill: #0D6895;");
    }

    @FXML
    public void btnExit(MouseEvent mouseEvent) {
        btnLogin.setStyle(  "-fx-background-color: #0D6895;" +
                            " -fx-text-fill: #FFF;");
    }

    @FXML
    public void forgotEnter(MouseEvent mouseEvent) {
        forgotLink.setStyle("-fx-text-fill: #1189C4;");
    }

    @FXML
    public void forgotExit(MouseEvent mouseEvent) {
        forgotLink.setStyle("-fx-text-fill: #0D6895;");
    }

    @FXML
    public void onSubmit() {
        String username = userField.getText();
        String password = passwordField.getText();
        if (username.equals("gv") && password.equals("gv")) {
                try {
                    Thread.sleep(300);
                    App.changeScene("TeacherDashboard");
                } catch (IOException | InterruptedException ioException) {
                    ioException.printStackTrace();
                }

        }
        else {
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
