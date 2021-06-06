package DAO;

import POJO.Student;
import POJO.Teacher;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
    public static boolean removeStudentByID(String studentID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Student st = getStudentByID(studentID);
        if (st == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(st);
            transaction.commit();
        } catch (HibernateException ex) {
            //Log the exception
            transaction.rollback();
            System.err.println(ex);
        } finally {
            session.close();
        }
        return true;
    }

    public static boolean addStudent(Student st) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (getStudentByID(st.getId()) != null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(st);
            transaction.commit();
        } catch (HibernateException ex) {
            //Log the exception
            transaction.rollback();
            System.err.println(ex);
        } finally {
            session.close();
        }
        return true;
    }

    public static boolean updateStudent(Student st) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (getStudentByID(st.getId()) == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(st);
            transaction.commit();
        } catch (HibernateException ex) {
            //Log the exception
            transaction.rollback();
            System.err.println(ex);
        } finally {
            session.close();
        }
        return true;
    }

    public static List<Student> getStudentListByClass(String classId) {
        List<Student> list = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Student st where st.classnameByClassName.id=:classId";
            Query query = session.createQuery(hql);
            query.setParameter("classId", classId);
            list = query.list();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return list;
    }
}
