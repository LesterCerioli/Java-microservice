package com.medicalapp.api.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public final class Customer {
    private final UUID id;
    private String name;
    private String email;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Customer(String name, String email) {
        this(UUID.randomUUID(),
                Objects.requireNonNull(name, "Name cannot be null"),
                Objects.requireNonNull(email, "Email cannot be null"),
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public Customer(UUID id, String name, String email,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.name = validateName(name);
        this.email = validateEmail(email);
        this.createdAt = Objects.requireNonNull(createdAt, "Creation date cannot be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Update date cannot be null");
    }

    private String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Name exceeds maximum length (100 chars)");
        }
        return name;
    }

    private String validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (email.length() > 100) {
            throw new IllegalArgumentException("Email exceeds maximum length (100 chars)");
        }
        return email;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setName(String name) {
        this.name = validateName(name);
        this.updatedAt = LocalDateTime.now();
    }

    public void setEmail(String email) {
        this.email = validateEmail(email);
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}