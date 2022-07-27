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
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    GridPane settingsGrid;

    @FXML
    TextField usernameSettings, displayNameSettings, passwordShow;

    @FXML
    PasswordField passwordSettings;

    @FXML
    CheckBox showPasswordCheckBox;

    @FXML
    RadioButton welcomePageShow, welcomePageNotShow;

    @FXML
    ToggleGroup welcomePage;

    @FXML
    Label passwordSettingsError, displayNameSettingsError;

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

        passwordShow.setVisible(false);
    }

    public void showPassword(ActionEvent event)
    {
        if(showPasswordCheckBox.isSelected())
        {
            settingsGrid.getChildren().remove(passwordSettings);

            passwordShow = new TextField(oldPassword);
            passwordShow.setFont(Font.font("Berlin Sans FB", 14));
            passwordShow.setPrefWidth(350);
            passwordShow.setPrefHeight(30);

            settingsGrid.add(passwordShow, 1,1);
        }
        else
        {
            int index = settingsGrid.getChildren().size()-1;
            settingsGrid.getChildren().remove(index);
            settingsGrid.add(passwordSettings, 1,1);
        }
    }

    public void editSettings(ActionEvent event)
    {
        usernameSettings.setDisable(false);
        passwordSettings.setDisable(false);
        passwordShow.setDisable(false);
        displayNameSettings.setDisable(false);
        welcomePageShow.setDisable(false);
        welcomePageNotShow.setDisable(false);
    }

    public void saveSettings(ActionEvent event)
    {
        String newUsername = usernameSettings.getText();
        String newPassword = passwordSettings.getText();
        String newDisplayName = displayNameSettings.getText();
        RadioButton newWelcomePageShown = (RadioButton) welcomePage.getSelectedToggle();

        boolean error = false;

        //Check if credentials are correct
        if(newPassword.length() < 8)
        {
            passwordSettingsError.setText("Error: Password must be at least 8 characters");
            passwordSettingsError.setTextFill(Color.color(255,0,0));
            error = true;
        }

        if(newDisplayName.length() > 30)
        {
            displayNameSettingsError.setText("Error: Display Name can be at most 30 characters");
            displayNameSettingsError.setTextFill(Color.color(255, 0, 0));
            error = true;
        }

        if(error)
        {
            return;
        }

        saveSettingsToFile(newUsername, newPassword, newDisplayName, newWelcomePageShown.getText());
        saveSettingsToUser(newUsername, newPassword, newDisplayName, newWelcomePageShown.getText());
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
        }
        catch(IOException e)
        {
            System.out.println("Error: cannot save new account");
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
        displayNameSettingsError.setText("Display Name must be at most 30 characters");
        displayNameSettingsError.setTextFill(Color.color(0, 0, 0));
    }

    public void logout(ActionEvent event)
    {

    }

    private void fillFields()
    {
        usernameSettings.setText(oldUsername);
        passwordSettings.setText(oldPassword);
        passwordShow.setText(oldPassword);
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
        passwordShow.setDisable(true);
        displayNameSettings.setDisable(true);
        welcomePageShow.setDisable(true);
        welcomePageNotShow.setDisable(true);
    }
}
