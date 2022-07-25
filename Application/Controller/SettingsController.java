package Application.Controller;

//Application.Controller for the Settings view
//Settings view will include
    //Editable fields
        //Username
        //Password
        //Name

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

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

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        passwordSettingsError.setText("");
        displayNameSettingsError.setText("");
    }
    public void saveSettings()
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

    public void cancelSettings()
    {

    }


}
