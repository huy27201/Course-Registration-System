package Controller;

import DAO.AccountDAO;
import DAO.StudentDAO;
import DAO.TeacherDAO;
import POJO.Student;
import POJO.Teacher;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
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
    ComboBox classLists;
    @FXML
    private TextField account;
    @FXML
    private TextField password;
    private boolean checkTeacher;
    private Teacher tch;
    private Student st;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setEditable(false);
        id.getStyleClass().add("non-editable");
        account.setEditable(false);
        account.getStyleClass().add("non-editable");
    }

    public void setUser(Teacher tch) {
        this.tch = tch;
        checkTeacher = true;
        id.setText(tch.getId());
        firstName.setText(tch.getFirstName());
        lastName.setText(tch.getLastName());
        dateOfBirth.setEditable(false);
        dateOfBirth.setDisable(true);
        if (tch.getSex().equals("Nam")) male.setSelected(true);
        else female.setSelected(true);
        classLists.setDisable(true);
        account.setText(tch.getAccountByAccount().getAccountId());
        password.setText(tch.getAccountByAccount().getPassword());
    }

    public void setUser(Student st) {
        this.st = st;
        checkTeacher = false;
        id.setText(st.getId());
        firstName.setText(st.getFirstName());
        lastName.setText(st.getLastName());
        dateOfBirth.setValue(st.getDateOfBirth().toLocalDate());
        if (st.getSex().equals("Nam")) male.setSelected(true);
        else female.setSelected(true);
        classLists.setValue(st.getClassnameByClassName().getId());
        classLists.setEditable(false);
        classLists.getStyleClass().add("non-editable");
        account.setText(st.getAccountByAccount().getAccountId());
        password.setText(st.getAccountByAccount().getPassword());
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
    public String getPassword() {
        return password.getText();
    }

    @FXML
    public void onSaveProfile(Event event) {
        Stage dialog = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (checkTeacher) {
            tch.setFirstName(getFirstName());
            tch.setLastName(getLastName());
            tch.setSex(getGender());
            tch.getAccountByAccount().setPassword(getPassword());
            AccountDAO.updateAccount(tch.getAccountByAccount());
            TeacherDAO.updateTeacher(tch);
        }
        else {
            st.setFirstName(getFirstName());
            st.setLastName(getLastName());
            st.setDateOfBirth(getDateOfBirth());
            st.setSex(getGender());
            st.getAccountByAccount().setPassword(getPassword());
            AccountDAO.updateAccount(st.getAccountByAccount());
            StudentDAO.updateStudent(st);
        }
        dialog.close();
    }

}
