package Application.Controller;

//Application.Controller for the Add Category view
//Add Category view will include
    //Color picker
    //Name of category

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCategoryController implements Initializable {
    @FXML
    Pane toolbarPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try
        {
            Parent toolbar = FXMLLoader.load(getClass().getResource("../View/Toolbar.fxml"));
            toolbarPane.getChildren().add(toolbar);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
