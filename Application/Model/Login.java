package Application.Model;

//Takes care of
    //Login
        //Checks that the credentials are correct
        //Forgot password
            //Presents user with the security question they have in their settings
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
import java.util.List;

public class Login {

    private User user;

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
            errorMessage = "Wrong username entered";
            return errorMessage;
        }

        //Check if password is incorrect
        if(!this.user.getPassword().equals(password))
        {
            errorMessage = "Wrong password entered";
            return errorMessage;
        }

        return errorMessage;
    }

    public String createAccount(List<String> information)
    {
        String username = information.get(0);
        String password = information.get(1);
        String confirmPassword = information.get(2);
        String displayName = information.get(3);
        String securityQuestion = information.get(4);
        String securityQuestionAnswer = information.get(5);

        String errorMessage = "";
        boolean emptyField = username.equals("") || password.equals("") || confirmPassword.equals("") || displayName.equals("") || securityQuestion.equals("") || securityQuestionAnswer.equals("");
        //Check if any field is empty
        if(emptyField)
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
            information.remove(2);
            saveNewAccount(information);
            saveNewUser(information);
        }

        return errorMessage;
    }

    public User getUser() {
        return user;
    }

    private void saveNewAccount(List<String> information)
    {
        try
        {
            String fileName = "Account/" + information.get(0) + "_info.csv";
            FileWriter newUserFile = new FileWriter(new File(fileName));

            newUserFile.write(String.format("%s,%s\n", "Setting", "Value"));
            newUserFile.write(String.format("%s,%s\n", "Username", information.get(0)));
            newUserFile.write(String.format("%s,%s\n", "Password", information.get(1)));
            newUserFile.write(String.format("%s,%s\n", "Display Name", information.get(2)));
            newUserFile.write(String.format("%s,%s\n", "Security Question", information.get(3)));
            newUserFile.write(String.format("%s,%s\n", "Security Question Answer", information.get(4)));
            newUserFile.write(String.format("%s,%s\n", "Welcome Page Shown", "No"));

            newUserFile.close();

            String eventFileName = "Account/" + information.get(0) + "_events.csv";
            FileWriter newEventsFile = new FileWriter(new File(eventFileName));
            newEventsFile.write(String.format("%s,%s,%s,%s,%s\n", "Event", "Date", "Location", "Category", "Description"));
            newEventsFile.close();

            String categoryFileName = "Account/" + information.get(0) + "_categories.csv";
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

    private void saveNewUser(List<String> information)
    {
        this.user.setUsername(information.get(0));
        this.user.setPassword(information.get(1));
        this.user.setDisplayName(information.get(2));
        this.user.setSecurityQuestion(information.get(3));
        this.user.setSecurityQuestionAnswer(information.get(4));
    }
}
