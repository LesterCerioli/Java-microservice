package com.medicalapp.api.domain.entities;



import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import com.medicalapp.api.domain.valueobjects.ChargeStatus;

public final class Charge {
    private final UUID id;
    private final BigDecimal amount;
    private final String currency;
    private final String description;
    private final Customer customer;
    private final String paymentMethod;
    private ChargeStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Charge create(BigDecimal amount, String currency,
                                Optional<String> description, Customer customer,
                                String paymentMethod) {
        return new Charge(
                UUID.randomUUID(),
                amount,
                currency,
                description.orElse(null),
                customer,
                paymentMethod,
                ChargeStatus.PENDING,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public Charge(UUID id, BigDecimal amount, String currency,
                  String description, Customer customer,
                  String paymentMethod, ChargeStatus status,
                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "ID cannot be null");
        this.amount = validateAmount(amount);
        this.currency = validateCurrency(currency);
        this.description = description; // Optional field
        this.customer = Objects.requireNonNull(customer, "Customer cannot be null");
        this.paymentMethod = validatePaymentMethod(paymentMethod);
        this.status = Objects.requireNonNull(status, "Status cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "Creation date cannot be null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "Update date cannot be null");
    }

    private BigDecimal validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return amount.setScale(2); // Ensure 2 decimal places
    }

    private String validateCurrency(String currency) {
        if (currency == null || currency.length() != 3 || !currency.matches("[A-Z]{3}")) {
            throw new IllegalArgumentException("Currency must be 3 uppercase letters");
        }
        return currency;
    }

    private String validatePaymentMethod(String paymentMethod) {
        if (paymentMethod == null || paymentMethod.isBlank()) {
            throw new IllegalArgumentException("Payment method cannot be empty");
        }
        if (paymentMethod.length() > 50) {
            throw new IllegalArgumentException("Payment method too long");
        }
        return paymentMethod;
    }

    public void markAsSucceeded() {
        this.status = ChargeStatus.SUCCEEDED;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsFailed() {
        this.status = ChargeStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public Optional<String> getDescription() { return Optional.ofNullable(description); }
    public Customer getCustomer() { return customer; }
    public String getPaymentMethod() { return paymentMethod; }
    public ChargeStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Charge charge = (Charge) o;
        return id.equals(charge.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Charge{" +
                "id=" + id +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", status=" + status +
                '}';
    }
}