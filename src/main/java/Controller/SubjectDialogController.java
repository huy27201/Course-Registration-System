package Controller;


import POJO.Subject;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;

public class SubjectDialogController {
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private TextField credits;

    public void setInfo(Subject sub) {
        this.id.setText(sub.getId());
        this.name.setText(sub.getName());
        this.credits.setText(sub.getCredits().toString());
    }

    @FXML
    public String getId() {
        return id.getText();
    }

    @FXML
    public String getName() {
        return name.getText();
    }

    @FXML
    public Integer getCredits() {
        try {
            Integer res = Integer.parseInt(credits.getText());
            return res;
        } catch (NumberFormatException e) {
            return null;
        }

    }

    @FXML
    public void setEditable(boolean b) {
        id.getStyleClass().add("non-editable");
        id.setEditable(b);
    }
}
