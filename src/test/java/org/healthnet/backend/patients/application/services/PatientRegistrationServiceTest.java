package org.healthnet.backend.patients.application.services;

import org.healthnet.backend.patients.application.dtos.PatientRegistrationDto;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PatientRegistrationServiceTest {
    private final static Patient patient = mock(Patient.class);
    private final static PatientRegistrationDto PATIENT_REGISTRATION_DTO = mock(PatientRegistrationDto.class);
    private final static PatientRepository patientRepository = mock(PatientRepository.class);
    private final static Function<PatientRegistrationDto, Patient> patientMapping = mock(Function.class);
    private final static PatientRegistrationService patientRegistrationService = new PatientRegistrationService(
            patientRepository,
            patientMapping
    );

    @BeforeEach
    void resetMocks() {
        reset(patient, patientRepository, patientMapping);
    }

    @Test
    void Accept_SuccessfulExecution_PatientHasBeenRegistered() {
        when(patientMapping.apply(PATIENT_REGISTRATION_DTO)).thenReturn(patient);
        patientRegistrationService.accept(PATIENT_REGISTRATION_DTO);

        verify(patientRepository).add(patient);
    }

    @Test
    void Accept_PatientIsAlreadyRegistered_IllegalStateExceptionHasBeenThrown() {
        when(patientMapping.apply(PATIENT_REGISTRATION_DTO)).thenReturn(patient);
        doThrow(IllegalStateException.class).when(patientRepository).add(patient);

        assertThrows(IllegalStateException.class, () -> patientRegistrationService.accept(PATIENT_REGISTRATION_DTO));
    }

    @Test
    void Accept_InvalidPatientRegistrationData_IllegalArgumentExceptionHasBeenThrown() {
        when(patientMapping.apply(PATIENT_REGISTRATION_DTO)).thenThrow(IllegalArgumentException.class);

        verifyNoInteractions(patientRepository);
        assertThrows(IllegalArgumentException.class, () -> patientRegistrationService.accept(PATIENT_REGISTRATION_DTO));
    }
}
