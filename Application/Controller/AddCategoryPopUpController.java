package Application.Controller;

import com.sun.javafx.css.parser.LadderConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
        //addEventController = new AddEventController();

        doneAddCategoryButton.setVisible(false);

        categoryColorPopUpField.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
    }

    public void setParentController(AddEventController addEventController)
    {
        this.addEventController = addEventController;
    }

    public void saveAddCategory()
    {
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
}
