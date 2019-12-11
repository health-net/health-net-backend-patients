package org.healthnet.backend.patients.domain.patient;

import org.healthnet.backend.patients.domain.shared.ValueObject;

import java.util.Objects;

public class FullName extends ValueObject<FullName> {
    private final String value;

    public FullName(String value) {
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
        if (!(o instanceof FullName)) return false;
        FullName fullName = (FullName) o;
        return Objects.equals(value, fullName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
