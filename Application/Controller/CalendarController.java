package Application.Controller;

//Application.Controller for the Event Calendar view
//Event Calendar view will include
    //Add Event button
    //Option for user to pick view they want to view the calendar (weekly or monthly)
    //The events for each date displayed in each date

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class CalendarController implements Initializable, ParentController {
    @FXML
    private Label calendarMonth;

    @FXML
    private DatePicker calendarDatePicker;

    @FXML
    private Button addCalendarEventButton;

    @FXML
    private GridPane datePicker, calendar;

    @FXML
    private Pane toolbarPane;

    private GaussianBlur blur = new GaussianBlur();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try
        {
            Node toolbar = FXMLLoader.load(getClass().getResource("../View/Toolbar.fxml"));
            toolbarPane.getChildren().add(toolbar);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void goToDate()
    {

    }

    public void addEvent()
    {
        setEffect(blur);
    }

    public void setEffect(Effect effect)
    {
        datePicker.setEffect(effect);
        addCalendarEventButton.setEffect(effect);
        calendar.setEffect(effect);
        toolbarPane.setEffect(effect);
    }

    public void closePopUp(String stringNeeded)
    {
        setEffect(null);
    }
}
