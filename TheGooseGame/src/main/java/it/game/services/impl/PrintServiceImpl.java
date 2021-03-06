package it.game.services.impl;

import it.game.model.Box;
import it.game.services.PrintService;

import java.io.PrintStream;

public class PrintServiceImpl implements PrintService {
    private final PrintStream printStream;

    public PrintServiceImpl(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void onPlayerRolls(String playerName, int dice1, int dice2) {
        printStream.print(playerName + " rolls " + dice1 + ", " + dice2 + ". ");
    }

    @Override
    public void onPlayerMoved(String playerName, Box from, Box to) {
        printStream.print(playerName
                + " moves from "
                + from.getName()
                + " to " + to.getName() + ". ");
    }

    @Override
    public void onPlayerBounced(String playerName, Box to) {
        printStream.print(playerName + " bounces! " + playerName + " returns to " + to.getName() + ". ");
    }

    @Override
    public void onPlayerWin(String playerName) {
        printStream.print(playerName + " Wins!!");
    }

    @Override
    public void onPlayerJump(String playerName, Box to) {
        printStream.print(playerName + " jumps to " + to.getName() + ". ");
    }

    @Override
    public void onPlayerPrank(String playerJokedName, Box from, Box to) {
        printStream.print("On " + from.getName() + " there is " + playerJokedName + ", who returns to " + to.getName() + ". ");
    }

    @Override
    public void onPlayerAdded(String playerName) {
        printStream.print("Added " + playerName + ". ");
    }

    @Override
    public void onPlayerGoose(String playerName, Box to) {
        printStream.print(playerName + " moves again and goes to " + to.getName() + ". ");
    }

    @Override
    public void printAllPlayers(String players) {
        printStream.println("Players: " + players);
    }


}
