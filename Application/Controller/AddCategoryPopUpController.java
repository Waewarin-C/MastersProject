package Application.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCategoryPopUpController implements Initializable {
    @FXML
    Button saveCategoryPopUp, cancelCategoryPopUp, doneAddCategory;

    @FXML
    TextField categoryNamePopUp;

    @FXML
    ColorPicker categoryColorPopUp;

    @FXML
    Label categoryErrorMessagePopUp;

    @FXML
    AddEventController addEventController;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        categoryErrorMessagePopUp.setText("");
        categoryColorPopUp.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
    }

    public void saveCategory()
    {
        saveCategoryPopUp.setVisible(false);
        cancelCategoryPopUp.setVisible(false);
        doneAddCategory.setVisible(true);
    }

    public void cancelAddCategory()
    {
        addEventController.closeAddCategoryPopUp();
    }

    public void doneAddCategory()
    {
        saveCategoryPopUp.setVisible(true);
        cancelCategoryPopUp.setVisible(true);
        doneAddCategory.setVisible(true);
        addEventController.closeAddCategoryPopUp();
    }
}
