package Application.Controller;

import Application.Main;
import Application.Model.Event;
import javafx.collections.ObservableList;
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
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The EventsListController interacts with the EventsList.fxml file
 * It displays the list of events for a specific date. The user can
 * add, edit, and delete events for the date.
 *
 * @author Waewarin Chindarassami
 */

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
    private AnchorPane anchorPane, eventDetails, manageEvents, deleteEventConfirmation;

    private ParentController parentController;
    private ManageEventsController manageEventsController;

    private final GaussianBlur blur = new GaussianBlur();
    private final String theme = Main.login.getUser().getTheme();
    private String date;
    private List<Event> events;
    private int eventIndex = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            FXMLLoader addEventLoader = new FXMLLoader(getClass().getResource("../View/ManageEvents.fxml"));
            Node addEventPopUp = addEventLoader.load();
            manageEvents.getChildren().add(addEventPopUp);

            this.manageEventsController = ((ManageEventsController)addEventLoader.getController());
            this.manageEventsController.setParentController(this);
            this.manageEventsController.popUpSetUp(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        setStyleFromTheme();
        eventsListView.setStyle("-fx-font: 14px \"Berlin Sans FB\";");

        eventDetailsGrid.setVisible(false);
        manageEvents.setVisible(false);
        deleteEventConfirmation.setVisible(false);
    }

    public void closePopUp(String neededString)
    {
        manageEvents.setVisible(false);
        displayEvents();

        if(this.eventIndex >= 0)
        {
            eventsListView.getSelectionModel().select(this.eventIndex);
            displaySelectedEventDetails();
        }

        setEffect(null);
    }

    public void setEffect(Effect effect)
    {
        eventsListLabel.setEffect(effect);
        topButtons.setEffect(effect);
        eventsListView.setEffect(effect);
        eventDetails.setEffect(effect);
    }

    /**
     * Sets the controller class of the view that this EventsList will pop up on to be
     * the parent of this controller class. The parent controller can either be HomeController
     * or CalendarController.
     *
     * @param parentController ParentController - controller of the view this will pop up on
     */
    public void setParentController(ParentController parentController)
    {
        this.parentController = parentController;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    /**
     * Displays the events for the selected date
     */
    public void displayEvents()
    {
        eventsListLabel.setText("Events for " + this.date);

        addEventButton.setText("Add Event for " + this.date);
        addEventsToListView(this.date);

        selectMessage.setVisible(true);
        eventDetailsGrid.setVisible(false);
    }

    /**
     * Display the details of the specific event selected
     */
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

    /**
     * Add an event, the date field will already be prefilled with the date of the
     * events list
     */
    public void addEvent()
    {
        setEffect(this.blur);
        this.manageEventsController.prefillDate(this.date);
        manageEvents.setVisible(true);
    }

    /**
     * Edit the selected event
     */
    public void editEvent()
    {
        setEffect(this.blur);

        List<String> eventDetails = new ArrayList<String>();
        eventDetails.add(eventNameDetails.getText());
        eventDetails.add(eventDateDetails.getText());
        eventDetails.add(eventLocationDetails.getText());
        eventDetails.add(eventCategoryDetails.getText());
        eventDetails.add(eventDescriptionDetails.getText());

        this.manageEventsController.editSetUp(eventDetails, this.eventIndex);
        manageEvents.setVisible(true);
    }

    /**
     * Confirm that the user actually wants to delete the event
     */
    public void confirmDeleteEvent()
    {
        setEffect(this.blur);
        deleteEventConfirmation.setVisible(true);
    }

    /**
     * The user cancels the deletion of the event
     */
    public void cancelConfirmDeleteEvent()
    {
        deleteEventConfirmation.setVisible(false);
        setEffect(null);
    }

    /**
     * Delete the selected event
     */
    public void deleteEvent()
    {
        String date = eventDateDetails.getText();
        int index = eventsListView.getSelectionModel().selectedIndexProperty().get();
        Main.login.getUser().deleteEvent(date, index);

        if(Main.login.getUser().saveEventToFile())
        {
            eventsListView.getSelectionModel().select(null);
            this.eventIndex = -1;
            eventDetailsGrid.setVisible(false);
            selectMessage.setVisible(true);
            displayEvents();
        }
        else
        {
            deleteFailMessage.setVisible(true);
        }

        deleteEventConfirmation.setVisible(false);
        setEffect(null);
    }

    /**
     * Closes the events list view
     * Calls the parent controller's closePopUp() method to close this view
     */
    public void closeListView()
    {
        this.parentController.closePopUp("");
    }

    /*
     * Sets the style of this view based on the theme
     */
    private void setStyleFromTheme()
    {
        Color color = getColorFromTheme();

        eventsListLabel.setTextFill(color);
        selectMessage.setTextFill(color);

        ObservableList<Node> detailsChildren = eventDetailsGrid.getChildren();
        for(Node detailsChild : detailsChildren)
        {
            if(detailsChild.getClass().getSimpleName().equals("Label"))
            {
                ((Label)detailsChild).setTextFill(color);
            }
        }

        ObservableList<Node> confirmChildren = deleteEventConfirmation.getChildren();
        for(Node confirmChild : confirmChildren)
        {
            if(confirmChild.getClass().getSimpleName().equals("Label"))
            {
                ((Label)confirmChild).setTextFill(color);
            }
        }
    }

    /*
     * Sets the anchorPane style based on the theme
     * Gets the color of the labels based on the theme
     */
    private Color getColorFromTheme()
    {
        if(this.theme.equals("Light"))
        {
            anchorPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: black; -fx-border-radius: 20; -fx-border-width: 2;");
            eventDetails.setStyle("-fx-background-color: white;");
            deleteEventConfirmation.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: black; -fx-border-radius: 20; -fx-border-width: 2;");
            return Color.BLACK;
        }
        else
        {
            anchorPane.setStyle("-fx-background-color: #31323e; -fx-background-radius: 20; -fx-border-color: white; -fx-border-radius: 20; -fx-border-width: 2;");
            eventDetails.setStyle("-fx-background-color: #31323e;");
            deleteEventConfirmation.setStyle("-fx-background-color: #31323e; -fx-background-radius: 20; -fx-border-color: white; -fx-border-radius: 20; -fx-border-width: 2;");
            return Color.WHITE;
        }
    }

    /*
     * Add the date's events to the list view to be displayed
     */
    private void addEventsToListView(String date)
    {
        eventsListView.getItems().clear();

        this.events = Main.login.getUser().getEvents().get(date);
        if(this.events != null)
        {
            for(Event event : this.events)
            {
                eventsListView.getItems().add(event.getEventName());
            }
        }
    }
}
