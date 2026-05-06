package com.sms.controller;

import com.sms.model.Report;
import com.sms.model.User;
import com.sms.service.AuthService;
import com.sms.service.ReportService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Named
@ViewScoped
public class ReportBean implements Serializable {

    @Inject private ReportService reportService;
    @Inject private AuthService authService;

    private List<Report> reports;

    @PostConstruct
    public void loadReports() {
        reports = reportService.getAll();
    }

    public void generateReport(String type) {
        try {
            User u = authService.getCurrentUser();
            Report r = new Report();
            r.setType(type);
            r.setDate(LocalDate.now());
            r.setGeneratedBy(u);
            reportService.save(r);
            loadReports();
            addMessage(type + " report generated.", FacesMessage.SEVERITY_INFO);
        } catch (Exception e) {
            addMessage("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    private void addMessage(String msg, FacesMessage.Severity sev) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(sev, msg, null));
    }

    public List<Report> getReports() { return reports; }
}
