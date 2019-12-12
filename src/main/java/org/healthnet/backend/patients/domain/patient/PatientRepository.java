package org.healthnet.backend.patients.domain.patient;

import org.healthnet.backend.patients.domain.shared.Repository;

import java.util.List;

public interface PatientRepository extends Repository<Patient> {
    void add(Patient patient);
    List<Patient> getAll();
    Patient get(Patient.Id patientId);
}
