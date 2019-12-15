package org.healthnet.backend.patients.application.dtos;

public class PatientSummaryDto {
    public final String id;
    public final String fullName;

    public PatientSummaryDto(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}
