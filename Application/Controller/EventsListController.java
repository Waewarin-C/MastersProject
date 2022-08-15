package Application.Controller;

//Application.Controller for the Events view
//Events view will include
    //List of events for the selected date, week, month, or year
    //Option for user to select if they want day, week, month, or year?
    //Clicking on an event will display its information
        //See textbook cover example in java book
        //Edit event button
        //Delete event button

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventsListController implements Initializable, ParentController {

    @FXML
    private Label eventsListLabel, eventNameDetails, eventDateDetails, eventLocationDetails, eventCategoryDetails, eventDescriptionDetails;

    @FXML
    private ListView eventsListView;

    @FXML
    private GridPane topButtons;

    @FXML
    private AnchorPane eventDetails, addEvent, editEvent;

    private ParentController parentController;
    private AddEventController addEventController;
    //private EditEventController editEventController;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            FXMLLoader addEventLoader = new FXMLLoader(getClass().getResource("../View/EventDetails.fxml"));
            Node addEventPopUp = addEventLoader.load();
            addEvent.getChildren().add(addEventPopUp);

            addEventController = ((AddEventController)addEventLoader.getController());
            addEventController.setParentController(this);

            /*FXMLLoader editEventLoader = new FXMLLoader(getClass().getResource("../View/EditEvent.fxml"));
            Node editEventPopUp = editEventLoader.load();
            editEvent.getChildren().add(editEventPopUp);

            editEventController = ((EditEventController)editEventLoader.getController());
            editEventController.setParentController(this);*/
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        addEvent.setVisible(false);
        editEvent.setVisible(false);
    }

    public void setParentController(ParentController parentController)
    {
        this.parentController = parentController;
    }

    public void closePopUp(String neededString)
    {
        setEffect(null);
    }

    public void setEffect(Effect effect)
    {
        eventsListLabel.setEffect(effect);
        topButtons.setEffect(effect);
        eventsListView.setEffect(effect);
        eventDetails.setEffect(effect);
    }

    public void displayEvents(String date)
    {

    }

    public void addEvent()
    {

    }

    public void editEvent()
    {

    }

    public void closeListView()
    {
        parentController.closePopUp("");
    }


}
