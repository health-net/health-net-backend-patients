package org.healthnet.backend.patients.infrastructure.persistence;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        Patient patient = aRandomPatient();
        patientRepository.add(patient);
        var statement = dataSource.getConnection().prepareStatement("SELECT * FROM `patients` WHERE id=?");
        statement.setString(1, patient.getId().getValue());
        ResultSet result = statement.executeQuery();
        result.next();
        assertEquals(patient.getId().getValue(), result.getString("id"));
    }

    @Test
    void Add_AlreadyStoredPatient_IllegalStateExceptionHasBeenThrown() {
        Patient patient = aRandomPatient();
        patientRepository.add(patient);
        assertThrows(IllegalStateException.class, () -> patientRepository.add(patient));
    }

    @Test
    void GetAll_SuccessfulExecution_PatientListHasBeenReturned() throws Exception {
        Patient p1 = aRandomPatient();
        Patient p2 = aRandomPatient();
        insert(p1);
        insert(p2);
        List<Patient> patients = patientRepository.getAll();
        assertTrue(patients.contains(p1));
        assertTrue(patients.contains(p2));
    }

    private void insert(Patient patient) throws Exception {
        var connection = dataSource.getConnection();
        var statement = connection.prepareStatement("INSERT INTO `patients` VALUES (?, ?)");
        statement.setString(1, patient.getId().getValue());
        statement.setString(2, patient.getFullName().getValue());
        statement.execute();
    }

    private Patient aRandomPatient() {
        return new Patient(
                new Patient.Id(UUID.randomUUID().toString()),
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
