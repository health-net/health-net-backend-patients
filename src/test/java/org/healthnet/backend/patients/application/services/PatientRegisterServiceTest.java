package org.healthnet.backend.patients.application.services;

import org.healthnet.backend.patients.application.dtos.PatientSummaryDto;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class PatientRegisterServiceTest {
    private final static Patient patient = mock(Patient.class);
    private final static PatientSummaryDto PATIENT_SUMMARY_DTO = mock(PatientSummaryDto.class);
    private final static Function<Patient, PatientSummaryDto> mappingPatientSummaryDataFromPatient = mock(Function.class);
    private final static PatientRepository patientRepository = mock(PatientRepository.class);
    private final static PatientRegisterService patientRegisterService = new PatientRegisterService(patientRepository, mappingPatientSummaryDataFromPatient);

    @Test
    void Get_SuccessfulExecution_PatientSummariesHasBeenReturned() {
        when(mappingPatientSummaryDataFromPatient.apply(patient)).thenReturn(PATIENT_SUMMARY_DTO);
        when(patientRepository.getAll()).thenReturn(Arrays.asList(patient));
        List<PatientSummaryDto> summaries = patientRegisterService.get();
        assertEquals(Arrays.asList(PATIENT_SUMMARY_DTO), summaries);
    }
}
