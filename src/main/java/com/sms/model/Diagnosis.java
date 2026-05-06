package com.sms.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Diagnosis")
public class Diagnosis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiagnosisID")
    private int diagnosisId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PatientID")
    private Student patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NurseID")
    private Nurse nurse;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DoctorID")
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(name = "DiagnosisStatus", length = 20)
    private DiagnosisStatus diagnosisStatus;

    @Column(name = "Result", columnDefinition = "TEXT")
    private String result;

    @Column(name = "Date")
    private LocalDate date;

    public enum DiagnosisStatus {
        Pending, InProgress, Completed
    }

    public Diagnosis() {}

    public int getDiagnosisId() { return diagnosisId; }
    public void setDiagnosisId(int v) { this.diagnosisId = v; }

    public Student getPatient() { return patient; }
    public void setPatient(Student v) { this.patient = v; }

    public Nurse getNurse() { return nurse; }
    public void setNurse(Nurse v) { this.nurse = v; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor v) { this.doctor = v; }

    public DiagnosisStatus getDiagnosisStatus() { return diagnosisStatus; }
    public void setDiagnosisStatus(DiagnosisStatus v) { this.diagnosisStatus = v; }

    public String getResult() { return result; }
    public void setResult(String v) { this.result = v; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate v) { this.date = v; }
}
