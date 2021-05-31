package dao;

import exception.ServerException;
import model.User;
import util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO {
    private static final String addUserQuery =
            "INSERT INTO users VALUES (default, ?, ?, ?, ?, ?)";
    private static final String selectUserQuery =
            "SELECT * FROM users WHERE uemail = ? AND upassword = ?";
    private static final String selectUserByEmailAndRoleQuery =
            "SELECT * FROM users WHERE uemail = ? AND urole = ?";


    public static User addUser(User user) throws ServerException, ClassNotFoundException {
        if(user==null)
            return null;
        try(Connection conn = JdbcConnection.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(addUserQuery);
            preparedStatement.setString(1,user.getFirstName());
            preparedStatement.setString(2,user.getLastName());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.setString(4,user.getPassword());
            preparedStatement.setInt(5,user.getRole().ordinal());
            int rows = preparedStatement.executeUpdate();
            if(rows <= 0) {
                return null;
            }
        }catch (SQLException e){
            throw new ServerException("can not add new user");
        }
        return user;
    }

    public static User selectUser(String email, String password) throws ClassNotFoundException, ServerException {
        if(email==null || password==null)
            return null;
        User user = null;
        try(Connection conn = JdbcConnection.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(selectUserQuery);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user= new User(resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6));
            }
        }catch (SQLException e){
            throw new ServerException("can not select user");
        }
        return user;
    }
    public static User selectUserByEmailAndRole(String email, int role) throws ServerException {
        User user = null;
        try(Connection conn = JdbcConnection.getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement(selectUserByEmailAndRoleQuery);
            preparedStatement.setString(1,email);
            preparedStatement.setInt(2,role);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user= new User(resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6));
            }
        }catch (SQLException | ClassNotFoundException e){
            throw new ServerException("can not select user");
        }
        return user;
    }
}
