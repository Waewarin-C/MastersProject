package Application.Controller;

//Application.Controller for the Home view
//Home view will include
    //Add Event button
    //Settings button
    //Message at the top right corner that says "Hello <Name>!"
    //Option for user to pick view they want to view the calendar
    //The week and month will have the events for each date displayed in each date

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
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
    }
}
