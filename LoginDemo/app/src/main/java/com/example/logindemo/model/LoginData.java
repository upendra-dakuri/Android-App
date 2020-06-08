package com.example.logindemo.model;

public class LoginData {

    private String id;
    private String name;
    private String email_id;
    private String password;
    private boolean isAdmin;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public LoginData(String email_id, String password) {
        this.email_id = email_id;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUname() {
        return email_id;
    }

    public void setUname(String uname) {
        this.email_id = uname;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email_id='" + email_id + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                ", token='" + token + '\'' +
                '}';
    }

    public LoginData(String id, String name, String email_id, String password, boolean isAdmin, String token) {
        this.id = id;
        this.name = name;
        this.email_id = email_id;
        this.password = password;
        this.isAdmin = isAdmin;
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
