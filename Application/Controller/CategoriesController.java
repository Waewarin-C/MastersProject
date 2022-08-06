package Application.Controller;

//Application.Controller for the Add Category view
//Add Category view will include
    //Color picker
    //Name of category

import Application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class CategoriesController implements Initializable {
    @FXML
    private Pane toolbarPane;

    @FXML
    private GridPane categories;

    @FXML
    private ColorPicker color; //STYLE: -fx-color-label-visible: false;

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

        displayCategories();

    }

    private void displayCategories()
    {
        List<String> categories = new ArrayList<String>();
        categories.addAll(Main.login.getUser().getCategories().keySet());
        Collections.sort(categories);
    }
}
