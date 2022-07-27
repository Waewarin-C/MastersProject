package Application.Model;

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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Login {

    User user;

    public Login()
    {
        this.user = new User();
    }

    public String login(String username, String password)
    {
        String errorMessage = "";

        //Check if username is incorrect
        if(!this.user.getUsername().equals(username))
        {
            errorMessage = "Incorrect username entered";
            return errorMessage;
        }

        //Check if password is incorrect
        if(!this.user.getPassword().equals(password))
        {
            errorMessage = "Incorrect password entered";
            return errorMessage;
        }

        return errorMessage;
    }

    public String createAccount(String username, String password, String confirmPassword, String displayName)
    {
        String errorMessage = "";

        //Check if any field is empty
        if(username.equals("") || password.equals("") || confirmPassword.equals("") || displayName.equals(""))
        {
            errorMessage += "One or more fields is empty\n";
        }

        //Check limit on display name
        if(displayName.length() > 30)
        {
            errorMessage += "Display name cannot be more than 30 characters\n";
        }

        //Check if password has at least 8 characters
        if(password.length() < 8)
        {
            errorMessage += "Password needs to be at least 8 characters\n";
        }

        //Check if password and confirm password are the same
        if(!password.equals(confirmPassword))
        {
            errorMessage += "Password and Confirm Password not the same";
        }

        //Save new account information with default settings if everything is good to go
        if(errorMessage.equals(""))
        {
            saveNewAccount(username, password, displayName);
        }

        return errorMessage;
    }

    public void saveNewAccount(String username, String password, String displayName)
    {
        //When writing to events file, need to specify that append is true
        try
        {
            String fileName = "Account/" + username + "_info.csv";
            FileWriter newUserFile = new FileWriter(new File(fileName));

            newUserFile.write(String.format("%s,%s\n", "Setting", "Value"));
            newUserFile.write(String.format("%s,%s\n", "Username", username));
            newUserFile.write(String.format("%s,%s\n", "Password", password));
            newUserFile.write(String.format("%s,%s\n", "Display Name", displayName));
            newUserFile.write(String.format("%s,%s\n", "Welcome Page Shown", "No"));

            newUserFile.close();

            String eventFileName = "Account/" + username + "_events.csv";
            FileWriter newEventsFile = new FileWriter(new File(eventFileName));
            newEventsFile.write(String.format("%s,%s,%s,%s,%s\n", "Event", "Date", "Location", "Category", "Description"));
            newEventsFile.close();

            String categoryFileName = "Account/" + username + "_categories.csv";
            FileWriter newCategoriesFile = new FileWriter(new File(categoryFileName));
            newCategoriesFile.write(String.format("%s,%s\n", "Category", "Color"));
            newCategoriesFile.close();
        }
        catch(IOException e)
        {
            System.out.println("Error: cannot save new account");
            e.printStackTrace();
        }
    }
    public User getUser() {
        return user;
    }
}
