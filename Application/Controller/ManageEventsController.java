package Application.Controller;

import Application.Main;
import Application.Model.Event;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * The ManageEventController interacts with the ManageEvents.fxml file.
 * When the view is the main page, it allows the user to add events.
 * When the view is a popup, it allows the user to add and edit events.
 * The user can also add a new category when adding or editing events.
 *
 * @author Waewarin Chindarassami
 */

public class ManageEventsController implements Initializable, ParentController {
    @FXML
    private Button saveManageEventButton, cancelManageEventButton, doneManageEventButton, addNewEventCategoryButton;

    @FXML
    private TextField eventNameField, eventLocationField, eventDescriptionField;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private ComboBox eventCategoryField;

    @FXML
    private Label manageEventPageLabel, saveEventMessage, orLabel, newCategory;

    @FXML
    private GridPane manageEventButtons, manageEventGridPane;

    @FXML
    private AnchorPane anchorPane, addCategoryPopUp;

    @FXML
    private Pane navigationPane;

    private boolean isPopUp = false;
    private boolean isEdit = false;
    private String date;
    private int eventIndex;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern(Main.login.getUser().getDateFormat());
    private ParentController parentController;
    private AddCategoryPopUpController popUpController;
    private final GaussianBlur blur = new GaussianBlur();
    private final String theme = Main.login.getUser().getTheme();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try
        {
            Node navigation = FXMLLoader.load(getClass().getResource("../View/Navigation.fxml"));
            navigationPane.getChildren().add(navigation);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/AddCategoryPopUp.fxml"));
            Node popUp = loader.load();
            addCategoryPopUp.getChildren().add(popUp);

            this.popUpController = ((AddCategoryPopUpController)loader.getController());
            this.popUpController.setParentController(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        setStyleFromTheme();
        eventDatePicker.setStyle("-fx-font: 14px \"Berlin Sans FB\";");

        doneManageEventButton.setVisible(false);
        addCategoryPopUp.setVisible(false);

        updateCategoriesList();

        saveEventMessage.setText("");
    }

    public void closePopUp(String category)
    {
        addCategoryPopUp.setVisible(false);
        if(category.length() > 0)
        {
            addNewEventCategoryButton.setVisible(false);
            newCategory.setText(category);
            newCategory.setVisible(true);
        }

        setEffect(null);
    }

    public void setEffect(Effect effect)
    {
        manageEventPageLabel.setEffect(effect);
        manageEventGridPane.setEffect(effect);
        manageEventButtons.setEffect(effect);
        navigationPane.setEffect(effect);
    }

    /**
     * Sets the controller class of the view that this ManageEvent will pop up on to be
     * the parent of this controller class. The parent controller can either be CalendarController
     * or EventsListController.
     *
     * @param parentController ParentController - controller of the view this will pop up on
     */
    public void setParentController(ParentController parentController)
    {
        this.parentController = parentController;
    }

    /**
     * Sets up the view for when it is a popup
     *
     * @param isPopUp boolean - if the view is going to a popup
     */
    public void popUpSetUp(boolean isPopUp)
    {
        this.isPopUp = isPopUp;

        manageEventGridPane.setLayoutX(66);
        manageEventButtons.setLayoutX(520);
        eventDescriptionField.setPrefHeight(100);
        addCategoryPopUp.setLayoutX(150);
        addCategoryPopUp.setLayoutY(90);
        anchorPane.getChildren().remove(navigationPane);
        anchorPane.setPrefSize(700, 400);
        setStyleFromTheme();
    }

    /**
     * Prefill the date field with the selected date for adding an event
     * on that date
     *
     * @param date String - selected date
     */
    public void prefillDate(String date)
    {
        eventDatePicker.setValue(LocalDate.parse(date, this.format));
        eventDatePicker.setDisable(true);
    }

    /**
     * Set up the view for editing an event
     *
     * @param eventDetails List<String> - the details of the event
     * @param eventIndex int - the index the event is at in the list
     */
    public void editSetUp(List<String> eventDetails, int eventIndex)
    {
        this.isEdit = true;
        this.eventIndex = eventIndex;
        this.date = eventDetails.get(1);

        manageEventPageLabel.setText("Edit Event");
        saveEventMessage.setText("");

        eventNameField.setText(eventDetails.get(0));
        eventDatePicker.setValue(LocalDate.parse(eventDetails.get(1), this.format));
        eventDatePicker.setDisable(false);
        eventLocationField.setText(eventDetails.get(2));
        eventCategoryField.setValue(eventDetails.get(3));
        eventDescriptionField.setText(eventDetails.get(4));
    }

    /**
     * Saves the event, whether is adding or editing an event
     */
    public void saveManageEvent()
    {
        String eventName = eventNameField.getText();
        String eventDate = formatDate();
        String eventLocation = eventLocationField.getText();
        String eventDescription = eventDescriptionField.getText();
        String eventCategory = "";
        if(eventCategoryField.getValue() != null)
        {
            eventCategory = eventCategoryField.getValue().toString();
        }
        else
        {
            eventCategory = newCategory.getText();
        }

        boolean emptyField = eventName.equals("") || eventDate.equals("") || eventLocation.equals("") || eventCategory.equals("") || eventDescription.equals("");
        if(emptyField)
        {
            saveEventMessage.setText("One or more fields is empty");
            saveEventMessage.setTextFill(Color.RED);
            return;
        }

        List<String> event = new ArrayList<String>();
        event.add(eventName);
        event.add(eventDate);
        event.add(eventLocation);
        event.add(eventCategory);
        event.add(eventDescription);

        saveEventToUser(event);
        if(Main.login.getUser().saveEventToFile())
        {
            saveEventMessage.setText("Saved successfully!");
            saveEventMessage.setTextFill(getColorFromTheme());
        }
        else
        {
            saveEventMessage.setText("Error: something went wrong, please try again");
            saveEventMessage.setTextFill(Color.RED);
        }

        resetFields();

        if(isPopUp)
        {
            this.isEdit = false;
            saveManageEventButton.setVisible(false);
            cancelManageEventButton.setVisible(false);
            doneManageEventButton.setVisible(true);
        }
    }

    /**
     * Cancels adding or editing an event and resets the field accordingly
     * If it is a popup, it will call the parent controller's closePopUp()
     * method to close this view
     */
    public void cancelManageEvent()
    {
        resetFields();

        if(this.isPopUp)
        {
            resetForPopUp();
            this.parentController.closePopUp("");
        }
    }

    /**
     * Done adding or editing an event in the popup version of the view
     * Calls the parent controller's closePopUp() method to close this view
     */
    public void doneManageEvent()
    {
        resetForPopUp();
        this.parentController.closePopUp("");
    }

    /**
     * Add a new category when adding or editing an event
     * The add category pop up will appear
     */
    public void addNewEventCategory()
    {
        setEffect(this.blur);

        popUpController.setUp();
        addCategoryPopUp.setVisible(true);
    }

    /*
     * Sets the style of this view based on the theme
     */
    private void setStyleFromTheme()
    {
        Color color = getColorFromTheme();

        manageEventPageLabel.setTextFill(color);
        orLabel.setTextFill(color);
        newCategory.setTextFill(color);

        ObservableList<Node> children = manageEventGridPane.getChildren();
        for(Node child : children)
        {
            if(child.getClass().getSimpleName().equals("Label"))
            {
                ((Label)child).setTextFill(color);
            }
        }
    }

    /*
     * Sets the anchorPane style based on the theme and if it is a popup
     * Gets the color of the labels based on the theme
     */
    private Color getColorFromTheme()
    {
        if(this.theme.equals("Light"))
        {
            if (isPopUp)
            {
                anchorPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: black; -fx-border-radius: 20; -fx-border-width: 2;");
            }
            else
            {
                anchorPane.setStyle("-fx-background-color: white;");
            }

            return Color.BLACK;
        }
        else
        {
            if(isPopUp)
            {
                anchorPane.setStyle("-fx-background-color: #31323e; -fx-background-radius: 20; -fx-border-color: white; -fx-border-radius: 20; -fx-border-width: 2;");
            }
            else
            {
                anchorPane.setStyle("-fx-background-color: #31323e;");
            }

            return Color.WHITE;
        }
    }

    /*
     * Update the categories list to have the current version display in the combobox
     */
    private void updateCategoriesList()
    {
        List<String> categories = new ArrayList<String>(Main.login.getUser().getCategories().keySet());

        eventCategoryField.getItems().clear();
        eventCategoryField.getItems().addAll(categories);
        eventCategoryField.setStyle("-fx-font: 14px \"Berlin Sans FB\";");

        newCategory.setText("");
        newCategory.setVisible(false);
    }

    /*
     * Format the date from the date field to be the format that matches the
     * user's settings
     */
    private String formatDate()
    {
        LocalDate date = eventDatePicker.getValue();

        if(date == null)
        {
            return "";
        }
        return date.format(this.format);
    }

    /*
     * Saves the event to User
     */
    private void saveEventToUser(List<String> event)
    {
        String eventName = event.get(0);
        String eventDate = event.get(1);
        String eventLocation = event.get(2);
        String eventCategory = event.get(3);
        String eventDescription = event.get(4);

        Event newEvent = new Event(eventName, eventDate, eventLocation, eventCategory, eventDescription);

        if(this.isEdit)
        {
            Main.login.getUser().editEvent(newEvent, this.date, this.eventIndex);
        }
        else
        {
            Main.login.getUser().addEvent(newEvent);
        }
    }

    /*
     * Resets all the fields
     */
    private void resetFields()
    {
        eventNameField.clear();
        eventDatePicker.setValue(null);
        eventLocationField.clear();

        eventCategoryField.getSelectionModel().clearSelection();
        updateCategoriesList();
        addNewEventCategoryButton.setVisible(true);

        eventDescriptionField.clear();
    }

    /*
     * Resets view for the popup version
     */
    private void resetForPopUp()
    {
        this.isEdit = false;
        manageEventPageLabel.setText("Add Event");
        saveManageEventButton.setVisible(true);
        cancelManageEventButton.setVisible(true);
        doneManageEventButton.setVisible(false);
        saveEventMessage.setText("");
    }
}
