package DAO;

import POJO.Course;
import POJO.CoursePK;
import POJO.Currentsemester;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CourseDAO {
    private static List<Course> courseList;

    public static List<Course> getCourseListBySemester(Currentsemester sem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Course c where c.semesterId=:semId and c.year=:year";
            Query query = session.createQuery(hql);
            query.setParameter("semId", sem.getId());
            query.setParameter("year", sem.getYear());
            courseList = query.list();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return courseList;
    }

    public static Course getCourseByID(CoursePK id) {
        Course res = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            res = session.get(Course.class, id);
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return res;
    }

    public static boolean removeCourseByID(CoursePK id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Course res = getCourseByID(id);
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

    public static boolean addCourse(Course temp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
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
