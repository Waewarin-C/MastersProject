package Application.Controller;

//Application.Controller for the Home view
//Home view will include
    //Add Event button
    //Settings button
    //Message at the top right corner that says "Hello <Name>!"
    //Option for user to pick view they want to view the calendar
    //The week and month will have the events for each date displayed in each date

import Application.Main;
import Application.Model.Event;
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
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Label helloMessage, todayDateLabel;

    @FXML
    private VBox todayEvents, sevenDayEvents;

    @FXML
    private Pane toolbarPane;

    private LocalDate today = LocalDate.now();
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yy");
    private String date = today.format(format);

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


        String dateLabel = "Your Events for Today: " + date;
        todayDateLabel.setText(dateLabel);

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
        Label firstEvent = new Label();
        firstEvent.setStyle("-fx-font: 18px \"Berlin Sans FB\";");

        if(!Main.login.getUser().getEvents().containsKey("08/22/22"))
        {
            firstEvent.setText("You have no events today");
        }
        else
        {
            List<Event> events = Main.login.getUser().getEvents().get("08/22/22");
            String eventName = events.get(0).getEventName();
            firstEvent.setText(eventName);

            int numMoreEvents = events.size() - 1;
            if(numMoreEvents > 0)
            {
                String moreEventText = "+ " + numMoreEvents + " more";
                Label moreEvents = new Label(moreEventText);
                todayEvents.getChildren().add(moreEvents);
            }
        }

        todayEvents.getChildren().add(0, firstEvent);
    }

    private void displayNextSevenDayEvents()
    {

    }
}
