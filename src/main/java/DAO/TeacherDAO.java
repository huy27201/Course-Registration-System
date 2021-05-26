package DAO;

import POJO.Teacher;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class TeacherDAO {
    private static List<Teacher> teacherList;
    private static Session session;
    public static List<Teacher> getTeacherList() {
        session = HibernateUtil.getSessionFactory().openSession();
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
    public static List<Teacher> getTeacherListWithAccount() {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Teacher tch, Account acc join tch.accountByAccount.accountId inner join fetch acc.accountId";
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
        session = HibernateUtil.getSessionFactory().openSession();
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
}
