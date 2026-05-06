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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Named
@ViewScoped
public class TeacherBean implements Serializable {

    @Inject private CourseService courseService;
    @Inject private StudentService studentService;
    @Inject private MarksService marksService;
    @Inject private BehaviorService behaviorService;
    @Inject private TrackingService trackingService;
    @Inject private AppointmentService appointmentService;
    @Inject private AnnouncementService announcementService;
    @Inject private AuthService authService;

    private List<Course> courses;
    private List<Student> students;
    private List<Marks> marksList;
    private List<StudentBehavior> behaviorList;
    private List<StudentTracking> trackingList;
    private List<Announcement> announcements;

    private Course selectedCourse = new Course();
    private Student selectedStudent;
    private Marks selectedMarks = new Marks();
    private StudentBehavior selectedBehavior = new StudentBehavior();
    private StudentTracking selectedTracking = new StudentTracking();
    private Appointment newAppointment = new Appointment();
    private Announcement newAnnouncement = new Announcement();

    private int selectedStudentId;
    private int selectedCourseId;
    private double marksValue;

    @PostConstruct
    public void loadAll() {
        courses = courseService.getAllCourses();
        students = studentService.getAllStudents();
        announcements = announcementService.getAll();
        newAppointment = new Appointment();
        newAnnouncement = new Announcement();
    }

    public void saveCourse() {
        try {
            if (selectedCourse.getCourseId() == 0) {
                courseService.save(selectedCourse);
                addMessage("Course added.", FacesMessage.SEVERITY_INFO);
            } else {
                courseService.update(selectedCourse);
                addMessage("Course updated.", FacesMessage.SEVERITY_INFO);
            }
            selectedCourse = new Course();
            courses = courseService.getAllCourses();
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editCourse(Course c) { selectedCourse = c; }

    public void deleteCourse(int id) {
        try {
            courseService.delete(id);
            addMessage("Course deleted.", FacesMessage.SEVERITY_INFO);
            courses = courseService.getAllCourses();
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void loadMarksByCourse() {
        if (selectedCourseId > 0) {
            marksList = marksService.getByCourse(selectedCourseId);
        }
    }

    public void saveMarks() {
        try {
            marksService.saveMarks(selectedStudentId, selectedCourseId, marksValue);
            addMessage("Marks saved.", FacesMessage.SEVERITY_INFO);
            loadMarksByCourse();
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void loadBehaviorByStudent() {
        if (selectedStudentId > 0) {
            behaviorList = behaviorService.getByStudent(selectedStudentId);
        }
    }

    public void saveBehavior() {
        try {
            if (selectedBehavior.getStudent() == null) {
                Student s = studentService.getById(selectedStudentId);
                selectedBehavior.setStudent(s);
            }
            if (selectedBehavior.getDate() == null) selectedBehavior.setDate(LocalDate.now());
            behaviorService.save(selectedBehavior);
            selectedBehavior = new StudentBehavior();
            addMessage("Behavior saved.", FacesMessage.SEVERITY_INFO);
            loadBehaviorByStudent();
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void loadTrackingByStudent() {
        if (selectedStudentId > 0) {
            trackingList = trackingService.getByStudent(selectedStudentId);
        }
    }

    public void saveTracking() {
        try {
            if (selectedTracking.getStudent() == null) {
                Student s = studentService.getById(selectedStudentId);
                selectedTracking.setStudent(s);
            }
            if (selectedTracking.getDate() == null) selectedTracking.setDate(LocalDate.now());
            trackingService.save(selectedTracking);
            selectedTracking = new StudentTracking();
            addMessage("Tracking saved.", FacesMessage.SEVERITY_INFO);
            loadTrackingByStudent();
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

    public void saveAnnouncement() {
        try {
            if (newAnnouncement.getDate() == null) newAnnouncement.setDate(LocalDate.now());
            announcementService.save(newAnnouncement);
            newAnnouncement = new Announcement();
            announcements = announcementService.getAll();
            addMessage("Announcement posted.", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    private void addMessage(String msg, FacesMessage.Severity sev) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(sev, msg, null));
    }

    public List<Course> getCourses() { return courses; }
    public List<Student> getStudents() { return students; }
    public List<Marks> getMarksList() { return marksList; }
    public List<StudentBehavior> getBehaviorList() { return behaviorList; }
    public List<StudentTracking> getTrackingList() { return trackingList; }
    public List<Announcement> getAnnouncements() { return announcements; }
    public Course getSelectedCourse() { return selectedCourse; }
    public void setSelectedCourse(Course c) { this.selectedCourse = c; }
    public StudentBehavior getSelectedBehavior() { return selectedBehavior; }
    public void setSelectedBehavior(StudentBehavior b) { this.selectedBehavior = b; }
    public StudentTracking getSelectedTracking() { return selectedTracking; }
    public void setSelectedTracking(StudentTracking t) { this.selectedTracking = t; }
    public Appointment getNewAppointment() { return newAppointment; }
    public void setNewAppointment(Appointment a) { this.newAppointment = a; }
    public Announcement getNewAnnouncement() { return newAnnouncement; }
    public void setNewAnnouncement(Announcement a) { this.newAnnouncement = a; }
    public int getSelectedStudentId() { return selectedStudentId; }
    public void setSelectedStudentId(int v) { this.selectedStudentId = v; }
    public int getSelectedCourseId() { return selectedCourseId; }
    public void setSelectedCourseId(int v) { this.selectedCourseId = v; }
    public double getMarksValue() { return marksValue; }
    public void setMarksValue(double v) { this.marksValue = v; }
}
