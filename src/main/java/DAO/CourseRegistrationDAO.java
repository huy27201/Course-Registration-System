package DAO;


import POJO.CourseRegistration;
import POJO.CourseRegistrationPK;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CourseRegistrationDAO {
    private static List<CourseRegistration> courseRegistrationList;

    public static List<CourseRegistration> getCourseRegistrationList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from CourseRegistration";
            Query query = session.createQuery(hql);
            courseRegistrationList = query.list();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return courseRegistrationList;
    }

    public static CourseRegistration getCourseRegistrationByID(int id, int semesterId, int year) {
        CourseRegistration res = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            CourseRegistrationPK courseRegistrationID = new CourseRegistrationPK(id, semesterId, year);
            res = session.get(CourseRegistration.class, courseRegistrationID);
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return res;
    }
    public static CourseRegistration getCourseRegistrationBySemester(int semesterId, int year) {
        CourseRegistration res = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from CourseRegistration c where c.semesterId=:semesterId and c.year=:year";
            Query query = session.createQuery(hql);
            query.setParameter("semesterId", semesterId);
            query.setParameter("year", year);
            res = (CourseRegistration) query.setMaxResults(1).uniqueResult();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return res;
    }
    public static boolean removeCourseRegistrationByID(int id, int semesterId, int year) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CourseRegistration res = getCourseRegistrationByID(id, semesterId, year);
        if (res == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(res);
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

    public static boolean addCourseRegistration(CourseRegistration temp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (getCourseRegistrationByID(temp.getId(), temp.getSemesterId(), temp.getYear()) != null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(temp);
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
