package Application.Controller;

import Application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * AddCategoryPopUpController interacts with AddCategoryPopUp.fxml
 * This is where the user can add new categories
 *
 * @author Waewarin Chindarassami
 */

public class AddCategoryPopUpController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button saveCategoryPopUpButton, cancelCategoryPopUpButton, doneAddCategoryButton;

    @FXML
    private TextField categoryNamePopUpField;

    @FXML
    private ColorPicker categoryColorPopUpField;

    @FXML
    private Label categoryMessagePopUp, addCategoryLabel, categoryNameLabel, categoryColorLabel;

    private ParentController parentController;
    private final String theme = Main.login.getUser().getTheme();

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        categoryColorPopUpField.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        setStyleFromTheme();

        categoryMessagePopUp.setText("");

        doneAddCategoryButton.setVisible(false);
    }

    /**
     * Sets the controller class of the view that this AddCategoryPopUp will pop up on to be
     * the parent of this controller class. The parent controller can either be CategoriesController
     * or ManageEventController.
     *
     * @param parentController ParentController - controller of the view this will pop up on
     */
    public void setParentController(ParentController parentController)
    {
        this.parentController = parentController;
    }

    /**
     * Set up the view before having it pop up on the parent controller's view
     * Called by the parent controller which can either by CategoriesController
     * or ManageEventController.
     */
    public void setUp()
    {
        categoryNamePopUpField.clear();
        categoryColorPopUpField.setValue(Color.WHITE);
        categoryMessagePopUp.setText("");
    }

    /**
     * Save the category that was added
     */
    public void saveAddCategory()
    {
        String categoryName = categoryNamePopUpField.getText();
        if(categoryName.equals(""))
        {
            categoryMessagePopUp.setText("Category Name is empty");
            categoryMessagePopUp.setTextFill(Color.RED);
            return;
        }

        String categoryNameCamelCase = categoryName.substring(0,1).toUpperCase() + categoryName.substring(1);

        String categoryColor = categoryColorPopUpField.getValue().toString();

        boolean isUnique = checkIfUnique(categoryNameCamelCase, categoryColor);
        if(isUnique)
        {
            saveCategoryToUser(categoryNameCamelCase, categoryColor);

            if(Main.login.getUser().saveCategoryToFile())
            {
                categoryMessagePopUp.setText("Saved successfully!");
                categoryMessagePopUp.setTextFill(getColorFromTheme());
            }
            else
            {
                categoryMessagePopUp.setText("Error: something went wrong in saving, please try again");
                categoryMessagePopUp.setTextFill(Color.RED);
            }

            saveCategoryPopUpButton.setVisible(false);
            cancelCategoryPopUpButton.setVisible(false);
            doneAddCategoryButton.setVisible(true);
        }
    }

    /**
     * Cancels the addition of a new category
     * Will call the parent controller's closePopUp() method to close this view
     */
    public void cancelAddCategory()
    {
        this.parentController.closePopUp("");
    }

    /**
     * Done adding a new category and prepare the view to be closed
     * Calls the parent controller's closePopUp() method to close this view
     */
    public void doneAddCategory()
    {
        saveCategoryPopUpButton.setVisible(true);
        cancelCategoryPopUpButton.setVisible(true);
        doneAddCategoryButton.setVisible(false);

        String categoryName = categoryNamePopUpField.getText().substring(0,1).toUpperCase();
        categoryName += categoryNamePopUpField.getText().substring(1);
        this.parentController.closePopUp(categoryName);
    }

    /*
     * Sets the style of this view based on the theme
     */
    private void setStyleFromTheme()
    {
        Color color = getColorFromTheme();

        addCategoryLabel.setTextFill(color);
        categoryNameLabel.setTextFill(color);
        categoryColorLabel.setTextFill(color);
        categoryMessagePopUp.setTextFill(color);
    }

    /*
     * Sets the anchorPane style based on the theme
     * Gets the color of the labels based on the theme
     */
    private Color getColorFromTheme()
    {
        if(this.theme.equals("Light"))
        {
            anchorPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: black; -fx-border-radius: 20; -fx-border-width: 2;");
            return Color.BLACK;
        }
        else
        {
            anchorPane.setStyle("-fx-background-color: #31323e; -fx-background-radius: 20; -fx-border-color: white; -fx-border-radius: 20; -fx-border-width: 2;");
            return Color.WHITE;
        }
    }

    /*
     * Checks to see if the category name has already been used and if the color
     * chosen is already assigned to another category
     */
    private boolean checkIfUnique(String categoryName, String categoryColor)
    {
        boolean isUniqueCategory = checkIfNewCategory(categoryName);
        if(isUniqueCategory)
        {
            boolean isUniqueColor = checkIfNewColor(categoryColor);
            if(!isUniqueColor)
            {
                categoryMessagePopUp.setText("This color is already with an existing category");
                categoryMessagePopUp.setTextFill(Color.RED);
                return false;
            }
        }
        else
        {
            String message = "The category \"" + categoryName + "\" already exists";
            categoryMessagePopUp.setText(message);
            categoryMessagePopUp.setTextFill(Color.RED);
            return false;
        }

        return true;
    }

    /*
     * Checks if the category name has already been used
     */
    private boolean checkIfNewCategory(String category)
    {
        if(Main.login.getUser().getCategories().containsKey(category))
        {
            return false;
        }

        return true;
    }

    /*
     * Checks if the color has already been assigned to another category
     */
    private boolean checkIfNewColor(String color)
    {
        if(Main.login.getUser().getCategories().containsValue(color))
        {
            return false;
        }

        return true;
    }

    /*
     * Saves the category to User
     */
    private void saveCategoryToUser(String categoryName, String categoryColor)
    {
        Main.login.getUser().addCategory(categoryName, categoryColor);
    }
}
