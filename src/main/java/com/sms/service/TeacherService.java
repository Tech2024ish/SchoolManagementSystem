package com.sms.service;

import com.sms.dao.TeacherDAO;
import com.sms.model.Teacher;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class TeacherService {

    @Inject
    private TeacherDAO teacherDAO;

    public List<Teacher> getAllTeachers() {
        return teacherDAO.findAll();
    }

    public Teacher getById(int id) {
        return teacherDAO.findById(id);
    }

    public void save(Teacher teacher) {
        teacherDAO.save(teacher);
    }

    public void update(Teacher teacher) {
        teacherDAO.update(teacher);
    }

    public void delete(int id) {
        Teacher t = teacherDAO.findById(id);
        if (t != null) teacherDAO.delete(t);
    }
}
