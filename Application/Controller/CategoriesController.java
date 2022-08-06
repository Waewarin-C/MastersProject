package Application.Controller;

//Application.Controller for the Add Category view
//Add Category view will include
    //Color picker
    //Name of category

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoriesController implements Initializable {
    @FXML
    private Pane toolbarPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try
        {
            Node toolbar = FXMLLoader.load(getClass().getResource("../View/Toolbar.fxml"));
            toolbarPane.getChildren().add(toolbar);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
