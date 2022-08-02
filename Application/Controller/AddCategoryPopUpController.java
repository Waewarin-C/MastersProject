package Application.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AddCategoryPopUpController {
    @FXML
    Button cancel;

    public void cancel()
    {
        AddEventController.cancelAddCategory();
    }
}
