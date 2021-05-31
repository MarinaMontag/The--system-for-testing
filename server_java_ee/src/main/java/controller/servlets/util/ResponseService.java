package controller.servlets.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseService {
    public static void sendResponseMessage(String message, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        try {
            writer.write("Test has been successfully created");
        }finally {
            writer.close();
        }
    }
}
