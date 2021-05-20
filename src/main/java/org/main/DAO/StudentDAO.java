package org.main.DAO;

import org.main.POJO.Sinhvien;
import org.main.UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class StudentDAO {
    public static List<Sinhvien> getStudentList() {
        List<Sinhvien> ds = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Sinhvien";
            Query query = session.createQuery(hql);
            ds = query.list();
            for (Sinhvien sv : ds)
            {
                System.out.println(sv.getTen());
            }
        } catch (HibernateException ex) {
        //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return ds;
    }
}
