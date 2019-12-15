package org.healthnet.backend.patients;

import com.google.gson.Gson;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.healthnet.backend.patients.application.dtos.PatientDetailDto;
import org.healthnet.backend.patients.application.dtos.PatientSummaryDto;
import org.healthnet.backend.patients.application.dtos.PatientRegistrationDto;
import org.healthnet.backend.patients.application.services.PatientDetailService;
import org.healthnet.backend.patients.application.services.PatientRegisterService;
import org.healthnet.backend.patients.application.services.PatientRegistrationService;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;
import org.healthnet.backend.patients.infrastructure.persistence.PatientPersistenceRepository;
import org.healthnet.backend.patients.presentation.rest.*;
import org.healthnet.backend.patients.presentation.tools.jetty.JettyEmbeddedServer;
import org.healthnet.backend.patients.presentation.tools.jetty.PatientsServlet;

import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName("healthnet_patients");
        dataSource.setUser(System.getenv().getOrDefault("HEALTHNET_DB_USER", "root"));
        dataSource.setPassword(System.getenv().getOrDefault("HEALTHNET_DB_PASSWORD", "root"));
        PatientRepository patientRepository = new PatientPersistenceRepository(dataSource);
        Consumer<PatientRegistrationDto> patientRegistrationService = new PatientRegistrationService(patientRepository, input -> new Patient(
                new Patient.Id(input.id),
                new Patient.FullName(input.fullName)
        ));
        Supplier<List<PatientSummaryDto>> patientRegisterService = new PatientRegisterService(patientRepository, patient -> new PatientSummaryDto(
                patient.getId().getValue(),
                patient.getFullName().getValue()
        ));
        Function<String, PatientDetailDto> patientDetailService = new PatientDetailService(
                patientRepository,
                (s) -> new Patient.Id(s),
                (patient) -> new PatientDetailDto(
                        patient.getId().getValue(),
                        patient.getFullName().getValue()
                )
        );
        WebHandler patientRegistrationWebHandler = new PatientRegistrationWebHandler(patientRegistrationService);
        WebHandler patientRegisterWebHandler = new PatientRegisterWebHandler(patientRegisterService, patientSummaryData -> new Gson().newBuilder().setPrettyPrinting().create().toJson(patientSummaryData));
        WebHandler patientDetailWebHandler = new PatientDetailWebHandler(patientDetailService, patientDetailDto -> new Gson().newBuilder().setPrettyPrinting().create().toJson(patientDetailDto));

        WebHandler router = new PatientRouter(patientRegistrationWebHandler, patientRegisterWebHandler, patientDetailWebHandler);

        HttpServlet patientsServlet = new PatientsServlet(router);

        int port = Integer.parseInt(System.getenv().getOrDefault("HEALTHNET_PORT", "8080"));
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(patientsServlet), "/*");
        JettyEmbeddedServer server = new JettyEmbeddedServer(port, servletContextHandler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
