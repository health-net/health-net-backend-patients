package org.healthnet.backend.patients.application.services;

import org.healthnet.backend.patients.application.shared.Creator;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;

import java.util.function.Consumer;

public class PatientRegistrationService implements Consumer<PatientRegistrationService.InputDto> {
    private final PatientRepository patientRepository;
    private final Creator<InputDto, Patient> patientCreation;

    public PatientRegistrationService(PatientRepository patientRepository,
                                      Creator<InputDto, Patient> patientCreation) {
        this.patientRepository = patientRepository;
        this.patientCreation = patientCreation;
    }

    @Override
    public void accept(InputDto inputDto) {
        Patient patient = patientCreation.from(inputDto);
        patientRepository.add(patient);
    }

    public static class InputDto {
        public final String id;
        public final String fullName;

        public InputDto(String id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }
    }
}
