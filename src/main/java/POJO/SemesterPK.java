package POJO;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class SemesterPK implements Serializable {
    private int id;
    private int year;

    public SemesterPK() {
    }

    ;

    public SemesterPK(int year, int id) {
        this.id = id;
        this.year = year;
    }

    @Column(name = "ID", nullable = false)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

        SemesterPK that = (SemesterPK) o;

        if (id != that.id) return false;
        if (year != that.year) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + year;
        return result;
    }
}
