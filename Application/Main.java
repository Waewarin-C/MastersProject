package Application;

import Application.Model.Event;
import Application.Model.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The Main class loads in the information about the user if there already exists an account.
 * The information loaded are general user information and settings, the events, and the
 * categories the user has added. All of the information is stored in the corresponding files.
 * Once the existing account has been loaded, the GUI will pop up with the appropriate first page
 * shown according to the user's settings. If there is no existing account, the first page shown
 * to the user will be the Login page.
 *
 * @author Waewarin Chindarassami
 */

public class Main extends Application {

    public static Stage stage;
    public static Login login;
    private static String firstPageShown;
    private static String themeCSS;

    @Override
    public void start(Stage primaryStage)
    {
        stage = primaryStage;
        stage.setTitle("Event Calendar");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("./calendar.png")));

        try
        {
            Parent root = FXMLLoader.load(getClass().getResource(firstPageShown));
            root.getStylesheets().add(getClass().getResource(themeCSS).toExternalForm());
            stage.setScene(new Scene(root, 840, 640));
            stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        login = new Login();

        loadExistingAccount();

        launch(args);
    }

    /**
     * Loads in the existing account if there is one from the corresponding files for
     * user information, events, and categories. If no account exists, then it sets a
     * default requirements status color.
     *
     * @exception NullPointerException - unable to load the files for the existing account
     */
    public static void loadExistingAccount()
    {
        try
        {
            File[] files = new File("./Account").listFiles();

            //If there is already an account, i.e., not the first time running program
            if(files.length != 0)
            {
                storeUserInfo(files[2]);
                storeUserEvents(files[1]);
                storeUserCategories(files[0]);
            }
            else
            {
                setFirstPageShown("Yes", "No");
                setThemeCSS("Light");
                setEditRequirementsStatusColor("Light");
            }
        }
        catch(NullPointerException e) {
            System.out.println("Error: unable to load existing account");
            e.printStackTrace();
        }
    }

    /**
     * Loads the user's information and settings from the corresponding file
     * and stores them into the User
     *
     * @param file File - file that stores the user information and settings
     * @exception IOException - unable to open the user's information file
     */
    public static void storeUserInfo(File file)
    {
        Scanner scan = null;

        try
        {
            scan = new Scanner(file);
            //Skip the first line
            scan.nextLine();
            String username = scan.nextLine().split(",")[1];
            String password = scan.nextLine().split(",")[1];
            String displayName = scan.nextLine().split(",")[1];
            String securityQuestion = scan.nextLine().split(",")[1];
            String securityQuestionAnswer = scan.nextLine().split(",")[1];
            String welcomePageShown = scan.nextLine().split(",")[1];
            String logout = scan.nextLine().split(",")[1];
            String dateFormat = scan.nextLine().split(",")[1];
            String theme = scan.nextLine().split(",")[1];

            login.getUser().setUsername(username);
            login.getUser().setPassword(password);
            login.getUser().setDisplayName(displayName);
            login.getUser().setSecurityQuestion(securityQuestion);
            login.getUser().setSecurityQuestionAnswer(securityQuestionAnswer);
            login.getUser().setWelcomePageShown(welcomePageShown);
            login.getUser().setLogout(logout);
            login.getUser().setDateFormat(dateFormat);
            login.getUser().setTheme(theme);

            setFirstPageShown(logout, welcomePageShown);
            setThemeCSS(theme);
            setEditRequirementsStatusColor(theme);
        }
        catch(IOException e)
        {
            System.out.println("Error: unable to load information of the user " + file.getName());
            e.printStackTrace();
        }
        finally
        {
            scan.close();
        }
    }

    /**
     * Loads the information about the user's events from the corresponding file
     * and stores them into the User's Events
     *
     * @param file File - file that stores the user's events
     * @exception IOException - unable to open the user's events file
     */
    public static void storeUserEvents(File file)
    {
        Scanner scan = null;

        try
        {
            scan = new Scanner(file);
            //Skip first line
            scan.nextLine();

            while(scan.hasNextLine())
            {
                String[] eventInfo = scan.nextLine().split(",");
                Event event = new Event(eventInfo[0], eventInfo[1], eventInfo[2], eventInfo[3], eventInfo[4]);

                login.getUser().addEvent(event);
            }
        }
        catch(IOException e)
        {
            System.out.println("Error: unable to load the events of the user " + login.getUser().getUsername());
            e.printStackTrace();
        }
        finally
        {
            scan.close();
        }
    }

    /**
     * Loads the user's categories from the corresponding file
     * and stores them into the User's list of categories
     *
     * @param file File - file that stores the user's categories
     * @exception IOException - unable to open the user's categories file
     */
    public static void storeUserCategories(File file)
    {
        Scanner scan = null;

        try
        {
            TreeMap<String, String> categories = new TreeMap<String, String>();

            scan = new Scanner(file);
            //Skip first line
            scan.nextLine();

            while(scan.hasNextLine())
            {
                String[] categoryInfo = scan.nextLine().split(",");

                categories.put(categoryInfo[0], categoryInfo[1]);
            }

            login.getUser().setCategories(categories);
        }
        catch(IOException e)
        {
            System.out.println("Error: unable to load the categories of the user " + login.getUser().getUsername());
            e.printStackTrace();
        }
        finally
        {
            scan.close();
        }
    }

    /*
     * Sets the first page shown to the user based on the user's setting if there
     * already exists an account, or to the Login page if no account exists
     */
    private static void setFirstPageShown(String logout, String welcomePageShown)
    {
        if(logout.equals("Yes"))
        {
            firstPageShown = "./View/Login.fxml";
        }
        else
        {
            if(welcomePageShown.equals("Yes"))
            {
                firstPageShown = "./View/Welcome.fxml";
            }
            else
            {
                firstPageShown = "./View/Home.fxml";
            }
        }
    }

    /*
     * Set the theme CSS file
     */
    private static void setThemeCSS(String theme)
    {
        if(theme.equals("Light"))
        {
            themeCSS = "./View/light_mode.css";
        }
        else
        {
            themeCSS = "./View/dark_mode.css";
        }
    }

    /*
     * Set the edit requirements status color
     */
    private static void setEditRequirementsStatusColor(String theme)
    {
        if(theme.equals("Light"))
        {
            login.getUser().getEditRequirementsStatus().setCurrentPasswordRequirementColor(Color.BLACK);
            login.getUser().getEditRequirementsStatus().setCurrentDisplayNameRequirementColor(Color.BLACK);
        }
        else
        {
            login.getUser().getEditRequirementsStatus().setCurrentPasswordRequirementColor(Color.WHITE);
            login.getUser().getEditRequirementsStatus().setCurrentDisplayNameRequirementColor(Color.WHITE);
        }
    }
}
