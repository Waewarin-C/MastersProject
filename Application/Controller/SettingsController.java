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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    GridPane settingsGrid;

    @FXML
    TextField usernameSettings, displayNameSettings;

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

    private String username = Main.login.getUser().getUsername();
    private String password = Main.login.getUser().getPassword();
    private String displayName = Main.login.getUser().getDisplayName();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        usernameSettings.setText(username);
        passwordSettings.setText(password);
        displayNameSettings.setText(displayName);

        //Add more logic to this
        welcomePage.selectToggle(welcomePageShow);
    }

    public void showPassword(ActionEvent event)
    {
        boolean isFieldDisabled = passwordSettings.isDisable();

        if(showPasswordCheckBox.isSelected())
        {
            settingsGrid.getChildren().remove(passwordSettings);

            TextField passwordShow = new TextField(password);
            passwordShow.setFont(Font.font("Berlin Sans FB", 14));
            passwordShow.setPrefWidth(350);
            passwordShow.setPrefHeight(30);
            settingsGrid.add(passwordShow, 1,1);

            if(isFieldDisabled)
            {
                passwordShow.setDisable(true);
            }
        }
        else
        {
            int index = settingsGrid.getChildren().size()-1;
            settingsGrid.getChildren().remove(index);
            settingsGrid.add(passwordSettings, 1,1);

            if(isFieldDisabled)
            {
                passwordSettings.setDisable(true);
            }
        }
    }

    public void editSettings(ActionEvent event)
    {
        usernameSettings.setDisable(false);
        passwordSettings.setDisable(false);
        displayNameSettings.setDisable(false);
        welcomePageShow.setDisable(false);
        welcomePageNotShow.setDisable(false);
    }

    public void saveSettings(ActionEvent event)
    {
        String username = usernameSettings.getText();
        String password = passwordSettings.getText();
        String displayName = displayNameSettings.getText();
        RadioButton button = (RadioButton) welcomePage.getSelectedToggle();

        //Check if credentials are correct
        if(password.length() < 8)
        {
            passwordSettingsError.setText("Error: Password must be at least 8 characters");
            passwordSettingsError.setTextFill(Color.color(255,0,0));
        }

        if(displayName.length() > 30)
        {
            displayNameSettingsError.setText("Error: Display Name can be at most 30 characters");
            displayNameSettingsError.setTextFill(Color.color(255, 0, 0));
        }
    }

    public void cancelSettings(ActionEvent event)
    {
        usernameSettings.setDisable(true);
        passwordSettings.setDisable(true);
        displayNameSettings.setDisable(true);
        welcomePageShow.setDisable(true);
        welcomePageNotShow.setDisable(true);

        usernameSettings.setText(username);
        passwordSettings.setText(password);
        displayNameSettings.setText(displayName);

        passwordSettingsError.setText("Password must be at least 8 characters");
        passwordSettingsError.setTextFill(Color.color(0, 0, 0));
        displayNameSettingsError.setText("Display Name must be at most 30 characters");
        displayNameSettingsError.setTextFill(Color.color(0, 0, 0));
    }

    public void logout(ActionEvent event)
    {

    }
}
