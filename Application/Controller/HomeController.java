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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
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
    private GridPane upcomingEvents;

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
        int week = 7;

        for(int i = 0; i < week; i++)
        {
            LocalDate nextDate = today.plusDays(i);
            String next = nextDate.format(format);

            VBox dayEvents = new VBox();
            dayEvents.setSpacing(5);
            dayEvents.setPadding(new Insets(0, 10, 0, 10));

            Label dayLabel = new Label();
            dayLabel.setStyle("-fx-font: 20px \"Berlin Sans FB\";");
            if(i == 0)
            {
                dayLabel.setText("Today: " + next);
            }
            else
            {
                dayLabel.setText(next);
            }
            dayEvents.getChildren().add(dayLabel);

            Label eventLabel = new Label();
            eventLabel.setStyle("-fx-font: 16px \"Berlin Sans FB\";");
            if(!Main.login.getUser().getEvents().containsKey(next))
            {
                eventLabel.setText("You have no events");
                dayEvents.getChildren().add(eventLabel);
            }
            else
            {
                List<Event> events = Main.login.getUser().getEvents().get(next);
                String eventName = events.get(0).getEventName();
                eventLabel.setText(eventName);
                dayEvents.getChildren().add(eventLabel);

                int numMoreEvents = events.size() - 1;
                if(numMoreEvents > 0)
                {
                    String moreEventText = "+ " + numMoreEvents + " more";
                    eventLabel.setText(eventName + "\t.\t.\t.\t" + moreEventText);
                    addViewAllEventsButton(i+1, next);
                }
                else
                {
                    addViewEventDetailsButton(i+1, next);
                }
            }
            upcomingEvents.add(dayEvents, 0, i+1);
        }
    }

    private void addViewAllEventsButton(int row, String date)
    {
        Button viewEvents = new Button();
        viewEvents.setPrefSize(Region.USE_COMPUTED_SIZE, 30);
        viewEvents.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        viewEvents.setText("View All Events for " + date);

        EventHandler<ActionEvent> viewAllEvents = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        };
        viewEvents.setOnAction(viewAllEvents);

        upcomingEvents.add(viewEvents, 1, row);
    }

    private void addViewEventDetailsButton(int row, String date)
    {
        Button viewEventDetails = new Button();
        viewEventDetails.setPrefSize(Region.USE_COMPUTED_SIZE, 30);
        viewEventDetails.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        viewEventDetails.setText("View Event Details for " + date);

        EventHandler<ActionEvent> viewDetails = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        };
        viewEventDetails.setOnAction(viewDetails);

        upcomingEvents.add(viewEventDetails, 1, row);
    }
}
