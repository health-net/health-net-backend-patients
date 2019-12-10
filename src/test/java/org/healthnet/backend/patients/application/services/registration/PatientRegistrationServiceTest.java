package org.healthnet.backend.patients.application.services.registration;

import org.healthnet.backend.patients.application.shared.Creator;
import org.healthnet.backend.patients.domain.patient.Patient;
import org.healthnet.backend.patients.domain.patient.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PatientRegistrationServiceTest {
    private final static Patient patient = mock(Patient.class);
    private final static PatientRegistrationDto patientRegistrationDto = mock(PatientRegistrationDto.class);
    private final static PatientRepository patientRepository = mock(PatientRepository.class);
    private final static Creator<PatientRegistrationDto, Patient> patientCreation = mock(Creator.class);
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
        when(patientCreation.from(patientRegistrationDto)).thenReturn(patient);
        patientRegistrationService.accept(patientRegistrationDto);

        verify(patientRepository).add(patient);
    }

    @Test
    void Accept_PatientIsAlreadyRegistered_IllegalStateExceptionHasBeenThrown() {
        when(patientCreation.from(patientRegistrationDto)).thenReturn(patient);
        doThrow(IllegalStateException.class).when(patientRepository).add(patient);

        assertThrows(IllegalStateException.class, () -> patientRegistrationService.accept(patientRegistrationDto));
    }

    @Test
    void Accept_InvalidPatientRegistrationData_IllegalArgumentExceptionHasBeenThrown() {
        when(patientCreation.from(patientRegistrationDto)).thenThrow(IllegalArgumentException.class);

        verifyNoInteractions(patientRepository);
        assertThrows(IllegalArgumentException.class, () -> patientRegistrationService.accept(patientRegistrationDto));
    }
}
