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
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEventController implements Initializable {
    @FXML
    Button saveAddEventButton, cancelAddEventButton, addNewEventCategoryButton;

    @FXML
    Button saveAddCategoryButton, doneAddCategoryButton, cancelAddCategoryButton;

    @FXML
    TextField eventNameField, eventLocationField, categoryNameField;

    @FXML
    DatePicker eventDatePicker;

    @FXML
    ComboBox eventCategoryField;

    @FXML
    TextArea eventDescriptionField;

    @FXML
    Label addEventPageLabel, saveEventMessage, addCategoryMessage;

    @FXML
    AnchorPane addCategoryPopUp;

    @FXML
    Pane toolbarPane;

    @FXML
    ColorPicker categoryColorPicker;

    @FXML
    GridPane addEventButtons, addEventGridPane;

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

        addCategoryPopUp.setVisible(false);

        categoryColorPicker.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        eventDatePicker.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        eventCategoryField.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        saveEventMessage.setText("");
    }

    public void saveAddEvent()
    {
        String eventName = eventNameField.getText();
        String eventDescription = eventDescriptionField.getText();

        if(eventName.equals("") || eventDescription.equals(""))
        {
            saveEventMessage.setText("One or more fields is empty");
        }
    }

    public void cancelAddEvent()
    {
        eventNameField.clear();
        eventDescriptionField.clear();
    }

    public void addNewEventCategory()
    {
        GaussianBlur blur = new GaussianBlur();
        setEffect(blur);

        addCategoryPopUp.setVisible(true);
        saveAddCategoryButton.setVisible(true);
        cancelAddCategoryButton.setVisible(true);
        doneAddCategoryButton.setVisible(false);
    }

    public void saveAddCategory()
    {
        saveAddCategoryButton.setVisible(false);
        cancelAddCategoryButton.setVisible(false);
        doneAddCategoryButton.setVisible(true);
    }

    public void doneAddCategory()
    {
        addCategoryPopUp.setVisible(false);
        doneAddCategoryButton.setVisible(false);

        setEffect(null);
    }

    public void cancelAddCategory()
    {
        addCategoryPopUp.setVisible(false);

        setEffect(null);
    }

    private void setEffect(Effect effect)
    {
        addEventPageLabel.setEffect(effect);
        addEventGridPane.setEffect(effect);
        addEventButtons.setEffect(effect);
        toolbarPane.setEffect(effect);
    }
}
