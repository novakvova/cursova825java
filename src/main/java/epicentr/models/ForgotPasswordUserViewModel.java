package epicentr.models;

public class ForgotPasswordUserViewModel {
    private String password;
    private String code;

    public ForgotPasswordUserViewModel(String password, String code) {
        this.password = password;
        this.code = code;
    }

    @Override
    public String toString() {
        return "ForgotPasswordUserViewModel{" +
                "password='" + password + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
