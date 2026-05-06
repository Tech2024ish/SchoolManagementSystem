package com.sms.dao;

import com.sms.model.Course;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class CourseDAO extends GenericDAO<Course> {

    public CourseDAO() {
        super(Course.class);
    }

    @Override
    public List<Course> findAll() {
        return query(em -> em.createQuery(
            "SELECT c FROM Course c LEFT JOIN FETCH c.teacher", Course.class)
            .getResultList());
    }

    public List<Course> findByTeacher(int teacherId) {
        return query(em -> em.createQuery(
            "SELECT c FROM Course c WHERE c.teacher.teacherId = :tid", Course.class)
            .setParameter("tid", teacherId)
            .getResultList());
    }
}
