package org.healthnet.backend.patients.application.services;

import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PatientRegisterService implements Supplier<List<PatientRegisterService.PatientSummaryData>> {
    private final PatientRepository patientRepository;
    private final Function<Patient, PatientSummaryData> patientSummaryDataFromPatientMapping;

    public PatientRegisterService(PatientRepository patientRepository,
                                  Function<Patient, PatientSummaryData> patientSummaryDataFromPatientMapping) {
        this.patientRepository = patientRepository;
        this.patientSummaryDataFromPatientMapping = patientSummaryDataFromPatientMapping;
    }

    @Override
    public List<PatientSummaryData> get() {
        List<Patient> patients = patientRepository.getAll();
        return patients.stream().map(patientSummaryDataFromPatientMapping).collect(Collectors.toUnmodifiableList());
    }

    public static class PatientSummaryData {
        public final String id;
        public final String fullName;

        public PatientSummaryData(String id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }
    }
}
