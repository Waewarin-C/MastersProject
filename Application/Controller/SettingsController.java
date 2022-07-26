package Application.Controller;

//Application.Controller for the Settings view
//Settings view will include
    //Editable fields
        //Username
        //Password
        //Name

import Application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    TextField usernameSettings, displayNameSettings;

    @FXML
    PasswordField passwordSettings;

    @FXML
    RadioButton welcomePageShow, welcomePageNotShow;

    @FXML
    Label passwordSettingsError, displayNameSettingsError;

    @FXML
    Button editSettingsButton, saveSettingsButton, cancelSettingsButton, logoutButton;

    private String username = Main.login.getUser().getUsername();
    private String password = Main.login.getUser().getPassword();
    private String displayName = Main.login.getUser().getDisplayName();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        usernameSettings.setText(username);
        passwordSettings.setText(password);
        displayNameSettings.setText(displayName);

        passwordSettingsError.setText("");
        displayNameSettingsError.setText("");
    }

    public void editSettings(ActionEvent event)
    {
        usernameSettings.setDisable(false);
        passwordSettings.setDisable(false);
        displayNameSettings.setDisable(false);
    }

    public void saveSettings(ActionEvent event)
    {
        String password = passwordSettings.getText();
        String displayName = displayNameSettings.getText();

        //Check if credentials are correct
        if(password.length() < 8)
        {
            passwordSettingsError.setText("Password must be at least 8 characters");
        }

        if(displayName.length() > 30)
        {
            displayNameSettingsError.setText("Display name can be at most 30 characters");
        }
    }

    public void cancelSettings(ActionEvent event)
    {
        usernameSettings.setDisable(false);
        passwordSettings.setDisable(false);
        displayNameSettings.setDisable(false);

        usernameSettings.setText(username);
        passwordSettings.setText(password);
        displayNameSettings.setText(displayName);

        passwordSettingsError.setText("");
        displayNameSettingsError.setText("");
    }

    public void logout(ActionEvent event)
    {

    }
}
