package Application.Controller;

//Application.Controller for the Login view
//Login view will include
    //Textfield
        //Username
        //Password
    //Login button
    //Error message will pop up if user types in wrong credentials
    //Redirects to Home view

import Application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    TextField loginUsernameField, loginShowPassword;

    @FXML
    TextField signUpUsernameField, signUpDisplayNameField, signUpShowPassword, confirmPasswordShow;

    @FXML
    PasswordField loginPasswordField, signUpPasswordField, confirmPasswordField;

    @FXML
    CheckBox loginShowPasswordCheckBox, signUpShowPasswordCheckBox;

    @FXML
    Button loginButton, signUpButton;

    @FXML
    Label loginErrorMessage, signUpErrorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginShowPassword.setVisible(false);
        signUpShowPassword.setVisible(false);
        confirmPasswordShow.setVisible(false);

        loginErrorMessage.setText("");
        signUpErrorMessage.setText("");
    }

    public void showPassword(ActionEvent event)
    {
        if(loginShowPasswordCheckBox.isSelected())
        {
            loginPasswordField.setVisible(false);
            loginShowPassword.setText(loginPasswordField.getText());
            loginShowPassword.setVisible(true);
        }
        else if(!loginShowPasswordCheckBox.isSelected())
        {
            loginShowPassword.setVisible(false);
            loginPasswordField.setText(loginShowPassword.getText());
            loginPasswordField.setVisible(true);
        }

        if(signUpShowPasswordCheckBox.isSelected())
        {
            signUpPasswordField.setVisible(false);
            signUpShowPassword.setText(signUpPasswordField.getText());
            signUpShowPassword.setVisible(true);

            confirmPasswordField.setVisible(false);
            confirmPasswordShow.setText(confirmPasswordField.getText());
            confirmPasswordShow.setVisible(true);
        }
        else if(!signUpShowPasswordCheckBox.isSelected())
        {
            signUpShowPassword.setVisible(false);
            signUpPasswordField.setText(signUpShowPassword.getText());
            signUpPasswordField.setVisible(true);

            confirmPasswordShow.setVisible(false);
            confirmPasswordField.setText(confirmPasswordShow.getText());
            confirmPasswordField.setVisible(true);
        }
    }

    public void login(ActionEvent event)
    {
        String username = loginUsernameField.getText();
        String password = "";

        //Get the password from the field that is visible to get the latest change
        if(loginPasswordField.isVisible())
        {
            password = loginPasswordField.getText();
        }
        else
        {
            password = loginShowPassword.getText();
        }

        String errorMessage = Main.login.login(username, password);
        if(!errorMessage.equals(""))
        {
            loginErrorMessage.setText(errorMessage);
        }
        else
        {
            continueToNextPage();
        }
    }

    public void signUp(ActionEvent event)
    {
        String username = signUpUsernameField.getText();
        String displayName = signUpDisplayNameField.getText();

        String password = "";
        String confirmPassword = "";
        //Get the password and confirm password from the field that is visible to get the latest change
        if(signUpPasswordField.isVisible() && confirmPasswordField.isVisible())
        {
            password = signUpPasswordField.getText();
            confirmPassword = confirmPasswordField.getText();
        }
        else
        {
            password = signUpShowPassword.getText();
            confirmPassword = confirmPasswordShow.getText();
        }

        String errorMessage = Main.login.createAccount(username, password, confirmPassword, displayName);
        if(!errorMessage.equals(""))
        {
            signUpErrorMessage.setText(errorMessage);
        }
        else
        {
            continueToNextPage();
        }
    }

    public void continueToNextPage()
    {
        String welcomePageShown = Main.login.getUser().getWelcomePageShown();

        if(welcomePageShown.equals("Yes"))
        {
            try
            {
                Parent root = FXMLLoader.load(getClass().getResource("../View/Welcome.fxml"));
                Main.stage.setScene(new Scene(root));
                Main.stage.show();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                Parent root = FXMLLoader.load(getClass().getResource("../View/Home.fxml"));
                Main.stage.setScene(new Scene(root));
                Main.stage.show();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
