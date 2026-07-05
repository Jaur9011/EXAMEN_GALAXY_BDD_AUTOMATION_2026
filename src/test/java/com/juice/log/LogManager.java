package com.juice.log;

import org.apache.logging.log4j.Logger;

/**
 * Envoltorio simple sobre Log4j2 para tener un unico punto de entrada
 * de logging (com.juice.log) usado por steps, pages y utils.
 */
public final class LogManager {

    private LogManager() {
    }

    public static Logger getLogger(Class<?> clazz) {
        return org.apache.logging.log4j.LogManager.getLogger(clazz);
    }
}
