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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The LoginController interacts with the Login.fxml file.
 * Shows the login space for the existing account and the
 * sign up space if there is no existing account.
 *
 * @author Waewarin Chindarassami
 */

public class LoginController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField loginUsernameField, loginShowPassword, loginSecurityQuestionAnswer;

    @FXML
    private TextField signUpUsernameField, signUpDisplayNameField, signUpShowPassword, confirmPasswordShow, signUpSecurityQuestionField, signUpAnswerField;

    @FXML
    private PasswordField loginPasswordField, signUpPasswordField, confirmPasswordField;

    @FXML
    private CheckBox loginShowPasswordCheckBox, signUpShowPasswordCheckBox;

    @FXML
    private Label nameOfProgram, welcomeBack, newHere, signUpPrompt;

    @FXML
    private Label loginErrorMessage, signUpErrorMessage, loginSecurityQuestion, securityQuestionMessage;

    @FXML
    private GridPane loginGrid, securityQuestionGrid, signUpGrid;

    private final String theme = Main.login.getUser().getTheme();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        setStyleFromTheme();

        loginShowPassword.setVisible(false);
        signUpShowPassword.setVisible(false);
        confirmPasswordShow.setVisible(false);
        securityQuestionGrid.setVisible(false);

        loginErrorMessage.setText("");
        signUpErrorMessage.setText("");
    }

    /**
     * Shows the password when the user checks the show password checkbox
     * Hide password when the user unchecks the show password checkbox
     */
    public void showPassword()
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

    /**
     * Shows security question for when user forgets the password
     */
    public void forgotPassword()
    {
        securityQuestionGrid.setVisible(true);
        loginSecurityQuestion.setText(Main.login.getUser().getSecurityQuestion());
        securityQuestionMessage.setText("");
    }

    /**
     * Checks if the security question answer is correct
     */
    public void checkSecurityQuestionAnswer()
    {
        String answerEntered = loginSecurityQuestionAnswer.getText();
        String answerSaved = Main.login.getUser().getSecurityQuestionAnswer();

        if(answerEntered.equalsIgnoreCase(answerSaved))
        {
            continueToNextPage();
        }
        else
        {
            securityQuestionMessage.setText("Sorry, incorrect answer");
        }
    }

    /**
     * Login to the account
     */
    public void login()
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
            loginErrorMessage.setTextFill(Color.RED);
        }
        else
        {
            nextStep();
        }
    }

    /**
     * Sign up for a new account
     */
    public void signUp()
    {
        String username = signUpUsernameField.getText();
        String displayName = signUpDisplayNameField.getText();
        String securityQuestion = signUpSecurityQuestionField.getText();
        String securityQuestionAnswer = signUpAnswerField.getText();

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

        //Make this a list and pass it in instead
        List<String> information = new ArrayList<String>();
        information.add(username);
        information.add(password);
        information.add(confirmPassword);
        information.add(displayName);
        information.add(securityQuestion);
        information.add(securityQuestionAnswer);

        String errorMessage = Main.login.createAccount(information);
        if(!errorMessage.equals(""))
        {
            signUpErrorMessage.setText(errorMessage);
            signUpErrorMessage.setTextFill(Color.RED);
        }
        else
        {
            nextStep();
        }
    }

    /*
     * Sets the style of this view based on the theme
     */
    private void setStyleFromTheme()
    {
        Color color = getColorFromTheme();

        nameOfProgram.setTextFill(color);
        welcomeBack.setTextFill(color);
        newHere.setTextFill(color);
        signUpPrompt.setTextFill(color);

        ObservableList<Node> loginChildren = loginGrid.getChildren();
        for(Node loginChild : loginChildren)
        {
            if(loginChild.getClass().getSimpleName().equals("Label"))
            {
                ((Label)loginChild).setTextFill(color);
            }
            if(loginChild.getClass().getSimpleName().equals("CheckBox"))
            {
                ((CheckBox)loginChild).setTextFill(color);
            }
        }

        ObservableList<Node> securityQuestionChildren = securityQuestionGrid.getChildren();
        for(Node securityQuestionChild : securityQuestionChildren)
        {
            if(securityQuestionChild.getClass().getSimpleName().equals("Label"))
            {
                ((Label)securityQuestionChild).setTextFill(color);
            }
        }

        ObservableList<Node> signUpChildren = signUpGrid.getChildren();
        for(Node signUpChild : signUpChildren)
        {
            if(signUpChild.getClass().getSimpleName().equals("Label"))
            {
                ((Label)signUpChild).setTextFill(color);
            }
            if(signUpChild.getClass().getSimpleName().equals("CheckBox"))
            {
                ((CheckBox)signUpChild).setTextFill(color);
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
     * Set up to continue on to the next page
     */
    private void nextStep()
    {
        Main.login.getUser().setLogout("No");
        Main.login.getUser().saveSettingsToFile(Main.login.getUser().getUsername());
        continueToNextPage();
    }

    /*
     * Continue to the next page.
     * For login, the next page shown is either the Welcome page or Home page,
     * depending on the user's setting. For sign up, the next page shown is
     * the Welcome page.
     */
    private void continueToNextPage()
    {
        String nextPage = getNextPage();
        String themeCSS = getThemeCSS();
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource(nextPage));
            root.getStylesheets().add(getClass().getResource(themeCSS).toExternalForm());
            Main.stage.setScene(new Scene(root, 840, 640));
            Main.stage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
     * Get the next page to show after login or sign up
     */
    private String getNextPage()
    {
        String welcomePageShown = Main.login.getUser().getWelcomePageShown();
        String nextPage = "";

        if(welcomePageShown.equals("Yes"))
        {
            nextPage = "../View/Welcome.fxml";
        }
        else
        {
            nextPage = "../View/Home.fxml";
        }

        return nextPage;
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
}
