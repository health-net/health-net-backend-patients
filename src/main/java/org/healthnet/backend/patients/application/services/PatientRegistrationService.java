package org.healthnet.backend.patients.application.services;

import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;

import java.util.function.Consumer;
import java.util.function.Function;

public class PatientRegistrationService implements Consumer<PatientRegistrationService.RegistrationData> {
    private final PatientRepository patientRepository;
    private final Function<RegistrationData, Patient> patientMapping;

    public PatientRegistrationService(PatientRepository patientRepository,
                                      Function<RegistrationData, Patient> patientMapping) {
        this.patientRepository = patientRepository;
        this.patientMapping = patientMapping;
    }

    @Override
    public void accept(RegistrationData registrationData) {
        Patient patient = patientMapping.apply(registrationData);
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
