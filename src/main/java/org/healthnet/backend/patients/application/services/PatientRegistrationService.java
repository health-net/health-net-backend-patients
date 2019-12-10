package org.healthnet.backend.patients.application.services;

import org.healthnet.backend.patients.application.shared.Creator;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;

import java.util.function.Consumer;

public class PatientRegistrationService implements Consumer<PatientRegistrationService.RegistrationData> {
    private final PatientRepository patientRepository;
    private final Creator<RegistrationData, Patient> patientCreation;

    public PatientRegistrationService(PatientRepository patientRepository,
                                      Creator<RegistrationData, Patient> patientCreation) {
        this.patientRepository = patientRepository;
        this.patientCreation = patientCreation;
    }

    @Override
    public void accept(RegistrationData registrationData) {
        Patient patient = patientCreation.from(registrationData);
        patientRepository.add(patient);
    }

    public static class RegistrationData {
        public final String id;
        public final String fullName;

        public RegistrationData(String id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }
    }
}
