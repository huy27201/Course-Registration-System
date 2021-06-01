package DAO;

import POJO.Subject;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class SubjectDAO {
    private static List<Subject> subjectList;
    public static List<Subject> getSubjectList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Subject";
            Query query = session.createQuery(hql);
            subjectList = query.list();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return subjectList;
    }
    public static Subject getSubjectByID(String subjectID) {
        Subject sub = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            sub = session.get(Subject.class, subjectID);
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return sub;
    }
    public static boolean removeSubjectByID(String subjectID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Subject sub = getSubjectByID(subjectID);
        if(sub == null){
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(sub);
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

    public static boolean addSubject(Subject sub) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (getSubjectByID(sub.getId()) != null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(sub);
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
    public static boolean updateSubject(Subject sub) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (getSubjectByID(sub.getId()) == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(sub);
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
