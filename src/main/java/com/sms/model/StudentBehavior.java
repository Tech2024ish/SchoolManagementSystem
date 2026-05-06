package com.sms.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "StudentBehavior")
public class StudentBehavior implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BehaviorId")
    private int behaviorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "StudentId")
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(name = "Behavior")
    private BehaviorType behavior;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "Date")
    private LocalDate date;

    public enum BehaviorType {
        Good, Misconduct, Active, Shy, Other
    }

    public StudentBehavior() {}

    public int getBehaviorId() { return behaviorId; }
    public void setBehaviorId(int behaviorId) { this.behaviorId = behaviorId; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public BehaviorType getBehavior() { return behavior; }
    public void setBehavior(BehaviorType behavior) { this.behavior = behavior; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
