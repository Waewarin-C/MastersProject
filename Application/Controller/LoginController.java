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
import com.sun.deploy.security.SelectableSecurityManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    TextField loginUsernameField, signUpUsernameField, signUpNameField;

    @FXML
    PasswordField loginPasswordField, signUpPasswordField, confirmPasswordField;

    @FXML
    Button loginButton, signUpButton;

    @FXML
    Label loginErrorMessage, signUpErrorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginErrorMessage.setText("");
        signUpErrorMessage.setText("");
    }

    public void login()
    {
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();

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

    public void signUp()
    {
        String username = signUpUsernameField.getText();
        String password = signUpPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String displayName = signUpNameField.getText();

        String errorMessage = Main.login.createAccount(username, password, confirmPassword, displayName);
        System.out.println(errorMessage);
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
        boolean welcomePageShown = Main.login.getUser().getWelcomePageShown();

        if(welcomePageShown)
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
