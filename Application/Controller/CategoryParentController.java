package Application.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;

interface CategoryParentController {

    public void closeAddCategory(String category);

    public void setEffect(Effect effect);
}