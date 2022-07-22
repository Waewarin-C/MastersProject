package Model;

//Keeps the information about a single user
    //Username
    //Password
    //Display Name
    //Events

import java.util.*;

public class User {
    String username;
    String password;
    String displayName;
    List<Event> events;

    HashMap<String, String> categories;

    public User()
    {
        this.events = new ArrayList<Event>();
        this.categories = new HashMap<String, String>();
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

    public List<Event> getEvents()
    {
        return this.events;
    }

    public void setEvents(List<Event> events)
    {
        this.events = events;
    }

    public HashMap<String, String> getCategories()
    {
        return this.categories;
    }

    public void setCategories(HashMap<String, String> categories)
    {
        this.categories = categories;
    }
}
