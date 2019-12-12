package org.healthnet.backend.patients.presentation.rest;

import org.healthnet.backend.patients.application.services.PatientRegistrationService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Function;

public class PatientsServlet extends HttpServlet {
    private final PatientRegistrationService patientRegistrationService;
    private final Function<HttpServletRequest, PatientRegistrationService.RegistrationData> registrationDataMapping;

    public PatientsServlet(PatientRegistrationService patientRegistrationService,
                           Function<HttpServletRequest, PatientRegistrationService.RegistrationData> registrationDataMapping) {
        this.patientRegistrationService = patientRegistrationService;
        this.registrationDataMapping = registrationDataMapping;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            PatientRegistrationService.RegistrationData registrationData = registrationDataMapping.apply(req);
            patientRegistrationService.accept(registrationData);
            resp.setStatus(201);
        } catch (IllegalArgumentException e) {
            resp.setStatus(400);
        } catch (IllegalStateException e) {
            resp.setStatus(409);
        } catch (RuntimeException e) {
            resp.setStatus(500);
        }
    }
}
