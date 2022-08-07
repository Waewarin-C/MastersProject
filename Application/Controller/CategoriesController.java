package Application.Controller;

//Application.Controller for the Add Category view
//Add Category view will include
    //Color picker
    //Name of category

import Application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.swing.text.AbstractDocument;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class CategoriesController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane categoriesGrid;

    @FXML
    private Pane toolbarPane;

    final int categoriesPerRow = 4;

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

        int numCategories = categories.size();
        int numRows = numCategories / 4;
        int numCols = 0;
        if(numCategories > categoriesPerRow)
        {
            numCols = 4;
            if(numCategories % 4 != 0) {
                numRows += 1;
            }
        }
        else
        {
            numRows = 1;
            numCols = numCategories;
        }

        int categoryIndex = 0;
        for(int row = 0; row < numRows; row++)
        {
            for(int col = 0; col < numCols; col++)
            {
                HBox box = new HBox();
                box.setAlignment(Pos.CENTER);
                box.setSpacing(10);

                Label categoryName = new Label();
                categoryName.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
                categoryName.setText(categories.get(categoryIndex));

                ColorPicker categoryColor = new ColorPicker();
                categoryColor.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
                categoryColor.setStyle("-fx-color-label-visible: false;");
                String color = Main.login.getUser().getCategories().get(categories.get(categoryIndex));
                categoryColor.setValue(Color.web(color));

                box.getChildren().add(categoryName);
                box.getChildren().add(categoryColor);

                categoriesGrid.add(box, col, row);

                categoryIndex++;
            }
        }
    }
}
