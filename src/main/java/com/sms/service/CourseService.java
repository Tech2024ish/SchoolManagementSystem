package com.sms.service;

import com.sms.dao.CourseDAO;
import com.sms.model.Course;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class CourseService {

    @Inject
    private CourseDAO courseDAO;

    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    public Course getById(int id) {
        return courseDAO.findById(id);
    }

    public void save(Course course) {
        courseDAO.save(course);
    }

    public void update(Course course) {
        courseDAO.update(course);
    }

    public void delete(int id) {
        Course c = courseDAO.findById(id);
        if (c != null) courseDAO.delete(c);
    }
}
