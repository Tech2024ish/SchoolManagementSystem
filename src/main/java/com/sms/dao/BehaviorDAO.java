package com.sms.dao;

import com.sms.model.StudentBehavior;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class BehaviorDAO extends GenericDAO<StudentBehavior> {

    public BehaviorDAO() {
        super(StudentBehavior.class);
    }

    public List<StudentBehavior> findByStudent(int studentId) {
        return query(em -> em.createQuery(
            "SELECT b FROM StudentBehavior b WHERE b.student.studentId = :sid ORDER BY b.date DESC",
            StudentBehavior.class)
            .setParameter("sid", studentId)
            .getResultList());
    }
}
