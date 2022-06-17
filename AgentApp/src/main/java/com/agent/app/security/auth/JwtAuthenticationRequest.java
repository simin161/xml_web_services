package com.agent.app.security.auth;

public class JwtAuthenticationRequest {
    private String email;
    private String password;
    private String code;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String email, String password, String code) {
        this.setEmail(email);
        this.setPassword(password);
        this.setCode(code);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() { return this.code; }

    public void setCode(String code) { this.code = code; }
}
