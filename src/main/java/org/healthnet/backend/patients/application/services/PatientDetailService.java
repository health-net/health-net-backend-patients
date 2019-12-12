package org.healthnet.backend.patients.application.services;

import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;

import java.util.function.Function;

public class PatientDetailService implements Function<PatientDetailService.PatientIdRequest, PatientDetailService.PatientDetailData> {
    private final PatientRepository patientRepository;
    private final Function<PatientIdRequest, Patient.Id> mappingPatientId;
    private final Function<Patient, PatientDetailData> mappingPatientDetailData;

    public PatientDetailService(PatientRepository patientRepository,
                                Function<PatientIdRequest, Patient.Id> mappingPatientId,
                                Function<Patient, PatientDetailData> mappingPatientDetailData) {
        this.patientRepository = patientRepository;
        this.mappingPatientId = mappingPatientId;
        this.mappingPatientDetailData = mappingPatientDetailData;
    }

    @Override
    public PatientDetailData apply(PatientIdRequest patientIdRequest) {
        Patient.Id patientId = mappingPatientId.apply(patientIdRequest);
        Patient patient = patientRepository.get(patientId);
        return mappingPatientDetailData.apply(patient);
    }

    public static class PatientIdRequest {
        public final String id;

        public PatientIdRequest(String id) {
            this.id = id;
        }
    }

    public static class PatientDetailData {
        public final String id;
        public final String fullName;

        public PatientDetailData(String id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }
    }
}
