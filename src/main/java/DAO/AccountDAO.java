package DAO;

import POJO.Account;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AccountDAO {
    private static List<Account> accountList;

    public static List<Account> getAccountList() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from Account";
            Query query = session.createQuery(hql);
            accountList = query.list();
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return accountList;
    }
}
