package controller.servlets;

import exception.ServerException;
import model.SubjectList;
import service.SubjectServiceImpl;
import util.JsonConverter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/subjects"})
public class SubjectsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SubjectList list;
        try {
            list = new SubjectServiceImpl().getSubjects();
            if (list.getSubjectList() == null || list.getSubjectList().size() == 0)
                resp.sendError(404, "Resource not found");
            else
                JsonConverter.makeResponse(list, resp);
        } catch (ServerException e) {
            resp.sendError(404, "Resource not found");
        }
    }
}
