package Controller;
import DAO.CurrentSemesterDAO;
import POJO.Currentsemester;
import POJO.Teacher;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TeacherDashboardController implements Initializable  {
    @FXML private Label identity;
    @FXML private Label currentSemesterLabel;
//    private Teacher currentTeacher;
    private CurrentUser currentUser = CurrentUser.getInstance();
    private Currentsemester curSem;
    FXMLLoader fxmlLoader = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        curSem = CurrentSemesterDAO.getCurrentSemester();
        currentSemesterLabel.setText(curSem.getId() + "/" + curSem.getYear() + "-" + (curSem.getYear() + 1));
    }
    public void setCurrentTeacher(Teacher tch, FXMLLoader fxmlLoader) {
        currentUser.setCurrentTeacher(tch);
        this.fxmlLoader = fxmlLoader;
        identity.setText("Xin chào, " + currentUser.getCurrentTeacher().getLastName());
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
    public void onSubject() {
        try {
            Thread.sleep(300);
            App.changeScene("TeacherSubject");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }
    @FXML
    public void onAccount() {
        try {
            Thread.sleep(300);
            App.changeScene("TeacherAccount");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void onClassName() {
        try {
            Thread.sleep(300);
            App.changeScene("TeacherAccount");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void onCourseManagement() {
        try {
            Thread.sleep(300);
            App.changeScene("Course");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void onCourseRegistration() {
        try {
            Thread.sleep(300);
            App.changeScene("CourseRegistration");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void onSemester() {
        try {
            Thread.sleep(300);
            App.changeScene("TeacherSemester");
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
        pc.setUser(currentUser.getCurrentTeacher());
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Profile");
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
