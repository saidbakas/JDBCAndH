package peaksoft;

import peaksoft.dao.UserDao;
import peaksoft.dao.UserDaoHibernateImpl;

public class Main {
    public static void main(String[] args) {

        UserDao userDao = new UserDaoHibernateImpl();
        userDao.createUsersTable();
        userDao.saveUser("Saidibakas","Said", (byte) 21);
        userDao.saveUser("Sadyk","Ali",(byte) 26);
//        userDao.removeUserById(1);
//        System.out.println(userDao.getAllUsers());
//        userDao.dropUsersTable();
//        System.out.println(userDao.getAllUsers());
        userDao.cleanUsersTable();
    }
}
