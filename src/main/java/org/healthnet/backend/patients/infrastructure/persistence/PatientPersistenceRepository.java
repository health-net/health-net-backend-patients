package org.healthnet.backend.patients.infrastructure.persistence;

import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class PatientPersistenceRepository implements PatientRepository {
    private final DataSource dataSource;

    public PatientPersistenceRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(Patient patient) {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.prepareStatement("INSERT INTO `patients` VALUES (?, ?)");
            statement.setString(1, patient.getId().getValue());
            statement.setString(2, patient.getFullName().getValue());
            statement.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new IllegalStateException();
            } else {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public List<Patient> getAll() {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.prepareStatement("SELECT * FROM `patients`;");
            ResultSet resultSet = statement.executeQuery();
            List<Patient> patients = new LinkedList<>();
            while(resultSet.next()) {
                patients.add(getPatientFromResultSet(resultSet));
            }
            return patients;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private Patient getPatientFromResultSet(ResultSet resultSet) throws SQLException {
        return new Patient(
                new Patient.Id(resultSet.getString("id")),
                new Patient.FullName(resultSet.getString("fullName"))
        );
    }
}
