package org.healthnet.backend.patients;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.healthnet.backend.patients.application.services.PatientRegistrationService;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;
import org.healthnet.backend.patients.infrastructure.persistence.PatientPersistenceRepository;
import org.healthnet.backend.patients.infrastructure.persistence.PatientsDataSource;
import org.healthnet.backend.patients.presentation.rest.JettyEmbeddedServer;
import org.healthnet.backend.patients.presentation.rest.PatientsServlet;
import org.healthnet.backend.patients.presentation.rest.RegistrationDataMapper;

import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = new PatientsDataSource();
        PatientRepository patientRepository = new PatientPersistenceRepository(dataSource);
        PatientRegistrationService patientRegistrationService = new PatientRegistrationService(patientRepository, input -> new Patient(
                new Patient.Id(input.id),
                new Patient.FullName(input.fullName)
        ));
        HttpServlet patientsServlet = new PatientsServlet(patientRegistrationService, new RegistrationDataMapper());
        int port = Integer.parseInt(System.getenv().getOrDefault("HEALTHNET_PORT", "8080"));
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(patientsServlet), "/patients");
        JettyEmbeddedServer server = new JettyEmbeddedServer(port, servletContextHandler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
