package Application.Controller;

//Application.Controller for the Welcome view
//Welcome view will include
    //Welcome message
    //Description of each view/feature
    //A checkbox option to not display again

import Application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //Load SVG paths
    }

    public void continueToHome(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("./view/Home.fxml"));
            Main.stage.setScene(new Scene(root));
            Main.stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}
