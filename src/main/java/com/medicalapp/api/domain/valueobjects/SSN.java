package com.medicalapp.api.domain.valueobjects;

import java.util.regex.Pattern;
import java.util.Objects;

public record SSN(String value) {

    private static final Pattern SSN_PATTERN = Pattern.compile("^\\d{9}$");
    private static final Pattern FORMATTED_SSN_PATTERN = Pattern.compile("^\\d{3}-\\d{2}-\\d{4}$");

    private static final String[] INVALID_SSNS = {
            "111111111", "123456789", "999999999", "000000000", "123123123"
    };

    public SSN {
        Objects.requireNonNull(value, "SSN não pode ser nulo");
        String cleanValue = cleanInput(value);
        validate(cleanValue);
        value = cleanValue;
    }

    public static SSN of(String value) {
        return new SSN(value);
    }

    public String formatted() {
        return value.substring(0, 3) + "-" +
                value.substring(3, 5) + "-" +
                value.substring(5);
    }

    public static boolean isValid(String ssn) {
        try {
            new SSN(ssn);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static String cleanInput(String input) {
        return input.replaceAll("[\\s-]", "");
    }

    private static void validate(String ssn) {
        if (ssn.length() != 9) {
            throw new IllegalArgumentException("SSN deve conter exatamente 9 dígitos");
        }

        if (!SSN_PATTERN.matcher(ssn).matches()) {
            throw new IllegalArgumentException("SSN deve conter apenas dígitos numéricos");
        }

        validateAreaNumber(ssn.substring(0, 3));
        validateGroupNumber(ssn.substring(3, 5));
        validateSerialNumber(ssn.substring(5));
        checkForInvalidSequences(ssn);
    }

    private static void validateAreaNumber(String area) {
        switch (area) {
            case "000", "666" -> throw new IllegalArgumentException("Área " + area + " é inválida para SSN");
            default -> {
                if (area.compareTo("900") >= 0) {
                    throw new IllegalArgumentException("Área " + area + " é reservada");
                }
            }
        }
    }

    private static void validateGroupNumber(String group) {
        if ("00".equals(group)) {
            throw new IllegalArgumentException("Número de grupo não pode ser 00");
        }
    }

    private static void validateSerialNumber(String serial) {
        if ("0000".equals(serial)) {
            throw new IllegalArgumentException("Número serial não pode ser 0000");
        }
    }

    private static void checkForInvalidSequences(String ssn) {
        for (String invalid : INVALID_SSNS) {
            if (invalid.equals(ssn)) {
                throw new IllegalArgumentException("SSN " + ssn + " é inválido ou reservado");
            }
        }
    }

    @Override
    public String toString() {
        return formatted();
    }
}