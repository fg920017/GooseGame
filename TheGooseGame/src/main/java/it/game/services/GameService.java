package it.game.services;

import it.game.exception.PlayerAlreadyExistsException;
import it.game.exception.PlayerNotFoundException;

public interface GameService {

    int toWin = 63;

    void addPlayer(String newPlayer) throws PlayerAlreadyExistsException;

    void movePlayer(String name) throws PlayerNotFoundException;

    void movePlayer(String name, int dice1, int dice2) throws PlayerNotFoundException;

    boolean isEnded();

}
