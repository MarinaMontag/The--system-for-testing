package controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.servlets.util.Token;
import exception.ServerException;
import model.CreatedTest;
import model.QuestionList;
import model.Test;
import model.User;
import service.TestService;
import service.TestServiceImpl;
import util.JsonConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static controller.servlets.util.ResponseService.sendResponseMessage;

@WebServlet(urlPatterns = {"/questions"})
public class TestInfoAndQuestionsServlet extends HttpServlet {
    private TestService testService;

    @Override
    public void init() throws ServletException {
        testService = new TestServiceImpl();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
                checkToken(req);
                int testId = Integer.parseInt(req.getParameter("id"));
                QuestionList questionList = testService.getQuestionAndAnswersByTestId(testId);
                Test testInfo = testService.getTestById(testId);
                CreatedTest test = new CreatedTest(testInfo, questionList.getQuestionList());
                if (testInfo==null||questionList.getQuestionList() == null ||
                        questionList.getQuestionList().size() == 0)
                    resp.sendError(404, "Resource not found");
                else
                    JsonConverter.makeResponse(test, resp);
        } catch (ServerException e) {
            if(!e.getMessage().equals("Unauthorized request"))
                resp.sendError(404, e.getMessage());
            else
                resp.sendError(401, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        try {
            checkToken(req);
            String json = JsonConverter.getJsonFromRequest(req, resp);
            CreatedTest test = new ObjectMapper().readValue(json, CreatedTest.class);
            testService.createTest(test);
            if (test == null) {
                resp.sendError(403, "Something wrong with your test");
            } else {
                sendResponseMessage("Test has been successfully created", resp);
            }
        } catch (ServerException e) {
            resp.sendError(401, e.getMessage());
        }
    }

    private void checkToken(HttpServletRequest req) throws ServerException {
        String jwt;
        if(req.getHeader("Authorization")!=null&&
                req.getHeader("Authorization").split(" ").length==2)
            jwt = req.getHeader("Authorization").split(" ")[1];
        else throw new ServerException("Unauthorized request");
        if(jwt==null)
            throw new ServerException("Unauthorized request");
        else{
            Token token = new Token(jwt);
            if(!token.verify())
                throw new ServerException("Unauthorized request");
        }

    }
}
