package org.healthnet.backend.patients.domain.patient;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {
    public static class IdTest {
        @Test
        void HasValidFormat_ValidUUID_ReturnTrue() {
            assertTrue(Patient.Id.hasValidFormat("7167528a-6418-4a46-8cfd-09077591348b"));
        }

        @Test
        void HasInvalidFormat_InvalidUUID_ReturnFalse() {
            assertFalse(Patient.Id.hasValidFormat("293239223923i92392"));
        }

        @Test
        void Equals_EqualsIds_ReturnTrue() {
            String idValue = UUID.randomUUID().toString();
            Patient.Id id1 = new Patient.Id(idValue);
            Patient.Id id2 = new Patient.Id(idValue);
            assertEquals(id1, id2);
        }

        @Test
        void Equals_UnequalsIds_ReturnFalse() {
            Patient.Id id1 = new Patient.Id(UUID.randomUUID().toString());
            Patient.Id id2 = new Patient.Id(UUID.randomUUID().toString());
            assertNotEquals(id1, id2);
        }
    }
    public static class FullNameTest {
        @Test
        void HasLengthBiggerThan_BiggerLength_ReturnTrue() {
            assertTrue(Patient.FullName.hasLengthBiggerThan(3, "foobar"));
        }

        @Test
        void HasLengthBiggerThan_SmallerLength_ReturnFalse() {
            assertFalse(Patient.FullName.hasLengthBiggerThan(3, "fo"));
        }

        @Test
        void HasLengthBiggerThan_EqualLength_ReturnFalse() {
            assertFalse(Patient.FullName.hasLengthBiggerThan(3, "foo"));
        }

        @Test
        void Equals_EqualsFullNames_ReturnTrue() {
            String fullNameValue = "John Riley";
            Patient.FullName fullName1 = new Patient.FullName(fullNameValue);
            Patient.FullName fullName2 = new Patient.FullName(fullNameValue);
            assertEquals(fullName1, fullName2);
        }

        @Test
        void Equals_UnequalsFullNames_ReturnFalse() {
            Patient.FullName f1 = new Patient.FullName("Sam Fisher");
            Patient.FullName f2 = new Patient.FullName("John Riley");
            assertNotEquals(f1, f2);
        }
    }
}
