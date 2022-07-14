package Model;

//Keeps the information about a single user
    //Username
    //Password
    //Name
    //Events
    //Notes

import java.util.List;

public class User {
    String username;
    String password;
    String displayName;
    List<Event> events;
    List<Note> notes;
}
