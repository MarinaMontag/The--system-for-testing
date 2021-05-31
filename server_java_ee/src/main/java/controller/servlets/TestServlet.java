package controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.ServerException;
import model.SubjectList;
import model.Test;
import model.TestList;
import service.SubjectService;
import service.SubjectServiceImpl;
import service.TestService;
import service.TestServiceImpl;
import util.JsonConverter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/tests"})
public class TestServlet extends HttpServlet {
    private TestService testService;
    private SubjectService subjectService;

    @Override
    public void init() throws ServletException {
        testService = new TestServiceImpl();
        subjectService = new SubjectServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            int max_id=subjectService.getMaxId();
            int min_id=subjectService.getMinId();
            if (id >= min_id && id <= max_id) {
                TestList list;
                list = testService.getTestsBySubjectId(id);
                if (list.getTestList() == null || list.getTestList().size() == 0)
                    resp.sendError(404, "Resource not found");
                else
                    JsonConverter.makeResponse(list, resp);
            } else {
                resp.sendError(400, "Bad request");
            }
        } catch (ServerException e) {
            resp.sendError(404, e.getMessage());
        }
    }

}
