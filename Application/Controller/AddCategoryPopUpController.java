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
        //addEventController = new AddEventController();

        doneAddCategory.setVisible(false);
        categoryErrorMessagePopUp.setText("hello");
        categoryColorPopUp.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
    }

    public void saveCategory()
    {

    }

    public void cancelAddCategory()
    {
        addEventController.closeAddCategoryPopUp();
    }

    public void doneAddCategory()
    {

    }
}
