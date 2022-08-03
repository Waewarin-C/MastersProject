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

        try
        {
            file = new FileWriter(new File(fileName), true);
            file.write(String.format("%s,%s\n", categoryName, categoryColor));
        }
        catch(IOException e)
        {
            System.out.println("Error: unable to save the category");
            e.printStackTrace();
        }
    }
}
