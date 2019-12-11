package org.healthnet.backend.patients.domain.patient;

import org.healthnet.backend.patients.domain.shared.ValueObject;

import java.util.Objects;

public class Id extends ValueObject<Id> {
    private final String value;

    public Id(String value) {
        if(Objects.isNull(value)) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public String getValue() {
        return value;
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
