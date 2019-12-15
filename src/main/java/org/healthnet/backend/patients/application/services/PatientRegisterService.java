package org.healthnet.backend.patients.application.services;

import org.healthnet.backend.patients.application.dtos.PatientSummaryDto;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PatientRegisterService implements Supplier<List<PatientSummaryDto>> {
    private final PatientRepository patientRepository;
    private final Function<Patient, PatientSummaryDto> patientSummaryDtoCreation;

    public PatientRegisterService(PatientRepository patientRepository,
                                  Function<Patient, PatientSummaryDto> patientSummaryDtoCreation) {
        this.patientRepository = patientRepository;
        this.patientSummaryDtoCreation = patientSummaryDtoCreation;
    }

    @Override
    public List<PatientSummaryDto> get() {
        List<Patient> patients = patientRepository.getAll();
        return patients.stream().map(patientSummaryDtoCreation).collect(Collectors.toUnmodifiableList());
    }

}
