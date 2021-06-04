package POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Classname {
    private String id;
    private Integer total;

    @Id
    @Column(name = "ID", nullable = false, length = 6)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Total", nullable = true)
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classname classname = (Classname) o;

        if (id != null ? !id.equals(classname.id) : classname.id != null) return false;
        if (total != null ? !total.equals(classname.total) : classname.total != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (total != null ? total.hashCode() : 0);
        return result;
    }
}
