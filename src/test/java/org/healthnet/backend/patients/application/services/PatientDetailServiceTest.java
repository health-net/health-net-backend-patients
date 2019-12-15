package org.healthnet.backend.patients.application.services;

import org.healthnet.backend.patients.application.dtos.PatientDetailDto;
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
    private final String id = "id";
    private final static PatientDetailDto PATIENT_DETAIL_DTO = mock(PatientDetailDto.class);
    private final static Function<String, Patient.Id> mappingPatientId = mock(Function.class);
    private final static Patient.Id patientId = mock(Patient.Id.class);
    private final static Function<Patient, PatientDetailDto> mappingPatientDetailData = mock(Function.class);
    private final static PatientRepository patientRepository = mock(PatientRepository.class);

    private final static PatientDetailService patientDetailService = new PatientDetailService(
            patientRepository,
            mappingPatientId,
            mappingPatientDetailData
    );

    @BeforeEach
    void resetMocks() {
        reset(patient, PATIENT_DETAIL_DTO, mappingPatientId, patientId, mappingPatientDetailData, patientRepository);
    }

    @Test
    void Apply_SuccessfulExecution_PatientDetailDataHasBeenReturned() {
        when(mappingPatientId.apply(id)).thenReturn(patientId);
        when(patientRepository.get(patientId)).thenReturn(patient);
        when(mappingPatientDetailData.apply(patient)).thenReturn(PATIENT_DETAIL_DTO);
        assertEquals(PATIENT_DETAIL_DTO, patientDetailService.apply(id));
    }

    @Test
    void Apply_PatientNotFound_NoSuchElementExceptionHasBeenThrown() {
        when(mappingPatientId.apply(id)).thenReturn(patientId);
        when(patientRepository.get(patientId)).thenThrow(NoSuchElementException.class);
        verifyNoInteractions(mappingPatientDetailData);
        assertThrows(NoSuchElementException.class, () -> patientRepository.get(patientId));
    }
}
