package Main;

import DAO.AccountDAO;
import POJO.Account;

import java.util.List;

public class HibernateMain {
    public static void connectHibernate(){
        List<Account> accountList = AccountDAO.getAccountList();

    }
}
