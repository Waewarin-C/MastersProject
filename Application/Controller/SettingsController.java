package Application.Controller;

//Application.Controller for the Settings view
//Settings view will include
    //Editable fields
        //Username
        //Password
        //Name
        //Welcome page shown

import Application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    private TextField usernameSettings, displayNameSettings, showPassword, securityQuestionSettings, securityQuestionAnswerSettings;

    @FXML
    private PasswordField passwordSettings;

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private RadioButton welcomePageShow, welcomePageNotShow;

    @FXML
    private ToggleGroup welcomePage;

    @FXML
    private Label passwordSettingsError, displayNameSettingsError, saveMessage;

    @FXML
    private Pane toolbarPane;

    private String oldUsername = Main.login.getUser().getUsername();
    private String oldPassword = Main.login.getUser().getPassword();
    private String oldDisplayName = Main.login.getUser().getDisplayName();
    private String oldSecurityQuestion = Main.login.getUser().getSecurityQuestion();
    private String oldSecurityQuestionAnswer = Main.login.getUser().getSecurityQuestionAnswer();
    private String oldWelcomePageShown = Main.login.getUser().getWelcomePageShown();

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

        Main.login.getUser().saveSettings(newSettings);
        saveSettingsToFile();

        setFieldsDisable(true);
    }

    public void cancelSettings()
    {
        fillFields();
        setFieldsDisable(true);

        passwordSettingsError.setText("Password must be at least 8 characters");
        passwordSettingsError.setTextFill(Color.rgb(0, 0, 0));
        displayNameSettingsError.setText("Display Name can be at most 30 characters");
        displayNameSettingsError.setTextFill(Color.rgb(0, 0, 0));
    }

    public void logout()
    {
        Main.login.getUser().setLogout("Yes");
        if(!Main.login.getUser().saveSettingsToFile(this.oldUsername))
        {
            saveMessage.setText("Error: something went wrong with logging out, please try again");
            saveMessage.setTextFill(Color.rgb(255, 0, 0));
            return;
        }

        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
            Main.stage.setScene(new Scene(root));
            Main.stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
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

        if(oldWelcomePageShown.equals("Yes"))
        {
            welcomePage.selectToggle(welcomePageShow);
        }
        else
        {
            welcomePage.selectToggle(welcomePageNotShow);
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
    }

    private boolean requirementsCheck(String newPassword, String newDisplayName)
    {
        boolean error = false;

        //Check if credentials are correct
        if(newPassword.length() < 8)
        {
            passwordSettingsError.setText("Error: Password must be at least 8 characters");
            passwordSettingsError.setTextFill(Color.rgb(255,0,0));
            error = true;
        }
        else
        {
            passwordSettingsError.setText("Password length satisfied");
            passwordSettingsError.setTextFill(Color.rgb(0,255,0));
        }

        if(newDisplayName.length() > 30)
        {
            displayNameSettingsError.setText("Error: Display Name must be at most 30 characters");
            displayNameSettingsError.setTextFill(Color.rgb(255, 0, 0));
            error = true;
        }
        else
        {
            displayNameSettingsError.setText("Display Name is within length limits");
            displayNameSettingsError.setTextFill(Color.rgb(0, 255, 0));
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
            saveMessage.setTextFill(Color.rgb(255, 0, 0));
        }
    }
}
