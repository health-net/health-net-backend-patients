package org.healthnet.backend.patients.presentation.tools.jetty;

import org.healthnet.backend.patients.presentation.rest.WebHandler;
import org.healthnet.backend.patients.presentation.rest.WebRequest;
import org.healthnet.backend.patients.presentation.rest.WebResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PatientsServlet extends HttpServlet {
    private final WebHandler router;

    public PatientsServlet(WebHandler router) {
        this.router = router;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebRequest webRequest = new WebRequest(req.getMethod(), req.getPathInfo(), req.getReader());
        WebResponse webResponse = router.handle(webRequest);
        resp.setContentType("application/json");
        resp.setStatus(webResponse.getStatusCode());
        resp.getWriter().write(webResponse.getBodyContent());
    }
}
