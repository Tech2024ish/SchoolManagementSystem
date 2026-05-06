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
public class StudentBean implements Serializable {

    @Inject private MarksService marksService;
    @Inject private ParentService parentService;
    @Inject private AnnouncementService announcementService;
    @Inject private AppointmentService appointmentService;
    @Inject private AuthService authService;
    @Inject private StudentService studentService;

    private List<Marks> myMarks;
    private Parent parentInfo = new Parent();
    private List<Announcement> announcements;
    private Appointment newAppointment = new Appointment();
    private double averageMarks;
    private Student myStudent;

    @PostConstruct
    public void loadAll() {
        User u = authService.getCurrentUser();
        if (u == null) return;
        myStudent = findStudentByUsername(u.getUsername());
        if (myStudent != null) {
            loadMyMarks();
            List<Parent> parents = parentService.getByStudent(myStudent.getStudentId());
            if (!parents.isEmpty()) parentInfo = parents.get(0);
        }
        announcements = announcementService.getForUserType("Students");
        newAppointment = new Appointment();
    }

    private Student findStudentByUsername(String username) {
        List<Student> all = studentService.getAllStudents();
        for (Student s : all) {
            if (s.getRegNumber() != null && username.toLowerCase().contains(
                s.getFirstName() != null ? s.getFirstName().toLowerCase() : "")) {
                return s;
            }
        }
        return all.isEmpty() ? null : all.get(0);
    }

    public void loadMyMarks() {
        if (myStudent == null) return;
        myMarks = marksService.getByStudent(myStudent.getStudentId());
        if (myMarks != null && !myMarks.isEmpty()) {
            double sum = 0;
            for (Marks m : myMarks) {
                if (m.getMarks() != null) sum += m.getMarks().doubleValue();
            }
            averageMarks = sum / myMarks.size();
        }
    }

    public void saveParentInfo() {
        try {
            if (myStudent != null) parentInfo.setStudent(myStudent);
            if (parentInfo.getParentId() == 0) {
                parentService.save(parentInfo);
            } else {
                parentService.update(parentInfo);
            }
            addMessage("Parent info saved.", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
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

    public List<Marks> getMyMarks() { return myMarks; }
    public Parent getParentInfo() { return parentInfo; }
    public void setParentInfo(Parent p) { this.parentInfo = p; }
    public List<Announcement> getAnnouncements() { return announcements; }
    public Appointment getNewAppointment() { return newAppointment; }
    public void setNewAppointment(Appointment a) { this.newAppointment = a; }
    public double getAverageMarks() { return averageMarks; }
    public Student getMyStudent() { return myStudent; }
}
