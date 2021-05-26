package POJO;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Student {
    private String id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String sex;
    private Classname classnameByClassName;
    private Account accountByAccount;

    @Id
    @Column(name = "ID", nullable = false, length = 8)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FirstName", nullable = true, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LastName", nullable = true, length = 10)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "DateOfBirth", nullable = true)
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "Sex", nullable = true, length = 3)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != null ? !id.equals(student.id) : student.id != null) return false;
        if (firstName != null ? !firstName.equals(student.firstName) : student.firstName != null) return false;
        if (lastName != null ? !lastName.equals(student.lastName) : student.lastName != null) return false;
        if (dateOfBirth != null ? !dateOfBirth.equals(student.dateOfBirth) : student.dateOfBirth != null) return false;
        if (sex != null ? !sex.equals(student.sex) : student.sex != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ClassName", referencedColumnName = "ID")
    public Classname getClassnameByClassName() {
        return classnameByClassName;
    }

    public void setClassnameByClassName(Classname classnameByClassName) {
        this.classnameByClassName = classnameByClassName;
    }

    @ManyToOne
    @JoinColumn(name = "Account", referencedColumnName = "AccountID")
    public Account getAccountByAccount() {
        return accountByAccount;
    }

    public void setAccountByAccount(Account accountByAccount) {
        this.accountByAccount = accountByAccount;
    }
}
