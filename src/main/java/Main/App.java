package Main;

import Controller.CurrentUser;
import POJO.Student;
import POJO.Teacher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Optional;

public class App extends Application {
    private static Scene scene;
    private static Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;
    private static CurrentUser currentUser = CurrentUser.getInstance();
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        Parent root = loadFXML("Login");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

        HibernateMain.connectHibernate();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/Controller/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void changeScene(String fxml) throws IOException {
        scene = new Scene(loadFXML(fxml));
        stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void exit() {
        Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION);
        confirmExit.setTitle("Exit");
        confirmExit.setHeaderText(null);
        confirmExit.setContentText("B???n c?? ch???c ch???n mu???n tho??t?");
        Optional<ButtonType> option = confirmExit.showAndWait();
        if (option.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public static void minimize() {
        stage.setIconified(true);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setUser(Teacher tch) {
        currentUser.setCurrentTeacher(tch);
    }
    public static Teacher getCurrentTeacher() {
        return currentUser.getCurrentTeacher();
    }
    public static void setUser(Student stu) {
        currentUser.setCurrentStudent(stu);
    }
    public static Student getCurrentStudent() {
        return currentUser.getCurrentStudent();
    }
}