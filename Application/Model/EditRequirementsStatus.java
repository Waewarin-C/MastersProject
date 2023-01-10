package Application.Model;

import javafx.scene.paint.Color;

public class EditRequirementsStatus {
    private String currentPasswordRequirementStatus;
    private String currentDisplayNameRequirementStatus;
    private Color currentPasswordRequirementColor;
    private Color currentDisplayNameRequirementColor;
    private boolean settingsPageReloaded;

    public EditRequirementsStatus()
    {
        this.currentPasswordRequirementStatus = "Password must be at least 8 characters";
        this.currentDisplayNameRequirementStatus = "Display Name can be at most 30 characters";
        this.settingsPageReloaded = false;
    }

    public String getCurrentPasswordRequirementStatus()
    {
        return this.currentPasswordRequirementStatus;
    }

    public void setCurrentPasswordRequirementStatus(String currentPasswordRequirementStatus)
    {
        this.currentPasswordRequirementStatus = currentPasswordRequirementStatus;
    }

    public String getCurrentDisplayNameRequirementStatus()
    {
        return this.currentDisplayNameRequirementStatus;
    }

    public void setCurrentDisplayNameRequirementStatus(String currentDisplayNameRequirementStatus)
    {
        this.currentDisplayNameRequirementStatus = currentDisplayNameRequirementStatus;
    }

    public Color getCurrentPasswordRequirementColor()
    {
        return this.currentPasswordRequirementColor;
    }

    public void setCurrentPasswordRequirementColor(Color currentPasswordRequirementColor)
    {
        this.currentPasswordRequirementColor = currentPasswordRequirementColor;
    }

    public Color getCurrentDisplayNameRequirementColor()
    {
        return this.currentDisplayNameRequirementColor;
    }

    public void setCurrentDisplayNameRequirementColor(Color currentDisplayNameRequirementColor)
    {
        this.currentDisplayNameRequirementColor = currentDisplayNameRequirementColor;
    }

    public boolean getSettingsPageReloaded()
    {
        return this.settingsPageReloaded;
    }

    public void setSettingsPageReloaded(boolean settingsPageReloaded)
    {
        this.settingsPageReloaded = settingsPageReloaded;
    }
}
