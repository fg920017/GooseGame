package it.game.cli.exception;

public class CommandNotFoundException extends Exception {

    public CommandNotFoundException(String userString) {
        super("Command " + userString + " not found");
    }
}
