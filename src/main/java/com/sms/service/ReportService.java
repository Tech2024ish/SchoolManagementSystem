package com.sms.service;

import com.sms.dao.ReportDAO;
import com.sms.model.Report;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class ReportService {

    @Inject
    private ReportDAO reportDAO;

    public List<Report> getAll() {
        return reportDAO.findAll();
    }

    public void save(Report report) {
        reportDAO.save(report);
    }
}
