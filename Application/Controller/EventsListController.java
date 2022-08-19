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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    private Label eventsListLabel, selectMessage, deleteFailMessage;

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
    private String date;
    private List<Event> events;
    private int eventIndex;

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

        eventDetailsGrid.setVisible(false);
        manageEvent.setVisible(false);
    }

    public void setParentController(ParentController parentController)
    {
        this.parentController = parentController;
    }

    public void setDate(String date)
    {
        this.date = date;
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

    public void displayEvents()
    {
        addEventButton.setText("Add Event for " + this.date);

        addEventsToList(this.date);
    }

    public void displaySelectedEventDetails()
    {
        selectMessage.setVisible(false);

        this.eventIndex = eventsListView.getSelectionModel().selectedIndexProperty().get();

        eventNameDetails.setText(this.events.get(this.eventIndex).getEventName());
        eventDateDetails.setText(this.events.get(this.eventIndex).getEventDate());
        eventLocationDetails.setText(this.events.get(this.eventIndex).getEventLocation());
        eventCategoryDetails.setText(this.events.get(this.eventIndex).getEventCategory());
        eventDescriptionDetails.setText(this.events.get(this.eventIndex).getEventDescription());

        deleteFailMessage.setVisible(false);
        eventDetailsGrid.setVisible(true);
    }

    public void addEvent()
    {
        setEffect(this.blur);
        manageEvent.setVisible(true);
    }

    public void editEvent()
    {
        setEffect(this.blur);

        List<String> eventDetails = new ArrayList<String>();
        eventDetails.add(eventNameDetails.getText());
        eventDetails.add(eventDateDetails.getText());
        eventDetails.add(eventLocationDetails.getText());
        eventDetails.add(eventCategoryDetails.getText());
        eventDetails.add(eventDescriptionDetails.getText());

        manageEventController.editSetUp(eventDetails, date, eventIndex);
        manageEvent.setVisible(true);
    }

    public void deleteEvent()
    {
        String date = eventDateDetails.getText();
        int index = eventsListView.getSelectionModel().selectedIndexProperty().get();
        Main.login.getUser().deleteEvent(date, index);

        boolean isDeleted = manageEventController.saveToFile(true);
        if(isDeleted)
        {
            eventsListView.getSelectionModel().select(null);
            eventDetailsGrid.setVisible(false);
            selectMessage.setVisible(true);
            displayEvents();
        }
        else
        {
            deleteFailMessage.setVisible(true);
        }
    }

    public void closeListView()
    {
        this.parentController.closePopUp("");
    }

    private void addEventsToList(String date)
    {
        eventsListView.getItems().clear();
        System.out.println("Listview size: " + eventsListView.getItems().size());
        this.events = Main.login.getUser().getEvents().get(date);
        System.out.println(this.events.size());
        for(Event event : this.events)
        {
            eventsListView.getItems().add(event.getEventName());
        }
    }
}
