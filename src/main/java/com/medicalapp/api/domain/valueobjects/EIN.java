package com.medicalapp.api.domain.valueobjects;

import java.util.regex.Pattern;
import java.util.Objects;

public final class EIN {
    private static final Pattern EIN_PATTERN = Pattern.compile("^\\d{2}-?\\d{7}$");
    private static final String[] INVALID_EINS = {
            "00-0000000", "07-7777777",
            "11-1111111", "22-2222222",
            "33-3333333", "44-4444444",
            "55-5555555", "66-6666666",
            "77-7777777", "88-8888888",
            "99-9999999"
    };

    private final String value;

    public EIN(String value) {
        String cleanedValue = cleanInput(value);
        validate(cleanedValue);
        this.value = cleanedValue;
    }

    public static EIN of(String value) {
        return new EIN(value);
    }

    public String formatted() {
        return value.substring(0, 2) + "-" + value.substring(2);
    }

    public String raw() {
        return value;
    }

    private static void validate(String ein) {
        if (ein.length() != 9) {
            throw new IllegalArgumentException("EIN deve conter exatamente 9 dígitos");
        }

        if (!EIN_PATTERN.matcher(ein).matches()) {
            throw new IllegalArgumentException("Formato de EIN inválido (use XX-XXXXXXX ou XXXXXXXXX)");
        }

        validatePrefix(ein.substring(0, 2));
        checkForInvalidSequences(ein);
    }

    private static void validatePrefix(String prefix) {
        if (prefix.startsWith("0")) {
            throw new IllegalArgumentException("EIN não pode começar com 0");
        }

        if (prefix.equals("00")) {
            throw new IllegalArgumentException("EIN não pode começar com 00");
        }
    }

    private static void checkForInvalidSequences(String ein) {
        for (String invalid : INVALID_EINS) {
            if (ein.equals(invalid.replace("-", ""))) {
                throw new IllegalArgumentException("EIN sequencial inválido: " + invalid);
            }
        }
    }

    private static String cleanInput(String input) {
        return input.replace("-", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EIN ein = (EIN) o;
        return value.equals(ein.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return formatted();
    }
}