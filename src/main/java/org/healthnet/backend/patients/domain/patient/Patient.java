package org.healthnet.backend.patients.domain.patient;

import org.healthnet.backend.patients.domain.shared.AggregateRoot;
import org.healthnet.backend.patients.domain.shared.ValueObject;

import java.util.Objects;

public class Patient extends AggregateRoot<Patient> {
    private final Id id;
    private final FullName fullName;

    public Patient(Id id, FullName fullName) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Id not specified");
        }
        if (Objects.isNull(fullName)) {
            throw new IllegalArgumentException("Fullname not specified");
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

    public static class Id extends ValueObject<Id> {
        private final String value;

        public Id(String value) {
            if (Objects.isNull(value)) {
                throw new IllegalArgumentException("Id not specified");
            }
            if (!hasValidFormat(value)) {
                throw new IllegalArgumentException("Invalid ID");
            }
            this.value = value;
        }

        static boolean hasValidFormat(String value) {
            return value.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
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

    public static class FullName extends ValueObject<FullName> {
        private final static int MAX_LENGTH = 300;
        private final String value;

        public FullName(String value) {
            if (Objects.isNull(value)) {
                throw new IllegalArgumentException("Full name not specified");
            }
            if (hasLengthBiggerThan(MAX_LENGTH, value)) {
                throw new IllegalArgumentException("Full name is too long");
            }
            this.value = value;
        }

        static boolean hasLengthBiggerThan(int length, String value) {
            return value.length() > length;
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
}
