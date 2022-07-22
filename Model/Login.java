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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

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
        }

        //Check if password is incorrect
        if(!this.user.getPassword().equals(password))
        {
            errorMessage = "Incorrect password entered";
        }

        return errorMessage;
    }

    public String createAccount(String username, String password, String confirmPassword, String displayName)
    {
        String errorMessage = "";

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
            String fileName = "../Accounts/" + username;
            FileWriter newFile = new FileWriter(new File(fileName));

            newFile.write(String.format("%s,%s\n", "Setting", "Value"));
            newFile.write(String.format("%s,%s\n", "Username", username));
            newFile.write(String.format("%s,%s\n", "Password", password));
            newFile.write(String.format("%s,%s", "Display Name", displayName));

            newFile.close();
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
