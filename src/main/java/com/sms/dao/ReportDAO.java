package com.sms.dao;

import com.sms.model.Report;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ReportDAO extends GenericDAO<Report> {

    public ReportDAO() {
        super(Report.class);
    }

    @Override
    public List<Report> findAll() {
        return query(em -> em.createQuery(
            "SELECT r FROM Report r ORDER BY r.date DESC", Report.class)
            .getResultList());
    }
}
