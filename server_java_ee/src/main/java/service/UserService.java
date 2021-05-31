package service;

import exception.ServerException;
import model.User;

public interface UserService {
   User createUser(User user) throws ServerException, ClassNotFoundException;
   User getUser(String email, String password) throws ServerException, ClassNotFoundException;
   User getUserByEmailAndRole(String email, int role) throws ServerException;
}
