package Application.Controller;

//Application.Controller for the Welcome view
//Welcome view will include
    //Welcome message
    //Description of each view/feature
    //A checkbox option to not display again

import Application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;

import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label welcomeMessage;

    @FXML
    private Button beginButton;

    @FXML
    private Pane homeIcon, calendarIcon, addEventsIcon, categoriesIcon, settingsIcon;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        anchorPane.getStylesheets().add(getClass().getResource("../view/light_mode.css").toExternalForm());
        welcomeMessage.setText("Welcome " + Main.login.getUser().getDisplayName() + "!");

        //Load SVG paths
        SVGPath homeIconPath = new SVGPath();
        homeIconPath.setContent("M10 19v-5h4v5c0 .55.45 1 1 1h3c.55 0 1-.45 1-1v-7h1.7c.46 0 .68-.57.33-.87L12.67 3.6c-.38-.34-.96-.34-1.34 0l-8.36 7.53c-.34.3-.13.87.33.87H5v7c0 .55.45 1 1 1h3c.55 0 1-.45 1-1z");
        homeIconPath.setScaleX(2.5);
        homeIconPath.setScaleY(2.5);
        homeIcon.getChildren().add(homeIconPath);

        SVGPath calendarIconPath = new SVGPath();
        calendarIconPath.setContent("M17,2c-0.55,0-1,0.45-1,1v1H8V3c0-0.55-0.45-1-1-1S6,2.45,6,3v1H5C3.89,4,3.01,4.9,3.01,6L3,20c0,1.1,0.89,2,2,2h14 c1.1,0,2-0.9,2-2V6c0-1.1-0.9-2-2-2h-1V3C18,2.45,17.55,2,17,2z M19,20H5V10h14V20z M11,13c0-0.55,0.45-1,1-1s1,0.45,1,1 s-0.45,1-1,1S11,13.55,11,13z M7,13c0-0.55,0.45-1,1-1s1,0.45,1,1s-0.45,1-1,1S7,13.55,7,13z M15,13c0-0.55,0.45-1,1-1s1,0.45,1,1 s-0.45,1-1,1S15,13.55,15,13z M11,17c0-0.55,0.45-1,1-1s1,0.45,1,1s-0.45,1-1,1S11,17.55,11,17z M7,17c0-0.55,0.45-1,1-1 s1,0.45,1,1s-0.45,1-1,1S7,17.55,7,17z M15,17c0-0.55,0.45-1,1-1s1,0.45,1,1s-0.45,1-1,1S15,17.55,15,17z");
        calendarIconPath.setScaleX(2.5);
        calendarIconPath.setScaleY(2.5);
        calendarIcon.getChildren().add(calendarIconPath);

        SVGPath addEventIconPath = new SVGPath();
        addEventIconPath.setContent("M12,22H5c-1.11,0-2-0.9-2-2L3.01,6c0-1.1,0.88-2,1.99-2h1V3c0-0.55,0.45-1,1-1s1,0.45,1,1v1h8V3c0-0.55,0.45-1,1-1 s1,0.45,1,1v1h1c1.1,0,2,0.9,2,2v6h-2v-2H5v10h7V22z M22.13,16.99l0.71-0.71c0.39-0.39,0.39-1.02,0-1.41l-0.71-0.71 c-0.39-0.39-1.02-0.39-1.41,0l-0.71,0.71L22.13,16.99z M21.42,17.7l-5.01,5.01c-0.18,0.18-0.44,0.29-0.7,0.29H14.5 c-0.28,0-0.5-0.22-0.5-0.5v-1.21c0-0.27,0.11-0.52,0.29-0.71l5.01-5.01L21.42,17.7z");
        addEventIconPath.setScaleX(2.5);
        addEventIconPath.setScaleY(2.5);
        addEventsIcon.getChildren().add(addEventIconPath);

        SVGPath categoriesIconPath = new SVGPath();
        categoriesIconPath.setContent("M4 13c.55 0 1-.45 1-1s-.45-1-1-1-1 .45-1 1 .45 1 1 1zm0 4c.55 0 1-.45 1-1s-.45-1-1-1-1 .45-1 1 .45 1 1 1zm0-8c.55 0 1-.45 1-1s-.45-1-1-1-1 .45-1 1 .45 1 1 1zm4 4h12c.55 0 1-.45 1-1s-.45-1-1-1H8c-.55 0-1 .45-1 1s.45 1 1 1zm0 4h12c.55 0 1-.45 1-1s-.45-1-1-1H8c-.55 0-1 .45-1 1s.45 1 1 1zM7 8c0 .55.45 1 1 1h12c.55 0 1-.45 1-1s-.45-1-1-1H8c-.55 0-1 .45-1 1zm-3 5c.55 0 1-.45 1-1s-.45-1-1-1-1 .45-1 1 .45 1 1 1zm0 4c.55 0 1-.45 1-1s-.45-1-1-1-1 .45-1 1 .45 1 1 1zm0-8c.55 0 1-.45 1-1s-.45-1-1-1-1 .45-1 1 .45 1 1 1zm4 4h12c.55 0 1-.45 1-1s-.45-1-1-1H8c-.55 0-1 .45-1 1s.45 1 1 1zm0 4h12c.55 0 1-.45 1-1s-.45-1-1-1H8c-.55 0-1 .45-1 1s.45 1 1 1zM7 8c0 .55.45 1 1 1h12c.55 0 1-.45 1-1s-.45-1-1-1H8c-.55 0-1 .45-1 1z");
        categoriesIconPath.setScaleX(2.5);
        categoriesIconPath.setScaleY(2.5);
        categoriesIcon.getChildren().add(categoriesIconPath);

        SVGPath settingsIconPath = new SVGPath();
        settingsIconPath.setContent("M19.5,12c0-0.23-0.01-0.45-0.03-0.68l1.86-1.41c0.4-0.3,0.51-0.86,0.26-1.3l-1.87-3.23c-0.25-0.44-0.79-0.62-1.25-0.42 l-2.15,0.91c-0.37-0.26-0.76-0.49-1.17-0.68l-0.29-2.31C14.8,2.38,14.37,2,13.87,2h-3.73C9.63,2,9.2,2.38,9.14,2.88L8.85,5.19 c-0.41,0.19-0.8,0.42-1.17,0.68L5.53,4.96c-0.46-0.2-1-0.02-1.25,0.42L2.41,8.62c-0.25,0.44-0.14,0.99,0.26,1.3l1.86,1.41 C4.51,11.55,4.5,11.77,4.5,12s0.01,0.45,0.03,0.68l-1.86,1.41c-0.4,0.3-0.51,0.86-0.26,1.3l1.87,3.23c0.25,0.44,0.79,0.62,1.25,0.42 l2.15-0.91c0.37,0.26,0.76,0.49,1.17,0.68l0.29,2.31C9.2,21.62,9.63,22,10.13,22h3.73c0.5,0,0.93-0.38,0.99-0.88l0.29-2.31 c0.41-0.19,0.8-0.42,1.17-0.68l2.15,0.91c0.46,0.2,1,0.02,1.25-0.42l1.87-3.23c0.25-0.44,0.14-0.99-0.26-1.3l-1.86-1.41 C19.49,12.45,19.5,12.23,19.5,12z M12.04,15.5c-1.93,0-3.5-1.57-3.5-3.5s1.57-3.5,3.5-3.5s3.5,1.57,3.5,3.5S13.97,15.5,12.04,15.5z");
        settingsIconPath.setScaleX(2.5);
        settingsIconPath.setScaleY(2.5);
        settingsIcon.getChildren().add(settingsIconPath);
    }

    public void continueToHome()
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("../View/Home.fxml"));
            Main.stage.setScene(new Scene(root));
            Main.stage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
