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
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @FXML
    private AnchorPane manageEvent;

    private ParentController parentController;
    private ManageEventController manageEventController;

    private LocalDate selectedDate = LocalDate.now();
    private LocalDate firstDayOfMonth = this.selectedDate.withDayOfMonth(1);
    private DayOfWeek firstDay = this.firstDayOfMonth.getDayOfWeek();
    private int weekDayOfFirstDay = this.firstDay.getValue();
    private int lastDayOfMonth = this.selectedDate.lengthOfMonth();
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yy");

    private GaussianBlur blur = new GaussianBlur();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try
        {
            Node toolbar = FXMLLoader.load(getClass().getResource("../View/Toolbar.fxml"));
            toolbarPane.getChildren().add(toolbar);

            FXMLLoader addEventLoader = new FXMLLoader(getClass().getResource("../View/ManageEvent.fxml"));
            Node addEventPopUp = addEventLoader.load();
            manageEvent.getChildren().add(addEventPopUp);

            this.manageEventController = ((ManageEventController)addEventLoader.getController());
            this.manageEventController.setParentController(this);
            this.manageEventController.popUpSetUp(true);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        manageEvent.setVisible(false);
        calendarDatePicker.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        setUpCalendar();
    }

    public void goToDate()
    {
        this.selectedDate = calendarDatePicker.getValue();
        this.firstDayOfMonth = this.selectedDate.withDayOfMonth(1);
        this.firstDay = this.firstDayOfMonth.getDayOfWeek();
        this.weekDayOfFirstDay = this.firstDay.getValue();
        this.lastDayOfMonth = this.selectedDate.lengthOfMonth();

        setUpCalendar();
    }

    public void addEvent()
    {
        manageEvent.setVisible(true);
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
        manageEvent.setVisible(false);
        setEffect(null);
    }

    private void setUpCalendar()
    {
        this.calendar.getChildren().remove(8, this.calendar.getChildren().size());
        calendarMonth.setText(this.selectedDate.getMonth().toString());

        displayMonth();
    }

    private void displayMonth()
    {
        int date = 1;
        //int next = 0;
        int row = 2;
        int col = this.weekDayOfFirstDay;

        while(date <= this.lastDayOfMonth)
        {
            this.calendar.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE));

            LocalDate current = this.firstDayOfMonth.plusDays(date - 1);
            String currentDate = current.format(this.format);

            VBox day = new VBox();
            day.setPrefHeight(62);
            day.setSpacing(5);
            day.setPadding(new Insets(0, 0, 1, 0));
            if(col == 0) {
                day.setStyle("-fx-border-color: black; -fx-border-width: 2 0 0 0;");
            }
            else
            {
                day.setStyle("-fx-border-color: black; -fx-border-width: 2 0 0 2;");
            }

            Button displayDate = new Button(Integer.toString(date));
            displayDate.setPrefSize(100, Region.USE_COMPUTED_SIZE);
            displayDate.setAlignment(Pos.CENTER_RIGHT);
            displayDate.setStyle("-fx-font: 12px \"Berlin Sans FB\"; -fx-background-radius: 0;");
            day.getChildren().add(displayDate);

            List<Label> eventsList = showEvents(currentDate);

            if(eventsList.size() > 0)
            {
                day.getChildren().add(eventsList.get(0));

                if(eventsList.size() > 1)
                {
                    day.getChildren().add(eventsList.get(1));
                }
            }

            calendar.add(day,col, row);

            if(col < 6)
            {
                col++;
            }
            else
            {
                col = 0;
                row++;
            }

            date++;
        }

        if(col != 0)
        {
            addAdditionalBorders(col, row);
        }
    }

    private List<Label> showEvents(String todayDate)
    {
        List<Label> eventsList = new ArrayList<Label>();

        if(Main.login.getUser().getEvents().containsKey(todayDate))
        {
            Label firstEvent = new Label();
            firstEvent.setStyle("-fx-font: 12px \"Berlin Sans FB\";");

            List<Event> events = Main.login.getUser().getEvents().get(todayDate);
            String eventName = events.get(0).getEventName();
            firstEvent.setText(eventName);

            eventsList.add(firstEvent);

            int numMoreEvents = events.size() - 1;
            if(numMoreEvents > 0)
            {
                String moreEventText = "+ " + numMoreEvents + " more";

                Label moreEvents = new Label(moreEventText);
                moreEvents.setStyle("-fx-font: 12px \"Berlin Sans FB\";");
                eventsList.add(moreEvents);
            }
        }
        return eventsList;
    }

    private void addAdditionalBorders(int col, int row)
    {
        for(int i = col; i < 7; i++)
        {
            VBox extra = new VBox();
            extra.setPrefHeight(62);
            extra.setSpacing(5);
            extra.setPadding(new Insets(0, 0, 1, 0));

            if(i == col)
            {
                extra.setStyle("-fx-border-color: black; -fx-border-width: 2 0 0 2;");
            }
            else
            {
                extra.setStyle("-fx-border-color: black; -fx-border-width: 2 0 0 0;");
            }

            calendar.add(extra, i, row);
        }
    }
}
