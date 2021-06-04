package DAO;

import POJO.Student;
import POJO.Teacher;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAO {
    private static List<Student> studentList;

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

    public static Student getStudentByID(String studentID) {
        Student student = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            student = (Student) session.get(Student.class, studentID);
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return student;
    }

    public static Student getStudentByUsername(String username) {
        Student student = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Student st where st.accountByAccount.accountId=:username";
            Query query = session.createQuery(hql);
            query.setParameter("username", username);
            student = (Student) query.getSingleResult();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return student;
    }
}
