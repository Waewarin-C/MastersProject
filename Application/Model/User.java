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

import java.time.LocalDate;
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

    public User()
    {
        this.events = new TreeMap<String, List<Event>>();
        this.categories = new TreeMap<String, String>();
        this.welcomePageShown = "No";
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

    public void addEvent(Event event, boolean isEdit, String oldDate, int eventIndex)
    {
        String date = event.getEventDate();

        //For edit
        if(isEdit)
        {
            deleteEvent(date, eventIndex);
        }

        if(this.events.containsKey(date))
        {
            if((oldDate.equals("") && eventIndex < 0) || !oldDate.equals(date))
            {
                this.events.get(date).add(event);
            }
            else
            {
                this.events.get(date).add(eventIndex, event);
            }
        }
        else
        {
            List<Event> events = new ArrayList<>();
            events.add(event);
            this.events.put(date, events);
        }
    }

    public void deleteEvent(String date, int index)
    {
        this.events.get(date).remove(index);
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

    public String getWelcomePageShown()
    {
        return this.welcomePageShown;
    }

    public void setWelcomePageShown(String welcomePageShown)
    {
        this.welcomePageShown = welcomePageShown;
    }
}
