package com.sms.controller;

import com.sms.model.Course;
import com.sms.model.Teacher;
import com.sms.model.User;
import com.sms.service.AuthService;
import com.sms.service.CourseService;
import com.sms.service.StudentService;
import com.sms.service.TeacherService;
import com.sms.dao.UserDAO;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AdminBean implements Serializable {

    @Inject private TeacherService teacherService;
    @Inject private StudentService studentService;
    @Inject private CourseService courseService;
    @Inject private AuthService authService;
    @Inject private UserDAO userDAO;

    private List<Teacher> teachers;
    private List<User> users;
    private List<Course> courses;
    private Teacher selectedTeacher = new Teacher();
    private long totalStudents;
    private long totalTeachers;
    private long totalCourses;
    private long totalUsers;

    private String newUsername;
    private String newPassword;
    private int selectedCourseId;

    @PostConstruct
    public void loadAll() {
        teachers = teacherService.getAllTeachers();
        users = userDAO.findAll();
        courses = courseService.getAllCourses();
        totalStudents = studentService.getAllStudents().size();
        totalTeachers = teachers.size();
        totalCourses = courses.size();
        totalUsers = users.size();
    }

    public void prepareNew() {
        selectedTeacher = new Teacher();
        newUsername = "";
        newPassword = "";
        selectedCourseId = 0;
    }

    public void editTeacher(Teacher t) {
        selectedTeacher = t;
        selectedCourseId = (t.getCourse() != null) ? t.getCourse().getCourseId() : 0;
    }

    public void saveTeacher() {
        try {
            if (selectedCourseId > 0) {
                Course c = courseService.getById(selectedCourseId);
                selectedTeacher.setCourse(c);
            }
            if (selectedTeacher.getTeacherId() == 0) {
                teacherService.save(selectedTeacher);
                if (newUsername != null && !newUsername.isEmpty()) {
                    User u = new User();
                    u.setUsername(newUsername);
                    u.setPassword(authService.hashPassword(
                        newPassword != null && !newPassword.isEmpty() ? newPassword : "admin123"));
                    u.setUserType(User.UserType.Teacher);
                    userDAO.save(u);
                }
                addMessage("Teacher added successfully.", FacesMessage.SEVERITY_INFO);
            } else {
                teacherService.update(selectedTeacher);
                addMessage("Teacher updated successfully.", FacesMessage.SEVERITY_INFO);
            }
            loadAll();
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deleteTeacher(int id) {
        try {
            teacherService.delete(id);
            addMessage("Teacher deleted.", FacesMessage.SEVERITY_INFO);
            loadAll();
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void saveUser(User user) {
        try {
            userDAO.update(user);
            addMessage("User updated.", FacesMessage.SEVERITY_INFO);
            loadAll();
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    private void addMessage(String msg, FacesMessage.Severity sev) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(sev, msg, null));
    }

    public List<Teacher> getTeachers() { return teachers; }
    public List<User> getUsers() { return users; }
    public List<Course> getCourses() { return courses; }
    public Teacher getSelectedTeacher() { return selectedTeacher; }
    public void setSelectedTeacher(Teacher t) { this.selectedTeacher = t; }
    public long getTotalStudents() { return totalStudents; }
    public long getTotalTeachers() { return totalTeachers; }
    public long getTotalCourses() { return totalCourses; }
    public long getTotalUsers() { return totalUsers; }
    public String getNewUsername() { return newUsername; }
    public void setNewUsername(String v) { this.newUsername = v; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String v) { this.newPassword = v; }
    public int getSelectedCourseId() { return selectedCourseId; }
    public void setSelectedCourseId(int v) { this.selectedCourseId = v; }
}
