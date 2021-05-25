package POJO;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CoursePK implements Serializable {
    private String id;
    private int semesterId;
    private int year;

    @Column(name = "ID", nullable = false, length = 8)
    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

        CoursePK coursePK = (CoursePK) o;

        if (semesterId != coursePK.semesterId) return false;
        if (year != coursePK.year) return false;
        if (id != null ? !id.equals(coursePK.id) : coursePK.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + semesterId;
        result = 31 * result + year;
        return result;
    }
}
