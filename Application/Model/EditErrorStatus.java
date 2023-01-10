package Application.Model;

import javafx.scene.paint.Color;

public class EditErrorStatus {
    private String currentPasswordError;
    private String currentDisplayNameError;
    private Color currentPasswordErrorColor;
    private Color currentDisplayNameErrorColor;

    public EditErrorStatus()
    {
        this.currentPasswordError = "Password must be at least 8 characters";
        this.currentDisplayNameError = "Display Name can be at most 30 characters";
    }

    public String getCurrentPasswordError()
    {
        return this.currentPasswordError;
    }

    public void setCurrentPasswordError(String currentPasswordError)
    {
        this.currentPasswordError = currentPasswordError;
    }

    public String getCurrentDisplayNameError()
    {
        return this.currentDisplayNameError;
    }

    public void setCurrentDisplayNameError(String currentDisplayNameError)
    {
        this.currentDisplayNameError = currentDisplayNameError;
    }

    public Color getCurrentPasswordErrorColor()
    {
        return this.currentPasswordErrorColor;
    }

    public void setCurrentPasswordErrorColor(Color currentPasswordErrorColor)
    {
        this.currentPasswordErrorColor = currentPasswordErrorColor;
    }

    public Color getCurrentDisplayNameErrorColor()
    {
        return this.currentDisplayNameErrorColor;
    }

    public void setCurrentDisplayNameErrorColor(Color currentDisplayNameErrorColor)
    {
        this.currentDisplayNameErrorColor = currentDisplayNameErrorColor;
    }
}
