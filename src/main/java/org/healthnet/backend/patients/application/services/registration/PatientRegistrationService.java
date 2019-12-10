package org.healthnet.backend.patients.application.services.registration;

import org.healthnet.backend.patients.application.shared.Creator;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;

import java.util.function.Consumer;

public class PatientRegistrationService implements Consumer<PatientRegistrationDto> {
    private final PatientRepository patientRepository;
    private final Creator<PatientRegistrationDto, Patient> patientCreation;

    public PatientRegistrationService(PatientRepository patientRepository,
                                      Creator<PatientRegistrationDto, Patient> patientCreation) {
        this.patientRepository = patientRepository;
        this.patientCreation = patientCreation;
    }

    @Override
    public void accept(PatientRegistrationDto patientRegistrationDto) {
        Patient patient = patientCreation.from(patientRegistrationDto);
        patientRepository.add(patient);
    }
}
