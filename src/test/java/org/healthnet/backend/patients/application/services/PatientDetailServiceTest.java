package org.healthnet.backend.patients.application.services;

import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientDetailServiceTest {
    private final static Patient patient = mock(Patient.class);
    private final static PatientDetailService.PatientIdRequest patientIdRequest = mock(PatientDetailService.PatientIdRequest.class);
    private final static PatientDetailService.PatientDetailData patientDetailData = mock(PatientDetailService.PatientDetailData.class);
    private final static Function<PatientDetailService.PatientIdRequest, Patient.Id> mappingPatientId = mock(Function.class);
    private final static Patient.Id patientId = mock(Patient.Id.class);
    private final static Function<Patient, PatientDetailService.PatientDetailData> mappingPatientDetailData = mock(Function.class);
    private final static PatientRepository patientRepository = mock(PatientRepository.class);

    private final static PatientDetailService patientDetailService = new PatientDetailService(
            patientRepository,
            mappingPatientId,
            mappingPatientDetailData
    );

    @BeforeEach
    void resetMocks() {
        reset(patient, patientIdRequest, patientDetailData, mappingPatientId, patientId, mappingPatientDetailData, patientRepository);
    }

    @Test
    void Apply_SuccessfulExecution_PatientDetailDataHasBeenReturned() {
        when(mappingPatientId.apply(patientIdRequest)).thenReturn(patientId);
        when(patientRepository.get(patientId)).thenReturn(patient);
        when(mappingPatientDetailData.apply(patient)).thenReturn(patientDetailData);
        assertEquals(patientDetailData, patientDetailService.apply(patientIdRequest));
    }

    @Test
    void Apply_PatientNotFound_NoSuchElementExceptionHasBeenThrown() {
        when(mappingPatientId.apply(patientIdRequest)).thenReturn(patientId);
        when(patientRepository.get(patientId)).thenThrow(NoSuchElementException.class);
        verifyNoInteractions(mappingPatientDetailData);
        assertThrows(NoSuchElementException.class, () -> patientRepository.get(patientId));
    }
}
