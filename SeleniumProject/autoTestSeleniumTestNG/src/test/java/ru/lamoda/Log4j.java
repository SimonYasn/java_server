package ru.lamoda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;

public class Log4j {

    private static Logger logger = LogManager.getLogger(Log4j.class);

    public static void main(String[] args) {

        System.out.println("Test");

        logger.warn("This is information message");

        System.out.println("Complited");
    }
}
