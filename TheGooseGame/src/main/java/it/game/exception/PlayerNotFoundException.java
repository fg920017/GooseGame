package it.game.exception;

public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException(String name) {
        super("Player " + name + " not found");
    }
}
