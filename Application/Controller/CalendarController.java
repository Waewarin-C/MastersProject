package Application.Controller;

//Application.Controller for the Event Calendar view
//Event Calendar view will include
    //Add Event button
    //Option for user to pick view they want to view the calendar (weekly or monthly)
    //The events for each date displayed in each date

import Application.Main;
import Application.Model.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class CalendarController implements Initializable, ParentController {
    @FXML
    private Label calendarLabel, calendarMonth;

    @FXML
    private DatePicker calendarDatePicker;

    @FXML
    private Button addCalendarEventButton;

    @FXML
    private GridPane datePicker, calendar;

    @FXML
    private Pane toolbarPane;

    private LocalDate today = LocalDate.now();
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yy");
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

        setUpCalendar();
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
        calendarLabel.setEffect(effect);
        datePicker.setEffect(effect);
        addCalendarEventButton.setEffect(effect);
        calendar.setEffect(effect);
        toolbarPane.setEffect(effect);
    }

    public void closePopUp(String stringNeeded)
    {
        setEffect(null);
    }

    private void setUpCalendar()
    {
        boolean notLastDayOfMonth = true;
        int lastDayOfMonth = this.today.getMonth().length(false);
        if(this.today.getMonth().equals(Month.FEBRUARY) && this.today.isLeapYear())
        {
            lastDayOfMonth = this.today.getMonth().length(true);
        }

        //Get the day of the 1st of the month
        int weekDayOfFirstDay = 0;
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        DayOfWeek firstDay = firstDayOfMonth.getDayOfWeek();
        if(!firstDay.equals(DayOfWeek.SUNDAY))
        {
            weekDayOfFirstDay = firstDay.getValue();
        }

        //Set up first week
        int date = setUpFirstWeek(weekDayOfFirstDay);


        //Set up week 2 onward till the end of the month
        /*while(notLastDayOfMonth)
        {
            System.out.println("while loop");
            if(date == lastDayOfMonth)
            {
                notLastDayOfMonth = false;
            }
        }*/
    }

    private int setUpFirstWeek(int weekDayOfFirstDay)
    {
        this.calendar.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE));

        int date = 1;
        int next = 0;
        for(int col = weekDayOfFirstDay; col < 7; col++)
        {
            LocalDate nextDate = this.today.plusDays(next);
            String nextDay = nextDate.format(this.format);

            VBox day = new VBox();
            day.setPrefHeight(Region.USE_COMPUTED_SIZE);
            day.setSpacing(5);
            day.setPadding(new Insets(0, 0, 1, 0));
            day.setStyle("-fx-border-color: black; -fx-border-width: 2;");

            Button displayDate = new Button(Integer.toString(date));
            displayDate.setPrefSize(100, Region.USE_COMPUTED_SIZE);
            displayDate.setAlignment(Pos.CENTER_RIGHT);
            displayDate.setStyle("-fx-font: 16px \"Berlin Sans FB\"; -fx-background-radius: 0;");
            day.getChildren().add(displayDate);

            if(Main.login.getUser().getEvents().containsKey(nextDay))
            {
                Label firstEvent = new Label();
                firstEvent.setStyle("-fx-font: 14px \"Berlin Sans FB\";");

                List<Event> events = Main.login.getUser().getEvents().get(nextDay);
                String eventName = events.get(0).getEventName();
                firstEvent.setText(eventName);

                day.getChildren().add(firstEvent);

                int numMoreEvents = events.size() - 1;
                if(numMoreEvents > 0)
                {
                    String moreEventText = "+ " + numMoreEvents + " more";

                    Label moreEvents = new Label(moreEventText);
                    moreEvents.setStyle("-fx-font: 13px \"Berlin Sans FB\";");
                    day.getChildren().add(moreEvents);
                }
            }

            calendar.add(day,col,2);
            date++;
        }
        return date;
    }
}
