package POJO;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@IdClass(CourseattendPK.class)
public class Courseattend {
    private String studentId;
    private int courseId;
    private Timestamp dateRegisterd;
    private Student studentByStudentId;
    private Course courseByCourseId;

    public Courseattend(){}
    public Courseattend(String studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.dateRegisterd = new Timestamp(System.currentTimeMillis());
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
        if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) return false;
        if (dateRegisterd != null ? !dateRegisterd.equals(that.dateRegisterd) : that.dateRegisterd != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId != null ? studentId.hashCode() : 0;
        result = 31 * result + courseId;
        result = 31 * result + (dateRegisterd != null ? dateRegisterd.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "StudentID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public Student getStudentByStudentId() {
        return studentByStudentId;
    }

    public void setStudentByStudentId(Student studentByStudentId) {
        this.studentByStudentId = studentByStudentId;
    }

    @ManyToOne
    @JoinColumn(name = "CourseID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    public Course getCourseByCourseId() {
        return courseByCourseId;
    }

    public void setCourseByCourseId(Course courseByCourseId) {
        this.courseByCourseId = courseByCourseId;
    }
}
