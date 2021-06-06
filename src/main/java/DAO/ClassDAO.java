package DAO;

import POJO.Classname;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ClassDAO {
    private static List<Classname> classList;

    public static List<Classname> getClassList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Classname";
            Query query = session.createQuery(hql);
            classList = query.list();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return classList;
    }

    public static Classname getClassByID(String id) {
        Classname cls = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            cls = session.get(Classname.class, id);
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return cls;
    }

    public static boolean removeClassByID(String id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Classname cls = getClassByID(id);
        if (cls == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(cls);
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

    public static boolean addClass(Classname cls) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if ((getClassByID(cls.getId())) != null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(cls);
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
    public static Long getClassMemberCount(String id) {
        Long count = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select count(*) from Student st inner join st.classnameByClassName where st.classnameByClassName.id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            count = (Long)query.uniqueResult();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return count;
    }
    public static Long getClassMaleCount(String id) {
        Long count = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select count(*) from Student st inner join st.classnameByClassName where st.classnameByClassName.id=:id and st.sex='Nam'";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            count = (Long)query.uniqueResult();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return count;
    }
    public static Long getClassFemaleCount(String id) {
        Long count = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select count(*) from Student st inner join st.classnameByClassName where st.classnameByClassName.id=:id and st.sex = 'Nữ'";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            count = (Long)query.uniqueResult();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return count;
    }

    public static boolean updateClass(Classname currentClass) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (getClassByID(currentClass.getId()) == null) {
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(currentClass);
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
