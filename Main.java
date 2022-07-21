import Model.Event;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage)
    {
        stage = primaryStage;

        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("./view/Login.fxml"));
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
        loadExistingAccounts();

        launch(args);
    }

    public static void loadExistingAccounts()
    {
        try
        {
            //for each loop to read every file in the Accounts folder
            File[] files = new File("./Accounts").listFiles();

            for(File file : files)
            {
                if(!file.getName().contains("Events"))
                {
                    storeUserInfo(file.getName());
                }
                else {
                    storeUserEvents(file.getName());
                }
            }
        }
        catch(NullPointerException e) {
            System.out.println("Error: unable to load existing accounts");
            e.printStackTrace();
        }
    }

    public static void storeUserInfo(String fileName)
    {
        Scanner scan = null;

        try
        {
            scan = new Scanner(new File(fileName));
            //Skip the first line
            scan.nextLine();
            String[] usernameInfo = scan.nextLine().split(",");
            String[] passwordInfo = scan.nextLine().split(",");
            String[] displayNameInfo = scan.nextLine().split(",");

            User user = new User(usernameInfo[1], passwordInfo[1], displayNameInfo[1]);
        }
        catch(IOException e)
        {
            System.out.println("Error: unable to load information of the user " + fileName);
            e.printStackTrace();
        }
        finally
        {
            scan.close();
        }
    }

    public static void storeUserEvents(String fileName)
    {
        Scanner scan = null;

        try
        {
            //Get user the events are associated with
            String username = fileName.split("_")[0];
            List<Event> events = new ArrayList<Event>();

            scan = new Scanner(new File(fileName));
            //Skip first line
            scan.nextLine();

            while(scan.hasNextLine())
            {
                String[] eventInfo = scan.nextLine().split(",");
                Event event = new Event(eventInfo[0], eventInfo[1], eventInfo[2], eventInfo[3], eventInfo[4]);

                events.add(event);
            }

            //Add the list of events to the corresponding user
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            scan.close();
        }
    }
}
