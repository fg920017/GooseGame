package it.game.services.impl;

import it.game.exception.PlayerAlreadyExistsException;
import it.game.exception.PlayerNotFoundException;
import it.game.model.*;
import it.game.services.GameService;
import it.game.services.PrintService;

import java.io.PrintStream;
import java.util.ArrayList;

public class GameServiceImpl implements GameService {
    private ArrayList<Player> players = new ArrayList<>();
    private Board board;
    private PrintService printService = new PrintServiceImpl(new PrintStream(System.out));

    //private static int indexPlayer = 0;

    public GameServiceImpl(Board board) {
        this.board = board;
    }

    public GameServiceImpl(Board board, PrintService printService) {
        this.board = board;
        this.printService = printService;
    }

    @Override
    public void addPlayer(String newPlayer) throws PlayerAlreadyExistsException {
        Player alreadyExistingPlayer = players.stream().filter(persona -> persona.getName().equalsIgnoreCase(newPlayer)).findAny().orElse(null);
        if (alreadyExistingPlayer != null) {
            throw new PlayerAlreadyExistsException(alreadyExistingPlayer.getName());
        }
        players.add(new Player(newPlayer));
        printService.onPlayerAdded(newPlayer);
        printService.printAllPlayers(getAllPlayers());
    }

    @Override
    public void movePlayer(String name, int dice1, int dice2) throws PlayerNotFoundException {
        Player player = getPlayer(name);
        updateStepPlayer(player, dice1, dice2);
        //updateStepPlayer(player, 2, 4);
    }

    @Override
    public void movePlayer(String name) throws PlayerNotFoundException {
        movePlayer(name, throwDice(), throwDice());
    }


    @Override
    public boolean isEnded() {
        return players.stream().filter(persona -> persona.getStep() == toWin).findAny().orElse(null) != null;
    }


    private Player getPlayer(String name) throws PlayerNotFoundException {
        return players.stream().filter(persona -> persona.getName().equalsIgnoreCase(name)).findFirst().orElseThrow(() -> new PlayerNotFoundException(name));
    }

    private int throwDice() {
        return (int) (Math.random() * 6) + 1;
    }

    public String getAllPlayers() {
        StringBuilder allPlayers = new StringBuilder();
        for (int i = 0; i <= players.size() - 1; i++) {
            allPlayers.append(players.get(i).getName());
            if (players.size() != i + 1) {
                allPlayers.append(", ");
            }
        }
        return allPlayers.toString();
    }

    private int applyRole(Player player, int dice1, int dice2, int newIndex) {
        if (newIndex == toWin) {
            printService.onPlayerWin(player.getName());
        } else if (newIndex > toWin) {
            newIndex = toWin - (newIndex - toWin);
            printService.onPlayerBounced(player.getName(), board.getBox(newIndex));
        }

        Integer evaluateRole = board.getBox(newIndex).getRole().apply(dice1 + dice2);

        Box newIndexBox = board.getBox(newIndex);
        if (newIndexBox instanceof BridgeBox) {
            printService.onPlayerJump(player.getName(), board.getBox(evaluateRole));
        } else if (newIndexBox instanceof GooseBox)
            printService.onPlayerGoose(player.getName(), board.getBox(evaluateRole));

        if (evaluateRole != newIndex) {
            Box box = board.getBox(evaluateRole);
            if (box instanceof GooseBox)
                printService.onPlayerGoose(player.getName(), box);
            return applyRole(player, dice1, dice2, evaluateRole);
        }
        return evaluateRole;

    }

    private void updateStepPlayer(Player player, int dice1, int dice2) {
        int actualIndex = player.getStep();
        printService.onPlayerRolls(player.getName(), dice1, dice2);

        int newIndex = actualIndex + dice1 + dice2;

        int indexMin = Math.min(newIndex, toWin);
        printService.onPlayerMoved(player.getName(), board.getBox(actualIndex), board.getBox(indexMin));

        newIndex = applyRole(player, dice1, dice2, newIndex);
        player.setStep(newIndex);

        handleAnotherPlayerInSameBox(player.getName(), actualIndex, newIndex);
    }

    /*private void updateIndexPlayer() {
        if (indexPlayer == players.size() - 1) {
            indexPlayer = 0;
        } else {
            indexPlayer++;
        }
    }*/

    private void handleAnotherPlayerInSameBox(String playerName, int actualIndex, int newIndex) {
        players.stream().
                filter(player1 -> player1.getStep() == newIndex && !player1.getName().equalsIgnoreCase(playerName)).
                forEach(player -> {
                    player.setStep(actualIndex);
                    printService.onPlayerPrank(player.getName(), board.getBox(newIndex), board.getBox(actualIndex));
                });
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
