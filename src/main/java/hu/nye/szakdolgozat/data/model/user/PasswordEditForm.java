package hu.nye.szakdolgozat.data.model.user;

public class PasswordEditForm {
    private String oldPassword;
    private String password1;
    private String password2;

    public PasswordEditForm(String oldPassword, String password1, String password2) {
        this.oldPassword = oldPassword;
        this.password1 = password1;
        this.password2 = password2;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getPassword1() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }
}
