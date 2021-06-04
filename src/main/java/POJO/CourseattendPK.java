package POJO;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CourseattendPK implements Serializable {
    private String studentId;
    private int courseId;
    private int semesterId;
    private int year;

    @Column(name = "StudentID", nullable = false, length = 8)
    @Id
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Column(name = "CourseID", nullable = false)
    @Id
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Column(name = "SemesterID", nullable = false)
    @Id
    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    @Column(name = "Year", nullable = false)
    @Id
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseattendPK that = (CourseattendPK) o;

        if (courseId != that.courseId) return false;
        if (semesterId != that.semesterId) return false;
        if (year != that.year) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId != null ? studentId.hashCode() : 0;
        result = 31 * result + courseId;
        result = 31 * result + semesterId;
        result = 31 * result + year;
        return result;
    }
}
