package org.healthnet.backend.patients.application.services;

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
    private final static PatientRegisterService.PatientSummaryData patientSummaryData = mock(PatientRegisterService.PatientSummaryData.class);
    private final static Function<Patient, PatientRegisterService.PatientSummaryData> mappingPatientSummaryDataFromPatient = mock(Function.class);
    private final static PatientRepository patientRepository = mock(PatientRepository.class);
    private final static PatientRegisterService patientRegisterService = new PatientRegisterService(patientRepository, mappingPatientSummaryDataFromPatient);

    @Test
    void Get_SuccessfulExecution_PatientSummariesHasBeenReturned() {
        when(mappingPatientSummaryDataFromPatient.apply(patient)).thenReturn(patientSummaryData);
        when(patientRepository.getAll()).thenReturn(Arrays.asList(patient));
        List<PatientRegisterService.PatientSummaryData> summaries = patientRegisterService.get();
        assertEquals(Arrays.asList(patientSummaryData), summaries);
    }
}
