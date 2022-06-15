package peaksoft.dao;


import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {

    public UserDaoJdbcImpl() {

    }

    public void createUsersTable() {
        String  sql  =  "CREATE TABLE IF NOT EXISTS users("+
                "id SERIAL PRIMARY KEY,"+
                "name VARCHAR(50) NOT NULL,"+
                "last_name VARCHAR(50) NOT NULL,"+
                "age INT2 NOT NULL);";
        try (Connection connection = Util.connec();
             Statement statement= connection.createStatement()) {
            statement.executeUpdate(sql);
        }catch (SQLException e ){
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Connection connection = Util.connec();
        Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }catch (SQLException e ){
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users(name,last_name,age) VALUES(?,?,?)";
        try(Connection connection =Util.connec();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,name);
            statement.setString(2,lastName);
            statement.setByte(3,age);
            statement.executeUpdate();
        }catch (SQLException e ){
            e.printStackTrace();
        }

    }


    public void removeUserById(long id) {
        String sql = " DELETE FROM users WHERE id =?";
        try (Connection connection =Util.connec();
        PreparedStatement statement=connection.prepareStatement(sql)){
            statement.setLong(1,id);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users= new ArrayList<>();
    try (Connection connection =Util.connec();
       Statement statement=connection.createStatement();
         ResultSet resultSet=statement.executeQuery(sql)) {
       while (resultSet.next()){
           User user = new User();
           user.setId(resultSet.getLong("id"));
           user.setName(resultSet.getString("name"));
           user.setLastName(resultSet.getString("last_name"));
           user.setAge(resultSet.getByte("age"));
           users.add(user);
       }
    }catch (SQLException e){
        e.printStackTrace();
    }

        return users;
    }

    public void cleanUsersTable() {
   String sql ="TRUNCATE users" ;
   try (Connection connection = Util.connec();
   PreparedStatement statement= connection.prepareStatement(sql)){
       statement.executeUpdate();
   }catch (SQLException e ){
       e.printStackTrace();
   }
    }
}