package org.healthnet.backend.patients.domain.patient;

import org.healthnet.backend.patients.domain.shared.ValueObject;

import java.util.Objects;

public class Id extends ValueObject<Id> {
    private final String value;

    public Id(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Id)) return false;
        Id id = (Id) o;
        return Objects.equals(value, id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
