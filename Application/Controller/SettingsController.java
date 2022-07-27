package Application.Controller;

//Application.Controller for the Settings view
//Settings view will include
    //Editable fields
        //Username
        //Password
        //Name
        //Welcome page shown

import Application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    GridPane settingsGrid;

    @FXML
    TextField usernameSettings, displayNameSettings, showPassword;

    @FXML
    PasswordField passwordSettings;

    @FXML
    CheckBox showPasswordCheckBox;

    @FXML
    RadioButton welcomePageShow, welcomePageNotShow;

    @FXML
    ToggleGroup welcomePage;

    @FXML
    Label passwordSettingsError, displayNameSettingsError, saveMessage;

    @FXML
    Button editSettingsButton, saveSettingsButton, cancelSettingsButton, logoutButton;

    @FXML
    Pane toolbarPane;

    //TextField passwordShow;

    private String oldUsername = Main.login.getUser().getUsername();
    private String oldPassword = Main.login.getUser().getPassword();
    private String oldDisplayName = Main.login.getUser().getDisplayName();
    private String oldWelcomePageShown = Main.login.getUser().getWelcomePageShown();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            Parent toolbar = FXMLLoader.load(getClass().getResource("../View/Toolbar.fxml"));
            toolbarPane.getChildren().add(toolbar);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        fillFields();
        disableFields();

        showPassword.setVisible(false);
        saveMessage.setText("");
    }

    public void showPassword(ActionEvent event)
    {
        if(showPasswordCheckBox.isSelected())
        {
            passwordSettings.setVisible(false);
            showPassword.setText(passwordSettings.getText());
            showPassword.setVisible(true);
        }
        else
        {
            showPassword.setVisible(false);
            passwordSettings.setText(showPassword.getText());
            passwordSettings.setVisible(true);
        }
    }

    public void editSettings(ActionEvent event)
    {
        usernameSettings.setDisable(false);
        passwordSettings.setDisable(false);
        showPassword.setDisable(false);
        displayNameSettings.setDisable(false);
        welcomePageShow.setDisable(false);
        welcomePageNotShow.setDisable(false);
    }

    public void saveSettings(ActionEvent event)
    {
        String newUsername = usernameSettings.getText();
        String newDisplayName = displayNameSettings.getText();
        String newWelcomePageShown = ((RadioButton) welcomePage.getSelectedToggle()).getText();

        //Get the new password from the field that is visible to get the latest change
        String newPassword;
        if(passwordSettings.isVisible())
        {
            newPassword = passwordSettings.getText();
        }
        else
        {
            newPassword = showPassword.getText();
        }

        boolean error = requirementsCheck(newPassword, newDisplayName);
        if(error)
        {
            return;
        }

        saveSettingsToFile(newUsername, newPassword, newDisplayName, newWelcomePageShown);
        saveSettingsToUser(newUsername, newPassword, newDisplayName, newWelcomePageShown);

        disableFields();
    }

    private boolean requirementsCheck(String newPassword, String newDisplayName)
    {
        boolean error = false;

        //Check if credentials are correct
        if(newPassword.length() < 8)
        {
            passwordSettingsError.setText("Error: Password must be at least 8 characters");
            passwordSettingsError.setTextFill(Color.color(1,0,0));
            error = true;
        }
        else
        {
            passwordSettingsError.setText("Password length satisfied");
            passwordSettingsError.setTextFill(Color.color(0,1,0));
        }

        if(newDisplayName.length() > 30)
        {
            displayNameSettingsError.setText("Error: Display Name must be at most 30 characters");
            displayNameSettingsError.setTextFill(Color.color(1, 0, 0));
            error = true;
        }
        else
        {
            displayNameSettingsError.setText("Display Name is within length limits");
            displayNameSettingsError.setTextFill(Color.color(0, 1, 0));
        }

        return error;
    }

    private void saveSettingsToFile(String newUsername, String newPassword, String newDisplayName, String newWelcomePageShown)
    {
        try
        {
            //Change the info file name
            String oldFileName = "Account/" + oldUsername + "_info.csv";
            File oldFile = new File(oldFileName);

            String newFileName = "Account/" + newUsername + "_info.csv";
            File newFile = new File(newFileName);
            oldFile.renameTo(newFile);

            FileWriter save = new FileWriter(oldFile);
            save.write(String.format("%s,%s\n", "Setting", "Value"));
            save.write(String.format("%s,%s\n", "Username", newUsername));
            save.write(String.format("%s,%s\n", "Password", newPassword));
            save.write(String.format("%s,%s\n", "Display Name", newDisplayName));
            save.write(String.format("%s,%s\n", "Welcome Page Shown", newWelcomePageShown));

            save.close();

            //Change the events file name
            oldFileName = "Account/" + oldUsername + "_events.csv";
            oldFile = new File(oldFileName);

            newFileName = "Account/" + newUsername + "_events.csv";
            newFile = new File(newFileName);
            oldFile.renameTo(newFile);

            //Change the categories file name
            oldFileName = "Account/" + oldUsername + "_categories.csv";
            oldFile = new File(oldFileName);

            newFileName = "Account/" + newUsername + "_categories.csv";
            newFile = new File(newFileName);
            oldFile.renameTo(newFile);

            saveMessage.setText("Saved successfully!");
        }
        catch(IOException e)
        {
            saveMessage.setText("Error: something went wrong, please try again");
            saveMessage.setTextFill(Color.color(1, 0, 0));
            System.out.println("Error: cannot save account settings");
            e.printStackTrace();
        }
    }

    private void saveSettingsToUser(String newUsername, String newPassword, String newDisplayName, String newWelcomePageShown)
    {
        Main.login.getUser().setUsername(newUsername);
        Main.login.getUser().setPassword(newPassword);
        Main.login.getUser().setDisplayName(newDisplayName);
        Main.login.getUser().setWelcomePageShown(newWelcomePageShown);
    }

    public void cancelSettings(ActionEvent event)
    {
        disableFields();

        usernameSettings.setText(oldUsername);
        passwordSettings.setText(oldPassword);
        displayNameSettings.setText(oldDisplayName);

        passwordSettingsError.setText("Password must be at least 8 characters");
        passwordSettingsError.setTextFill(Color.color(0, 0, 0));
        displayNameSettingsError.setText("Display Name can be at most 30 characters");
        displayNameSettingsError.setTextFill(Color.color(0, 0, 0));
    }

    public void logout(ActionEvent event)
    {

    }

    private void fillFields()
    {
        usernameSettings.setText(oldUsername);
        passwordSettings.setText(oldPassword);
        showPassword.setText(oldPassword);
        displayNameSettings.setText(oldDisplayName);

        if(oldWelcomePageShown.equals("Yes"))
        {
            welcomePage.selectToggle(welcomePageShow);
        }
        else
        {
            welcomePage.selectToggle(welcomePageNotShow);
        }
    }
    private void disableFields()
    {
        usernameSettings.setDisable(true);
        passwordSettings.setDisable(true);
        showPassword.setDisable(true);
        displayNameSettings.setDisable(true);
        welcomePageShow.setDisable(true);
        welcomePageNotShow.setDisable(true);
    }
}
