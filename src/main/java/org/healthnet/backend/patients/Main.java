package org.healthnet.backend.patients;

import com.google.gson.Gson;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.healthnet.backend.patients.application.dtos.PatientDetailDto;
import org.healthnet.backend.patients.application.dtos.PatientSummaryDto;
import org.healthnet.backend.patients.application.services.PatientDetailService;
import org.healthnet.backend.patients.application.services.PatientRegisterService;
import org.healthnet.backend.patients.application.services.PatientRegistrationService;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;
import org.healthnet.backend.patients.infrastructure.persistence.PatientPersistenceRepository;
import org.healthnet.backend.patients.infrastructure.persistence.tools.PatientsDataSource;
import org.healthnet.backend.patients.presentation.rest.*;
import org.healthnet.backend.patients.presentation.tools.jetty.JettyEmbeddedServer;
import org.healthnet.backend.patients.presentation.tools.jetty.PatientsServlet;

import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = new PatientsDataSource();
        PatientRepository patientRepository = new PatientPersistenceRepository(dataSource);
        var patientRegistrationService = new PatientRegistrationService(
                patientRepository,
                (patientRegistrationDto -> new Patient(
                        new Patient.Id(patientRegistrationDto.id),
                        new Patient.FullName(patientRegistrationDto.fullName)
                ))
        );
        var patientRegisterService = new PatientRegisterService(
                patientRepository,
                patient -> new PatientSummaryDto(
                        patient.getId().getValue(),
                        patient.getFullName().getValue()
                )
        );
        var patientDetailService = new PatientDetailService(
                patientRepository,
                Patient.Id::new,
                (patient) -> new PatientDetailDto(
                        patient.getId().getValue(),
                        patient.getFullName().getValue()
                )
        );
        WebHandler patientRegistrationWebHandler = new PatientRegistrationWebHandler(patientRegistrationService);
        WebHandler patientRegisterWebHandler = new PatientRegisterWebHandler(
                patientRegisterService,
                patientSummaryData -> new Gson().newBuilder().setPrettyPrinting().create().toJson(patientSummaryData)
        );
        WebHandler patientDetailWebHandler = new PatientDetailWebHandler(
                patientDetailService,
                patientDetailDto -> new Gson().newBuilder().setPrettyPrinting().create().toJson(patientDetailDto)
        );

        WebHandler router = new PatientRouter(
                patientRegistrationWebHandler,
                patientRegisterWebHandler,
                patientDetailWebHandler
        );

        HttpServlet patientsServlet = new PatientsServlet(router);

        int port = Integer.parseInt(System.getenv().getOrDefault("SERVICE_PORT", "8080"));
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(patientsServlet), "/patients/*");
        JettyEmbeddedServer server = new JettyEmbeddedServer(port, servletContextHandler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
