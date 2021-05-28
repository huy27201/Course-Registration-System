package Controller;
import DAO.SubjectDAO;
import POJO.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import Main.App;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TeacherSubjectController<VBOX> implements Initializable {
    @FXML
    TableView<Subject> table;
    @FXML TableColumn<Subject, String> col_id;
    @FXML TableColumn<Subject, String> col_name;
    @FXML TableColumn<Subject, Integer> col_credits;
    ObservableList<Subject> list = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Subject> subjectList = SubjectDAO.getSubjectList();
        for (Subject subject : subjectList)
            list.add(subject);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_credits.setCellValueFactory(new PropertyValueFactory<>("credits"));
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
        Dialog<Subject> dialog = new Dialog<>();
        dialog.setTitle("Add a subject");
        dialog.setResizable(false);
        dialog.setHeaderText(null);
        VBox box = new VBox();
        TextField idField = new TextField();
        idField.setPromptText("Nhập Mã môn học");
        TextField nameField = new TextField();
        nameField.setPromptText("Nhập tên môn học");
        TextField creditsField = new TextField();
        creditsField.setPromptText("Nhập số tin chỉ");
        box.getChildren().addAll(idField, nameField, creditsField);
        dialog.getDialogPane().setContent(box);
        ButtonType buttonTypeOk = new ButtonType("Add");
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        Optional<Subject> result = dialog.showAndWait();
        result.ifPresent(res -> {

        });
    }
    @FXML
    public void onRemove() {
        Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION);
        confirmExit.setTitle("Delete");
        confirmExit.setContentText("Are you sure to want to delete?");
        confirmExit.setHeaderText(null);
        Optional<ButtonType> option = confirmExit.showAndWait();
        if (option.get() == ButtonType.OK) {
            Subject selectedSub = table.getSelectionModel().getSelectedItem();
            table.getItems().remove(selectedSub);
            SubjectDAO.removeSubjectByID(selectedSub.getId());
        }
    }
}
