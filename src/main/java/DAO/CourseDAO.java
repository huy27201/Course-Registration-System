package DAO;

import POJO.Course;
import POJO.Currentsemester;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.io.Serializable;
import java.util.List;

public class CourseDAO implements Serializable {
    private static List<Course> courseList;

    public static List<Course> getCourseListBySemester(int id, int year) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Course c where c.semesterId=:semId and c.year=:year";
            Query query = session.createQuery(hql);
            query.setParameter("semId", id);
            query.setParameter("year", year);
            courseList = query.list();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return courseList;
    }

    public static Course getCourseByID(int id) {
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
    public static int getCourseStudentCount(int id) {
        int count = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select  count (*) from Courseattend ca where ca.courseId=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            count = ((Long)query.uniqueResult()).intValue();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return count;
    }
    public static boolean removeCourseByID(int id) {
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
    public static boolean updateCourse(Course temp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(temp);
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
    public static Course getUniqueCourseBySubject(String subjectId) {
        Course res = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Course c where c.subjectBySubjectId.id=:subjectId";
            Query query = session.createQuery(hql);
            query.setParameter("subjectId", subjectId);
            res = (Course) query.setMaxResults(1).uniqueResult();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return res;
    }
}
