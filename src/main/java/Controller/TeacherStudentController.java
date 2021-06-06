package Controller;

import DAO.AccountDAO;
import DAO.ClassDAO;
import DAO.StudentDAO;
import Main.App;
import POJO.Account;
import POJO.Classname;
import POJO.Student;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TeacherStudentController implements Initializable {
    @FXML
    private TextField searchBar;
    @FXML
    private Label classLabel;
    @FXML
    TableView<Student> table;
    @FXML
    TableColumn<Student, String> col_id;
    @FXML
    TableColumn<Student, String> col_firstName;
    @FXML
    TableColumn<Student, String> col_lastName;
    @FXML
    TableColumn<Student, Date> col_dateOfBirth;
    @FXML
    TableColumn<Student, String> col_gender;
    @FXML
    TableColumn<Student, String> col_account;
    @FXML
    TableColumn<Student, String> col_password;
    ObservableList<Student> list = FXCollections.observableArrayList();
    FilteredList<Student> filterList = new FilteredList<>(list);
    private Classname currentClass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_dateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        col_gender.setCellValueFactory(new PropertyValueFactory<>("sex"));
        col_account.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAccountByAccount().getAccountId()));
        col_password.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAccountByAccount().getPassword()));
    }
    public void setCurrentClass(Classname currentClass) {
        this.currentClass = currentClass;
        classLabel.setText("Lớp: " + currentClass.getId());
        List<Student> studentList = StudentDAO.getStudentListByClass(currentClass.getId());
        for (Student student : studentList)
            list.add(student);
        table.setItems(list);
    }
    @FXML
    public void exit() {
        App.exit();
    }

    @FXML
    public void minimize() {
        App.minimize();
    }

    @FXML
    public void onDashboard() throws IOException {
        App.changeScene("TeacherDashboard");
    }

    @FXML
    public void logoutClicked() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("Login");
    }

    @FXML
    public void onReturn() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("TeacherClass");
    }

    @FXML
    public void onAdd() throws IOException {
        Dialog<Student> dialog = newDialog(null);
        Optional<Student> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (AccountDAO.addAccount(result.get().getAccountByAccount()))
                if (StudentDAO.addStudent(result.get())) {
                    currentClass.setTotal(Math.toIntExact(ClassDAO.getClassMemberCount(currentClass.getId())));
                    currentClass.setMaleCount(Math.toIntExact(ClassDAO.getClassMaleCount(currentClass.getId())));
                    currentClass.setFemaleCount(Math.toIntExact(ClassDAO.getClassFemaleCount(currentClass.getId())));
                    ClassDAO.updateClass(currentClass);
                    table.getItems().add(result.get());
                }
                else AccountDAO.removeAccountByID(result.get().getAccountByAccount().getAccountId());
        }
    }

    @FXML
    public void onRemove() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION);
            confirmExit.setTitle("Delete");
            confirmExit.setContentText("Bạn có chắc chắn muốn xóa sinh viên này không?");
            confirmExit.setHeaderText(null);
            Optional<ButtonType> option = confirmExit.showAndWait();
            if (option.get() == ButtonType.OK) {
                Student selectedItem = table.getSelectionModel().getSelectedItem();
                if (StudentDAO.removeStudentByID(selectedItem.getId()))
                    if (AccountDAO.removeAccountByID(selectedItem.getAccountByAccount().getAccountId())) {
                        currentClass.setTotal(Math.toIntExact(ClassDAO.getClassMemberCount(currentClass.getId())));
                        currentClass.setMaleCount(Math.toIntExact(ClassDAO.getClassMaleCount(currentClass.getId())));
                        currentClass.setFemaleCount(Math.toIntExact(ClassDAO.getClassFemaleCount(currentClass.getId())));
                        ClassDAO.updateClass(currentClass);
                        table.getItems().remove(selectedItem);
                    }
                    else StudentDAO.addStudent(selectedItem);
            }
        } else {
            Alert confirmExit = new Alert(Alert.AlertType.WARNING);
            confirmExit.setTitle("Warning");
            confirmExit.setContentText("Vui lòng chọn tài khoản cần xóa!!");
            confirmExit.setHeaderText(null);
            confirmExit.showAndWait();
        }
    }
    @FXML
    public void onSearch() {
        String data = searchBar.getText().toLowerCase();
        filterList.setPredicate(
                student -> {
                    if (data == null || data.isEmpty()) return true;
                    if (student.getId().toLowerCase().contains(data)) return true;
                    if (student.getFirstName().toLowerCase().contains(data)) return true;
                    if (student.getLastName().toLowerCase().contains(data)) return true;
                    if (student.getAccountByAccount().getAccountId().toLowerCase().contains(data)) return true;
                    if (((student.getFirstName() + " " + student.getLastName()).toLowerCase()).contains(data)) return true;
                    return false;
                }
        );
        table.setItems(filterList);
    }
    @FXML public void onUpdate(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Student st = table.getSelectionModel().getSelectedItem();
            Dialog<Student> dialog = newDialog(st);
            Optional<Student> result = dialog.showAndWait();
            if (result.isPresent()) {
                st = result.get();
                if (AccountDAO.updateAccount(st.getAccountByAccount())) {
                    StudentDAO.updateStudent(st);
                    table.refresh();
                }
            }
        }
        else if (event.getButton() == MouseButton.SECONDARY) {
            Student st = table.getSelectionModel().getSelectedItem();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Reset mật khẩu");
            confirm.setContentText("Reset mật khẩu cho sinh viên này? Mật khẩu mặc định sẽ là 12345678");
            confirm.setHeaderText(null);
            Optional<ButtonType> option = confirm.showAndWait();
            if (option.get() == ButtonType.OK) {
                st.getAccountByAccount().setPassword("12345678");
                if (AccountDAO.updateAccount(st.getAccountByAccount())) {
                    StudentDAO.updateStudent(st);
                    table.refresh();
                }
            }
        }
    }
    public Dialog<Student> newDialog(Student st) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/AddStudentDialog.fxml"));
        DialogPane content = fxmlLoader.load();
        Dialog<Student> dialog = new Dialog<>();
        dialog.setTitle("Thông tin sinh viên");
        dialog.setResizable(false);
        dialog.setDialogPane(content);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/assets/img/SchoolLogo.png").toString()));
        AddStudentDialogController adc = fxmlLoader.getController();
        if (st != null) {
            adc.setInfo(st);
            adc.setEditable(false);
        }
        Button btn = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
        btn.addEventFilter(ActionEvent.ACTION, event -> {
            if (adc.getId().equals("") || adc.getFirstName().equals("") || adc.getLastName().equals("") || adc.getGender() == null || adc.getAccount().equals("") || adc.getPassword().equals("")) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setContentText("Vui lòng nhập hết thông tin!");
                warning.setHeaderText(null);
                warning.showAndWait();
                event.consume();
            }
        });
        dialog.setResultConverter(button -> {
            if (button == ButtonType.APPLY) {
                System.out.println(adc.getLastName());
                System.out.println(adc.getDateOfBirth());
                if (st == null)
                    return new Student(adc.getId(), adc.getFirstName(), adc.getLastName(), adc.getDateOfBirth(), adc.getGender(), currentClass, new Account(adc.getAccount(), adc.getPassword(), "SV"));
                else {
                    st.setFirstName(adc.getFirstName());
                    st.setLastName(adc.getLastName());
                    st.setDateOfBirth(adc.getDateOfBirth());
                    st.setSex(adc.getGender());
                    st.getAccountByAccount().setAccountId(adc.getAccount());
                    st.getAccountByAccount().setPassword(adc.getPassword());
                    return st;
                }
            }
            return null;
        });
        return dialog;
    }
}
