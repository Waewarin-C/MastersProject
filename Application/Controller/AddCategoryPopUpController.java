package Application.Controller;

import Application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    }

    public void setParentController(AddEventController addEventController)
    {
        this.addEventController = addEventController;
    }

    public void saveAddCategory()
    {
        saveCategoryToFile();

        saveCategoryPopUpButton.setVisible(false);
        cancelCategoryPopUpButton.setVisible(false);
        doneAddCategoryButton.setVisible(true);
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

    private void saveCategoryToFile()
    {
        FileWriter file = null;
        String fileName = "Account/" + Main.login.getUser().getUsername() + "_categories.csv";

        String categoryName = categoryNamePopUpField.getText();
        String categoryColor = categoryColorPopUpField.getValue().toString();

        boolean isUniqueCategory = checkIfNewCategory(categoryName);
        if(isUniqueCategory)
        {
            boolean isUniqueColor = checkIfNewColor(categoryColor);
            if(!isUniqueColor)
            {
                categoryErrorMessagePopUp.setText("This color already associates with an existing category, please choose another color");
                return;
            }
        }
        else
        {
            String message = "The category \"" + categoryName + "\" already exists";
            categoryErrorMessagePopUp.setText(message);
            return;
        }

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
}