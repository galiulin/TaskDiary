package pojo.outer;

public class UserData {
    public int id;
    public int id_personal;
    public UserPersonal userPersonal;
    public String login;
    public String password;
    public String date_reg;
    private String role;
    private boolean enabled;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UserData(int id, int id_personal, String login, String password, String date_reg) {
        this.id = id;
        this.id_personal = id_personal;
        this.login = login;
        this.password = password;
        this.date_reg = date_reg;
    }

    public UserData() {
    }

    public UserData(int id, UserPersonal userPersonal, String login, String password, String date_reg) {
        this.id = id;
        this.userPersonal = userPersonal;
        this.login = login;
        this.password = password;
        this.date_reg = date_reg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_personal() {
        return id_personal;
    }

    public void setId_personal(int id_personal) {
        this.id_personal = id_personal;
    }

    public UserPersonal getUserPersonal() {
        return userPersonal;
    }

    public void setUserPersonal(UserPersonal userPersonal) {
        this.userPersonal = userPersonal;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate_reg() {
        return date_reg;
    }

    public void setDate_reg(String date_reg) {
        this.date_reg = date_reg;
    }
}
