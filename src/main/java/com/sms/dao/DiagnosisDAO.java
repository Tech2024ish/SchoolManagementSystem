package com.sms.dao;

import com.sms.model.Diagnosis;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class DiagnosisDAO extends GenericDAO<Diagnosis> {

    public DiagnosisDAO() { super(Diagnosis.class); }

    @Override
    public List<Diagnosis> findAll() {
        return query(em -> em.createQuery(
            "SELECT d FROM Diagnosis d LEFT JOIN FETCH d.patient LEFT JOIN FETCH d.nurse LEFT JOIN FETCH d.doctor",
            Diagnosis.class).getResultList());
    }
}
