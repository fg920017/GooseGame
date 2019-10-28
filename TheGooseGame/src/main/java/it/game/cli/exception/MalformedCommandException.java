package it.game.cli.exception;

public class MalformedCommandException extends Exception {
    public MalformedCommandException() {
        super("Command malformed");
    }
}
