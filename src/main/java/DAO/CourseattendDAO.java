package DAO;

import POJO.*;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.io.Serializable;
import java.util.List;

public class CourseattendDAO implements Serializable {
    private static List<Courseattend> courseattendList;

    public static List<Courseattend> getCourseattendList(String studentId , Semester sem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Courseattend c where c.courseByCourseId.semesterId=:semesterId and c.courseByCourseId.year=:year  and c.studentId=:studentId";
            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("year", sem.getYear());
            query.setParameter("semesterId", sem.getId());
            courseattendList = query.list();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return courseattendList;
    }
    public static int getCourseattendListCount(String studentId, Currentsemester sem) {
        int count = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select count(*) from Courseattend c where c.courseByCourseId.semesterId=:semesterId and c.courseByCourseId.year=:year  and c.studentId=:studentId";
            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            query.setParameter("year", sem.getYear());
            query.setParameter("semesterId", sem.getId());
            count = ((Long) query.uniqueResult()).intValue();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return count;
    }
    public static int getCourseattendListCountByStudent(String studentId) {
        int count = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select count(*) from Courseattend c where c.studentId=:studentId";
            Query query = session.createQuery(hql);
            query.setParameter("studentId", studentId);
            count = ((Long) query.uniqueResult()).intValue();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return count;
    }
    public static Courseattend getCourseattendByID(String studentId, int courseId) {
        Courseattend res = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            CourseattendPK temp = new CourseattendPK(studentId, courseId);
            res = session.get(Courseattend.class, temp);
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return res;
    }
    public static List<Courseattend> getCourseattendByCourseID(int courseId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Courseattend c where c.courseId=:courseId";
            Query query = session.createQuery(hql);
            query.setParameter("courseId", courseId);
            courseattendList = query.list();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return courseattendList;
    }
    public static boolean removeCourseattendByID(String studentId, int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Courseattend res = getCourseattendByID(studentId, id);
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

    public static boolean addCourseattend(Courseattend courseattend) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Courseattend res = getCourseattendByID(courseattend.getStudentId(), courseattend.getCourseId());
        if (res != null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(courseattend);
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
