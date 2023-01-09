package Application.Model;

public class EditStatus {
    private boolean accountSettingEditing;
    private String currentPasswordError;
    private String currentDisplayNameError;

    public EditStatus()
    {
        this.accountSettingEditing = false;
        this.currentPasswordError = "Password must be at least 8 characters";
        this.currentDisplayNameError = "Display Name can be at most 30 characters";
    }
}
