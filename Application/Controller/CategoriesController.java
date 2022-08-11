package Application.Controller;

//Application.Controller for the Add Category view
//Add Category view will include
    //Color picker
    //Name of category

import Application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class CategoriesController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label saveEditMessage;

    @FXML
    private Pane toolbarPane;

    private GridPane categoriesGrid;

    final int categoriesPerRow = 4;
    final int layoutXInterval = 80;

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

    public void saveEditCategories()
    {
        FileWriter file = null;
        String fileName = "Account/" + Main.login.getUser().getUsername() + "_categories.csv";

        try
        {
            file = new FileWriter(new File(fileName));
            file.write(String.format("%s,%s\n", "Category", "Color"));

            for(Node node : categoriesGrid.getChildren())
            {
                HBox box = (HBox)node;
                String categoryName = box.getChildren().get(0).toString();
                String categoryColor = box.getChildren().get(1).toString();

                file.write(String.format("%s,%s\n", categoryName, categoryColor));
            }

            file.close();

            saveEditMessage.setText("Saved successfully!");
        }
        catch(IOException e)
        {
            saveEditMessage.setText("Error: something went wrong, please try again");
            saveEditMessage.setTextFill(Color.rgb(255,0,0));
            System.out.println("Error: unable to save the categories");
            e.printStackTrace();
        }
    }

    public void addNewCategory()
    {

    }

    private void displayCategories()
    {
        List<String> categories = new ArrayList<String>();
        categories.addAll(Main.login.getUser().getCategories().keySet());
        Collections.sort(categories);

        int numCategories = categories.size();
        int numRows = numCategories / categoriesPerRow;
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

        setUpGrid(numRows, numCols);
        fillGrid(categories, numRows, numCols);
    }

    private void setUpGrid(int numRows, int numCols)
    {
        categoriesGrid = new GridPane();
        categoriesGrid.setPadding(new Insets(10, 10, 10, 10));
        categoriesGrid.setHgap(10);
        categoriesGrid.setVgap(10);

        int layoutX = 335;
        categoriesGrid.setLayoutY(130);

        for(int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                categoriesGrid.getColumnConstraints().add(new ColumnConstraints(150));

                layoutX -= (col * layoutXInterval);
                categoriesGrid.setLayoutX(layoutX);

                HBox box = new HBox();
                box.setAlignment(Pos.CENTER);
                box.setSpacing(10);
                categoriesGrid.add(box, col, row);
            }
        }
        anchorPane.getChildren().add(categoriesGrid);
    }

    private void fillGrid(List<String> categories, int numRows, int numCols)
    {
        int categoryIndex = 0;
        for(int row = 0; row < numRows; row++)
        {
            for(int col = 0; col < numCols; col++)
            {
                TextField categoryName = new TextField(categories.get(categoryIndex));
                categoryName.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
                categoryName.setPrefWidth(75);
                categoryName.setDisable(true);

                ColorPicker categoryColor = new ColorPicker();
                categoryColor.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
                categoryColor.setStyle("-fx-color-label-visible: false;");
                String color = Main.login.getUser().getCategories().get(categories.get(categoryIndex));
                categoryColor.setValue(Color.web(color));
                categoryColor.setDisable(true);

                ((HBox)categoriesGrid.getChildren().get(categoryIndex)).getChildren().add(categoryName);
                ((HBox)categoriesGrid.getChildren().get(categoryIndex)).getChildren().add(categoryColor);

                categoryIndex++;
            }
        }
    }
}
