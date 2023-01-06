package Application.Controller;

import Application.Main;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCategoryPopUpController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private GridPane gridPane;

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
        anchorPane.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-border-color: black; -fx-border-radius: 20; -fx-border-width: 2;");
        categoryColorPopUpField.setStyle("-fx-font: 14px \"Berlin Sans FB\";");
        setLabelLightModeColor();

        categoryMessagePopUp.setText("");
        categoryMessagePopUp.setTextFill(Color.BLACK);

        doneAddCategoryButton.setVisible(false);


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
                categoryMessagePopUp.setTextFill(Color.RED);
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

    private void setLabelLightModeColor()
    {
        ObservableList<Node> anchorPaneChildren = anchorPane.getChildren();
        for(Node child : anchorPaneChildren)
        {
            if(child.getClass().getSimpleName().equals("Label"))
            {
                ((Label)child).setTextFill(Color.WHITE);
            }
        }

        ObservableList<Node> gridPaneChildren = gridPane.getChildren();
        for(Node child : gridPaneChildren)
        {
            if(child.getClass().getSimpleName().equals("Label"))
            {
                ((Label)child).setTextFill(Color.WHITE);
            }
        }
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
