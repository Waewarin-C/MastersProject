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
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {
    @FXML
    private Pane toolbarPane;

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
}
