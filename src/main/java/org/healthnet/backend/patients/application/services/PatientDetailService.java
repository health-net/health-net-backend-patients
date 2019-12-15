package org.healthnet.backend.patients.application.services;

import org.healthnet.backend.patients.application.dtos.PatientDetailDto;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;

import java.util.function.Function;

public class PatientDetailService implements Function<String, PatientDetailDto> {
    private final PatientRepository patientRepository;
    private final Function<String, Patient.Id> patientIdCreation;
    private final Function<Patient, PatientDetailDto> patientDetailDtoCreation;

    public PatientDetailService(PatientRepository patientRepository,
                                Function<String, Patient.Id> patientIdCreation,
                                Function<Patient, PatientDetailDto> patientDetailDtoCreation) {
        this.patientRepository = patientRepository;
        this.patientIdCreation = patientIdCreation;
        this.patientDetailDtoCreation = patientDetailDtoCreation;
    }

    @Override
    public PatientDetailDto apply(String id) {
        Patient.Id patientId = patientIdCreation.apply(id);
        Patient patient = patientRepository.get(patientId);
        return patientDetailDtoCreation.apply(patient);
    }
}
