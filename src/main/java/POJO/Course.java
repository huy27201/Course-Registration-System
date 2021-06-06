package POJO;

import javax.persistence.*;
import java.util.Collection;

@Entity
@IdClass(CoursePK.class)
public class Course {
    private int id;
    private int semesterId;
    private int year;
    private String room;
    private String day;
    private String period;
    private Integer maxSlot;
    private Subject subjectBySubjectId;
    private String teacherName;

    public Course() {
    }

    public Course(int semesterId, int year, String teacherName, Subject subject, String room, String day, String period, Integer maxSlot) {
        this.semesterId = semesterId;
        this.year = year;
        this.teacherName = teacherName;
        this.room = room;
        this.day = day;
        this.period = period;
        this.maxSlot = maxSlot;
        this.subjectBySubjectId = subject;
    }

    @Id
    @Column(name = "teacherName", nullable = false)
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Id
    @Column(name = "SemesterID", nullable = false)
    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    @Id
    @Column(name = "Year", nullable = false)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Basic
    @Column(name = "Room", nullable = true, length = 10)
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Basic
    @Column(name = "Day", nullable = true, length = 10)
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Basic
    @Column(name = "Period", nullable = true, length = 45)
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Basic
    @Column(name = "maxSlot", nullable = true)
    public Integer getMaxSlot() {
        return maxSlot;
    }

    public void setMaxSlot(Integer maxSlot) {
        this.maxSlot = maxSlot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (id != course.id) return false;
        if (semesterId != course.semesterId) return false;
        if (year != course.year) return false;
        if (room != null ? !room.equals(course.room) : course.room != null) return false;
        if (day != null ? !day.equals(course.day) : course.day != null) return false;
        if (period != null ? !period.equals(course.period) : course.period != null) return false;
        if (maxSlot != null ? !maxSlot.equals(course.maxSlot) : course.maxSlot != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + semesterId;
        result = 31 * result + year;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (maxSlot != null ? maxSlot.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "SubjectID", referencedColumnName = "ID")
    public Subject getSubjectBySubjectId() {
        return subjectBySubjectId;
    }

    public void setSubjectBySubjectId(Subject subjectBySubjectId) {
        this.subjectBySubjectId = subjectBySubjectId;
    }


}
