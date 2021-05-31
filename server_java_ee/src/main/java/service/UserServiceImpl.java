package service;

import dao.UserDAO;
import exception.ServerException;
import model.User;

public class UserServiceImpl implements UserService{
    @Override
    public User createUser(User user) throws ServerException, ClassNotFoundException {
        return UserDAO.addUser(user);
    }

    @Override
    public User getUser(String email, String password) throws ServerException, ClassNotFoundException {
        return UserDAO.selectUser(email, password);
    }

    @Override
    public User getUserByEmailAndRole(String email, int role) throws ServerException{
        return UserDAO.selectUserByEmailAndRole(email, role);
    }
}
