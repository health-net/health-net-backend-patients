package org.healthnet.backend.patients.application.services;

import org.healthnet.backend.patients.application.services.PatientRegistrationService;
import org.healthnet.backend.patients.application.shared.Creator;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PatientRegistrationServiceTest {
    private final static Patient patient = mock(Patient.class);
    private final static PatientRegistrationService.InputDto inputDto = mock(PatientRegistrationService.InputDto.class);
    private final static PatientRepository patientRepository = mock(PatientRepository.class);
    private final static Creator<PatientRegistrationService.InputDto, Patient> patientCreation = mock(Creator.class);
    private final static PatientRegistrationService patientRegistrationService = new PatientRegistrationService(
            patientRepository,
            patientCreation
    );

    @BeforeEach
    void resetMocks() {
        reset(patient, patientRepository, patientCreation);
    }

    @Test
    void Accept_SuccessfulExecution_PatientHasBeenRegistered() {
        when(patientCreation.from(inputDto)).thenReturn(patient);
        patientRegistrationService.accept(inputDto);

        verify(patientRepository).add(patient);
    }

    @Test
    void Accept_PatientIsAlreadyRegistered_IllegalStateExceptionHasBeenThrown() {
        when(patientCreation.from(inputDto)).thenReturn(patient);
        doThrow(IllegalStateException.class).when(patientRepository).add(patient);

        assertThrows(IllegalStateException.class, () -> patientRegistrationService.accept(inputDto));
    }

    @Test
    void Accept_InvalidPatientRegistrationData_IllegalArgumentExceptionHasBeenThrown() {
        when(patientCreation.from(inputDto)).thenThrow(IllegalArgumentException.class);

        verifyNoInteractions(patientRepository);
        assertThrows(IllegalArgumentException.class, () -> patientRegistrationService.accept(inputDto));
    }
}
