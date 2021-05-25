package DAO;

import POJO.Student;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class StudentDAO {
    private static List<Student> studentList;
    private static List student = null;
    public static List<Student> getStudentList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Student";
            Query query = session.createQuery(hql);
            studentList = query.list();
        } catch (HibernateException ex) {
        //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return studentList;
    }
    public static List getStudent(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select st.lastName from Student as st, Account as acc " +
                    "join st inner join acc where st.account = acc.acountID and " +
                    " acc.accountID = " + username;
            Query query = session.createQuery(hql);
            student = query.list();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return student;
    }
}
