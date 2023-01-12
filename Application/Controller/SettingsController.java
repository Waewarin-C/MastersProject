package Application.Controller;

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

/**
 * The SettingsController interacts with the Settings.fxml file
 * Allows the user to edit account and preferences as well as
 * logout.
 *
 * @author Waewarin Chindarassami
 */

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
    private Label settingsLabel, accountSettingsLabel, preferenceSettingsLabel, themeLabel, saveMessage, logoutMessage;

    @FXML
    private Label passwordRequirement, displayNameRequirement;

    @FXML
    private GridPane accountSettings;

    @FXML
    private VBox preferenceSettings;

    @FXML
    private HBox numbersHbox, monthHbox, dayHbox;

    @FXML
    private Button saveSettingsButton, cancelSettingsButton;

    @FXML
    private Pane navigationPane;

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
            Node navigation = FXMLLoader.load(getClass().getResource("../View/Navigation.fxml"));
            navigationPane.getChildren().add(navigation);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        if(Main.login.getUser().getEditRequirementsStatus().getSettingsPageReloaded())
        {
            Main.login.getUser().getEditRequirementsStatus().setSettingsPageReloaded(false);
            saveMessage.setText("Saved successfully!");
        }
        else
        {
            resetRequirementsMessages();
            saveMessage.setText("");
        }

        setStyleFromTheme();

        fillFields();
        setFieldsDisable(true);

        showPassword.setVisible(false);
        logoutMessage.setVisible(false);


    }

    /**
     * Shows the password when the user checks the show password checkbox
     * Hide password when the user unchecks the show password checkbox
     */
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

    /**
     * Sets up the page for editing the settings
     */
    public void editSettings()
    {
        resetRequirementsMessages();
        saveMessage.setText("");
        saveSettingsButton.setDisable(false);
        cancelSettingsButton.setDisable(false);
        setFieldsDisable(false);
    }

    /**
     * Saves the settings
     */
    public void saveSettings()
    {
        String oldUsername = this.username;
        this.username = usernameSettings.getText();
        this.displayName = displayNameSettings.getText();
        this.securityQuestion = securityQuestionSettings.getText();
        this.securityQuestionAnswer = securityQuestionAnswerSettings.getText();
        this.welcomePageShown = ((RadioButton) welcomePage.getSelectedToggle()).getText();

        String oldDateFormat = this.dateFormat;
        this.dateFormat = ((RadioButton) dateFormatOption.getSelectedToggle()).getText();

        String oldTheme = this.theme;
        this.theme = ((RadioButton) themeOption.getSelectedToggle()).getText();

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

        saveSettingsToUser();
        saveSettingsToFile(oldUsername);
        Main.login.getUser().updateDateFormatOfEvents(oldDateFormat);

        setFieldsDisable(true);

        setStyleFromTheme();
        if(!oldTheme.equals(this.theme))
        {
            reloadPage();
        }
    }

    /**
     * Cancels the editing of settings
     */
    public void cancelSettings()
    {
        fillFields();
        resetRequirementsMessages();
        setFieldsDisable(true);
    }

    /**
     * Logs out of account
     */
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

    /*
     * Sets the style of this view based on the theme
     */
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
        saveMessage.setTextFill(color);

        ObservableList<Node> accountSettingsChildren = accountSettings.getChildren();
        for(Node accountSettingsChild : accountSettingsChildren)
        {
            if(accountSettingsChild.getClass().getSimpleName().equals("Label"))
            {
                ((Label)accountSettingsChild).setTextFill(color);
            }
        }

        passwordRequirement.setText(Main.login.getUser().getEditRequirementsStatus().getCurrentPasswordRequirementStatus());
        passwordRequirement.setTextFill(Main.login.getUser().getEditRequirementsStatus().getCurrentPasswordRequirementColor());
        displayNameRequirement.setText(Main.login.getUser().getEditRequirementsStatus().getCurrentDisplayNameRequirementStatus());
        displayNameRequirement.setTextFill(Main.login.getUser().getEditRequirementsStatus().getCurrentDisplayNameRequirementColor());

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

    /*
     * Sets the anchorPane style based on the theme
     * Gets the color of the labels based on the theme
     */
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

    /*
     * Get the theme CSS depending on the theme
     */
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

    /*
     * Fills all the fields with the current settings
     */
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

    /*
     * Disable or enable the fields for when the user clicks edit or cancels
     */
    private void setFieldsDisable(boolean disable)
    {
        usernameSettings.setDisable(disable);
        passwordSettings.setDisable(disable);
        showPassword.setDisable(disable);
        displayNameSettings.setDisable(disable);
        securityQuestionSettings.setDisable(disable);
        securityQuestionAnswerSettings.setDisable(disable);
        welcomePageShow.setDisable(disable);
        welcomePageNotShow.setDisable(disable);
        numbersHbox.setDisable(disable);
        monthHbox.setDisable(disable);
        dayHbox.setDisable(disable);
        lightTheme.setDisable(disable);
        darkTheme.setDisable(disable);
        saveSettingsButton.setDisable(disable);
        cancelSettingsButton.setDisable(disable);
    }

    /*
     * Resets the password and display name requirements messages
     */
    private void resetRequirementsMessages()
    {
        Color color = getColorFromTheme();
        passwordRequirement.setText("Password must be at least 8 characters");
        passwordRequirement.setTextFill(color);
        displayNameRequirement.setText("Display Name can be at most 30 characters");
        displayNameRequirement.setTextFill(color);

        Main.login.getUser().getEditRequirementsStatus().setCurrentPasswordRequirementStatus("Password must be at least 8 characters");
        Main.login.getUser().getEditRequirementsStatus().setCurrentPasswordRequirementColor(color);
        Main.login.getUser().getEditRequirementsStatus().setCurrentDisplayNameRequirementStatus("Display Name can be at most 30 characters");
        Main.login.getUser().getEditRequirementsStatus().setCurrentDisplayNameRequirementColor(color);
    }

    /*
     * Checks if all the requirements are satisfied
     */
    private boolean requirementsCheck()
    {
        boolean error = false;

        if(this.password.length() < 8)
        {
            passwordRequirement.setText("Error: Password must be at least 8 characters");
            passwordRequirement.setTextFill(Color.RED);

            Main.login.getUser().getEditRequirementsStatus().setCurrentPasswordRequirementStatus("Error: Password must be at least 8 characters");
            Main.login.getUser().getEditRequirementsStatus().setCurrentPasswordRequirementColor(Color.RED);

            error = true;
        }
        else
        {
            passwordRequirement.setText("Password length satisfied");
            passwordRequirement.setTextFill(Color.GREEN);

            Main.login.getUser().getEditRequirementsStatus().setCurrentPasswordRequirementStatus("Password length satisfied");
            Main.login.getUser().getEditRequirementsStatus().setCurrentPasswordRequirementColor(Color.GREEN);
        }

        if(this.displayName.length() > 30)
        {
            displayNameRequirement.setText("Error: Display Name must be at most 30 characters");
            displayNameRequirement.setTextFill(Color.RED);

            Main.login.getUser().getEditRequirementsStatus().setCurrentDisplayNameRequirementStatus("Error: Display Name must be at most 30 characters");
            Main.login.getUser().getEditRequirementsStatus().setCurrentDisplayNameRequirementColor(Color.RED);

            error = true;
        }
        else
        {
            displayNameRequirement.setText("Display Name is within length limits");
            displayNameRequirement.setTextFill(Color.GREEN);

            Main.login.getUser().getEditRequirementsStatus().setCurrentDisplayNameRequirementStatus("Display Name is within length limits");
            Main.login.getUser().getEditRequirementsStatus().setCurrentDisplayNameRequirementColor(Color.GREEN);
        }

        return error;
    }

    /*
     * Saves the settings to User
     */
    private void saveSettingsToUser()
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
    }

    /*
     * Saves the settings to the user's information file
     */
    private void saveSettingsToFile(String oldUsername)
    {
        if(Main.login.getUser().saveSettingsToFile(oldUsername))
        {
            saveMessage.setText("Saved successfully!");
        }
        else
        {
            saveMessage.setText("Error: something went wrong, please try again");
            saveMessage.setTextFill(Color.RED);
        }
    }

    /*
     * Reloads the settings page when the user changes theme
     */
    private void reloadPage()
    {
        Main.login.getUser().getEditRequirementsStatus().setSettingsPageReloaded(true);
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("../View/Settings.fxml"));
            root.getStylesheets().add(getClass().getResource(getThemeCSS()).toExternalForm());
            Main.stage.setScene(new Scene(root));
            Main.stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
