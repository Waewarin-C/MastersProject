package Model;

//Takes care of
    //Login
        //Checks that the credentials are correct
    //Create account
        //Checks all fields are entered
        //Checks all fields are correct
            //Check if username is already taken
            //Check limit on display name
            //Check if password contains at least 8 characters
            //Check if password and confirm password are the same

import java.util.HashMap;
import java.util.List;

public class Account {

    HashMap<String, User> users = new HashMap<String, User>();

    public String login(String username, String password)
    {
        String errorMessage = "";

        //Check if username is incorrect
        if(!users.containsKey(username))
        {
            errorMessage = "Incorrect username entered";
        }

        //Check if password is incorrect
        if(!users.get(username).password.equals(password))
        {
            errorMessage = "Incorrect password entered";
        }
        return errorMessage;
    }

    public String createAccount(String username, String password, String confirmPassword, String displayName)
    {
        String errorMessage = "";

        //Check if username is already taken
        if(users.containsKey(username))
        {
            errorMessage = "Username \"" + username + "\" is already taken, please enter another one";
        }

        //Check limit on display name
        if(displayName.length() > 30)
        {
            errorMessage += "\nDisplay name cannot be more than 30 characters";
        }

        //Check if password has at least 8 characters
        if(password.length() < 8)
        {
            errorMessage += "\nPassword needs to be at least 8 characters";
        }

        //Check if password and confirm password are the same
        if(!password.equals(confirmPassword))
        {
            errorMessage += "\nPassword and Confirm Password not the same";
        }

        return errorMessage;
    }
}
