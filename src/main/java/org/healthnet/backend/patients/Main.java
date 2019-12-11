package org.healthnet.backend.patients;

import org.healthnet.backend.patients.application.services.PatientRegistrationService;
import org.healthnet.backend.patients.domain.patient.FullName;
import org.healthnet.backend.patients.domain.patient.Id;
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
                new Id(input.id),
                new FullName(input.fullName)
        ));
        HttpServlet patientsServlet = new PatientsServlet(patientRegistrationService, new RegistrationDataMapper());
        JettyEmbeddedServer server = new JettyEmbeddedServer(8080, patientsServlet);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
