package Application.Controller;

//Application.Controller for the Settings view
//Settings view will include
    //Editable fields
        //Username
        //Password
        //Name
        //Welcome page shown

import Application.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField usernameSettings, displayNameSettings, showPassword, securityQuestionSettings, securityQuestionAnswerSettings;

    @FXML
    private PasswordField passwordSettings;

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private RadioButton welcomePageShow, welcomePageNotShow, lightTheme, darkTheme;

    @FXML
    private ToggleGroup welcomePage, dateFormatOption, themeOption;

    @FXML
    private Label settingsLabel, accountSettingsLabel, preferenceSettingsLabel, themeLabel, accountSaveMessage, preferenceSaveMessage, logoutMessage;

    @FXML
    private Label passwordSettingsError, displayNameSettingsError;

    @FXML
    private GridPane accountSettings;

    @FXML
    private VBox preferenceSettings;

    @FXML
    private HBox numbersHbox, monthHbox, dayHbox;

    @FXML
    private Button editAccountButton, saveAccountButton, editPreferenceButton, savePreferenceButton;

    @FXML
    private Pane toolbarPane;

    private String username = Main.login.getUser().getUsername();
    private String password = Main.login.getUser().getPassword();
    private String displayName = Main.login.getUser().getDisplayName();
    private String securityQuestion = Main.login.getUser().getSecurityQuestion();
    private String securityQuestionAnswer = Main.login.getUser().getSecurityQuestionAnswer();
    private String welcomePageShown = Main.login.getUser().getWelcomePageShown();
    private String dateFormat = Main.login.getUser().getDateFormat();
    private String theme = Main.login.getUser().getTheme();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try
        {
            Node toolbar = FXMLLoader.load(getClass().getResource("../View/Toolbar.fxml"));
            toolbarPane.getChildren().add(toolbar);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //resetRequirementsMessages();
        setStyleFromTheme();

        fillFields();
        setAccountFieldsDisable(true);
        setPreferenceFieldsDisable(true);

        showPassword.setVisible(false);
        logoutMessage.setVisible(false);

        accountSaveMessage.setText("");
        preferenceSaveMessage.setText("");
    }

    public void showPassword()
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

    public void editAccountSettings()
    {
        resetRequirementsMessages();
        accountSaveMessage.setText("");
        editAccountButton.setDisable(true);
        saveAccountButton.setDisable(false);
        setAccountFieldsDisable(false);
    }

    public void editPreferenceSettings()
    {
        //resetRequirementsMessages();
        preferenceSaveMessage.setText("");
        editPreferenceButton.setDisable(true);
        savePreferenceButton.setDisable(false);
        setPreferenceFieldsDisable(false);
    }

    public void saveAccountSettings()
    {
        this.username = usernameSettings.getText();
        this.displayName = displayNameSettings.getText();
        this.securityQuestion = securityQuestionSettings.getText();
        this.securityQuestionAnswer = securityQuestionAnswerSettings.getText();
        this.welcomePageShown = ((RadioButton) welcomePage.getSelectedToggle()).getText();

        //Get the new password from the field that is visible to get the latest change
        if(passwordSettings.isVisible())
        {
            this.password = passwordSettings.getText();
        }
        else
        {
            this.password = showPassword.getText();
        }

        if(requirementsCheck())
        {
            return;
        }

        saveSettings("account");

        editAccountButton.setDisable(false);
        setAccountFieldsDisable(true);
    }

    public void savePreferenceSettings()
    {
        List<String> newSettings = new ArrayList<String>();

        String oldDateFormat = this.dateFormat;
        this.dateFormat = ((RadioButton) dateFormatOption.getSelectedToggle()).getText();
        this.theme = ((RadioButton) themeOption.getSelectedToggle()).getText();

        saveSettings("preference");
        Main.login.getUser().updateDateFormatOfEvents(oldDateFormat);

        editPreferenceButton.setDisable(false);
        setPreferenceFieldsDisable(true);

        setStyleFromTheme();
    }

    public void cancelSettings()
    {
        editAccountButton.setDisable(false);
        editPreferenceButton.setDisable(false);

        fillFields();
        resetRequirementsMessages();
        setAccountFieldsDisable(true);
        setPreferenceFieldsDisable(true);
    }

    public void logout()
    {
        Main.login.getUser().setLogout("Yes");
        if(!Main.login.getUser().saveSettingsToFile(this.username))
        {
            logoutMessage.setVisible(true);
            return;
        }

        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
            root.getStylesheets().add(getClass().getResource(getThemeCSS()).toExternalForm());
            Main.stage.setScene(new Scene(root));
            Main.stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void setStyleFromTheme()
    {
        Color color = getColorFromTheme();

        settingsLabel.setTextFill(color);
        accountSettingsLabel.setTextFill(color);
        showPasswordCheckBox.setTextFill(color);
        welcomePageShow.setTextFill(color);
        welcomePageNotShow.setTextFill(color);
        preferenceSettingsLabel.setTextFill(color);
        lightTheme.setTextFill(color);
        themeLabel.setTextFill(color);
        darkTheme.setTextFill(color);
        accountSaveMessage.setTextFill(color);
        preferenceSaveMessage.setTextFill(color);

        Color passwordErrorColor = (Color)passwordSettingsError.getTextFill();
        Color displayErrorColor = (Color)displayNameSettingsError.getTextFill();

        ObservableList<Node> accountSettingsChildren = accountSettings.getChildren();
        for(Node accountSettingsChild : accountSettingsChildren)
        {
            if(accountSettingsChild.getClass().getSimpleName().equals("Label"))
            {
                ((Label)accountSettingsChild).setTextFill(color);
            }
        }

        passwordSettingsError.setTextFill(passwordErrorColor);
        displayNameSettingsError.setTextFill(displayErrorColor);

        ObservableList<Node> preferenceSettingsChildren = preferenceSettings.getChildren();
        for(Node preferenceSettingsChild : preferenceSettingsChildren)
        {
            if(preferenceSettingsChild.getClass().getSimpleName().equals("Label"))
            {
                ((Label)preferenceSettingsChild).setTextFill(color);
            }
        }

        ObservableList<Node> numberChildren = numbersHbox.getChildren();
        for(Node numberChild : numberChildren)
        {
            if(numberChild.getClass().getSimpleName().equals("RadioButton"))
            {
                ((RadioButton)numberChild).setTextFill(color);
            }
        }

        ObservableList<Node> monthChildren = monthHbox.getChildren();
        for(Node monthChild : monthChildren)
        {
            if(monthChild.getClass().getSimpleName().equals("RadioButton"))
            {
                ((RadioButton)monthChild).setTextFill(color);
            }
        }

        ObservableList<Node> dayChildren = dayHbox.getChildren();
        for(Node dayChild : dayChildren)
        {
            if(dayChild.getClass().getSimpleName().equals("RadioButton"))
            {
                ((RadioButton)dayChild).setTextFill(color);
            }
        }
    }

    private Color getColorFromTheme()
    {
        if(this.theme.equals("Light"))
        {
            anchorPane.setStyle("-fx-background-color: white;");
            return Color.BLACK;
        }
        else
        {
            anchorPane.setStyle("-fx-background-color: #31323e;");
            return Color.WHITE;
        }
    }

    private String getThemeCSS()
    {
        String themeCSS = "";

        if(this.theme.equals("Light"))
        {
            themeCSS = "../View/light_mode.css";
        }
        else
        {
            themeCSS = "../View/dark_mode.css";
        }

        return themeCSS;
    }

    private void fillFields()
    {
        usernameSettings.setText(this.username);
        passwordSettings.setText(this.password);
        showPassword.setText(this.password);
        displayNameSettings.setText(this.displayName);
        securityQuestionSettings.setText(this.securityQuestion);
        securityQuestionAnswerSettings.setText(this.securityQuestionAnswer);

        if(this.welcomePageShown.equals("Yes"))
        {
            welcomePage.selectToggle(welcomePageShow);
        }
        else
        {
            welcomePage.selectToggle(welcomePageNotShow);
        }

        for(Toggle dateFormatOption : dateFormatOption.getToggles())
        {
            if(((RadioButton)dateFormatOption).getText().equals(this.dateFormat))
            {
                this.dateFormatOption.selectToggle(dateFormatOption);
                break;
            }
        }

        if(this.theme.equals("Light"))
        {
            themeOption.selectToggle(lightTheme);
        }
        else
        {
            themeOption.selectToggle(darkTheme);
        }
    }

    private void setAccountFieldsDisable(boolean disable)
    {
        usernameSettings.setDisable(disable);
        passwordSettings.setDisable(disable);
        showPassword.setDisable(disable);
        displayNameSettings.setDisable(disable);
        securityQuestionSettings.setDisable(disable);
        securityQuestionAnswerSettings.setDisable(disable);
        welcomePageShow.setDisable(disable);
        welcomePageNotShow.setDisable(disable);
        saveAccountButton.setDisable(disable);
    }

    private void setPreferenceFieldsDisable(boolean disable)
    {
        numbersHbox.setDisable(disable);
        monthHbox.setDisable(disable);
        dayHbox.setDisable(disable);
        lightTheme.setDisable(disable);
        darkTheme.setDisable(disable);
        savePreferenceButton.setDisable(disable);
    }

    private void resetRequirementsMessages()
    {
        Color color = getColorFromTheme();
        passwordSettingsError.setText("Password must be at least 8 characters");
        passwordSettingsError.setTextFill(color);
        displayNameSettingsError.setText("Display Name can be at most 30 characters");
        displayNameSettingsError.setTextFill(color);
    }

    private boolean requirementsCheck()
    {
        boolean error = false;

        //Check if credentials are correct
        if(this.password.length() < 8)
        {
            passwordSettingsError.setText("Error: Password must be at least 8 characters");
            passwordSettingsError.setTextFill(Color.RED);
            error = true;
        }
        else
        {
            passwordSettingsError.setText("Password length satisfied");
            passwordSettingsError.setTextFill(Color.GREEN);
        }

        if(this.displayName.length() > 30)
        {
            displayNameSettingsError.setText("Error: Display Name must be at most 30 characters");
            displayNameSettingsError.setTextFill(Color.RED);
            error = true;
        }
        else
        {
            displayNameSettingsError.setText("Display Name is within length limits");
            displayNameSettingsError.setTextFill(Color.GREEN);
        }

        return error;
    }

    /*private void reloadStyleFromTheme()
    {
        if(this.oldTheme.equals("Light"))
        {
            anchorPane.getStylesheets().add(this.getClass().getResource(getThemeCSS()).toExternalForm());
        }
        else
        {
            anchorPane.getStylesheets().add(this.getClass().getResource(getThemeCSS()).toExternalForm());
        }
    }*/

    private void saveSettings(String settingToSave)
    {
        List<String> newSettings = new ArrayList<String>();

        newSettings.add(this.username);
        newSettings.add(this.password);
        newSettings.add(this.displayName);
        newSettings.add(this.securityQuestion);
        newSettings.add(this.securityQuestionAnswer);
        newSettings.add(this.welcomePageShown);
        newSettings.add(this.dateFormat);
        newSettings.add(this.theme);

        Main.login.getUser().saveSettings(newSettings);

        saveSettingsToFile(settingToSave);
    }

    private void saveSettingsToFile(String settingToSave)
    {
        if(Main.login.getUser().saveSettingsToFile(this.username))
        {
            if(settingToSave.equals("account"))
            {
                accountSaveMessage.setText("Saved successfully!");
            }
            else if(settingToSave.equals("preference"))
            {
                preferenceSaveMessage.setText("Saved successfully!");
            }
        }
        else
        {
            if(settingToSave.equals("account"))
            {
                accountSaveMessage.setText("Error: something went wrong, please try again");
                accountSaveMessage.setTextFill(Color.RED);
            }
            else if(settingToSave.equals("preference"))
            {
                preferenceSaveMessage.setText("Error: something went wrong, please try again");
                preferenceSaveMessage.setTextFill(Color.RED);
            }
        }
    }
}
