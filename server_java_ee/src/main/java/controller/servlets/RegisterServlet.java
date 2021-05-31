package controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.servlets.util.Token;
import exception.ServerException;
import model.User;
import service.UserService;
import service.UserServiceImpl;
import util.JWTConverter;
import util.JsonConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String json = JsonConverter.getJsonFromRequest(req,resp);
        User user = new ObjectMapper().readValue(json, User.class);
        try {
            user = userService.createUser(user);
            if(user == null){
                resp.sendError(400, "Bad request");
            }else{
                String jwt = JWTConverter.createJWT(user.getEmail(), user.getRole().toString(),
                        TimeUnit.MINUTES.toMillis(30));
                Token token = new Token(jwt);
                JsonConverter.makeResponse(token, resp);
            }
        } catch (ServerException | ClassNotFoundException e) {
            resp.sendError(400, "Bad request");
            e.printStackTrace();
        }
    }
}
