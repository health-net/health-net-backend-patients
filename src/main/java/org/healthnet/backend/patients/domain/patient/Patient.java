package org.healthnet.backend.patients.domain.patient;

import org.healthnet.backend.patients.domain.shared.AggregateRoot;

import java.util.Objects;

public class Patient extends AggregateRoot<Patient> {
    private final Id id;
    private final FullName fullName;

    public Patient(Id id, FullName fullName) {
        if (Objects.isNull(id) || Objects.isNull(fullName)) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.fullName = fullName;
    }

    public Id getId() {
        return id;
    }

    public FullName getFullName() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
