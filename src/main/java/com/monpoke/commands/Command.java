package com.monpoke.commands;

public abstract class Command {
    protected void validateParametersSize(String[] commandParameters, int expectedSize) {
        if (commandParameters.length != expectedSize) {
            throw new IllegalArgumentException("Expecting " + expectedSize + " parameters for command but were received " + commandParameters.length);
        }
    }
}
