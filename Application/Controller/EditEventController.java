package Application.Controller;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EditEventController implements Initializable {

    private ParentController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    public void setParentController(ParentController parentController)
    {
        this.parentController = parentController;
    }
}
