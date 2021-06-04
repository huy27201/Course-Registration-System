package Controller;

import POJO.Student;
import POJO.Teacher;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class ProfileController {
    @FXML private TextField id;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML private DatePicker dateOfBirth;
    @FXML ToggleGroup gender;
    @FXML RadioButton male;
    @FXML RadioButton female;
    @FXML ComboBox classLists;
    @FXML private TextField account;
    @FXML private TextField password;

    public void setUser(Teacher tch) {
        id.setDisable(true);
        classLists.setDisable(true);
        dateOfBirth.setDisable(true);
        id.setText(tch.getId());
        firstName.setText(tch.getFirstName());
        lastName.setText(tch.getLastName());
        if (tch.getSex().equals("Nam")) male.setSelected(true);
        else female.setSelected(true);
        account.setText(tch.getAccountByAccount().getAccountId());
        password.setText(tch.getAccountByAccount().getPassword());
    }
    public void setUser(Student tch) {
        id.setDisable(true);
        id.setText(tch.getId());
        firstName.setText(tch.getFirstName());
        lastName.setText(tch.getLastName());
        if (tch.getSex().equals("Nam")) male.setSelected(true);
        else female.setSelected(true);
        account.setText(tch.getAccountByAccount().getAccountId());
        password.setText(tch.getAccountByAccount().getPassword());
    }
    @FXML
    public void onSaveProfile(Event event) {
        Stage dialog = (Stage) ((Node)event.getSource()).getScene().getWindow();
        dialog.close();
    }
    @FXML
    public void onKeySave(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            onSaveProfile(event);
        }
    }
}
