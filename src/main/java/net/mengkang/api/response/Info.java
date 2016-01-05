package net.mengkang.api.response;

public class Info {
    private int     code    = 200;
    private String errorMessage;

    public int getCode() {
        return code;
    }

    public Info setCode(int error) {
        this.code = error;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
