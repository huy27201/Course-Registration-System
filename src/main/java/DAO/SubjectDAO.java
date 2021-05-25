package DAO;


import POJO.Subject;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
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
}
