package com.juice.utils;

/**
 * Contexto compartido entre las distintas clases de Steps de un mismo
 * escenario (email/password del usuario recien registrado, etc.).
 *
 * Se usa ThreadLocal porque los escenarios de Cucumber+TestNG corren en
 * paralelo (ver testng.xml, parallel="methods"): cada escenario se ejecuta
 * completo en un unico hilo, por lo que ThreadLocal aisla correctamente
 * los datos de un escenario de los de otro que se ejecuta simultaneamente.
 *
 * No se usa un framework de inyeccion de dependencias (Picocontainer/Spring)
 * a proposito, para mantener el proyecto simple y con las dependencias
 * minimas indicadas en el examen.
 */
public final class TestContext {

    private static final ThreadLocal<String> email = new ThreadLocal<>();
    private static final ThreadLocal<String> password = new ThreadLocal<>();

    private TestContext() {
    }

    public static void setCredentials(String userEmail, String userPassword) {
        email.set(userEmail);
        password.set(userPassword);
    }

    public static String getEmail() {
        return email.get();
    }

    public static String getPassword() {
        return password.get();
    }

    public static void clear() {
        email.remove();
        password.remove();
    }
}
