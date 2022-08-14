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

        if(!Main.login.getUser().getEvents().containsKey(date))
        {
            firstEvent.setText("You have no events today");
        }
        else
        {
            List<Event> events = Main.login.getUser().getEvents().get(date);
            String eventName = events.get(0).getEventName();
            firstEvent.setText(eventName);

            int numMoreEvents = events.size() - 1;
            if(numMoreEvents > 0)
            {
                String moreEventText = "+ " + numMoreEvents + " more";
                Label moreEvents = new Label(moreEventText);
                moreEvents.setStyle("-fx-font: 16px \"Berlin Sans FB\";");
                todayEvents.getChildren().add(moreEvents);
            }
        }

        todayEvents.getChildren().add(0, firstEvent);
    }

    private void displayNextSevenDayEvents()
    {
        int week = 7;

        for(int i = 1; i <= week; i++)
        {
            LocalDate nextDate = today.plusDays(i);
            String next = nextDate.format(format);

            Label eventLabel = new Label();
            eventLabel.setStyle("-fx-font: 18px \"Berlin Sans FB\";");

            if(!Main.login.getUser().getEvents().containsKey(next))
            {
                String noEvents = next + ": You have no events";
                eventLabel.setText(noEvents);
                sevenDayEvents.getChildren().add(eventLabel);
            }
            else
            {
                List<Event> events = Main.login.getUser().getEvents().get(next);
                String eventName = next + ": " + events.get(0).getEventName();
                eventLabel.setText(eventName);
                sevenDayEvents.getChildren().add(eventLabel);

                int numMoreEvents = events.size() - 1;
                if(numMoreEvents > 0)
                {
                    String moreEventText = "+ " + numMoreEvents + " more";
                    eventLabel.setText(eventName + "\t.\t.\t.\t" + moreEventText);
                }
            }
        }
    }
}
