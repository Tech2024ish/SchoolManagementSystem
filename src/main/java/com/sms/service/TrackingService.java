package com.sms.service;

import com.sms.dao.TrackingDAO;
import com.sms.model.StudentTracking;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class TrackingService {

    @Inject
    private TrackingDAO trackingDAO;

    public List<StudentTracking> getByStudent(int studentId) {
        return trackingDAO.findByStudent(studentId);
    }

    public void save(StudentTracking tracking) {
        trackingDAO.save(tracking);
    }
}
