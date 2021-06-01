package Main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.Optional;

public class App extends Application {
    private static Scene scene;
    private static Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        Parent root = loadFXML("TeacherSubject");
        scene = new Scene(root);
        root.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });
        root.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - xOffset);
            stage.setY(mouseEvent.getScreenY() - yOffset);
        });
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.show();

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
    }

    public static void exit() {
        Alert confirmExit = new Alert(Alert.AlertType.CONFIRMATION);
        confirmExit.setTitle("Exit");
        confirmExit.setHeaderText(null);
        confirmExit.setContentText("Bạn có chắc chắn muốn thoát?");
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
}