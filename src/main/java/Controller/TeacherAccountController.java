package Controller;
import DAO.AccountDAO;
import DAO.TeacherDAO;
import POJO.Teacher;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import Main.App;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


public class TeacherAccountController implements Initializable {
    @FXML TableView<Teacher> table;
    @FXML TableColumn<Teacher, String> col_id;
    @FXML TableColumn<Teacher, String> col_firstName;
    @FXML TableColumn<Teacher, String> col_lastName;
    @FXML TableColumn<Teacher, String> col_gender;
    @FXML TableColumn<Teacher, String> col_account;
    @FXML TableColumn<Teacher, String> col_password;
    ObservableList<Teacher> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Teacher> teacherList = TeacherDAO.getTeacherList();
        for (Teacher teacher : teacherList)
            list.add(teacher);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        col_gender.setCellValueFactory(new PropertyValueFactory<>("sex"));
        col_account.setCellValueFactory(new Callback<CellDataFeatures<Teacher,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Teacher, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getAccountByAccount().getAccountId());
            }
        });
        col_password.setCellValueFactory(new Callback<CellDataFeatures<Teacher,String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Teacher, String> data) {
                return new ReadOnlyStringWrapper(data.getValue().getAccountByAccount().getPassword());
            }
        });
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
        App.changeScene("TeacherDashboard");
    }
    @FXML
    public void onAdd() {
        Dialog<Teacher> addDialog = new Dialog<Teacher>();
    }
    @FXML
    public void onRemove() {
        Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION);
        confirmExit.setTitle("Delete");
        confirmExit.setContentText("Are you sure to want to delete?");
        confirmExit.setHeaderText(null);
        Optional<ButtonType> option = confirmExit.showAndWait();
        if (option.get() == ButtonType.OK) {
            Teacher selectedTch = table.getSelectionModel().getSelectedItem();
            table.getItems().remove(selectedTch);
            TeacherDAO.removeTeacherByID(selectedTch.getId());
            AccountDAO.removeAccountByID(selectedTch.getAccountByAccount().getAccountId());
        }
    }
}
