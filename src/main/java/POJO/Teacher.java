package POJO;

import javax.persistence.*;

@Entity
public class Teacher {
    private String id;
    private String firstName;
    private String lastName;
    private String sex;
    private Account accountByAccount;

    public Teacher() {
    }

    public Teacher(String id, String firstName, String lastName, String sex, Account acc) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.accountByAccount = acc;
        this.accountByAccount.setRole("GV");
    }

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

    public void setFullName(String fullName) {
    }

    public String getFullName() {
        return firstName + " " + lastName;
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

        Teacher teacher = (Teacher) o;

        if (id != null ? !id.equals(teacher.id) : teacher.id != null) return false;
        if (firstName != null ? !firstName.equals(teacher.firstName) : teacher.firstName != null) return false;
        if (lastName != null ? !lastName.equals(teacher.lastName) : teacher.lastName != null) return false;
        if (sex != null ? !sex.equals(teacher.sex) : teacher.sex != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        return result;
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
