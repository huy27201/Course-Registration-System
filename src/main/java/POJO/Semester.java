package POJO;

import javax.persistence.*;
import java.sql.Date;

@Entity
@IdClass(SemesterPK.class)
public class Semester {
    private int id;
    private int year;
    private Date dateStart;
    private Date dateEnd;

    public Semester(int id, int year, Date dateStart, Date dateEnd) {
        this.id = id;
        this.year = year;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public Semester() {
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
    @Column(name = "Year", nullable = false)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Basic
    @Column(name = "DateStart", nullable = true)
    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    @Basic
    @Column(name = "DateEnd", nullable = true)
    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Semester semester = (Semester) o;

        if (id != semester.getId()) return false;
        if (year != semester.getYear()) return false;
        if (dateStart != null ? !dateStart.equals(semester.dateStart) : semester.dateStart != null) return false;
        if (dateEnd != null ? !dateEnd.equals(semester.dateEnd) : semester.dateEnd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + year;
        result = 31 * result + (dateStart != null ? dateStart.hashCode() : 0);
        result = 31 * result + (dateEnd != null ? dateEnd.hashCode() : 0);
        return result;
    }
}
