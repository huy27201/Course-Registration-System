package Main;

import DAO.AccountDAO;
import DAO.StudentDAO;
import POJO.Account;
import POJO.Student;

import java.util.List;

public class HibernateMain {
    public static void connectHibernate(){
        List<Account> accountList = AccountDAO.getAccountList();
    }
}
