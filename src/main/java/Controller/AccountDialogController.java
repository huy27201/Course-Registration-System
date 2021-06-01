package Controller;

import POJO.Teacher;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class AccountDialogController {
    @FXML private TextField id;
    @FXML private TextField firstName;
    @FXML private TextField lastName;
    @FXML ToggleGroup gender;
    @FXML RadioButton male;
    @FXML RadioButton female;
    @FXML private TextField account;
    @FXML private TextField password;

    public void setInfo(Teacher tch) {
        this.id.setText(tch.getId());
        this.firstName.setText(tch.getFirstName());
        this.lastName.setText(tch.getLastName());
        if (tch.getSex().equals("Nam")) male.setSelected(true);
        else female.setSelected(true);
        this.account.setText(tch.getAccountByAccount().getAccountId());
        this.password.setText(tch.getAccountByAccount().getPassword());
    }
    @FXML
    public String getId() {
        return id.getText();
    }
    @FXML
    public String getFirstName() {
        return firstName.getText();
    }
    @FXML
    public String getLastName() {
        return lastName.getText();
    }
    @FXML
    public String getGender() {
        RadioButton selectedRadioButton = (RadioButton) gender.getSelectedToggle();
        if (selectedRadioButton != null) return selectedRadioButton.getText();
        else return null;
    }
    @FXML
    public String getAccount() {
        return account.getText();
    }
    @FXML
    public String getPassword() {
        return password.getText();
    }
    @FXML
    public void setDisable(boolean b) {
        id.setDisable(true);
    }
}
