package org.healthnet.backend.patients.infrastructure.persistence;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PatientPersistenceRepositoryTest {
    private final static DataSource dataSource = new TestDataSource();
    private final static PatientRepository patientRepository = new PatientPersistenceRepository(dataSource);

    @BeforeEach
    void setUp() throws Exception {
        var connection = dataSource.getConnection();
        connection.createStatement().execute("TRUNCATE TABLE `patients`");
    }

    @Test
    void Add_SuccessfulExecution_PatientHasBeenStored() throws Exception {
        Patient patient = aPatient();
        patientRepository.add(patient);
        var statement = dataSource.getConnection().prepareStatement("SELECT * FROM `patients` WHERE id=?");
        statement.setString(1, patient.getId().getValue());
        ResultSet result = statement.executeQuery();
        result.next();
        assertEquals(patient.getId().getValue(), result.getString("id"));
    }

    @Test
    void Add_AlreadyStoredPatient_IllegalStateExceptionHasBeenThrown() {
        Patient patient = aPatient();
        patientRepository.add(patient);
        assertThrows(IllegalStateException.class, () -> patientRepository.add(patient));
    }

    private Patient aPatient() {
        return new Patient(
                new Patient.Id("cde63ec7-586b-4f31-94ac-6a3b20f2e39d"),
                new Patient.FullName("John Riley")
        );
    }

    public static class TestDataSource extends MysqlDataSource {
        public TestDataSource() {
            setDatabaseName("healthnet_patients_test");
            Map<String, String> env = System.getenv();
            setUser(env.getOrDefault("HEALTHNET_DB_USER", "root"));
            setPassword(env.getOrDefault("HEALTHNET_DB_PASSWORD", "root"));
        }
    }
}
