package Application.Controller;

//Application.Controller for the Home view
//Home view will include
    //Add Event button
    //Settings button
    //Message at the top right corner that says "Hello <Name>!"
    //Option for user to pick view they want to view the calendar
    //The week and month will have the events for each date displayed in each date

import Application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Label helloMessage, todayDateLabel;

    @FXML
    private Button logoutHomButton;

    @FXML
    private VBox todayEvents, sevenDayEvents;

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

        String message = "Hello " + Main.login.getUser().getDisplayName() + "!";
        helloMessage.setText(message);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yy");
        String date = LocalDate.now().format(format);
        String dateLabel = "Your Events for Today: " + date;
        todayDateLabel.setText(dateLabel);
        todayDateLabel.setAlignment(Pos.CENTER);

        displayEvents();
    }

    public void logout()
    {

    }

    private void displayEvents()
    {
        displayTodayEvents();
        displayNextSevenDayEvents();
    }

    private void displayTodayEvents()
    {

    }

    private void displayNextSevenDayEvents()
    {

    }
}
