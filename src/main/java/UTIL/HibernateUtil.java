package UTIL;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration cfg = new Configuration().configure();
            sessionFactory = cfg.buildSessionFactory();
        } catch (Throwable th) {
            System.err.println("Enitial SessionFactory creation failed: " + th);
            throw new ExceptionInInitializerError(th);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
