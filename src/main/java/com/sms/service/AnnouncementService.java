package com.sms.service;

import com.sms.dao.AnnouncementDAO;
import com.sms.model.Announcement;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class AnnouncementService {

    @Inject
    private AnnouncementDAO announcementDAO;

    public List<Announcement> getAll() {
        return announcementDAO.findAll();
    }

    public List<Announcement> getForUserType(String userType) {
        return announcementDAO.findByTargetGroup(userType);
    }

    public void save(Announcement announcement) {
        announcementDAO.save(announcement);
    }

    public void delete(int id) {
        Announcement a = announcementDAO.findById(id);
        if (a != null) announcementDAO.delete(a);
    }
}
