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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEventController implements Initializable {
    @FXML
    Button saveAddEventButton, cancelAddEventButton;

    @FXML
    TextField eventNameField, eventLocationField;

    @FXML
    DatePicker eventDatePicker;

    @FXML
    ComboBox eventCategoryField;

    @FXML
    TextArea eventDescriptionField;

    @FXML
    Label saveEventMessage;

    @FXML
    Pane toolbarPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try
        {
            Parent toolbar = FXMLLoader.load(getClass().getResource("../View/Toolbar.fxml"));
            toolbarPane.getChildren().add(toolbar);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        eventDatePicker.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        eventCategoryField.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        saveEventMessage.setText("");
    }

    public void saveAddEvent(ActionEvent event)
    {
        String eventName = eventNameField.getText();
        String eventDescription = eventDescriptionField.getText();

        if(eventName.equals("") || eventDescription.equals(""))
        {
            saveEventMessage.setText("One or more fields is empty");
        }
    }

    public void cancelAddEvent(ActionEvent event)
    {
        eventNameField.clear();
        eventDescriptionField.clear();
    }
}
