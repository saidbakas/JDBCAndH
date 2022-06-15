package peaksoft.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import peaksoft.model.User;
import peaksoft.util.Util;


import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        try {
            Session session =Util.getSessionFactory().openSession();
            session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXIST users ("+
                "id SERIAL PRIMARY KEY,"+
                "name VARCHAR(50) NOT NULL,"+
                "last_name VARCHAR(50) NOT NULL,"+
                "age INT2 NOT NULL);";

            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
            session.close();
    }catch (HibernateException e){
            System.out.println(e.getMessage());;
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE users").executeUpdate();
            session.getTransaction().commit();
            session.close();
        }catch (Exception e ){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            User user = new User(name,lastName,age);
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("Succesfully");
            session.close();
        }catch (Exception e ){
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try {
            Session session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            User us = (User) session.get(User.class,id);
            session.delete(us);
            session.getTransaction().commit();
            session.close();
            System.out.println("User with id "+ id+" was delete" + us);
        }catch (HibernateException e ){
            if (transaction==null)
                transaction.rollback();
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        Transaction transaction= null;
        try {
            Session session = Util.getSessionFactory().openSession();
            transaction=session.beginTransaction();
            users = session.createQuery("FROM  User"). list();
            transaction.commit();
        }catch (HibernateException e){
            if (transaction==null)
                transaction.rollback();
            System.out.println(e.getMessage());
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
    try {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE users").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }catch (Exception e ){
        e.printStackTrace();
    }
    }
}
