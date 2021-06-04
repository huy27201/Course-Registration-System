package Controller;


import DAO.SubjectDAO;
import POJO.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import Main.App;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class TeacherSubjectController implements Initializable {
    @FXML
    private TextField searchBar;
    @FXML
    TableView<Subject> table;
    @FXML
    TableColumn<Subject, String> col_id;
    @FXML
    TableColumn<Subject, String> col_name;
    @FXML
    TableColumn<Subject, Integer> col_credits;
    ObservableList<Subject> list = FXCollections.observableArrayList();
    FilteredList<Subject> filterList = new FilteredList<>(list);

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
        Dialog<Subject> dialog = newDialog(null);
        Optional<Subject> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (SubjectDAO.addSubject(result.get()))
                table.getItems().add(result.get());
        }
    }

    @FXML
    public void onRemove() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION);
            confirmExit.setTitle("Delete");
            confirmExit.setContentText("Bạn có chắc chắn muốn xóa môn học này không? Các học phần chứa môn học này có thể bị xóa theo!!");
            confirmExit.setHeaderText(null);
            Optional<ButtonType> option = confirmExit.showAndWait();
            if (option.get() == ButtonType.OK) {
                Subject selectedSub = table.getSelectionModel().getSelectedItem();
                table.getItems().remove(selectedSub);
                SubjectDAO.removeSubjectByID(selectedSub.getId());
            }
        } else {
            Alert confirmExit = new Alert(Alert.AlertType.WARNING);
            confirmExit.setTitle("Warning");
            confirmExit.setContentText("Vui lòng chọn môn học cần xóa!!");
            confirmExit.setHeaderText(null);
            confirmExit.showAndWait();
        }
    }

    @FXML
    public void onSearch() {
        String data = searchBar.getText().toLowerCase();
        filterList.setPredicate(
                subject -> {
                    if (data == null || data.isEmpty()) return true;
                    if (subject.getId().toLowerCase().contains(data)) return true;
                    if (subject.getName().toLowerCase().contains(data)) return true;
                    return false;
                }
        );
        table.setItems(filterList);
    }

    @FXML
    void onUpdate(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            Subject sub = table.getSelectionModel().getSelectedItem();
            Dialog<Subject> dialog = newDialog(sub);
            Optional<Subject> result = dialog.showAndWait();
            if (result.isPresent()) {
                sub = result.get();
                SubjectDAO.updateSubject(sub);
                table.refresh();
            }
        }
    }

    public Dialog<Subject> newDialog(Subject sub) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/SubjectDialog.fxml"));
        DialogPane content = fxmlLoader.load();
        Dialog<Subject> dialog = new Dialog<>();
        dialog.setTitle("Thông tin môn học");
        dialog.setDialogPane(content);
        SubjectDialogController sdc = fxmlLoader.getController();
        if (sub != null) {
            sdc.setInfo(sub);
            sdc.setDisable(true);
        }
        Button btn = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
        btn.addEventFilter(ActionEvent.ACTION, event -> {
            if (sdc.getId().equals("") || sdc.getName().equals("") || sdc.getCredits() == null) {
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
                if (sub == null)
                    return new Subject(sdc.getId(), sdc.getName(), sdc.getCredits());
                else {
                    sub.setName(sdc.getName());
                    sub.setCredits(Integer.valueOf(sdc.getCredits()));
                    return sub;
                }
            }
            return null;
        });
        return dialog;
    }
}
