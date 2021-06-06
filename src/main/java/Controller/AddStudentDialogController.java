package Controller;

import POJO.Student;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import java.sql.Date;
import java.time.LocalDate;

public class AddStudentDialogController {
    @FXML
    private TextField id;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    ToggleGroup gender;
    @FXML
    RadioButton male;
    @FXML
    RadioButton female;
    @FXML
    private TextField account;
    @FXML
    private TextField password;

    public void setInfo(Student st) {
        this.id.setText(st.getId());
        this.firstName.setText(st.getFirstName());
        this.lastName.setText(st.getLastName());
        this.dateOfBirth.setValue(st.getDateOfBirth().toLocalDate());
        if (st.getSex().equals("Nam")) male.setSelected(true);
        else female.setSelected(true);
        this.account.setText(st.getAccountByAccount().getAccountId());
        this.password.setText(st.getAccountByAccount().getPassword());
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
    public Date getDateOfBirth() {
        if (dateOfBirth.getValue() == null) return null;
        return Date.valueOf(dateOfBirth.getValue());
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
    public void setEditable(boolean b) {
        account.setEditable(false);
        id.setEditable(false);
        id.getStyleClass().add("non-editable");
        account.getStyleClass().add("non-editable");
    }
}
