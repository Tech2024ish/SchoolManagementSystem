package com.sms.controller;

import com.sms.model.Appointment;
import com.sms.model.User;
import com.sms.service.AppointmentService;
import com.sms.service.AuthService;

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
public class AppointmentBean implements Serializable {

    @Inject private AppointmentService appointmentService;
    @Inject private AuthService authService;

    private List<Appointment> appointments;
    private Appointment newAppointment = new Appointment();

    @PostConstruct
    public void loadAppointments() {
        User u = authService.getCurrentUser();
        if (u != null) {
            appointments = appointmentService.getByUser(u.getUserId());
        }
        newAppointment = new Appointment();
    }

    public void saveAppointment() {
        try {
            User u = authService.getCurrentUser();
            newAppointment.setRequestedBy(u);
            if (newAppointment.getResponsible() == null) {
                newAppointment.setResponsible(Appointment.ResponsibleType.Administration);
            }
            if (newAppointment.getDate() == null) newAppointment.setDate(LocalDate.now());
            appointmentService.save(newAppointment);
            newAppointment = new Appointment();
            loadAppointments();
            addMessage("Appointment saved.", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    private void addMessage(String msg, FacesMessage.Severity sev) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(sev, msg, null));
    }

    public List<Appointment> getAppointments() { return appointments; }
    public Appointment getNewAppointment() { return newAppointment; }
    public void setNewAppointment(Appointment a) { this.newAppointment = a; }
}
