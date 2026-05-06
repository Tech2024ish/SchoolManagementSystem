package com.sms.dao;

import com.sms.model.Teacher;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TeacherDAO extends GenericDAO<Teacher> {

    public TeacherDAO() {
        super(Teacher.class);
    }

    @Override
    public List<Teacher> findAll() {
        return query(em -> em.createQuery(
            "SELECT t FROM Teacher t LEFT JOIN FETCH t.course", Teacher.class)
            .getResultList());
    }

    @Override
    public Teacher findById(int id) {
        return query(em -> em.find(Teacher.class, id));
    }
}
