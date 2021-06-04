package DAO;

import POJO.Currentsemester;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class CurrentSemesterDAO {
    private static Currentsemester currentSemester;

    public static Currentsemester getCurrentSemester() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Currentsemester";
            Query query = session.createQuery(hql);
            currentSemester = (Currentsemester) query.getSingleResult();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return currentSemester;
    }

    public static boolean addCurrrentSemester(Currentsemester sem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
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

    public static boolean removeCurrrentSemester() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Currentsemester sem = getCurrentSemester();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.remove(sem);
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
