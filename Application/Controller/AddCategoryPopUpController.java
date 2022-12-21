package Application.Controller;

import Application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class AddCategoryPopUpController implements Initializable {
    @FXML
    private Button saveCategoryPopUpButton, cancelCategoryPopUpButton, doneAddCategoryButton;

    @FXML
    private TextField categoryNamePopUpField;

    @FXML
    private ColorPicker categoryColorPopUpField;

    @FXML
    private Label categoryMessagePopUp;

    private ParentController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        doneAddCategoryButton.setVisible(false);
        categoryColorPopUpField.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        categoryMessagePopUp.setText("");
    }

    public void setParentController(ParentController parentController)
    {
        this.parentController = parentController;
    }

    public void setUp()
    {
        categoryNamePopUpField.clear();
        categoryColorPopUpField.setValue(Color.WHITE);
        categoryMessagePopUp.setText("");
    }

    public void saveAddCategory()
    {
        String categoryName = categoryNamePopUpField.getText();
        if(categoryName.equals(""))
        {
            categoryMessagePopUp.setText("Category Name is empty");
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
            }
            else
            {
                categoryMessagePopUp.setText("Error: something went wrong in saving, please try again");
                categoryMessagePopUp.setTextFill(Color.rgb(255,0,0));
            }

            saveCategoryPopUpButton.setVisible(false);
            cancelCategoryPopUpButton.setVisible(false);
            doneAddCategoryButton.setVisible(true);
        }
    }

    public void cancelAddCategory()
    {
        this.parentController.closePopUp("");
    }

    public void doneAddCategory()
    {
        saveCategoryPopUpButton.setVisible(true);
        cancelCategoryPopUpButton.setVisible(true);
        doneAddCategoryButton.setVisible(false);

        String categoryName = categoryNamePopUpField.getText().substring(0,1).toUpperCase();
        categoryName += categoryNamePopUpField.getText().substring(1);
        this.parentController.closePopUp(categoryName);
    }

    private boolean checkIfUnique(String categoryName, String categoryColor)
    {
        boolean isUniqueCategory = checkIfNewCategory(categoryName);
        if(isUniqueCategory)
        {
            boolean isUniqueColor = checkIfNewColor(categoryColor);
            if(!isUniqueColor)
            {
                categoryMessagePopUp.setText("This color is already with an existing category");
                categoryMessagePopUp.setTextFill(Color.rgb(255,0,0));
                return false;
            }
        }
        else
        {
            String message = "The category \"" + categoryName + "\" already exists";
            categoryMessagePopUp.setText(message);
            categoryMessagePopUp.setTextFill(Color.rgb(255,0,0));
            return false;
        }

        return true;
    }

    private boolean checkIfNewCategory(String category)
    {
        if(Main.login.getUser().getCategories().containsKey(category))
        {
            return false;
        }

        return true;
    }

    private boolean checkIfNewColor(String color)
    {
        if(Main.login.getUser().getCategories().containsValue(color))
        {
            return false;
        }

        return true;
    }

    private void saveCategoryToUser(String categoryName, String categoryColor)
    {
        Main.login.getUser().addCategory(categoryName, categoryColor);
    }
}
