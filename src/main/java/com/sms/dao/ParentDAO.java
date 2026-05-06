package com.sms.dao;

import com.sms.model.Parent;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ParentDAO extends GenericDAO<Parent> {

    public ParentDAO() {
        super(Parent.class);
    }

    public List<Parent> findByStudent(int studentId) {
        return query(em -> em.createQuery(
            "SELECT p FROM Parent p WHERE p.student.studentId = :sid", Parent.class)
            .setParameter("sid", studentId)
            .getResultList());
    }
}
