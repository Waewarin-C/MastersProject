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
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.*;

public class CategoriesController implements Initializable, ParentController {
    @FXML
    private AnchorPane anchorPane, addCategoryPopUp;

    @FXML
    private Label categoriesPageLabel, editMessage, deleteInstructions;

    @FXML
    private Button saveEditButton, deleteButton;

    @FXML
    private GridPane addAndDeleteButtons, editButtons;

    @FXML
    private Pane toolbarPane;

    private GridPane categoriesGrid;

    private int numRows, numCols;
    private final int categoriesPerRow = 4;
    private final int layoutXInterval = 90;
    private AddCategoryPopUpController popUpController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try
        {
            Node toolbar = FXMLLoader.load(getClass().getResource("../View/Toolbar.fxml"));
            toolbarPane.getChildren().add(toolbar);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/AddCategoryPopUp.fxml"));
            Node popUp = loader.load();
            addCategoryPopUp.getChildren().add(popUp);

            this.popUpController = ((AddCategoryPopUpController)loader.getController());
            this.popUpController.setParentController(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        displayCategories();
        editMessage.setText("");
        deleteInstructions.setVisible(false);
        deleteButton.setVisible(false);
        addCategoryPopUp.setVisible(false);
    }

    public void closePopUp(String category)
    {
        addCategoryPopUp.setVisible(false);
        anchorPane.getChildren().remove(categoriesGrid);
        displayCategories();
        setEffect(null);
    }

    public void setEffect(Effect effect)
    {
        categoriesPageLabel.setEffect(effect);
        addAndDeleteButtons.setEffect(effect);
        editButtons.setEffect(effect);
        categoriesGrid.setEffect(effect);
        editMessage.setEffect(effect);
        toolbarPane.setEffect(effect);
    }

    public void saveEditCategories()
    {
        //TODO: find a way to change edited categories accordingly
        //TODO: check if user wants to edit category if it has events associated with it
        Main.login.getUser().getCategories().clear();

        for(Node node : categoriesGrid.getChildren())
        {
            HBox box = (HBox)node;
            String categoryName = ((TextField)box.getChildren().get(0)).getText();
            String categoryColor = ((ColorPicker)box.getChildren().get(1)).getValue().toString();

            Main.login.getUser().addCategory(categoryName, categoryColor);
        }

        this.popUpController.saveCategoryToFile();

        displayCategories();
    }

    public void addNewCategory()
    {
        GaussianBlur blur = new GaussianBlur();
        setEffect(blur);

        this.popUpController.setUp();
        addCategoryPopUp.setVisible(true);
    }

    public void deleteSetUp()
    {
        addAndDeleteButtons.setVisible(false);
        saveEditButton.setVisible(false);
        deleteButton.setVisible(true);
        deleteInstructions.setVisible(true);

        int categoryIndex = 0;
        for(int row = 0; row < numRows; row++)
        {
            for(int col = 0; col < numCols; col++)
            {
                CheckBox deleteCheckBox = new CheckBox();
                ((HBox)this.categoriesGrid.getChildren().get(categoryIndex)).getChildren().add(0, deleteCheckBox);

                categoryIndex++;
            }
        }
    }

    public void deleteCategories()
    {
        //TODO: find a way to change or delete events with deleted categories accordingly
        //TODO: check if user wants to delete category if it has events associated with it
        int categoryIndex = 0;
        for(int row = 0; row < numRows; row++)
        {
            for(int col = 0; col < numCols; col++)
            {
                HBox category = (HBox)this.categoriesGrid.getChildren().get(categoryIndex);
                boolean isCategoryChecked = ((CheckBox)category.getChildren().get(0)).isSelected();
                if(isCategoryChecked)
                {
                    String categoryName = ((TextField)category.getChildren().get(1)).getText();
                    Main.login.getUser().deleteCategory(categoryName);
                }

                categoryIndex++;
            }
        }

        deleteInstructions.setVisible(false);
        deleteButton.setVisible(false);
        saveEditButton.setVisible(true);
        addAndDeleteButtons.setVisible(true);

        this.categoriesGrid.getChildren().clear();
        displayCategories();
        saveEditCategories();
    }

    public void cancelEditCategories()
    {
        this.categoriesGrid.getChildren().clear();
        displayCategories();

        deleteInstructions.setVisible(false);
        deleteButton.setVisible(false);
        saveEditButton.setVisible(true);
        addAndDeleteButtons.setVisible(true);
    }



    private void displayCategories()
    {
        List<String> categories = new ArrayList<>(Main.login.getUser().getCategories().keySet());

        int numCategories = categories.size();
        this.numRows = numCategories / this.categoriesPerRow;

        if(numCategories > this.categoriesPerRow)
        {
            this.numCols = 4;
            if(numCategories % 4 != 0) {
                this.numRows += 1;
            }
        }
        else
        {
            this.numRows = 1;
            this.numCols = numCategories;
        }

        setUpGrid();
        fillGrid(categories);
    }

    private void setUpGrid()
    {
        this.categoriesGrid = new GridPane();
        this.categoriesGrid.setPadding(new Insets(10, 10, 10, 10));
        this.categoriesGrid.setHgap(10);
        this.categoriesGrid.setVgap(10);

        int layoutX = 325;
        this.categoriesGrid.setLayoutY(130);

        for(int row = 0; row < this.numRows; row++)
        {
            for(int col = 0; col < this.numCols; col++)
            {
                this.categoriesGrid.getColumnConstraints().add(new ColumnConstraints(170));

                if(col != 0)
                {
                    layoutX -= this.layoutXInterval;
                }

                this.categoriesGrid.setLayoutX(layoutX);

                HBox box = new HBox();
                box.setAlignment(Pos.CENTER);
                box.setSpacing(5);
                this.categoriesGrid.add(box, col, row);
            }
        }
        anchorPane.getChildren().add(this.categoriesGrid);
    }

    private void fillGrid(List<String> categories)
    {
        int categoryIndex = 0;
        for(int row = 0; row < this.numRows; row++)
        {
            for(int col = 0; col < this.numCols; col++)
            {
                TextField categoryName = new TextField(categories.get(categoryIndex));
                categoryName.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
                categoryName.setPrefWidth(75);

                ColorPicker categoryColor = new ColorPicker();
                categoryColor.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
                categoryColor.setStyle("-fx-color-label-visible: false;");
                String color = Main.login.getUser().getCategories().get(categories.get(categoryIndex));
                categoryColor.setValue(Color.web(color));

                ((HBox)this.categoriesGrid.getChildren().get(categoryIndex)).getChildren().add(categoryName);
                ((HBox)this.categoriesGrid.getChildren().get(categoryIndex)).getChildren().add(categoryColor);

                categoryIndex++;
            }
        }
    }
}
