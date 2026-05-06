package com.sms.service;

import com.sms.dao.CourseDAO;
import com.sms.dao.MarksDAO;
import com.sms.dao.StudentDAO;
import com.sms.model.Course;
import com.sms.model.Marks;
import com.sms.model.Student;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.util.List;

@Named
@ApplicationScoped
public class MarksService {

    @Inject
    private MarksDAO marksDAO;

    @Inject
    private StudentDAO studentDAO;

    @Inject
    private CourseDAO courseDAO;

    public void saveMarks(int studentId, int courseId, double marksValue) {
        Marks existing = marksDAO.findByStudentAndCourse(studentId, courseId);
        if (existing != null) {
            existing.setMarks(BigDecimal.valueOf(marksValue));
            existing.setGrade(calculateGrade(marksValue));
            marksDAO.update(existing);
        } else {
            Marks m = new Marks();
            Student s = studentDAO.findById(studentId);
            Course c = courseDAO.findById(courseId);
            m.setStudent(s);
            m.setCourse(c);
            m.setMarks(BigDecimal.valueOf(marksValue));
            m.setGrade(calculateGrade(marksValue));
            marksDAO.save(m);
        }
    }

    public String calculateGrade(double marks) {
        if (marks >= 80) return "A";
        if (marks >= 70) return "B";
        if (marks >= 60) return "C";
        if (marks >= 50) return "D";
        return "F";
    }

    public List<Marks> getByStudent(int studentId) {
        return marksDAO.findByStudent(studentId);
    }

    public List<Marks> getByCourse(int courseId) {
        return marksDAO.findByCourse(courseId);
    }
}
