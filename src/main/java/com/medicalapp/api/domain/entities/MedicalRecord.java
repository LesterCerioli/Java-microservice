package com.medicalapp.api.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class MedicalRecord {
    private final UUID id;
    private final UUID patientId;
    private final UUID organizationId;
    private String patientName; // Transient field
    private final UUID doctorId;
    private String diagnosis;
    private String treatment;
    private String notes;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final List<HistoryRecord> historyRecords;

    public static MedicalRecord create(UUID patientId, UUID organizationId,
                                       UUID doctorId, String diagnosis) {
        return new MedicalRecord(
                UUID.randomUUID(),
                patientId,
                organizationId,
                null, // patientName initially null
                doctorId,
                diagnosis,
                null, // treatment
                null, // notes
                LocalDateTime.now(),
                LocalDateTime.now(),
                new ArrayList<>()
        );
    }

    public MedicalRecord(UUID id, UUID patientId, UUID organizationId,
                         String patientName, UUID doctorId, String diagnosis,
                         String treatment, String notes, LocalDateTime createdAt,
                         LocalDateTime updatedAt, List<HistoryRecord> historyRecords) {
        this.id = Objects.requireNonNull(id);
        this.patientId = Objects.requireNonNull(patientId);
        this.organizationId = Objects.requireNonNull(organizationId);
        this.patientName = patientName; // Optional
        this.doctorId = Objects.requireNonNull(doctorId);
        this.diagnosis = validateDiagnosis(diagnosis);
        this.treatment = treatment;
        this.notes = notes;
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
        this.historyRecords = new ArrayList<>(Objects.requireNonNull(historyRecords));
    }

    private String validateDiagnosis(String diagnosis) {
        if (diagnosis == null || diagnosis.isBlank()) {
            throw new IllegalArgumentException("Diagnosis cannot be blank");
        }
        if (diagnosis.length() > 10000) {
            throw new IllegalArgumentException("Diagnosis exceeds maximum length");
        }
        return diagnosis;
    }

    public void addHistoryRecord(HistoryRecord record) {
        this.historyRecords.add(Objects.requireNonNull(record));
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTreatment(String treatment) {
        this.treatment = treatment;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateNotes(String notes) {
        this.notes = notes;
        this.updatedAt = LocalDateTime.now();
    }

    public void setPatientName(String name) {
        this.patientName = name;
    }

    public UUID getId() { return id; }
    public UUID getPatientId() { return patientId; }
    public UUID getOrganizationId() { return organizationId; }
    public String getPatientName() { return patientName; }
    public UUID getDoctorId() { return doctorId; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public List<HistoryRecord> getHistoryRecords() { return new ArrayList<>(historyRecords); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecord that = (MedicalRecord) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", diagnosis='" + diagnosis + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}