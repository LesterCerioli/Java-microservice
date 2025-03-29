package com.medicalapp.api.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


public record HistoryRecord(
        UUID id,
        UUID medicalRecordId,
        String action,
        String details,
        LocalDateTime timestamp
) {

    public HistoryRecord {
        Objects.requireNonNull(id, "ID cannot be null");
        Objects.requireNonNull(medicalRecordId, "MedicalRecord ID cannot be null");
        action = validateAction(action);
        details = validateDetails(details);
        Objects.requireNonNull(timestamp, "Timestamp cannot be null");
    }


    public static HistoryRecord create(UUID medicalRecordId, String action, String details) {
        return new HistoryRecord(
                UUID.randomUUID(),
                medicalRecordId,
                action,
                details,
                LocalDateTime.now()
        );
    }

    private static String validateAction(String action) {
        if (action == null || action.isBlank()) {
            throw new IllegalArgumentException("Action cannot be blank");
        }
        if (action.length() > 255) {
            throw new IllegalArgumentException("Action exceeds maximum length of 255 characters");
        }
        return action;
    }

    private static String validateDetails(String details) {
        if (details == null) {
            return "";
        }
        if (details.length() > 10000) {
            throw new IllegalArgumentException("Details exceed maximum length");
        }
        return details;
    }


    public String toAuditString() {
        return String.format("[%s] %s - %s",
                timestamp.toString(),
                action,
                details.length() > 50 ? details.substring(0, 50) + "..." : details);
    }
}