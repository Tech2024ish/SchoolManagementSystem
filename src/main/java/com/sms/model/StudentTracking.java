package com.sms.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "StudentTracking")
public class StudentTracking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TrackId")
    private int trackId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "StudentId")
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private StatusType status;

    @Column(name = "Progress", length = 50)
    private String progress;

    @Column(name = "Location", length = 50)
    private String location;

    @Column(name = "Date")
    private LocalDate date;

    public enum StatusType {
        Wellbeing, Sick
    }

    public StudentTracking() {}

    public int getTrackId() { return trackId; }
    public void setTrackId(int trackId) { this.trackId = trackId; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public StatusType getStatus() { return status; }
    public void setStatus(StatusType status) { this.status = status; }

    public String getProgress() { return progress; }
    public void setProgress(String progress) { this.progress = progress; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
