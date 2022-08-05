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

public class AddCategoryPopUpController implements Initializable {
    @FXML
    private Button saveCategoryPopUpButton, cancelCategoryPopUpButton, doneAddCategoryButton;

    @FXML
    private TextField categoryNamePopUpField;

    @FXML
    private ColorPicker categoryColorPopUpField;

    @FXML
    private Label categoryErrorMessagePopUp;

    private AddEventController addEventController;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        doneAddCategoryButton.setVisible(false);
        categoryColorPopUpField.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        categoryErrorMessagePopUp.setText("");
    }

    public void setParentController(AddEventController addEventController)
    {
        this.addEventController = addEventController;
    }

    public void setUp()
    {
        categoryNamePopUpField.clear();
        categoryColorPopUpField.setValue(Color.WHITE);
        categoryErrorMessagePopUp.setText("");
    }

    public void saveAddCategory()
    {
        String categoryName = categoryNamePopUpField.getText();
        if(categoryName.equals(""))
        {
            categoryErrorMessagePopUp.setText("Category Name is empty");
            return;
        }

        String categoryNameCamelCase = categoryName.substring(0,1).toUpperCase() + categoryName.substring(1);

        String categoryColor = categoryColorPopUpField.getValue().toString();

        boolean isUnique = checkIfUnique(categoryNameCamelCase, categoryColor);
        if(isUnique)
        {
            saveCategoryToFile(categoryNameCamelCase, categoryColor);
            saveCategoryToUser(categoryNameCamelCase, categoryColor);
            saveCategoryPopUpButton.setVisible(false);
            cancelCategoryPopUpButton.setVisible(false);
            doneAddCategoryButton.setVisible(true);
        }
    }

    public void cancelAddCategory()
    {
        addEventController.closeAddCategory();
    }

    public void doneAddCategory()
    {
        saveCategoryPopUpButton.setVisible(true);
        cancelCategoryPopUpButton.setVisible(true);
        doneAddCategoryButton.setVisible(false);

        addEventController.closeAddCategory();
    }

    private boolean checkIfUnique(String categoryName, String categoryColor)
    {
        boolean isUniqueCategory = checkIfNewCategory(categoryName);
        if(isUniqueCategory)
        {
            boolean isUniqueColor = checkIfNewColor(categoryColor);
            if(!isUniqueColor)
            {
                categoryErrorMessagePopUp.setText("This color is already with an existing category");
                return false;
            }
        }
        else
        {
            String message = "The category \"" + categoryName + "\" already exists";
            categoryErrorMessagePopUp.setText(message);
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

    private void saveCategoryToFile(String categoryName, String categoryColor)
    {
        FileWriter file = null;
        String fileName = "Account/" + Main.login.getUser().getUsername() + "_categories.csv";

        try
        {
            file = new FileWriter(new File(fileName), true);
            file.write(String.format("%s,%s\n", categoryName, categoryColor));
            file.close();
        }
        catch(IOException e)
        {
            System.out.println("Error: unable to save the category");
            e.printStackTrace();
        }
    }

    private void saveCategoryToUser(String categoryName, String categoryColor)
    {
        Main.login.getUser().addCategory(categoryName, categoryColor);
    }
}
