package org.healthnet.backend.patients.presentation.tools.jetty;

import org.healthnet.backend.patients.presentation.rest.WebHandler;
import org.healthnet.backend.patients.presentation.rest.WebRequest;
import org.healthnet.backend.patients.presentation.rest.WebResponse;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PatientsServlet extends HttpServlet {
    private final WebHandler patientRegistrationWebHandler;
    private final WebHandler patientRegisterWebHandler;

    public PatientsServlet(WebHandler patientRegistrationWebHandler, WebHandler patientRegisterWebHandler) {
        this.patientRegistrationWebHandler = patientRegistrationWebHandler;
        this.patientRegisterWebHandler = patientRegisterWebHandler;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebRequest webRequest = new WebRequest(req.getReader());
        WebResponse webResponse = patientRegistrationWebHandler.handle(webRequest);
        resp.setContentType("application/json");
        resp.setStatus(webResponse.getStatusCode());
        resp.getWriter().write(webResponse.getBodyContent());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebRequest webRequest = new WebRequest(req.getReader());
        WebResponse webResponse = patientRegisterWebHandler.handle(webRequest);
        resp.setContentType("application/json");
        resp.setStatus(webResponse.getStatusCode());
        resp.getWriter().write(webResponse.getBodyContent());
    }
}
