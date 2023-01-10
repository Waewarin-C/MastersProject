package Application.Model;

import javafx.scene.paint.Color;

/**
 * EditRequirementsStatus class stores information about the current requirement
 * status for the password and display name. These requirements are shown in the
 * Settings page. This information helps make sure that the statuses do not reset
 * when the user changes theme. They should only reset when the user visits or
 * reloads the Settings page, or cancels the editions they made in the Settings page
 *
 * @author Waewarin Chindarassami
 */

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
