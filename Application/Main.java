package Application;

import Application.Model.Event;
import Application.Model.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends Application {

    public static Stage stage;
    public static Login login;

    @Override
    public void start(Stage primaryStage)
    {
        stage = primaryStage;

        try
        {
            //Parent root = FXMLLoader.load(getClass().getResource("./View/Login.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("./View/Settings.fxml"));
            stage.setScene(new Scene(root));
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
        }
        catch(NullPointerException e) {
            System.out.println("Error: unable to load existing account");
            e.printStackTrace();
        }
    }

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
            String welcomePageShown = scan.nextLine().split(",")[1];

            login.getUser().setUsername(username);
            login.getUser().setPassword(password);
            login.getUser().setDisplayName(displayName);
            login.getUser().setWelcomePageShown(Boolean.parseBoolean(welcomePageShown));
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

    public static void storeUserEvents(File file)
    {
        Scanner scan = null;

        try
        {
            List<Event> events = new ArrayList<Event>();

            scan = new Scanner(file);
            //Skip first line
            scan.nextLine();

            while(scan.hasNextLine())
            {
                String[] eventInfo = scan.nextLine().split(",");
                Event event = new Event(eventInfo[0], eventInfo[1], eventInfo[2], eventInfo[3], eventInfo[4]);

                events.add(event);
            }

            //Add the list of events to the user object
            login.getUser().setEvents(events);
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

    public static void storeUserCategories(File file)
    {
        Scanner scan = null;

        try
        {
            HashMap<String, String> categories = new HashMap<String, String>();

            scan = new Scanner(file);
            //Skip first line
            scan.nextLine();

            while(scan.hasNextLine())
            {
                String[] categoryInfo = scan.nextLine().split(",");

                categories.put(categoryInfo[0], categoryInfo[1]);
            }

            //Add the list of events to the user object
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
}
