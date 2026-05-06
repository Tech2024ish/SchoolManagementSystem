package com.sms.dao;

import com.sms.model.StudentTracking;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TrackingDAO extends GenericDAO<StudentTracking> {

    public TrackingDAO() {
        super(StudentTracking.class);
    }

    public List<StudentTracking> findByStudent(int studentId) {
        return query(em -> em.createQuery(
            "SELECT t FROM StudentTracking t WHERE t.student.studentId = :sid ORDER BY t.date DESC",
            StudentTracking.class)
            .setParameter("sid", studentId)
            .getResultList());
    }
}
