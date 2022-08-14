package Application.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class EventDetailsController implements Initializable {
    @FXML
    private Label eventNameDetails, eventDateDetails, eventLocationDetails, eventCategoryDetails, eventDescriptionDetails;
    private ParentController parentController;
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    public void setParentController(ParentController parentController)
    {
        this.parentController = parentController;
    }

    public void displayEventDetails(String date)
    {

    }

    public void closeEventDetails()
    {
        parentController.closePopUp("");
    }
}
