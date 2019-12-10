package org.healthnet.backend.patients.domain.patient;

import org.healthnet.backend.patients.domain.shared.Repository;

public interface PatientRepository extends Repository<Patient> {
    void add(Patient patient);
}
