package it.game.services;

import it.game.model.Box;

public interface PrintService {
    void onPlayerRolls(String playerName, int dice1, int dice2);

    void onPlayerMoved(String playerName, Box from, Box to);

    void onPlayerBounced(String playerName, Box to);

    void onPlayerWin(String playerName);

    void onPlayerJump(String playerName, Box to);

    void onPlayerPrank(String playerJokedName, Box from, Box to);

    void onPlayerAdded(String playerName);

    void onPlayerGoose(String playerName, Box to);

    void printAllPlayers(String players);
}
