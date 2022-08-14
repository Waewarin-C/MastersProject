package Application.Controller;

//Application.Controller for the Events view
//Events view will include
    //List of events for the selected date, week, month, or year
    //Option for user to select if they want day, week, month, or year?
    //Clicking on an event will display its information
        //See textbook cover example in java book
        //Edit event button
        //Delete event button

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EventsController implements Initializable {

    private ParentController parentController;
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    public void setParentController(ParentController parentController)
    {
        this.parentController = parentController;
    }

    public void displayEvents(String date)
    {

    }
}
