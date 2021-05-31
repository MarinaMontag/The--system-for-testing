package controller.servlets.util;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import exception.ServerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import model.Role;
import model.User;
import service.UserService;
import service.UserServiceImpl;
import util.JWTConverter;

@JsonPropertyOrder({"token"})
public class Token {
    private String token;

    public Token() {
    }

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public boolean verify() throws ServerException {
        try{
        Claims claims = JWTConverter.decodeJWT(token);
        String email = claims.getId();
        if(email==null)
            return false;
        String roleString = claims.getSubject();
        if(roleString == null)
            return false;
        if(!roleString.equals(Role.STUDENT.toString())&&
        !roleString.equals(Role.TUTOR.toString()))
            return false;
        int role = roleString.equals(Role.TUTOR.toString()) ? 0 : 1;
        UserService service = new UserServiceImpl();
        User user = service.getUserByEmailAndRole(email, role);
        if (user == null)
            return false;
        return true;
        }catch (SignatureException e){
            throw new ServerException("Unauthorized request");
        }

    }
}
