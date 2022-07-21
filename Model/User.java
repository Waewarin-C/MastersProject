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
}
