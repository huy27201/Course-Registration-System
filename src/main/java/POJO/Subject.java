package POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Subject {
    private String id;
    private String name;
    private Integer credits;

    public Subject(String id, String name, Integer credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
    }
    public Subject() { }

    @Id
    @Column(name = "ID", nullable = false, length = 8)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name", nullable = true, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Credits", nullable = true)
    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        if (id != null ? !id.equals(subject.id) : subject.id != null) return false;
        if (name != null ? !name.equals(subject.name) : subject.name != null) return false;
        if (credits != null ? !credits.equals(subject.credits) : subject.credits != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (credits != null ? credits.hashCode() : 0);
        return result;
    }
}
