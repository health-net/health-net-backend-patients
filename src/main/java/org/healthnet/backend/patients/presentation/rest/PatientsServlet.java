package org.healthnet.backend.patients.presentation.rest;

import org.healthnet.backend.patients.application.services.PatientRegistrationService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PatientsServlet extends HttpServlet {
    private final PatientRegistrationService patientRegistrationService;
    private final Mapper<HttpServletRequest, PatientRegistrationService.RegistrationData> mapper;

    public PatientsServlet(PatientRegistrationService patientRegistrationService,
                           Mapper<HttpServletRequest, PatientRegistrationService.RegistrationData> assembler) {
        this.patientRegistrationService = patientRegistrationService;
        this.mapper = assembler;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            PatientRegistrationService.RegistrationData registrationData = mapper.map(req);
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
