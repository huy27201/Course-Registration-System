package DAO;

import POJO.Teacher;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class TeacherDAO {
    private static List<Teacher> teacherList;
    public static List<Teacher> getTeacherList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Teacher";
            Query query = session.createQuery(hql);
            teacherList = query.list();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return teacherList;
    }
    public static Teacher getTeacherByID(String teacherID) {
        Teacher teacher = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            teacher = (Teacher) session.get(Teacher.class, teacherID);
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return teacher;
    }
    public static boolean removeTeacherByID(String teacherID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Teacher tch = getTeacherByID(teacherID);
        if(tch == null){
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(tch);
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
    public static boolean addTeacher(Teacher tch) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (getTeacherByID(tch.getId()) != null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(tch);
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
    public static boolean updateTeacher(Teacher tch) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (getTeacherByID(tch.getId()) == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(tch);
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
}
