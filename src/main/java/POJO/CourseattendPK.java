package POJO;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CourseattendPK implements Serializable {
    private String studentId;
    private int courseId;

    public CourseattendPK() {}
    public CourseattendPK(String studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseattendPK that = (CourseattendPK) o;

        if (courseId != that.courseId) return false;
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId != null ? studentId.hashCode() : 0;
        result = 31 * result + courseId;
        return result;
    }
}
