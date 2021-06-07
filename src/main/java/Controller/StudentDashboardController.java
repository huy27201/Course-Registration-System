package Controller;

import DAO.CurrentSemesterDAO;
import POJO.Currentsemester;
import POJO.Student;
import javafx.animation.ScaleTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import Main.App;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StudentDashboardController implements Initializable {
    @FXML
    private Label identity;
    @FXML
    private Label currentSemesterLabel;
    private CurrentUser currentUser = CurrentUser.getInstance();
    private Currentsemester curSem;
    FXMLLoader fxmlLoader = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        curSem = CurrentSemesterDAO.getCurrentSemester();
        currentSemesterLabel.setText(curSem.getId() + "/" + curSem.getYear() + "-" + (curSem.getYear() + 1));
        identity.setText("Xin chào, " + currentUser.getCurrentStudent().getLastName());

    }

    public void setCurrentStudent(Student stu, FXMLLoader fxmlLoader) {
        App.setUser(stu);
        this.fxmlLoader = fxmlLoader;
        identity.setText("Xin chào, " + currentUser.getCurrentStudent().getLastName());
    }

    @FXML
    public void hoverTab(MouseEvent event) {
        AnchorPane ap = (AnchorPane) event.getSource();
        ScaleTransition st = new ScaleTransition(Duration.millis(100), ap);
        st.setFromX(1);
        st.setFromY(1);
        st.setToX(1.1);
        st.setToY(1.1);
        st.play();
    }

    public void unHoverTab(MouseEvent event) {
        AnchorPane ap = (AnchorPane) event.getSource();
        ScaleTransition st = new ScaleTransition(Duration.millis(100), ap);
        st.setFromX(1.1);
        st.setFromY(1.1);
        st.setToX(1);
        st.setToY(1);
        st.play();
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
    public void onCourseResult() {
        try {
            Thread.sleep(300);
            App.changeScene("CourseResult");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void onCourseAttend() {
        try {
            Thread.sleep(300);
            App.changeScene("StudentAttend");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void logoutClicked() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("Login");
    }

    @FXML
    public void onProfile(Event event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/Profile.fxml"));
        Parent root = fxmlLoader.load();
        ProfileController pc = fxmlLoader.getController();
        pc.setUser(currentUser.getCurrentStudent());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.getIcons().add(new Image(this.getClass().getResource("/assets/img/SchoolLogo.png").toString()));
        stage.setResizable(false);
        stage.setTitle("Profile");
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
