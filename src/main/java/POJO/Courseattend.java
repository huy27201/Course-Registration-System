package POJO;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@IdClass(CourseattendPK.class)
public class Courseattend {
    private String studentId;
    private int courseId;
    private int semesterId;
    private int year;
    private Timestamp dateRegisterd;

    public Courseattend() {
    }

    public Courseattend(String studentId, int courseId, int semesterId, int year, String subjectId, Timestamp dateRegisterd) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.semesterId = semesterId;
        this.year = year;
        this.dateRegisterd = dateRegisterd;
    }

    @Id
    @Column(name = "StudentID", nullable = false, length = 8)
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Id
    @Column(name = "CourseID", nullable = false)
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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
    @Column(name = "DateRegisterd", nullable = true)
    public Timestamp getDateRegisterd() {
        return dateRegisterd;
    }

    public void setDateRegisterd(Timestamp dateRegisterd) {
        this.dateRegisterd = dateRegisterd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Courseattend that = (Courseattend) o;

        if (courseId != that.courseId) return false;
        if (semesterId != that.semesterId) return false;
        if (year != that.year) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (dateRegisterd != null ? !dateRegisterd.equals(that.dateRegisterd) : that.dateRegisterd != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId != null ? studentId.hashCode() : 0;
        result = 31 * result + courseId;
        result = 31 * result + semesterId;
        result = 31 * result + year;
        result = 31 * result + (dateRegisterd != null ? dateRegisterd.hashCode() : 0);
        return result;
    }

}
