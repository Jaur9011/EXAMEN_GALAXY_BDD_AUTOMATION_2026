package com.juice.utils;

import net.datafaker.Faker;

/**
 * Generador de datos falsos (registro, direcciones, tarjetas) usando DataFaker.
 * Se usa para no depender de datos fijos y permitir reejecuciones sin
 * choques por "correo ya existente".
 */
public final class TestDataGenerator {

    private static final Faker faker = new Faker();

    private TestDataGenerator() {
    }

    public static String randomEmail() {
        return "ranty.qa." + System.currentTimeMillis() + "." + faker.number().digits(4) + "@ranty-test.com";
    }

    public static String defaultPassword() {
        return "RantyQA#" + faker.number().digits(4);
    }

    public static String randomFullName() {
        return faker.name().fullName();
    }

    public static String randomMobileNumber() {
        // Juice Shop exige 8 a 20 digitos numericos
        return faker.number().digits(9);
    }

    public static String randomZipCode() {
        return faker.number().digits(5);
    }

    public static String randomStreetAddress() {
        return faker.address().streetAddress();
    }

    public static String randomCity() {
        return faker.address().city();
    }

    public static String randomState() {
        return faker.address().state();
    }

    public static String randomCountry() {
        return "Peru";
    }

    public static String randomCardHolder() {
        return faker.name().fullName();
    }

    /**
     * Genera un numero de tarjeta valido (16 digitos) tipo Visa de prueba,
     * basado en los ejemplos de BlueSnap (referencia del examen).
     */
    public static String randomCardNumber() {
        return "4111" + faker.number().digits(12);
    }
}
