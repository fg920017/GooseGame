package it.game.services;

import it.game.model.Box;

public interface PrintService {
    void onPlayerMoved(String playerName, Box from, Box to);

    void onPlayerBounced(String playerName, Box to);

    void onPlayerWin(String playerName);

    void onPlayerJump(String playerName, Box to);

    void onPlayerPrank(String playerJokedName, Box from, Box to);
}
