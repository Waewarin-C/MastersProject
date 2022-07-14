package Model;

//Keeps the information about a single user
    //Username
    //Password
    //Display Name
    //Notes font
    //Events
    //Notes

import java.util.List;

public class User {
    String username;
    String password;
    String displayName;
    String notesFont;
    List<Event> events;
    List<Note> notes;
}
