package com.sms.controller;

import com.sms.model.*;
import com.sms.service.*;

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
public class ParentBean implements Serializable {

    @Inject private MarksService marksService;
    @Inject private BehaviorService behaviorService;
    @Inject private TrackingService trackingService;
    @Inject private AnnouncementService announcementService;
    @Inject private AppointmentService appointmentService;
    @Inject private AuthService authService;
    @Inject private ParentService parentService;
    @Inject private StudentService studentService;

    private List<Marks> childMarks;
    private List<StudentBehavior> childBehavior;
    private List<StudentTracking> childTracking;
    private List<Announcement> announcements;
    private Appointment newAppointment = new Appointment();
    private Student myChild;

    @PostConstruct
    public void loadAll() {
        User u = authService.getCurrentUser();
        if (u == null) return;
        List<Student> allStudents = studentService.getAllStudents();
        if (!allStudents.isEmpty()) myChild = allStudents.get(0);
        loadChildData();
        announcements = announcementService.getForUserType("Parents");
        newAppointment = new Appointment();
    }

    public void loadChildData() {
        if (myChild == null) return;
        childMarks = marksService.getByStudent(myChild.getStudentId());
        childBehavior = behaviorService.getByStudent(myChild.getStudentId());
        childTracking = trackingService.getByStudent(myChild.getStudentId());
    }

    public void bookAppointment() {
        try {
            User u = authService.getCurrentUser();
            newAppointment.setRequestedBy(u);
            newAppointment.setResponsible(Appointment.ResponsibleType.Administration);
            if (newAppointment.getDate() == null) newAppointment.setDate(LocalDate.now());
            appointmentService.save(newAppointment);
            newAppointment = new Appointment();
            addMessage("Appointment booked.", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    private void addMessage(String msg, FacesMessage.Severity sev) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(sev, msg, null));
    }

    public List<Marks> getChildMarks() { return childMarks; }
    public List<StudentBehavior> getChildBehavior() { return childBehavior; }
    public List<StudentTracking> getChildTracking() { return childTracking; }
    public List<Announcement> getAnnouncements() { return announcements; }
    public Appointment getNewAppointment() { return newAppointment; }
    public void setNewAppointment(Appointment a) { this.newAppointment = a; }
    public Student getMyChild() { return myChild; }
    public void setMyChild(Student s) { this.myChild = s; }
}
