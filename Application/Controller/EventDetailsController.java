package Application.Controller;

import Application.Main;
import Application.Model.Event;
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
        Event event = Main.login.getUser().getEvents().get(date).get(0);
        String eventName = event.getEventName();
        String eventLocation = event.getEventLocation();
        String eventCategory = event.getEventCategory();
        String eventDescription = event.getEventDescription();

        eventNameDetails.setText(eventName);
        eventDateDetails.setText(date);
        eventLocationDetails.setText(eventLocation);
        eventCategoryDetails.setText(eventCategory);
        eventDescriptionDetails.setText(eventDescription);
    }

    public void closeEventDetails()
    {
        parentController.closePopUp("");
    }
}
