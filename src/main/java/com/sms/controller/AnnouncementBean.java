package com.sms.controller;

import com.sms.model.Announcement;
import com.sms.service.AnnouncementService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Named
@ViewScoped
public class AnnouncementBean implements Serializable {

    @Inject private AnnouncementService announcementService;

    private List<Announcement> announcements;
    private Announcement newAnnouncement = new Announcement();

    @PostConstruct
    public void loadAnnouncements() {
        announcements = announcementService.getAll();
    }

    public void saveAnnouncement() {
        try {
            if (newAnnouncement.getDate() == null) newAnnouncement.setDate(LocalDate.now());
            announcementService.save(newAnnouncement);
            newAnnouncement = new Announcement();
            loadAnnouncements();
            addMessage("Announcement posted.", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deleteAnnouncement(int id) {
        try {
            announcementService.delete(id);
            loadAnnouncements();
            addMessage("Announcement deleted.", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    private void addMessage(String msg, FacesMessage.Severity sev) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(sev, msg, null));
    }

    public List<Announcement> getAnnouncements() { return announcements; }
    public Announcement getNewAnnouncement() { return newAnnouncement; }
    public void setNewAnnouncement(Announcement a) { this.newAnnouncement = a; }
}
