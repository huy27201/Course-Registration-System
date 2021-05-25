package POJO;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@IdClass(CourseattendPK.class)
public class Courseattend {
    private String studentId;
    private String courseId;
    private int semesterId;
    private int year;
    private String subjectId;
    private Timestamp dateRegisterd;

    @Id
    @Column(name = "StudentID", nullable = false, length = 8)
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Id
    @Column(name = "CourseID", nullable = false, length = 8)
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
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
    @Column(name = "SubjectID", nullable = true, length = 8)
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
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

        if (semesterId != that.semesterId) return false;
        if (year != that.year) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (courseId != null ? !courseId.equals(that.courseId) : that.courseId != null) return false;
        if (subjectId != null ? !subjectId.equals(that.subjectId) : that.subjectId != null) return false;
        if (dateRegisterd != null ? !dateRegisterd.equals(that.dateRegisterd) : that.dateRegisterd != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId != null ? studentId.hashCode() : 0;
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + semesterId;
        result = 31 * result + year;
        result = 31 * result + (subjectId != null ? subjectId.hashCode() : 0);
        result = 31 * result + (dateRegisterd != null ? dateRegisterd.hashCode() : 0);
        return result;
    }
}
