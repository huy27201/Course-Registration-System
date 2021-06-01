package DAO;


import POJO.Semester;
import POJO.SemesterPK;
import POJO.Subject;

import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class SemesterDAO {
    private static List<Semester> semesterList;
    public static List<Semester> getSemesterList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Semester";
            Query query = session.createQuery(hql);
            semesterList = query.list();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return semesterList;
    }
    public static Semester getSemesterByID(SemesterPK semID) {
        Semester sem = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            sem = session.get(Semester.class, semID);
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return sem;
    }
    public static boolean removeSemesterByID(SemesterPK semID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Semester sem = getSemesterByID(semID);
        if(sem == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(sem);
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

    public static boolean addSemester(Semester sem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (getSemesterByID(sem.getSemesterId()) != null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(sem);
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
    public static boolean updateSemester(Semester sem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (getSemesterByID(sem.getSemesterId()) == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(sem);
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
