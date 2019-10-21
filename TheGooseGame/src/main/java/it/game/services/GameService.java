package it.game.services;

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

    Player getPlayer(String name);

    void movePlayer(Player player, int dice1, int dice2);

    void moveNextPlayer();

    boolean isEnded();

    void extractionPlayers();
}
