package org.healthnet.backend.patients.application.services;

import org.healthnet.backend.patients.application.dtos.PatientRegistrationDto;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;

import java.util.function.Consumer;
import java.util.function.Function;

public class PatientRegistrationService implements Consumer<PatientRegistrationDto> {
    private final PatientRepository patientRepository;
    private final Function<PatientRegistrationDto, Patient> patientCreation;

    public PatientRegistrationService(PatientRepository patientRepository,
                                      Function<PatientRegistrationDto, Patient> patientCreation) {
        this.patientRepository = patientRepository;
        this.patientCreation = patientCreation;
    }

    @Override
    public void accept(PatientRegistrationDto patientRegistrationDto) {
        Patient patient = patientCreation.apply(patientRegistrationDto);
        patientRepository.add(patient);
    }
}
