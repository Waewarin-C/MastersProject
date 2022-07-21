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
import java.util.Scanner;

public class Account {

    HashMap<String, User> accounts;

    public Account()
    {
        this.accounts = new HashMap<String, User>();
    }

    public String login(String username, String password)
    {
        String errorMessage = "";

        //Check if username is incorrect
        if(!accounts.containsKey(username))
        {
            errorMessage = "Incorrect username entered";
        }

        //Check if password is incorrect
        if(!accounts.get(username).password.equals(password))
        {
            errorMessage = "Incorrect password entered";
        }

        //Grab user information and settings are store in User object if credentials are correct
        if(errorMessage.equals(""))
        {
            storeUserInfo(username);
        }

        return errorMessage;
    }

    public void storeUserInfo(String username)
    {
        Scanner scan = null;
        try
        {
            String fileName = "../Accounts/" + username;
            scan = new Scanner(new File(fileName));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            scan.close();
        }
    }

    public String createAccount(String username, String password, String confirmPassword, String displayName)
    {
        String errorMessage = "";

        //Check if username is already taken
        if(accounts.containsKey(username))
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
    public HashMap<String, User> getAccounts() {
        return accounts;
    }
}
