package com.sms.service;

import com.sms.dao.AppointmentDAO;
import com.sms.model.Appointment;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class AppointmentService {

    @Inject
    private AppointmentDAO appointmentDAO;

    public List<Appointment> getAll() {
        return appointmentDAO.findAll();
    }

    public List<Appointment> getByUser(int userId) {
        return appointmentDAO.findByUser(userId);
    }

    public void save(Appointment appointment) {
        appointmentDAO.save(appointment);
    }
}
