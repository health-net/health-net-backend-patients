package org.healthnet.backend.patients;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.healthnet.backend.patients.application.services.PatientRegistrationService;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;
import org.healthnet.backend.patients.infrastructure.persistence.PatientPersistenceRepository;
import org.healthnet.backend.patients.presentation.rest.PatientRegistrationWebHandler;
import org.healthnet.backend.patients.presentation.rest.WebHandler;
import org.healthnet.backend.patients.presentation.tools.jetty.JettyEmbeddedServer;
import org.healthnet.backend.patients.presentation.tools.jetty.PatientsServlet;

import javax.servlet.http.HttpServlet;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName("healthnet_patients");
        dataSource.setUser(System.getenv().getOrDefault("HEALTHNET_DB_USER", "root"));
        dataSource.setPassword(System.getenv().getOrDefault("HEALTHNET_DB_PASSWORD", "root"));
        PatientRepository patientRepository = new PatientPersistenceRepository(dataSource);
        Consumer<PatientRegistrationService.RegistrationData> patientRegistrationService = new PatientRegistrationService(patientRepository, input -> new Patient(
                new Patient.Id(input.id),
                new Patient.FullName(input.fullName)
        ));
        WebHandler patientRegistrationWebHandler = new PatientRegistrationWebHandler(patientRegistrationService);
        HttpServlet patientsServlet = new PatientsServlet(patientRegistrationWebHandler);

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
