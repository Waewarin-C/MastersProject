import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        storeUserInfo();

        launch(args);
    }
    public static void storeUserInfo()
    {
        Scanner scan = null;
        try
        {
            //for each loop to read every file in the Accounts folder
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
