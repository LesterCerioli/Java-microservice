package com.medicalapp.api.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import com.medicalapp.api.domain.valueobjects.EIN;


public final class Organization {
    private final UUID id;
    private String name;
    private String address;
    private final EIN ein;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Organization(String name, String address, EIN ein) {
        this(UUID.randomUUID(),
                Objects.requireNonNull(name, "Name cannot be null"),
                Objects.requireNonNull(address, "Address cannot be null"),
                Objects.requireNonNull(ein, "EIN cannot be null"),
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public Organization(UUID id, String name, String address, EIN ein,
                        LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.name = validateName(name);
        this.address = validateAddress(address);
        this.ein = Objects.requireNonNull(ein, "EIN cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "Creation date cannot be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Update date cannot be null");
    }

    private String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Organization name cannot be blank");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Organization name exceeds maximum length (100 chars)");
        }
        return name;
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

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public EIN getEin() { return ein; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setName(String name) {
        this.name = validateName(name);
        this.updatedAt = LocalDateTime.now();
    }

    public void setAddress(String address) {
        this.address = validateAddress(address);
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return id.equals(that.id) && ein.equals(that.ein);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ein);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ein=" + ein.formatted() +
                '}';
    }
}