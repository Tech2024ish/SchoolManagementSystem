package com.sms.controller;

import com.sms.dao.DiagnosisDAO;
import com.sms.dao.DoctorDAO;
import com.sms.dao.NurseDAO;
import com.sms.dao.StudentDAO;
import com.sms.model.Diagnosis;
import com.sms.model.Doctor;
import com.sms.model.Nurse;
import com.sms.model.Student;

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
public class MedicalBean implements Serializable {

    @Inject private NurseDAO nurseDAO;
    @Inject private DoctorDAO doctorDAO;
    @Inject private DiagnosisDAO diagnosisDAO;
    @Inject private StudentDAO studentDAO;

    private List<Nurse> nurses;
    private List<Doctor> doctors;
    private List<Diagnosis> diagnoses;
    private List<Student> students;

    private Nurse selectedNurse = new Nurse();
    private Doctor selectedDoctor = new Doctor();
    private Diagnosis selectedDiagnosis = new Diagnosis();

    private int selectedPatientId;
    private int selectedNurseId;
    private int selectedDoctorId;

    @PostConstruct
    public void init() {
        loadAll();
    }

    private void loadAll() {
        nurses = nurseDAO.findAll();
        doctors = doctorDAO.findAll();
        diagnoses = diagnosisDAO.findAll();
        students = studentDAO.findAll();
    }

    // Nurse
    public void prepareNewNurse() { selectedNurse = new Nurse(); }

    public void editNurse(Nurse n) { selectedNurse = n; }

    public void saveNurse() {
        try {
            if (selectedNurse.getNurseId() == 0) nurseDAO.save(selectedNurse);
            else nurseDAO.update(selectedNurse);
            msg("Nurse saved.", FacesMessage.SEVERITY_INFO);
            loadAll();
        } catch (Exception e) {
            msg("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deleteNurse(Nurse n) {
        try { nurseDAO.delete(n); msg("Nurse deleted.", FacesMessage.SEVERITY_INFO); loadAll(); }
        catch (Exception e) { msg("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR); }
    }

    // Doctor
    public void prepareNewDoctor() { selectedDoctor = new Doctor(); }

    public void editDoctor(Doctor d) { selectedDoctor = d; }

    public void saveDoctor() {
        try {
            if (selectedDoctor.getDoctorId() == 0) doctorDAO.save(selectedDoctor);
            else doctorDAO.update(selectedDoctor);
            msg("Doctor saved.", FacesMessage.SEVERITY_INFO);
            loadAll();
        } catch (Exception e) {
            msg("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deleteDoctor(Doctor d) {
        try { doctorDAO.delete(d); msg("Doctor deleted.", FacesMessage.SEVERITY_INFO); loadAll(); }
        catch (Exception e) { msg("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR); }
    }

    // Diagnosis
    public void prepareNewDiagnosis() {
        selectedDiagnosis = new Diagnosis();
        selectedDiagnosis.setDate(LocalDate.now());
        selectedPatientId = 0;
        selectedNurseId = 0;
        selectedDoctorId = 0;
    }

    public void editDiagnosis(Diagnosis d) {
        selectedDiagnosis = d;
        selectedPatientId = d.getPatient() != null ? d.getPatient().getStudentId() : 0;
        selectedNurseId = d.getNurse() != null ? d.getNurse().getNurseId() : 0;
        selectedDoctorId = d.getDoctor() != null ? d.getDoctor().getDoctorId() : 0;
    }

    public void saveDiagnosis() {
        try {
            if (selectedPatientId > 0) selectedDiagnosis.setPatient(studentDAO.findById(selectedPatientId));
            if (selectedNurseId > 0)   selectedDiagnosis.setNurse(nurseDAO.findById(selectedNurseId));
            if (selectedDoctorId > 0)  selectedDiagnosis.setDoctor(doctorDAO.findById(selectedDoctorId));
            if (selectedDiagnosis.getDate() == null) selectedDiagnosis.setDate(LocalDate.now());
            if (selectedDiagnosis.getDiagnosisId() == 0) diagnosisDAO.save(selectedDiagnosis);
            else diagnosisDAO.update(selectedDiagnosis);
            msg("Diagnosis saved.", FacesMessage.SEVERITY_INFO);
            loadAll();
        } catch (Exception e) {
            msg("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deleteDiagnosis(Diagnosis d) {
        try { diagnosisDAO.delete(d); msg("Diagnosis deleted.", FacesMessage.SEVERITY_INFO); loadAll(); }
        catch (Exception e) { msg("Error: " + e.getMessage(), FacesMessage.SEVERITY_ERROR); }
    }

    private void msg(String m, FacesMessage.Severity s) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(s, m, null));
    }

    public Diagnosis.DiagnosisStatus[] getDiagnosisStatuses() { return Diagnosis.DiagnosisStatus.values(); }

    public List<Nurse> getNurses() { return nurses; }
    public List<Doctor> getDoctors() { return doctors; }
    public List<Diagnosis> getDiagnoses() { return diagnoses; }
    public List<Student> getStudents() { return students; }
    public Nurse getSelectedNurse() { return selectedNurse; }
    public void setSelectedNurse(Nurse v) { this.selectedNurse = v; }
    public Doctor getSelectedDoctor() { return selectedDoctor; }
    public void setSelectedDoctor(Doctor v) { this.selectedDoctor = v; }
    public Diagnosis getSelectedDiagnosis() { return selectedDiagnosis; }
    public void setSelectedDiagnosis(Diagnosis v) { this.selectedDiagnosis = v; }
    public int getSelectedPatientId() { return selectedPatientId; }
    public void setSelectedPatientId(int v) { this.selectedPatientId = v; }
    public int getSelectedNurseId() { return selectedNurseId; }
    public void setSelectedNurseId(int v) { this.selectedNurseId = v; }
    public int getSelectedDoctorId() { return selectedDoctorId; }
    public void setSelectedDoctorId(int v) { this.selectedDoctorId = v; }

    public long getTotalNurses() { return nurses != null ? nurses.size() : 0; }
    public long getTotalDoctors() { return doctors != null ? doctors.size() : 0; }
    public long getTotalDiagnoses() { return diagnoses != null ? diagnoses.size() : 0; }
}
