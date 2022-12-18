package Application.Controller;

//Application.Controller for the Add Event view
//Add Event view will include
    //Date picker
    //Name of event
    //Category of event (maybe a dropdown or radio button)
        //Adding a new category will pop up an Add Category view
    //Location
    //Description
    //Save event button
    //Cancel button

import Application.Main;
import Application.Model.Event;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ManageEventController implements Initializable, ParentController {

    @FXML
    private Button saveManageEventButton, cancelManageEventButton, doneManageEventButton, addNewEventCategoryButton;

    @FXML
    private TextField eventNameField, eventLocationField;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private ComboBox eventCategoryField;

    @FXML
    private TextArea eventDescriptionField;

    @FXML
    private Label manageEventPageLabel, saveEventMessage, newCategory;

    @FXML
    private GridPane manageEventButtons, manageEventGridPane;

    @FXML
    private AnchorPane anchorPane, addCategoryPopUp;

    @FXML
    private Pane toolbarPane;

    private boolean isPopUp = false;
    private boolean isEdit = false;
    private String date;
    private int eventListSize;
    private int eventIndex;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yy");
    private ParentController parentController;
    private AddCategoryPopUpController popUpController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try
        {
            Node toolbar = FXMLLoader.load(getClass().getResource("../View/Toolbar.fxml"));
            toolbarPane.getChildren().add(toolbar);

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

        doneManageEventButton.setVisible(false);
        addCategoryPopUp.setVisible(false);

        eventDatePicker.setStyle("-fx-font: 14px \"Berlin Sans FB\";");

        //TODO: update this into its own method for getting current list of categories
        updateCategoriesList();

        saveEventMessage.setText("");
    }

    public void setParentController(ParentController parentController)
    {
        this.parentController = parentController;
    }

    //public void setDate(String date)
    //{
      //  this.date = date;
    //}

    public void setEventListSize(int eventListSize)
    {
        this.eventListSize = eventListSize;
    }

    public void popUpSetUp(boolean isPopUp)
    {
        this.isPopUp = isPopUp;

        manageEventGridPane.setLayoutX(66);
        manageEventButtons.setLayoutX(520);
        eventDescriptionField.setPrefHeight(100);
        anchorPane.getChildren().remove(toolbarPane);
        anchorPane.setPrefSize(700, 450);
    }

    public void prefillDate(String date)
    {
        eventDatePicker.setValue(LocalDate.parse(date, this.format));
        eventDatePicker.setDisable(true);
    }

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
            return;
        }

        List<String> event = new ArrayList<String>();
        event.add(eventName);
        event.add(eventDate);
        event.add(eventLocation);
        event.add(eventCategory);
        event.add(eventDescription);

        saveEventToUser(event);
        saveToFile(false);

        resetFields();

        if(isPopUp)
        {
            saveManageEventButton.setVisible(false);
            cancelManageEventButton.setVisible(false);
            doneManageEventButton.setVisible(true);
        }
    }

    public void cancelManageEvent()
    {
        resetFields();

        if(this.isPopUp)
        {
            resetForPopUp();
            this.parentController.closePopUp("");
        }
    }

    public void doneManageEvent()
    {
        resetForPopUp();
        this.parentController.closePopUp("");
    }

    public void addNewEventCategory()
    {
        GaussianBlur blur = new GaussianBlur();
        setEffect(blur);

        popUpController.setUp();
        addCategoryPopUp.setVisible(true);
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
        toolbarPane.setEffect(effect);
    }

    public boolean saveToFile(boolean isDelete)
    {
        String fileName = "Account/" + Main.login.getUser().getUsername() + "_events.csv";

        try
        {
            FileWriter file = new FileWriter(new File(fileName));;
            file.write(String.format("%s,%s,%s,%s,%s\n", "Event", "Date", "Location", "Category", "Description"));

            Set<String> eventDates = Main.login.getUser().getEvents().keySet();
            for(String date : eventDates)
            {
                List<Event> eventsList = Main.login.getUser().getEvents().get(date);

                for(Event event : eventsList)
                {
                    String eventName = event.getEventName();
                    String eventDate = event.getEventDate();
                    String eventLocation = event.getEventLocation();
                    String eventCategory = event.getEventCategory();
                    String eventDescription = event.getEventDescription();

                    file.write(String.format("%s,%s,%s,%s,%s\n", eventName, eventDate, eventLocation, eventCategory, eventDescription));
                }
            }

            file.close();
            saveEventMessage.setText("Saved successfully!");

            if(isDelete)
            {
                return true;
            }
        }
        catch (IOException e)
        {
            saveEventMessage.setText("Error: something went wrong, please try again");
            saveEventMessage.setTextFill(Color.rgb(255, 0, 0));
            System.out.println("Error: unable to save the event");
            e.printStackTrace();

            if(isDelete)
            {
                return false;
            }
        }

        return true;
    }

    private void updateCategoriesList()
    {
        List<String> categories = new ArrayList<String>(Main.login.getUser().getCategories().keySet());
        Collections.sort(categories);
        eventCategoryField.getItems().addAll(categories);
        eventCategoryField.setStyle("-fx-font: 14px \"Berlin Sans FB\";");

        newCategory.setText("");
        newCategory.setVisible(false);
    }

    private String formatDate()
    {
        LocalDate date = eventDatePicker.getValue();

        if(date == null)
        {
            return "";
        }
        return date.format(this.format);
    }

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
            Main.login.getUser().addEvent(newEvent, this.isEdit, this.date, this.eventIndex);
        }
        else
        {
            Main.login.getUser().addEvent(newEvent, this.isEdit, this.date, this.eventListSize);
        }
    }

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

    private void resetForPopUp()
    {
        manageEventPageLabel.setText("Add Event");
        saveManageEventButton.setVisible(true);
        cancelManageEventButton.setVisible(true);
        doneManageEventButton.setVisible(false);
        saveEventMessage.setText("");
    }
}
