package DAO;

import POJO.Account;
import POJO.Teacher;
import UTIL.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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

    public static Account getAccountByID(String accountID) {
        Account acc = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            acc = (Account) session.get(Account.class, accountID);
        } catch (HibernateException ex) {
            //Log the exception
            System.err.println(ex);
        } finally {
            session.close();
        }
        return acc;
    }
    public static boolean removeAccountByID(String accountID) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Account acc = getAccountByID(accountID);
        if(acc == null){
            return false;
        }
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(acc);
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
