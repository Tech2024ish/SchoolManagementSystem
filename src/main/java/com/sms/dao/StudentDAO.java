package com.sms.dao;

import com.sms.model.Student;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class StudentDAO extends GenericDAO<Student> {

    public StudentDAO() {
        super(Student.class);
    }

    @Override
    public List<Student> findAll() {
        return query(em -> em.createQuery(
            "SELECT s FROM Student s", Student.class).getResultList());
    }

    public Student findByRegNumber(String regNumber) {
        return query(em -> {
            List<Student> results = em.createQuery(
                "SELECT s FROM Student s WHERE s.regNumber = :reg", Student.class)
                .setParameter("reg", regNumber)
                .getResultList();
            return results.isEmpty() ? null : results.get(0);
        });
    }
}
