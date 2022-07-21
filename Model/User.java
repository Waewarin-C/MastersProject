package Model;

//Keeps the information about a single user
    //Username
    //Password
    //Display Name
    //Notes font
    //Events
    //Notes

import java.util.*;

public class User {
    String username;
    String password;
    String displayName;
    String notesFont;
    List<Event> events;
    List<Note> notes;

    public User(String username, String password, String displayName)
    {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.events = new ArrayList<Event>();
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
}
