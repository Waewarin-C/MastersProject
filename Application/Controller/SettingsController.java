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
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    private Button editSettingsButton, saveSettingsButton, cancelSettingsButton, logoutButton;

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

        saveSettingsToFile(newSettings);
        saveSettingsToUser(newSettings);

        setFieldsDisable(true);
    }

    public void cancelSettings()
    {
        setFieldsDisable(true);

        usernameSettings.setText(oldUsername);
        passwordSettings.setText(oldPassword);
        displayNameSettings.setText(oldDisplayName);
        securityQuestionSettings.setText(oldSecurityQuestion);
        securityQuestionAnswerSettings.setText(oldSecurityQuestionAnswer);

        passwordSettingsError.setText("Password must be at least 8 characters");
        passwordSettingsError.setTextFill(Color.color(0, 0, 0));
        displayNameSettingsError.setText("Display Name can be at most 30 characters");
        displayNameSettingsError.setTextFill(Color.color(0, 0, 0));
    }

    public void logout()
    {

    }

    private void fillFields()
    {
        usernameSettings.setText(oldUsername);
        passwordSettings.setText(oldPassword);
        showPassword.setText(oldPassword);
        displayNameSettings.setText(oldDisplayName);
        securityQuestionSettings.setText(oldSecurityQuestion);
        securityQuestionAnswerSettings.setText(oldSecurityQuestionAnswer);

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

    private void saveSettingsToFile(List<String> newSettings)
    {
        try
        {
            //Change the info file name
            String oldFileName = "Account/" + oldUsername + "_info.csv";
            File oldFile = new File(oldFileName);

            String newFileName = "Account/" + newSettings.get(0) + "_info.csv";
            File newFile = new File(newFileName);
            oldFile.renameTo(newFile);

            FileWriter save = new FileWriter(oldFile);
            save.write(String.format("%s,%s\n", "Setting", "Value"));
            save.write(String.format("%s,%s\n", "Username", newSettings.get(0)));
            save.write(String.format("%s,%s\n", "Password", newSettings.get(1)));
            save.write(String.format("%s,%s\n", "Display Name", newSettings.get(2)));
            save.write(String.format("%s,%s\n", "Security Question", newSettings.get(3)));
            save.write(String.format("%s,%s\n", "Security Question Answer", newSettings.get(4)));
            save.write(String.format("%s,%s\n", "Welcome Page Shown", newSettings.get(5)));

            save.close();

            //Change the events file name
            oldFileName = "Account/" + oldUsername + "_events.csv";
            oldFile = new File(oldFileName);

            newFileName = "Account/" + newSettings.get(0) + "_events.csv";
            newFile = new File(newFileName);
            oldFile.renameTo(newFile);

            //Change the categories file name
            oldFileName = "Account/" + oldUsername + "_categories.csv";
            oldFile = new File(oldFileName);

            newFileName = "Account/" + newSettings.get(0) + "_categories.csv";
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

    private void saveSettingsToUser(List<String> newSettings)
    {
        Main.login.getUser().setUsername(newSettings.get(0));
        Main.login.getUser().setPassword(newSettings.get(1));
        Main.login.getUser().setDisplayName(newSettings.get(2));
        Main.login.getUser().setSecurityQuestion(newSettings.get(3));
        Main.login.getUser().setSecurityQuestionAnswer(newSettings.get(4));
        Main.login.getUser().setWelcomePageShown(newSettings.get(5));
    }
}
