package Application.Controller;

//Application.Controller for the Event Calendar view
//Event Calendar view will include
    //Add Event button
    //Option for user to pick view they want to view the calendar (weekly or monthly)
    //The events for each date displayed in each date

import Application.Main;
import Application.Model.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CalendarController implements Initializable, ParentController {
    @FXML
    private Label calendarLabel, goToDateLabel, calendarMonth, sunday, monday, tuesday, wednesday, thursday, friday, saturday;

    @FXML
    private DatePicker calendarDatePicker;

    @FXML
    private Button addCalendarEventButton;

    @FXML
    private GridPane datePicker, calendar;

    @FXML
    private Pane toolbarPane;

    @FXML
    private AnchorPane anchorPane, manageEvent, listOfEvents;

    private EventsListController eventsListController;
    private ManageEventController manageEventController;

    private LocalDate selectedDate = LocalDate.now();
    private LocalDate firstDayOfMonth = this.selectedDate.withDayOfMonth(1);
    private DayOfWeek firstDay = this.firstDayOfMonth.getDayOfWeek();
    private int weekDayOfFirstDay = this.firstDay.getValue();
    private int dayOfMonth = this.selectedDate.getDayOfMonth();
    private int lastDayOfMonth = this.selectedDate.lengthOfMonth();
    private DateTimeFormatter format = DateTimeFormatter.ofPattern(Main.login.getUser().getDateFormat());

    private final GaussianBlur blur = new GaussianBlur();
    private final String theme = Main.login.getUser().getTheme();

    @Override
    public void initialize(URL location, ResourceBundle resources){
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

            FXMLLoader eventsListLoader = new FXMLLoader(getClass().getResource("../View/EventsList.fxml"));
            Node eventsListPopUp = eventsListLoader.load();
            listOfEvents.getChildren().add(eventsListPopUp);

            this.eventsListController = ((EventsListController)eventsListLoader.getController());
            this.eventsListController.setParentController(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        setStyleFromTheme();
        calendarDatePicker.setStyle("-fx-font: 14px \"Berlin Sans FB\";");

        manageEvent.setVisible(false);
        listOfEvents.setVisible(false);

        calendarDatePicker.setValue(this.selectedDate);
        setUpCalendar();
    }

    public void closePopUp(String stringNeeded)
    {
        manageEvent.setVisible(false);
        listOfEvents.setVisible(false);
        setEffect(null);
        setUpCalendar();
    }

    public void setEffect(Effect effect)
    {
        calendarLabel.setEffect(effect);
        datePicker.setEffect(effect);
        addCalendarEventButton.setEffect(effect);
        calendar.setEffect(effect);
        toolbarPane.setEffect(effect);
    }

    public void goToDate()
    {
        this.selectedDate = calendarDatePicker.getValue();
        this.firstDayOfMonth = this.selectedDate.withDayOfMonth(1);
        this.firstDay = this.firstDayOfMonth.getDayOfWeek();
        this.weekDayOfFirstDay = this.firstDay.getValue();
        this.dayOfMonth = this.selectedDate.getDayOfMonth();
        this.lastDayOfMonth = this.selectedDate.lengthOfMonth();

        setUpCalendar();
    }

    public void addEvent()
    {
        manageEvent.setVisible(true);
        setEffect(this.blur);
    }

    private void setStyleFromTheme()
    {
        Color color = getColorFromTheme();

        calendarLabel.setTextFill(color);
        goToDateLabel.setTextFill(color);
        calendarMonth.setTextFill(color);
        sunday.setTextFill(color);
        monday.setTextFill(color);
        tuesday.setTextFill(color);
        wednesday.setTextFill(color);
        thursday.setTextFill(color);
        friday.setTextFill(color);
        saturday.setTextFill(color);

        setCalendarStyleFromTheme();

    }

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

    private void setCalendarStyleFromTheme()
    {
        if(this.theme.equals("Light"))
        {
            calendar.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: black; -fx-border-width: 2;");
            calendarMonth.setStyle("-fx-border-color: black; -fx-border-width: 0 0 2 0;");
            sunday.setStyle("-fx-border-color: black; -fx-border-width: 0 0 2 0;");
            monday.setStyle("-fx-border-color: black; -fx-border-width: 0 0 2 0;");
            tuesday.setStyle("-fx-border-color: black; -fx-border-width: 0 0 2 0;");
            wednesday.setStyle("-fx-border-color: black; -fx-border-width: 0 0 2 0;");
            thursday.setStyle("-fx-border-color: black; -fx-border-width: 0 0 2 0;");
            friday.setStyle("-fx-border-color: black; -fx-border-width: 0 0 2 0;");
            saturday.setStyle("-fx-border-color: black; -fx-border-width: 0 0 2 0;");
        }
        else
        {
            calendar.setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: white; -fx-border-width: 2;");
            calendarMonth.setStyle("-fx-border-color: white; -fx-border-width: 0 0 2 0;");
            sunday.setStyle("-fx-border-color: white; -fx-border-width: 0 0 2 0;");
            monday.setStyle("-fx-border-color: white; -fx-border-width: 0 0 2 0;");
            tuesday.setStyle("-fx-border-color: white; -fx-border-width: 0 0 2 0;");
            wednesday.setStyle("-fx-border-color: white; -fx-border-width: 0 0 2 0;");
            thursday.setStyle("-fx-border-color: white; -fx-border-width: 0 0 2 0;");
            friday.setStyle("-fx-border-color: white; -fx-border-width: 0 0 2 0;");
            saturday.setStyle("-fx-border-color: white; -fx-border-width: 0 0 2 0;");
        }
    }

    private void setUpCalendar()
    {
        this.calendar.getChildren().remove(8, this.calendar.getChildren().size());
        String month = this.selectedDate.getMonth().toString();
        int year = this.selectedDate.getYear();
        calendarMonth.setText(month + " " + year);

        displayMonth();
    }

    private void displayMonth()
    {
        int date = 1;
        int row = 2;
        int col = 0;
        if(this.weekDayOfFirstDay != 7)
        {
            col = this.weekDayOfFirstDay;
        }

        while(date <= this.lastDayOfMonth)
        {
            this.calendar.getRowConstraints().add(new RowConstraints(Region.USE_COMPUTED_SIZE));

            LocalDate current = this.firstDayOfMonth.plusDays(date - 1);
            String currentDate = current.format(this.format);

            VBox day = new VBox();
            day.setPrefHeight(62);
            day.setSpacing(5);
            day.setPadding(new Insets(0, 2, 1, 2));

            String dayBoxStyle = getDayBoxStyle(date, col);
            day.setStyle(dayBoxStyle);

            Label displayDate = new Label(Integer.toString(date));
            displayDate.setPrefWidth(100);
            displayDate.setTextFill(getColorFromTheme());
            displayDate.setStyle(getDisplayDateStyle());

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

            EventHandler<MouseEvent> viewEvents = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    eventsListController.setDate(currentDate);
                    eventsListController.displayEvents();
                    setEffect(blur);
                    listOfEvents.setVisible(true);
                }
            };
            day.setOnMouseClicked(viewEvents);

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

    private String getDisplayDateStyle()
    {
        if(this.theme.equals("Light"))
        {
            return "-fx-font: 14px \"Berlin Sans FB\"; -fx-border-color: black; -fx-border-width: 0 0 2 0;";
        }
        else
        {
            return "-fx-font: 14px \"Berlin Sans FB\"; -fx-border-color: white; -fx-border-width: 0 0 2 0;";
        }
    }

    private String getDayBoxStyle(int date, int col)
    {
        String dayBoxStyle = "";

        if(this.theme.equals("Light"))
        {
            if (date == this.dayOfMonth && date == this.lastDayOfMonth && col == 6)
            {
                dayBoxStyle = "-fx-background-color: #e3e3e3; -fx-background-radius: 0 0 20 0; -fx-border-color: black; -fx-border-width: 2 0 0 2;";
            }
            else if (date == this.dayOfMonth && col == 0 && this.lastDayOfMonth - date <= 6)
            {
                dayBoxStyle = "-fx-background-color: #e3e3e3; -fx-background-radius: 0 0 0 20; -fx-border-color: black; -fx-border-width: 2 0 0 0;";
            }
            else if (date == this.dayOfMonth)
            {
                if (col == 0)
                {
                    dayBoxStyle = "-fx-background-color: #e3e3e3; -fx-border-color: black; -fx-border-width: 2 0 0 0;";
                }
                else
                {
                    dayBoxStyle = "-fx-background-color: #e3e3e3; -fx-border-color: black; -fx-border-width: 2 0 0 2;";
                }
            }
            else if (col == 0)
            {
                dayBoxStyle = "-fx-border-color: black; -fx-border-width: 2 0 0 0;";
            }
            else
            {
                dayBoxStyle = "-fx-border-color: black; -fx-border-width: 2 0 0 2;";
            }
        }
        else
        {
            if (date == this.dayOfMonth && date == this.lastDayOfMonth && col == 6)
            {
                dayBoxStyle = "-fx-background-color: black; -fx-background-radius: 0 0 20 0; -fx-border-color: white; -fx-border-width: 2 0 0 2;";
            }
            else if (date == this.dayOfMonth && col == 0 && this.lastDayOfMonth - date <= 6)
            {
                dayBoxStyle = "-fx-background-color: black; -fx-background-radius: 0 0 0 20; -fx-border-color: white; -fx-border-width: 2 0 0 0;";
            }
            else if (date == this.dayOfMonth)
            {
                if (col == 0)
                {
                    dayBoxStyle = "-fx-background-color: black; -fx-border-color: white; -fx-border-width: 2 0 0 0;";
                }
                else
                {
                    dayBoxStyle = "-fx-background-color: black; -fx-border-color: white; -fx-border-width: 2 0 0 2;";
                }
            }
            else if (col == 0)
            {
                dayBoxStyle = "-fx-border-color: white; -fx-border-width: 2 0 0 0;";
            }
            else
            {
                dayBoxStyle = "-fx-border-color: white; -fx-border-width: 2 0 0 2;";
            }
        }
        return dayBoxStyle;
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
            String eventCategory = events.get(0).getEventCategory();
            String categoryColor = Main.login.getUser().getCategories().get(eventCategory);
            firstEvent.setText(eventName);
            firstEvent.setTextFill(Color.web(categoryColor));

            eventsList.add(firstEvent);

            int numMoreEvents = events.size() - 1;
            if(numMoreEvents > 0)
            {
                String moreEventText = "+ " + numMoreEvents + " more";

                Label moreEvents = new Label(moreEventText);
                moreEvents.setStyle("-fx-font: 12px \"Berlin Sans FB\";");
                moreEvents.setTextFill(getColorFromTheme());
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
            extra.setPadding(new Insets(0, 2, 1, 2));

            if(this.theme.equals("Light"))
            {
                if (i == col)
                {
                    extra.setStyle("-fx-border-color: black; -fx-border-width: 2 0 0 2;");
                }
                else
                {
                    extra.setStyle("-fx-border-color: black; -fx-border-width: 2 0 0 0;");
                }
            }
            else
            {
                if (i == col)
                {
                    extra.setStyle("-fx-border-color: white; -fx-border-width: 2 0 0 2;");
                }
                else
                {
                    extra.setStyle("-fx-border-color: white; -fx-border-width: 2 0 0 0;");
                }
            }

            calendar.add(extra, i, row);
        }
    }
}
