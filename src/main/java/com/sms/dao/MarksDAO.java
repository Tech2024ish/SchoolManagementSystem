package com.sms.dao;

import com.sms.model.Marks;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class MarksDAO extends GenericDAO<Marks> {

    public MarksDAO() {
        super(Marks.class);
    }

    public List<Marks> findByStudent(int studentId) {
        return query(em -> em.createQuery(
            "SELECT m FROM Marks m WHERE m.student.studentId = :sid", Marks.class)
            .setParameter("sid", studentId)
            .getResultList());
    }

    public List<Marks> findByCourse(int courseId) {
        return query(em -> em.createQuery(
            "SELECT m FROM Marks m WHERE m.course.courseId = :cid", Marks.class)
            .setParameter("cid", courseId)
            .getResultList());
    }

    public Marks findByStudentAndCourse(int studentId, int courseId) {
        return query(em -> {
            List<Marks> results = em.createQuery(
                "SELECT m FROM Marks m WHERE m.student.studentId = :sid AND m.course.courseId = :cid", Marks.class)
                .setParameter("sid", studentId)
                .setParameter("cid", courseId)
                .getResultList();
            return results.isEmpty() ? null : results.get(0);
        });
    }
}
