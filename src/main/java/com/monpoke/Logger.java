package com.monpoke;

public class Logger {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";

    public void info(String message) {
        System.out.println(message);
    }

    public void error(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }
}
