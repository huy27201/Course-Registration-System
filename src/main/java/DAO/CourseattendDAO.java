package DAO;

import POJO.Course;
import POJO.Courseattend;
import POJO.Currentsemester;
import POJO.Semester;
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
            System.out.println(sem.getYear() + " " + sem.getId());
            System.out.println(studentId);
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
}
