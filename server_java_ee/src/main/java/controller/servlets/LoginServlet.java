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

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
    }

    public LoginServlet(){
        userService = new UserServiceImpl();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String json = JsonConverter.getJsonFromRequest(req, resp);
            User user = new ObjectMapper().readValue(json, User.class);
            user = userService.getUser(user.getEmail(), user.getPassword());
            if (user == null) {
                resp.sendError(403, "You are not registered");
            } else {
                String jwt = JWTConverter.createJWT(user.getEmail(), user.getRole().toString(),
                        TimeUnit.MINUTES.toMillis(30));
                Token token = new Token(jwt);
                JsonConverter.makeResponse(token, resp);
            }
        } catch (ServerException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }
}
