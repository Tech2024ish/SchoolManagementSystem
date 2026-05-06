package com.sms.controller;

import com.sms.dao.UserDAO;
import com.sms.model.User;
import com.sms.service.AuthService;
import com.sms.service.ImageService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.Serializable;

@Named
@ViewScoped
public class ProfileBean implements Serializable {

    @Inject private AuthService authService;
    @Inject private ImageService imageService;
    @Inject private UserDAO userDAO;

    private User profileUser;
    private String newPassword;
    private Part uploadedFile;

    @PostConstruct
    public void loadProfile() {
        profileUser = authService.getCurrentUser();
        if (profileUser != null) {
            profileUser = userDAO.findById(profileUser.getUserId());
        }
    }

    public void saveProfile() {
        try {
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                profileUser.setPassword(authService.hashPassword(newPassword));
            }
            userDAO.update(profileUser);
            addMessage("Profile updated.", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void uploadImage() {
        try {
            if (uploadedFile == null || uploadedFile.getSize() == 0) {
                addMessage("Please select an image first.", FacesMessage.SEVERITY_WARN);
                return;
            }
            HttpServletRequest req = (HttpServletRequest)
                FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String webappPath = req.getServletContext().getRealPath("/");
            String link = imageService.saveImage(uploadedFile, profileUser.getUserId(), webappPath);
            if (link != null) {
                profileUser.setImageLink(link);
                addMessage("Image uploaded.", FacesMessage.SEVERITY_INFO);
            }
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    private void addMessage(String msg, FacesMessage.Severity sev) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(sev, msg, null));
    }

    public User getProfileUser() { return profileUser; }
    public void setProfileUser(User u) { this.profileUser = u; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String p) { this.newPassword = p; }
    public Part getUploadedFile() { return uploadedFile; }
    public void setUploadedFile(Part f) { this.uploadedFile = f; }
}
