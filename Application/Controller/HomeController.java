package Application.Controller;

import Application.Main;
import Application.Model.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * HomeController interacts with Home.fxml
 * Shows the events of the week ahead starting from the current date.
 * If a date has an event, it will be shown. If a date has more than,
 * one event, the first event is shown with how many more the date has.
 * The user can view, add, and events for the seven days
 * The user can also logout through this page.
 *
 * @author Waewarin Chindarassami
 */

public class HomeController implements Initializable, ParentController {
    @FXML
    private Label helloMessage, logoutMessage, weekAheadLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private GridPane upcomingEvents;

    @FXML
    private Pane navigationPane;

    @FXML
    private AnchorPane anchorPane, eventsList;

    private EventsListController eventsPopUpController;

    private LocalDate today = LocalDate.now();
    private DateTimeFormatter format = DateTimeFormatter.ofPattern(Main.login.getUser().getDateFormat());

    private String currentUsername = Main.login.getUser().getUsername();

    private final GaussianBlur blur = new GaussianBlur();
    private final String theme = Main.login.getUser().getTheme();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try
        {
            Node navigation = FXMLLoader.load(getClass().getResource("../View/Navigation.fxml"));
            navigationPane.getChildren().add(navigation);

            FXMLLoader listLoader = new FXMLLoader(getClass().getResource("../View/EventsList.fxml"));
            Node listPopUp = listLoader.load();
            eventsList.getChildren().add(listPopUp);

            this.eventsPopUpController = ((EventsListController)listLoader.getController());
            this.eventsPopUpController.setParentController(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        setStyleFromTheme();

        String message = "Hello " + Main.login.getUser().getDisplayName() + "!";
        helloMessage.setText(message);

        eventsList.setVisible(false);
        logoutMessage.setVisible(false);

        displayWeekEvents();
    }

    public void closePopUp(String stringNeeded)
    {
        eventsList.setVisible(false);
        displayWeekEvents();
        setEffect(null);
    }

    public void setEffect(Effect effect)
    {
        helloMessage.setEffect(effect);
        logoutButton.setEffect(effect);
        logoutMessage.setEffect(effect);
        upcomingEvents.setEffect(effect);
        navigationPane.setEffect(effect);
    }

    /**
     * Logs out of account
     */
    public void logout()
    {
        Main.login.getUser().setLogout("Yes");
        if(!Main.login.getUser().saveSettingsToFile(this.currentUsername))
        {
            logoutMessage.setVisible(true);
            return;
        }

        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
            Main.stage.setScene(new Scene(root));
            Main.stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
     * Sets the style of this view based on the theme
     */
    private void setStyleFromTheme()
    {
        Color color = getColorFromTheme();

        helloMessage.setTextFill(color);
        weekAheadLabel.setTextFill(color);
    }

    /*
     * Sets the anchorPane style based on the theme
     * Gets the color of the labels based on the theme
     */
    private Color getColorFromTheme()
    {
        if(this.theme.equals("Light"))
        {
            anchorPane.setStyle("-fx-background-color: white;");
            return Color.BLACK;
        }
        else
        {
            anchorPane.setStyle("-fx-background-color: #31323e;");
            return Color.WHITE;
        }
    }

    /*
     * Display events for the week ahead starting from the current date
     */
    private void displayWeekEvents()
    {
        Color labelColor = getColorFromTheme();

        upcomingEvents.getChildren().remove(1, upcomingEvents.getChildren().size());

        int week = 7;

        for(int i = 0; i < week; i++)
        {
            LocalDate nextDate = this.today.plusDays(i);
            String next = nextDate.format(this.format);

            VBox dayEvents = new VBox();
            dayEvents.setSpacing(5);
            dayEvents.setPadding(new Insets(0, 10, 0, 10));

            Label dayLabel = new Label();
            dayLabel.setStyle("-fx-font: 20px \"Berlin Sans FB\";");
            dayLabel.setTextFill(labelColor);
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
            eventLabel.setTextFill(labelColor);
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
                }

                addViewEventsButton(i+1, next);
            }
            upcomingEvents.add(dayEvents, 0, i+1);
        }
    }

    /*
     * Add view events button for the dates that have events
     */
    private void addViewEventsButton(int row, String eventDate)
    {
        Button viewEventsButton = new Button();
        viewEventsButton.setPrefSize(Region.USE_COMPUTED_SIZE, 30);
        viewEventsButton.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        viewEventsButton.setText("View Events for " + eventDate);

        EventHandler<ActionEvent> viewEvents = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eventsPopUpController.setDate(eventDate);
                eventsPopUpController.displayEvents();
                setEffect(blur);
                eventsList.setVisible(true);
            }
        };
        viewEventsButton.setOnAction(viewEvents);

        upcomingEvents.add(viewEventsButton, 1, row);
    }
}
