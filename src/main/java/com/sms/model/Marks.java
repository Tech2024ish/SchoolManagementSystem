package com.sms.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "Marks")
public class Marks implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MarksId")
    private int marksId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CourseId")
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "StudentId")
    private Student student;

    @Column(name = "Marks", precision = 5, scale = 2)
    private BigDecimal marks;

    @Column(name = "Grade", length = 2)
    private String grade;

    public Marks() {}

    public int getMarksId() { return marksId; }
    public void setMarksId(int marksId) { this.marksId = marksId; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public BigDecimal getMarks() { return marks; }
    public void setMarks(BigDecimal marks) { this.marks = marks; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
