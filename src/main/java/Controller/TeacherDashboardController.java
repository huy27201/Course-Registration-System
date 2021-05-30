package Controller;
import POJO.Teacher;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import Main.App;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.io.IOException;

public class TeacherDashboardController {
    //@FXML private Label identity;

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
            App.changeScene("");
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
            App.changeScene("TeacherAccount");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void onCourseRegistration() {
        try {
            Thread.sleep(300);
            App.changeScene("TeacherAccount");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void onSemester() {
        try {
            Thread.sleep(300);
            App.changeScene("TeacherAccount");
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }
    }
    @FXML
    public void logoutClicked() throws IOException, InterruptedException {
        Thread.sleep(300);
        App.changeScene("Login");
    }
}
