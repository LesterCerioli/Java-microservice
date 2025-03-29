package com.medicalapp.api.domain.entities;

import com.medicalapp.api.domain.valueobjects.SSN;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.List;

public final class Patient {
    private final UUID id;
    private final UUID organizationId;
    private String name;
    private final SSN ssn;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private String contact;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Factory method for new patients
    public static Patient create(UUID organizationId, String name, SSN ssn,
                                 LocalDate dateOfBirth, String gender,
                                 String address, String contact) {
        return new Patient(
                UUID.randomUUID(),
                organizationId,
                name,
                ssn,
                dateOfBirth,
                gender,
                address,
                contact,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    // Full constructor for reconstruction
    public Patient(UUID id, UUID organizationId, String name, SSN ssn,
                   LocalDate dateOfBirth, String gender, String address,
                   String contact, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id);
        this.organizationId = Objects.requireNonNull(organizationId);
        this.name = validateName(name);
        this.ssn = Objects.requireNonNull(ssn);
        this.dateOfBirth = validateDateOfBirth(dateOfBirth);
        this.gender = validateGender(gender);
        this.address = validateAddress(address);
        this.contact = validateContact(contact);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    // Validation methods
    private String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Patient name cannot be blank");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Name exceeds maximum length (100 chars)");
        }
        return name;
    }

    private LocalDate validateDateOfBirth(LocalDate dob) {
        if (dob == null) {
            throw new IllegalArgumentException("Date of birth cannot be null");
        }
        if (dob.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }
        return dob;
    }

    private String validateGender(String gender) {
        if (gender == null || gender.isBlank()) {
            throw new IllegalArgumentException("Gender cannot be blank");
        }
        if (!List.of("M", "F", "NB", "OTHER").contains(gender.toUpperCase())) {
            throw new IllegalArgumentException("Invalid gender specification");
        }
        return gender;
    }

    private String validateAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be blank");
        }
        if (address.length() > 200) {
            throw new IllegalArgumentException("Address exceeds maximum length (200 chars)");
        }
        return address;
    }

    private String validateContact(String contact) {
        if (contact == null || contact.isBlank()) {
            throw new IllegalArgumentException("Contact cannot be blank");
        }
        if (!contact.matches("^\\+?[0-9\\s-]{10,15}$")) {
            throw new IllegalArgumentException("Invalid contact format");
        }
        return contact;
    }

    // Getters
    public UUID getId() { return id; }
    public UUID getOrganizationId() { return organizationId; }
    public String getName() { return name; }
    public SSN getSsn() { return ssn; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getAddress() { return address; }
    public String getContact() { return contact; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters with validation
    public void setName(String name) {
        this.name = validateName(name);
        updateTimestamp();
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = validateDateOfBirth(dateOfBirth);
        updateTimestamp();
    }

    public void setGender(String gender) {
        this.gender = validateGender(gender);
        updateTimestamp();
    }

    public void setAddress(String address) {
        this.address = validateAddress(address);
        updateTimestamp();
    }

    public void setContact(String contact) {
        this.contact = validateContact(contact);
        updateTimestamp();
    }

    private void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id.equals(patient.id) && ssn.equals(patient.ssn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ssn);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ssn=" + ssn.formatted() +
                '}';
    }
}