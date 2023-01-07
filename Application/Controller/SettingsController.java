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
    private ToggleGroup welcomePage, dateFormat, theme;

    @FXML
    private Label settingsLabel, passwordSettingsError, displayNameSettingsError, accountSettingsLabel, preferenceSettingsLabel, themeLabel, saveMessage;

    @FXML
    private GridPane accountSettings;

    @FXML
    private VBox preferenceSettings;

    @FXML
    private HBox numbersHbox, monthHbox, dayHbox;

    @FXML
    private Button saveSettingsButton, cancelSettingsButton;

    @FXML
    private Pane toolbarPane;

    private String oldUsername = Main.login.getUser().getUsername();
    private String oldPassword = Main.login.getUser().getPassword();
    private String oldDisplayName = Main.login.getUser().getDisplayName();
    private String oldSecurityQuestion = Main.login.getUser().getSecurityQuestion();
    private String oldSecurityQuestionAnswer = Main.login.getUser().getSecurityQuestionAnswer();
    private String oldWelcomePageShown = Main.login.getUser().getWelcomePageShown();
    private String oldDateFormat = Main.login.getUser().getDateFormat();
    private String oldTheme = Main.login.getUser().getTheme();

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

        resetRequirementsMessages();
        setStyleFromTheme();

        fillFields();
        setFieldsDisable(true);

        showPassword.setVisible(false);
        saveMessage.setText("");
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

    public void editSettings()
    {
        resetRequirementsMessages();
        saveMessage.setText("");
        setFieldsDisable(false);
    }

    public void saveSettings()
    {
        List<String> newSettings = new ArrayList<String>();

        String newUsername = usernameSettings.getText();
        String newDisplayName = displayNameSettings.getText();
        String newSecurityQuestion = securityQuestionSettings.getText();
        String newSecurityQuestionAnswer = securityQuestionAnswerSettings.getText();
        String newWelcomePageShown = ((RadioButton) welcomePage.getSelectedToggle()).getText();
        String newDateFormat = ((RadioButton) dateFormat.getSelectedToggle()).getText();
        String newTheme = ((RadioButton) theme.getSelectedToggle()).getText();

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

        newSettings.add(newUsername);
        newSettings.add(newPassword);
        newSettings.add(newDisplayName);
        newSettings.add(newSecurityQuestion);
        newSettings.add(newSecurityQuestionAnswer);
        newSettings.add(newWelcomePageShown);
        newSettings.add(newDateFormat);
        newSettings.add(newTheme);

        Main.login.getUser().saveSettings(newSettings);
        Main.login.getUser().updateDateFormatOfEvents(this.oldDateFormat);
        saveSettingsToFile();

        setFieldsDisable(true);
        setStyleFromTheme();
    }

    public void cancelSettings()
    {
        fillFields();
        resetRequirementsMessages();
        setFieldsDisable(true);
    }

    public void logout()
    {
        Main.login.getUser().setLogout("Yes");
        if(!Main.login.getUser().saveSettingsToFile(this.oldUsername))
        {
            saveMessage.setText("Error: something went wrong with logging out, please try again");
            saveMessage.setTextFill(Color.RED);
            return;
        }

        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
            root.getStylesheets().add(getClass().getResource("./View/light_mode.css").toExternalForm());
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
        saveMessage.setTextFill(color);

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
        if(this.oldTheme.equals("Light"))
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

    private void fillFields()
    {
        usernameSettings.setText(this.oldUsername);
        passwordSettings.setText(this.oldPassword);
        showPassword.setText(this.oldPassword);
        displayNameSettings.setText(this.oldDisplayName);
        securityQuestionSettings.setText(this.oldSecurityQuestion);
        securityQuestionAnswerSettings.setText(this.oldSecurityQuestionAnswer);

        if(this.oldWelcomePageShown.equals("Yes"))
        {
            welcomePage.selectToggle(welcomePageShow);
        }
        else
        {
            welcomePage.selectToggle(welcomePageNotShow);
        }

        for(Toggle dateFormatOption : dateFormat.getToggles())
        {
            if(((RadioButton)dateFormatOption).getText().equals(this.oldDateFormat))
            {
                dateFormat.selectToggle(dateFormatOption);
                break;
            }
        }

        if(this.oldTheme.equals("Light"))
        {
            theme.selectToggle(lightTheme);
        }
        else
        {
            theme.selectToggle(darkTheme);
        }
    }

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

    private void resetRequirementsMessages()
    {
        Color color = getColorFromTheme();
        passwordSettingsError.setText("Password must be at least 8 characters");
        passwordSettingsError.setTextFill(color);
        displayNameSettingsError.setText("Display Name can be at most 30 characters");
        displayNameSettingsError.setTextFill(color);
    }

    private boolean requirementsCheck(String newPassword, String newDisplayName)
    {
        boolean error = false;

        //Check if credentials are correct
        if(newPassword.length() < 8)
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

        if(newDisplayName.length() > 30)
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

    private void saveSettingsToFile()
    {
        if(Main.login.getUser().saveSettingsToFile(this.oldUsername))
        {
            saveMessage.setText("Saved successfully!");
        }
        else
        {
            saveMessage.setText("Error: something went wrong, please try again");
            saveMessage.setTextFill(Color.RED);
        }
    }
}
