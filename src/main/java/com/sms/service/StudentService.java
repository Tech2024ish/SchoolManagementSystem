package com.sms.service;

import com.sms.dao.StudentDAO;
import com.sms.model.Student;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class StudentService {

    @Inject
    private StudentDAO studentDAO;

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    public Student getById(int id) {
        return studentDAO.findById(id);
    }

    public Student getByRegNumber(String regNumber) {
        return studentDAO.findByRegNumber(regNumber);
    }

    public void save(Student student) {
        studentDAO.save(student);
    }

    public void update(Student student) {
        studentDAO.update(student);
    }

    public void delete(int id) {
        Student s = studentDAO.findById(id);
        if (s != null) studentDAO.delete(s);
    }
}
