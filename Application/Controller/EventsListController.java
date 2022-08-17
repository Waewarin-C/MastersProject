package Application.Controller;

//Application.Controller for the Events view
//Events view will include
    //List of events for the selected date, week, month, or year
    //Option for user to select if they want day, week, month, or year?
    //Clicking on an event will display its information
        //See textbook cover example in java book
        //Edit event button
        //Delete event button

import Application.Main;
import Application.Model.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EventsListController implements Initializable, ParentController {

    @FXML
    private Label eventsListLabel, selectMessage;

    @FXML
    private Label eventNameDetails, eventDateDetails, eventLocationDetails, eventCategoryDetails, eventDescriptionDetails;

    @FXML
    private Button addEventButton;

    @FXML
    private ListView<String> eventsListView;

    @FXML
    private GridPane topButtons, eventDetailsGrid;

    @FXML
    private AnchorPane eventDetails, manageEvent;

    private ParentController parentController;
    private ManageEventController manageEventController;

    private final GaussianBlur blur = new GaussianBlur();
    private List<Event> events;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            FXMLLoader addEventLoader = new FXMLLoader(getClass().getResource("../View/ManageEvent.fxml"));
            Node addEventPopUp = addEventLoader.load();
            manageEvent.getChildren().add(addEventPopUp);

            manageEventController = ((ManageEventController)addEventLoader.getController());
            manageEventController.setParentController(this);
            manageEventController.popUpSetUp(true);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        eventsListView.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        manageEvent.setVisible(false);
    }

    public void setParentController(ParentController parentController)
    {
        this.parentController = parentController;
    }

    public void closePopUp(String neededString)
    {
        manageEvent.setVisible(false);
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
        addEventButton.setText("Add Event for " + date);

        addEventsToList(date);

        eventDetailsGrid.setVisible(false);
    }

    public void displaySelectedEventDetails()
    {
        selectMessage.setVisible(false);

        int index = eventsListView.getSelectionModel().selectedIndexProperty().get();

        eventNameDetails.setText(events.get(index).getEventName());
        eventDateDetails.setText(events.get(index).getEventDate());
        eventLocationDetails.setText(events.get(index).getEventLocation());
        eventCategoryDetails.setText(events.get(index).getEventCategory());
        eventDescriptionDetails.setText(events.get(index).getEventDescription());

        eventDetailsGrid.setVisible(true);
    }

    public void addEvent()
    {
        setEffect(blur);
        manageEvent.setVisible(true);
    }

    public void editEvent()
    {
        setEffect(blur);

        List<String> eventDetails = new ArrayList<String>();
        eventDetails.add(eventNameDetails.getText());
        eventDetails.add(eventDateDetails.getText());
        eventDetails.add(eventLocationDetails.getText());
        eventDetails.add(eventCategoryDetails.getText());
        eventDetails.add(eventDescriptionDetails.getText());

        manageEventController.setUpForEdit(eventDetails);
        manageEvent.setVisible(true);
    }

    public void closeListView()
    {
        parentController.closePopUp("");
    }

    private void addEventsToList(String date)
    {
        eventsListView.getItems().clear();

        events = Main.login.getUser().getEvents().get(date);
        for(Event event : events)
        {
            eventsListView.getItems().add(event.getEventName());
        }
    }
}
