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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class AddEventController implements Initializable, ParentController {
    @FXML
    private TextField eventNameField, eventLocationField;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private ComboBox eventCategoryField;

    @FXML
    private TextArea eventDescriptionField;

    @FXML
    private Label addEventPageLabel, saveEventMessage, newCategory;

    @FXML
    private Button addNewEventCategoryButton;

    @FXML
    private GridPane addEventButtons, addEventGridPane;

    @FXML
    private AnchorPane anchorPane, addCategoryPopUp;

    @FXML
    private Pane toolbarPane;

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

            popUpController = ((AddCategoryPopUpController)loader.getController());
            popUpController.setParentController(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        addCategoryPopUp.setVisible(false);

        eventDatePicker.setStyle("-fx-font: 14px \"Berlin Sans FB\";");

        List<String> categories = new ArrayList<String>();
        categories.addAll(Main.login.getUser().getCategories().keySet());
        Collections.sort(categories);
        eventCategoryField.getItems().addAll(categories);
        eventCategoryField.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        newCategory.setText("");
        newCategory.setVisible(false);

        saveEventMessage.setText("");
    }

    public void setParentController(ParentController parentController)
    {
        this.parentController = parentController;
    }

    public void adjustLayout()
    {
        addEventGridPane.setLayoutX(66);
        addEventButtons.setLayoutX(520);
        eventDescriptionField.setPrefHeight(100);
        anchorPane.getChildren().remove(toolbarPane);
        anchorPane.setPrefSize(700, 450);
    }

    public void saveAddEvent()
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

        saveEventToFile(event);
        saveEventToUser(event);

        resetFields();
    }

    public void cancelAddEvent()
    {
        resetFields();
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
        addEventPageLabel.setEffect(effect);
        addEventGridPane.setEffect(effect);
        addEventButtons.setEffect(effect);
        toolbarPane.setEffect(effect);
    }

    private String formatDate()
    {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yy");
        LocalDate date = eventDatePicker.getValue();

        return date.format(format);
    }

    private void saveEventToFile(List<String> event)
    {
        String eventName = event.get(0);
        String eventDate = event.get(1);
        String eventLocation = event.get(2);
        String eventCategory = event.get(3);
        String eventDescription = event.get(4);

        FileWriter file = null;
        String fileName = "Account/" + Main.login.getUser().getUsername() + "_events.csv";

        try
        {
            file = new FileWriter(new File(fileName), true);
            file.write(String.format("%s,%s,%s,%s,%s\n", eventName, eventDate, eventLocation, eventCategory, eventDescription));
            file.close();

            saveEventMessage.setText("Saved successfully!");
        }
        catch(IOException e)
        {
            saveEventMessage.setText("Error: something went wrong, please try again");
            saveEventMessage.setTextFill(Color.rgb(255,0,0));
            System.out.println("Error: unable to save the event");
            e.printStackTrace();
        }
    }

    private void saveEventToUser(List<String> event)
    {
        String eventName = event.get(0);
        String eventDate = event.get(1);
        String eventLocation = event.get(2);
        String eventCategory = event.get(3);
        String eventDescription = event.get(4);

        Event newEvent = new Event(eventName, eventDate, eventLocation, eventCategory, eventDescription);
        Main.login.getUser().addEvent(newEvent);
    }

    private void resetFields()
    {
        eventNameField.clear();
        eventDatePicker.setValue(null);
        eventLocationField.clear();
        eventCategoryField.getSelectionModel().clearSelection();
        newCategory.setText("");
        eventDescriptionField.clear();
        addNewEventCategoryButton.setVisible(true);
        newCategory.setVisible(false);
    }
}
