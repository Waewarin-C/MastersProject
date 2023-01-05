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

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ManageEventController implements Initializable, ParentController {
    @FXML
    private Button saveManageEventButton, cancelManageEventButton, doneManageEventButton, addNewEventCategoryButton;

    @FXML
    private TextField eventNameField, eventLocationField, eventDescriptionField;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private ComboBox eventCategoryField;

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
    private DateTimeFormatter format = DateTimeFormatter.ofPattern(Main.login.getUser().getDateFormat());
    private ParentController parentController;
    private AddCategoryPopUpController popUpController;
    private final GaussianBlur blur = new GaussianBlur();

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

        updateCategoriesList();

        saveEventMessage.setText("");

        eventDatePicker.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        anchorPane.setStyle("-fx-background-color: white;");
        anchorPane.getStylesheets().add(getClass().getResource("../view/light_mode.css").toExternalForm());
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
    public void setParentController(ParentController parentController)
    {
        this.parentController = parentController;
    }

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
        addCategoryPopUp.setLayoutX(150);
        addCategoryPopUp.setLayoutY(90);
        anchorPane.getChildren().remove(toolbarPane);
        anchorPane.setPrefSize(700, 400);
        anchorPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: black; -fx-border-radius: 20; -fx-border-width: 2;");
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
        if(Main.login.getUser().saveEventToFile())
        {
            saveEventMessage.setText("Saved successfully!");
        }
        else
        {
            saveEventMessage.setText("Error: something went wrong, please try again");
            saveEventMessage.setTextFill(Color.rgb(255, 0, 0));
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
        setEffect(this.blur);

        popUpController.setUp();
        addCategoryPopUp.setVisible(true);
    }

    private void updateCategoriesList()
    {
        List<String> categories = new ArrayList<String>(Main.login.getUser().getCategories().keySet());

        eventCategoryField.getItems().clear();
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
            Main.login.getUser().editEvent(newEvent, this.date, this.eventIndex);
        }
        else
        {
            Main.login.getUser().addEvent(newEvent);
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
        this.isEdit = false;
        manageEventPageLabel.setText("Add Event");
        saveManageEventButton.setVisible(true);
        cancelManageEventButton.setVisible(true);
        doneManageEventButton.setVisible(false);
        saveEventMessage.setText("");
    }
}
