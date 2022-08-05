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

import java.net.URL;
import java.util.ResourceBundle;

public class AddEventController implements Initializable {
    @FXML
    private TextField eventNameField, eventLocationField;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private ComboBox eventCategoryField;

    @FXML
    private TextArea eventDescriptionField;

    @FXML
    private Label addEventPageLabel;

    @FXML
    private Label saveEventMessage;

    @FXML
    private GridPane addEventButtons, addEventGridPane;

    @FXML
    private AnchorPane addCategoryPopUp;

    @FXML
    private Pane toolbarPane;

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

        popUpController.setUp();
        addCategoryPopUp.setVisible(true);
    }

    public void closeAddCategory()
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
