package Controller;

import DAO.AccountDAO;
import DAO.TeacherDAO;
import Main.App;
import POJO.Account;
import POJO.Teacher;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TeacherAccountController implements Initializable {
    @FXML
    private TextField searchBar;
    @FXML
    TableView<Teacher> table;
    @FXML
    TableColumn<Teacher, String> col_id;
    @FXML
    TableColumn<Teacher, String> col_firstName;
    @FXML
    TableColumn<Teacher, String> col_lastName;
    @FXML
    TableColumn<Teacher, String> col_gender;
    @FXML
    TableColumn<Teacher, String> col_account;
    @FXML
    TableColumn<Teacher, String> col_password;
    ObservableList<Teacher> list = FXCollections.observableArrayList();
    FilteredList<Teacher> filterList = new FilteredList<>(list);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Teacher> teacherList = TeacherDAO.getTeacherList();
        for (Teacher teacher : teacherList)
            list.add(teacher);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_gender.setCellValueFactory(new PropertyValueFactory<>("sex"));
        col_account.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAccountByAccount().getAccountId()));
        col_password.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getAccountByAccount().getPassword()));
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
    public void onDashboard() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("TeacherDashboard");
    }

    @FXML
    public void logoutClicked() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("Login");
    }

    @FXML
    public void onReturn() throws IOException, InterruptedException {
        onDashboard();
    }

    @FXML
    public void onAdd() throws IOException {
        Dialog<Teacher> dialog = newDialog(null);
        Optional<Teacher> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (AccountDAO.addAccount(result.get().getAccountByAccount()))
                if (TeacherDAO.addTeacher(result.get()))
                    table.getItems().add(result.get());
                else AccountDAO.removeAccountByID(result.get().getAccountByAccount().getAccountId());
        }
    }

    @FXML
    public void onRemove() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Teacher selectedTch = table.getSelectionModel().getSelectedItem();
            if (App.getCurrentTeacher().getId().equals(selectedTch.getId())) {
                Alert confirm = new Alert(Alert.AlertType.WARNING);
                confirm.setTitle("Warning");
                confirm.setContentText("Bạn không thể xóa tài khoản của chính mình!!");
                confirm.setHeaderText(null);
                confirm.showAndWait();
            }
            else {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Delete");
                confirm.setContentText("Bạn có chắc chắn muốn xóa tài khoản này không?");
                confirm.setHeaderText(null);
                Optional<ButtonType> option = confirm.showAndWait();
                if (option.get() == ButtonType.OK) {
                    if (TeacherDAO.removeTeacherByID(selectedTch.getId())) {
                        if (AccountDAO.removeAccountByID(selectedTch.getAccountByAccount().getAccountId()))
                            table.getItems().remove(selectedTch);
                        else TeacherDAO.addTeacher(selectedTch);
                    }
                }
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
                teacher -> {
                    if (data == null || data.isEmpty()) return true;
                    if (teacher.getId().toLowerCase().contains(data)) return true;
                    if (teacher.getFirstName().toLowerCase().contains(data)) return true;
                    if (teacher.getLastName().toLowerCase().contains(data)) return true;
                    if (teacher.getAccountByAccount().getAccountId().toLowerCase().contains(data)) return true;
                    if (((teacher.getFirstName() + " " + teacher.getLastName()).toLowerCase()).contains(data)) return true;
                    return false;
                }
        );
        table.setItems(filterList);
    }

    @FXML
    void onUpdate(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Teacher tch = table.getSelectionModel().getSelectedItem();
            if (tch != null) {
                Dialog<Teacher> dialog = newDialog(tch);
                Optional<Teacher> result = dialog.showAndWait();
                if (result.isPresent()) {
                    tch = result.get();
                    if (AccountDAO.updateAccount(tch.getAccountByAccount())) {
                        TeacherDAO.updateTeacher(tch);
                        table.refresh();
                    }
                }
            }
        }
        else if (event.getButton() == MouseButton.SECONDARY) {
            Teacher tch = table.getSelectionModel().getSelectedItem();
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Reset mật khẩu");
            confirm.setContentText("Reset mật khẩu cho tài khoản này? Mật khẩu mặc định sẽ là 12345678");
            confirm.setHeaderText(null);
            Optional<ButtonType> option = confirm.showAndWait();
            if (option.get() == ButtonType.OK) {
                tch.getAccountByAccount().setPassword("12345678");
                if (AccountDAO.updateAccount(tch.getAccountByAccount())) {
                    TeacherDAO.updateTeacher(tch);
                    table.refresh();
                }
            }
        }
    }

    public Dialog<Teacher> newDialog(Teacher tch) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/AccountDialog.fxml"));
        DialogPane content = fxmlLoader.load();
        Dialog<Teacher> dialog = new Dialog<>();
        dialog.setTitle("Thông tin giáo vụ");
        dialog.setDialogPane(content);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/assets/img/SchoolLogo.png").toString()));
        AccountDialogController adc = fxmlLoader.getController();
        if (tch != null) {
            adc.setInfo(tch);
            adc.setEditable(false);
        }
        Button btn = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
        btn.addEventFilter(ActionEvent.ACTION, event -> {
            if (adc.getId() == null || adc.getLastName() == null || adc.getGender() == null || adc.getAccount().equals("") || adc.getPassword().equals("")) {
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
                if (tch == null)
                    return new Teacher(adc.getId(), adc.getFirstName(), adc.getLastName(), adc.getGender(), new Account(adc.getAccount(), adc.getPassword(), "GV"));
                else {
                    tch.setFirstName(adc.getFirstName());
                    tch.setLastName(adc.getLastName());
                    tch.setSex(adc.getGender());
                    tch.getAccountByAccount().setAccountId(adc.getAccount());
                    tch.getAccountByAccount().setPassword(adc.getPassword());
                    return tch;
                }
            }
            return null;
        });
        return dialog;
    }
}
