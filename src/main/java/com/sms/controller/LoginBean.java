package com.sms.controller;

import com.sms.model.User;
import com.sms.service.AuthService;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class LoginBean implements Serializable {

    @Inject
    private AuthService authService;

    private String username;
    private String password;

    public String login() {
        boolean success = authService.login(username, password);
        if (!success) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username or password", null));
            return null;
        }
        User user = authService.getCurrentUser();
        switch (user.getUserType()) {
            case Admin:   return "/admin/dashboard.xhtml?faces-redirect=true";
            case Teacher: return "/teacher/dashboard.xhtml?faces-redirect=true";
            case Student: return "/student/dashboard.xhtml?faces-redirect=true";
            case Parent:  return "/parent/dashboard.xhtml?faces-redirect=true";
            default:      return "/index.xhtml?faces-redirect=true";
        }
    }

    public String logout() {
        authService.logout();
        return "/index.xhtml?faces-redirect=true";
    }

    public User getCurrentUser() {
        return authService.getCurrentUser();
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
