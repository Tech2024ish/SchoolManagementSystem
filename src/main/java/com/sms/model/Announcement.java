package com.sms.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Announcement")
public class Announcement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnnouncementId")
    private int announcementId;

    @Column(name = "Message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "Date")
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "TargetGroup")
    private TargetGroup targetGroup;

    public enum TargetGroup {
        Students, Teachers, Parents, All
    }

    public Announcement() {}

    public int getAnnouncementId() { return announcementId; }
    public void setAnnouncementId(int announcementId) { this.announcementId = announcementId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public TargetGroup getTargetGroup() { return targetGroup; }
    public void setTargetGroup(TargetGroup targetGroup) { this.targetGroup = targetGroup; }
}
