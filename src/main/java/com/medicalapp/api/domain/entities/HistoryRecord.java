package com.medicalapp.api.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "history_records")
public record HistoryRecord(
        @Id UUID id,
        UUID medicalRecordId,
        String action,
        String details,
        LocalDateTime timestamp
) {

    private static final int MAX_ACTION_LENGTH = 255;
    private static final int MAX_DETAILS_LENGTH = 10000;
    private static final int AUDIT_SUMMARY_LENGTH = 50;

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
        if (action.length() > MAX_ACTION_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Action exceeds maximum length of %d characters", MAX_ACTION_LENGTH)
            );
        }
        return action.trim();
    }

    private static String validateDetails(String details) {
        if (details == null) {
            return "";
        }
        if (details.length() > MAX_DETAILS_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Details exceed maximum length of %d characters", MAX_DETAILS_LENGTH)
            );
        }
        return details.trim();
    }

    public String toAuditString() {
        String summary = details.length() > AUDIT_SUMMARY_LENGTH
                ? details.substring(0, AUDIT_SUMMARY_LENGTH) + "..."
                : details;

        return String.format("[%s] %s - %s", timestamp, action, summary);
    }


    public String getSummary() {
        return action + ": " + (details.isEmpty() ? "No details" :
                details.length() > AUDIT_SUMMARY_LENGTH
                        ? details.substring(0, AUDIT_SUMMARY_LENGTH) + "..."
                        : details);
    }
}