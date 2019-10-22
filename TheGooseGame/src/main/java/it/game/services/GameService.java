package it.game.services;

import it.game.exception.PlayerNotFoundException;
import it.game.model.Player;

public interface GameService {
    enum MessageType {
        STANDARD,
        GOOSE,
        BRIDGE,
        WIN,
        BOUNCES,
        OCCUPIED
    }

    int toWin = 63;
    int bridgeFrom = 6;
    int bridgeTo = 12;
    int theGoose[] = {5, 9, 14, 18, 23, 27};

    void addPlayer(Player newPlayer);

    void movePlayer(String name, int dice1, int dice2) throws PlayerNotFoundException;

    void movePlayer(String name) throws PlayerNotFoundException;

    void moveNextPlayer();

    void moveNextPlayer(int dice1, int dice2);

    boolean isEnded();

    void extractionPlayers();
}
