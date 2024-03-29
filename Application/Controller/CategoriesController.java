package Application.Controller;

import Application.Main;
import javafx.collections.ObservableList;
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

/**
 * CategoriesController interacts with Categories.fxml
 * It displays the user's list of categories, each with its assigned color.
 * Here the user can add, edit, and delete categories.
 *
 * @author Waewarin Chindarassami
 */

public class CategoriesController implements Initializable, ParentController {
    @FXML
    private AnchorPane anchorPane, addCategoryPopUp, deleteCategoriesConfirmation;

    @FXML
    private Label categoriesPageLabel, editInstruction, editSuccessMessage, editErrorMessage, deleteInstruction;

    @FXML
    private Button editButton, saveEditButton, deleteButton;

    @FXML
    private GridPane addAndDeleteButtons, editButtons;

    @FXML
    private Pane navigationPane;

    private GridPane categoriesGrid;

    private int numRows, numCols, numCategories;
    private final int categoriesPerRow = 4;
    private final int layoutXInterval = 90;
    private HashMap<String, String> oldAndNewCategories = new HashMap<String, String>();
    private List<String> oldCategories = new ArrayList<String>();
    private List<String> newCategories = new ArrayList<String>();

    private AddCategoryPopUpController popUpController;

    private final GaussianBlur blur = new GaussianBlur();
    private final String theme = Main.login.getUser().getTheme();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try
        {
            Node navigation = FXMLLoader.load(getClass().getResource("../View/Navigation.fxml"));
            navigationPane.getChildren().add(navigation);

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

        setStyleFromTheme();

        displayCategories();
        this.categoriesGrid.setDisable(true);

        editInstruction.setVisible(false);
        editSuccessMessage.setVisible(false);
        editErrorMessage.setVisible(false);
        saveEditButton.setVisible(false);
        deleteInstruction.setVisible(false);
        deleteButton.setVisible(false);
        addCategoryPopUp.setVisible(false);
        deleteCategoriesConfirmation.setVisible(false);
    }

    public void closePopUp(String category)
    {
        addCategoryPopUp.setVisible(false);
        editInstruction.setVisible(false);
        deleteInstruction.setVisible(false);
        editSuccessMessage.setVisible(false);
        editErrorMessage.setVisible(false);

        anchorPane.getChildren().remove(this.categoriesGrid);
        displayCategories();
        this.categoriesGrid.setDisable(true);

        setEffect(null);
    }

    public void setEffect(Effect effect)
    {
        categoriesPageLabel.setEffect(effect);
        addAndDeleteButtons.setEffect(effect);
        editButtons.setEffect(effect);
        categoriesGrid.setEffect(effect);
        editInstruction.setEffect(effect);
        editSuccessMessage.setEffect(effect);
        editErrorMessage.setEffect(effect);
        deleteInstruction.setEffect(effect);
        navigationPane.setEffect(effect);
    }

    /**
     * Set up to edit categories
     */
    public void editCategories()
    {
        categoriesGrid.setDisable(false);
        editButton.setVisible(false);
        editInstruction.setVisible(true);
        saveEditButton.setVisible(true);
        editSuccessMessage.setVisible(false);
        editErrorMessage.setVisible(false);
    }

    /**
     * Save the edited categories
     */
    public void saveEditCategories()
    {
        this.oldCategories.addAll(Main.login.getUser().getCategories().keySet());
        Main.login.getUser().getCategories().clear();

        for(Node node : categoriesGrid.getChildren())
        {
            HBox box = (HBox)node;
            String categoryName = ((TextField)box.getChildren().get(0)).getText();
            String categoryColor = ((ColorPicker)box.getChildren().get(1)).getValue().toString();

            this.newCategories.add(categoryName);
            Main.login.getUser().addCategory(categoryName, categoryColor);
        }

        this.categoriesGrid.getChildren().clear();
        saveCategoriesToFile();
        getEditedCategories();
        Main.login.getUser().updateCategoriesOfEvents(this.oldAndNewCategories);
        Main.login.getUser().saveEventToFile();

        editButton.setVisible(true);
        saveEditButton.setVisible(false);
        editInstruction.setVisible(false);

        this.categoriesGrid.setDisable(true);
    }

    /**
     * Add a new category, the add a new category pop up will appear
     */
    public void addNewCategory()
    {
        setEffect(this.blur);

        this.popUpController.setUp();
        addCategoryPopUp.toFront();
        addCategoryPopUp.setVisible(true);
    }

    /**
     * Set up for deleting categories
     */
    public void deleteSetUp()
    {
        addAndDeleteButtons.setVisible(false);
        editInstruction.setVisible(false);
        editButton.setVisible(false);
        saveEditButton.setVisible(false);
        deleteButton.setVisible(true);
        deleteInstruction.setVisible(true);
        editSuccessMessage.setVisible(false);
        editErrorMessage.setVisible(false);

        categoriesGrid.setDisable(false);

        int categoryIndex = 0;
        for(int row = 0; row < numRows; row++)
        {
            for(int col = 0; col < numCols; col++)
            {
                CheckBox deleteCheckBox = new CheckBox();
                ((HBox)this.categoriesGrid.getChildren().get(categoryIndex)).getChildren().add(0, deleteCheckBox);

                categoryIndex++;

                if(categoryIndex == numCategories)
                {
                    return;
                }
            }
        }
    }

    /**
     * Confirm that the user actually wants to delete the categories
     */
    public void confirmDeleteCategories()
    {
        setEffect(this.blur);
        deleteCategoriesConfirmation.toFront();
        deleteCategoriesConfirmation.setVisible(true);
    }

    /**
     * The user cancels the deletion of categories
     */
    public void cancelConfirmDeleteCategories()
    {
        deleteCategoriesConfirmation.setVisible(false);
        setEffect(null);
    }

    /**
     * Delete selected categories
     */
    public void deleteCategories()
    {
        List<String> deletedCategories = new ArrayList<String>();
        int categoryIndex = 0;
        for(int row = 0; row < numRows; row++)
        {
            if(categoryIndex == this.numCategories)
            {
                break;
            }

            for(int col = 0; col < numCols; col++)
            {
                HBox category = (HBox)this.categoriesGrid.getChildren().get(categoryIndex);
                boolean isCategoryChecked = ((CheckBox)category.getChildren().get(0)).isSelected();
                if(isCategoryChecked)
                {
                    String categoryName = ((TextField)category.getChildren().get(1)).getText();
                    deletedCategories.add(categoryName);
                    Main.login.getUser().deleteCategory(categoryName);
                }

                categoryIndex++;

                if(categoryIndex == this.numCategories)
                {
                    break;
                }
            }
        }

        deleteButton.setVisible(false);
        saveEditButton.setVisible(false);
        editButton.setVisible(true);
        addAndDeleteButtons.setVisible(true);
        editInstruction.setVisible(false);
        deleteInstruction.setVisible(false);

        this.categoriesGrid.getChildren().clear();
        saveCategoriesToFile();
        Main.login.getUser().deleteEventsOfCategories(deletedCategories);
        Main.login.getUser().saveEventToFile();

        deleteCategoriesConfirmation.setVisible(false);
        this.categoriesGrid.setDisable(true);
        setEffect(null);
    }

    /**
     * Cancel the editing of categories
     */
    public void cancelEditCategories()
    {
        this.categoriesGrid.getChildren().clear();
        displayCategories();

        editButton.setVisible(true);
        saveEditButton.setVisible(false);
        addAndDeleteButtons.setVisible(true);
        editInstruction.setVisible(false);
        deleteInstruction.setVisible(false);
        deleteButton.setVisible(false);
        editSuccessMessage.setVisible(false);
        editErrorMessage.setVisible(false);

        this.categoriesGrid.setDisable(true);
    }

    /*
     * Sets the style of this view based on the theme
     */
    private void setStyleFromTheme()
    {
        Color color = getColorFromTheme();

        categoriesPageLabel.setTextFill(color);
        editInstruction.setTextFill(color);
        deleteInstruction.setTextFill(color);
        editSuccessMessage.setTextFill(color);

        ObservableList<Node> children = deleteCategoriesConfirmation.getChildren();
        for(Node child : children)
        {
            if(child.getClass().getSimpleName().equals("Label"))
            {
                ((Label)child).setTextFill(color);
            }
        }
    }

    /*
     * Sets the anchorPane style based on the theme
     * Gets the color of the labels based on the theme
     */
    private Color getColorFromTheme()
    {
        if(this.theme.equals("Light"))
        {
            anchorPane.setStyle("-fx-background-color: white;");
            deleteCategoriesConfirmation.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: black; -fx-border-radius: 20; -fx-border-width: 2;");
            return Color.BLACK;
        }
        else
        {
            anchorPane.setStyle("-fx-background-color: #31323e;");
            deleteCategoriesConfirmation.setStyle("-fx-background-color: #31323e; -fx-background-radius: 20; -fx-border-color: white; -fx-border-radius: 20; -fx-border-width: 2;");
            return Color.WHITE;
        }
    }

    /*
     * Display the user's categories
     */
    private void displayCategories()
    {
        List<String> categories = new ArrayList<>(Main.login.getUser().getCategories().keySet());

        this.numCategories = categories.size();
        this.numRows = this.numCategories / this.categoriesPerRow;

        if(this.numCategories > this.categoriesPerRow)
        {
            this.numCols = 4;
            if(this.numCategories % this.categoriesPerRow != 0) {
                this.numRows += 1;
            }
        }
        else
        {
            this.numRows = 1;
            this.numCols = this.numCategories;
        }

        setUpGrid();
        fillGrid(categories);
    }

    /*
     * Set up the grid where the categories will be displayed in
     */
    private void setUpGrid()
    {
        this.categoriesGrid = new GridPane();
        this.categoriesGrid.setPadding(new Insets(10, 10, 10, 10));
        this.categoriesGrid.setHgap(10);
        this.categoriesGrid.setVgap(10);

        int layoutX = 325;
        this.categoriesGrid.setLayoutY(130);

        int categoryNumber = 1;

        for(int row = 0; row < this.numRows; row++)
        {
            if(categoryNumber > this.numCategories)
            {
                break;
            }

            for(int col = 0; col < this.numCols; col++)
            {
                if(row == 0)
                {
                    this.categoriesGrid.getColumnConstraints().add(new ColumnConstraints(170));
                    if(col != 0)
                    {
                        layoutX -= this.layoutXInterval;
                    }
                }

                this.categoriesGrid.setLayoutX(layoutX);
                HBox box = new HBox();
                box.setAlignment(Pos.CENTER);
                box.setSpacing(5);
                this.categoriesGrid.add(box, col, row);

                categoryNumber++;
                if(categoryNumber > this.numCategories)
                {
                    break;
                }
            }
        }
        anchorPane.getChildren().add(this.categoriesGrid);
    }

    /*
     * Fill the grid with the categories
     */
    private void fillGrid(List<String> categories)
    {
        int categoryIndex = 0;

        while(categoryIndex != this.numCategories)
        {
            TextField categoryName = new TextField(categories.get(categoryIndex));
            categoryName.setStyle("-fx-font: 14px \"Berlin Sans FB\"; -fx-background-radius: 20; -fx-border-radius: 20;");
            categoryName.setPrefWidth(75);

            ColorPicker categoryColor = new ColorPicker();
            categoryColor.setStyle("-fx-color-label-visible: false;");
            String color = Main.login.getUser().getCategories().get(categories.get(categoryIndex));
            categoryColor.setValue(Color.web(color));

            ((HBox)this.categoriesGrid.getChildren().get(categoryIndex)).getChildren().add(categoryName);
            ((HBox)this.categoriesGrid.getChildren().get(categoryIndex)).getChildren().add(categoryColor);

            categoryIndex++;
        }
    }

    /*
     * Save the categories to the user's categories file
     */
    private void saveCategoriesToFile()
    {
        if(Main.login.getUser().saveCategoryToFile())
        {
            this.editSuccessMessage.setVisible(true);
            this.editErrorMessage.setVisible(false);
            displayCategories();
        }
        else
        {
            this.editErrorMessage.setVisible(true);
            this.editSuccessMessage.setVisible(false);
            cancelEditCategories();
        }
    }

    /*
     * Get the categories that were edited
     */
    private void getEditedCategories()
    {
        int numCategories = this.oldCategories.size();
        for(int i = 0; i < numCategories; i++)
        {
            this.oldAndNewCategories.put(this.oldCategories.get(i), this.newCategories.get(i));
        }
    }
}