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

    public PatientsServlet(WebHandler patientRegistrationWebHandler) {
        this.patientRegistrationWebHandler = patientRegistrationWebHandler;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        WebRequest webRequest = new WebRequest(req.getReader());
        WebResponse webResponse = patientRegistrationWebHandler.handle(webRequest);
        resp.setStatus(webResponse.getStatusCode());
    }
}
