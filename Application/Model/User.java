package Application.Model;

//Keeps the information about a single user
    //Username
    //Password
    //Display Name
    //Security question
    //Answer to security question
    //Events
    //Categories
    //If Welcome page is shown everytime user logins

import Application.Main;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class User {
    String username;
    String password;
    String displayName;
    String securityQuestion;
    String securityQuestionAnswer;
    TreeMap<String, List<Event>> events;
    TreeMap<String, String> categories;
    String welcomePageShown;
    String logout;
    String dateFormat;

    public User()
    {
        this.events = new TreeMap<String, List<Event>>();
        this.categories = new TreeMap<String, String>();
        this.welcomePageShown = "No";
        this.logout = "No";
        this.dateFormat = "MM/dd/yy";
    }

    public void saveSettings(List<String> newSettings)
    {
        this.username = newSettings.get(0);
        this.password = newSettings.get(1);
        this.displayName = newSettings.get(2);
        this.securityQuestion = newSettings.get(3);
        this.securityQuestionAnswer = newSettings.get(4);
        this.welcomePageShown = newSettings.get(5);
    }

    public boolean saveSettingsToFile(String oldUsername)
    {
        try
        {
            //Change the info file name
            String oldFileName = "Account/" + oldUsername + "_info.csv";
            File oldFile = new File(oldFileName);

            String newFileName = "Account/" + this.username + "_info.csv";
            File newFile = new File(newFileName);
            oldFile.renameTo(newFile);

            FileWriter save = new FileWriter(oldFile);
            save.write(String.format("%s,%s\n", "Setting", "Value"));
            save.write(String.format("%s,%s\n", "Username", this.username));
            save.write(String.format("%s,%s\n", "Password", this.password));
            save.write(String.format("%s,%s\n", "Display Name", this.displayName));
            save.write(String.format("%s,%s\n", "Security Question", this.securityQuestion));
            save.write(String.format("%s,%s\n", "Security Question Answer", this.securityQuestionAnswer));
            save.write(String.format("%s,%s\n", "Welcome Page Shown", this.welcomePageShown));
            save.write(String.format("%s,%s\n", "Logout", this.logout));

            save.close();

            //Change the events file name
            oldFileName = "Account/" + oldUsername + "_events.csv";
            oldFile = new File(oldFileName);

            newFileName = "Account/" + this.username + "_events.csv";
            newFile = new File(newFileName);
            oldFile.renameTo(newFile);

            //Change the categories file name
            oldFileName = "Account/" + oldUsername + "_categories.csv";
            oldFile = new File(oldFileName);

            newFileName = "Account/" + this.username + "_categories.csv";
            newFile = new File(newFileName);
            oldFile.renameTo(newFile);
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String getUsername()
    {
        return this.username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getDisplayName()
    {
        return this.displayName;
    }

    public void setDisplayName(String displayNamename)
    {
        this.displayName = displayNamename;
    }

    public String getSecurityQuestion()
    {
        return this.securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion)
    {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityQuestionAnswer()
    {
        return this.securityQuestionAnswer;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer)
    {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public TreeMap<String, List<Event>> getEvents()
    {
        return this.events;
    }

    public void setEvents(TreeMap<String, List<Event>> events)
    {
        this.events = events;
    }

    public void addEvent(Event event)
    {
        String date = event.getEventDate();

        if(this.events.containsKey(date))
        {
            this.events.get(date).add(event);

        }
        else
        {
            List<Event> events = new ArrayList<>();
            events.add(event);
            this.events.put(date, events);
        }
    }

    public void editEvent(Event event, String oldDate, int eventIndex)
    {
        String newDate = event.getEventDate();

        deleteEvent(oldDate, eventIndex);

        if(this.events.containsKey(newDate))
        {
            if(newDate.equals(oldDate))
            {
                this.events.get(newDate).add(eventIndex, event);
            }
            else
            {
                this.events.get(newDate).add(event);
            }
        }
        else
        {
            List<Event> events = new ArrayList<>();
            events.add(event);
            this.events.put(newDate, events);
        }
    }

    public void deleteEvent(String date, int index)
    {
        this.events.get(date).remove(index);

        if(this.events.get(date).size() == 0)
        {
            this.events.remove(date);
        }
    }

    public boolean saveEventToFile()
    {
        String fileName = "Account/" + Main.login.getUser().getUsername() + "_events.csv";

        try
        {
            FileWriter file = new FileWriter(new File(fileName));;
            file.write(String.format("%s,%s,%s,%s,%s\n", "Event", "Date", "Location", "Category", "Description"));

            Set<String> eventDates = Main.login.getUser().getEvents().keySet();
            for(String date : eventDates)
            {
                List<Event> eventsList = Main.login.getUser().getEvents().get(date);

                for(Event event : eventsList)
                {
                    String eventName = event.getEventName();
                    String eventDate = event.getEventDate();
                    String eventLocation = event.getEventLocation();
                    String eventCategory = event.getEventCategory();
                    String eventDescription = event.getEventDescription();

                    file.write(String.format("%s,%s,%s,%s,%s\n", eventName, eventDate, eventLocation, eventCategory, eventDescription));
                }
            }

            file.close();
        }
        catch (IOException e)
        {
            System.out.println("Error: unable to save the event");
            e.printStackTrace();

            return false;
        }

        return true;
    }

    public void deleteEventsOfCategories(List<String> deletedCategories)
    {
        Set<String> allDates = this.events.keySet();
        HashMap<String, List<Event>> eventsToDelete = new HashMap<String, List<Event>>();

        for(String date : allDates)
        {
            List<Event> allEvents = this.events.get(date);

            for(Event event : allEvents)
            {
                String currentCategory = event.getEventCategory();
                if(deletedCategories.contains(currentCategory))
                {
                    if(eventsToDelete.containsKey(date))
                    {
                        eventsToDelete.get(date).add(event);

                    }
                    else
                    {
                        List<Event> events = new ArrayList<>();
                        events.add(event);
                        eventsToDelete.put(date, events);
                    }
                }
            }

        }
        deleteSpecificEvents(eventsToDelete);
    }

    public TreeMap<String, String> getCategories()
    {
        return this.categories;
    }

    public void setCategories(TreeMap<String, String> categories)
    {
        this.categories = categories;
    }

    public void addCategory(String categoryName, String categoryColor)
    {
        this.categories.put(categoryName, categoryColor);
    }

    public void deleteCategory(String categoryName)
    {
        this.categories.remove(categoryName);
    }

    public boolean saveCategoryToFile()
    {
        String fileName = "Account/" + Main.login.getUser().getUsername() + "_categories.csv";

        try
        {
            FileWriter file = new FileWriter(new File(fileName));
            file.write(String.format("%s,%s\n", "Category", "Color"));

            Set<String> categories = this.categories.keySet();
            for(String category : categories)
            {
                file.write(String.format("%s,%s\n", category, this.categories.get(category)));
            }

            file.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void updateCategoriesOfEvents(HashMap<String, String> oldAndNewCategories)
    {
        Set<String> allDates = this.events.keySet();
        Set<String> oldCategories = oldAndNewCategories.keySet();

        for(String date : allDates)
        {
            List<Event> allEvents = this.events.get(date);

            for(Event event : allEvents)
            {
                String currentCategory = event.getEventCategory();
                if(oldCategories.contains(currentCategory))
                {
                    event.setEventCategory(oldAndNewCategories.get(currentCategory));
                }
            }
        }
    }

    public String getWelcomePageShown()
    {
        return this.welcomePageShown;
    }

    public void setWelcomePageShown(String welcomePageShown)
    {
        this.welcomePageShown = welcomePageShown;
    }

    public String getLogout()
    {
        return this.logout;
    }

    public void setLogout(String logout)
    {
        this.logout = logout;
    }

    public String getDateFormat()
    {
        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat)
    {
        this.dateFormat = dateFormat;
    }

    private void deleteSpecificEvents(HashMap<String, List<Event>> eventsToDelete)
    {
        Set<String> specificDates = eventsToDelete.keySet();
        for(String date : specificDates)
        {
            this.events.get(date).removeAll(eventsToDelete.get(date));
            if(this.events.get(date).size() == 0)
            {
                this.events.remove(date);
            }
        }

    }
}
