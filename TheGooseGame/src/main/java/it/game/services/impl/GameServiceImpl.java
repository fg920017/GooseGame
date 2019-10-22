package it.game.services.impl;

import it.game.exception.PlayerNotFoundException;
import it.game.model.Board;
import it.game.model.Box;
import it.game.model.Player;
import it.game.services.GameService;

import java.util.ArrayList;
import java.util.Collections;

public class GameServiceImpl implements GameService {
    private ArrayList<Player> players = new ArrayList<>();
    private Board board;
    private int indexPlayer = 0;

    public GameServiceImpl(Board board) {
        this.board = board;
    }

    @Override
    public void addPlayer(Player newPlayer) {
        Player alreadyExistingPlayer = players.stream().filter(persona -> persona.getName().equalsIgnoreCase(newPlayer.getName())).findAny().orElse(null);
        if (alreadyExistingPlayer != null) {
            System.out.println(alreadyExistingPlayer.getName() + ": already existing player ");
            return;
        }
        players.add(newPlayer);
        System.out.println("players: " + getAllPlayers());
    }

    @Override
    public void movePlayer(String name, int dice1, int dice2) throws PlayerNotFoundException {
        Player player = getPlayer(name);
        updateStepPlayer(player, dice1, dice2);
    }

    @Override
    public void movePlayer(String name) throws PlayerNotFoundException {
        movePlayer(name, throwDice(), throwDice());
    }

    @Override
    public void moveNextPlayer(int dice1, int dice2) {
        Player player = players.get(indexPlayer);
        updateStepPlayer(player, throwDice(), throwDice());
    }

    @Override
    public void moveNextPlayer() {
        moveNextPlayer(throwDice(), throwDice());
    }

    @Override
    public boolean isEnded() {
        return players.stream().filter(persona -> persona.getStep() == toWin).findAny().orElse(null) != null;
    }

    @Override
    public void extractionPlayers() {
        Collections.shuffle(players);
        System.out.println("Ordine players: " + getAllPlayers());
    }

    private Player getPlayer(String name) throws PlayerNotFoundException {
        return players.stream().filter(persona -> persona.getName().equalsIgnoreCase(name)).findFirst().orElseThrow(() -> new PlayerNotFoundException(name));
    }

    private int throwDice() {
        return (int) (Math.random() * 6) + 1;
    }

    private String getAllPlayers() {
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
            // print win
            System.out.println(player.getName() + "wins");
        } else if (newIndex > toWin) {
            newIndex = toWin - (newIndex - toWin);
            //print onbounced
        }
        Integer evaluateRole = board.getBox(newIndex).getRole().apply(dice1 + dice2);

        if (!evaluateRole.equals(newIndex)) {
            //print
            //ricorsione
            return applyRole(player, dice1, dice2, evaluateRole);
        }
        return evaluateRole;

    }

    private void updateStepPlayer(Player player, int dice1, int dice2) {
        int actualIndex = player.getStep();
        int newIndex = actualIndex + dice1 + dice2;

       /* Box from = board.getBox(actualIndex);
        Box to = board.getBox(newIndex);*/

        newIndex = applyRole(player, dice1, dice2, newIndex);
        player.setStep(newIndex);
        //print move

        handleAnotherPlayerInSameBox(player.getName(), actualIndex, newIndex);
        updateIndexPlayer();
    }

    private void updateIndexPlayer() {
        if (indexPlayer == players.size() - 1) {
            indexPlayer = 0;
        } else {
            indexPlayer++;
        }
    }

    private void handleAnotherPlayerInSameBox(String playerName, int actualIndex, int newIndex) {
        players.stream().
                filter(player1 -> player1.getStep() == newIndex && !player1.getName().equalsIgnoreCase(playerName)).
                forEach(player -> {
                    player.setStep(actualIndex);
                    // print same box
                });
    }

    private void print(MessageType type, String playerName, String from, Integer to, Integer dice1, Integer dice2, String description) {
        String result = "";
        String printRolls = playerName + " rolls " + dice1 + ", " + dice2 + ". ";
        switch (type) {
            case STANDARD:
                result = printRolls + playerName + " moves from " + from + " to " + to + description;
                break;
            case GOOSE:
                result = playerName + " moves again and goes to " + to + description;
                break;
            case BRIDGE:
                result = printRolls + playerName + " moves from " + from + " to The Bridge. " + playerName + " jumps to " + bridgeTo + ". ";
                break;
            case WIN:
                result = printRolls + playerName + " moves from " + from + " to " + to + ". " + playerName + " Wins!!";
                break;
            case BOUNCES:
                result = printRolls + playerName + " moves from " + from + " to " + toWin + ". " + playerName + " bounces! " + playerName + " returns to " + to + ". ";
                break;
            case OCCUPIED:
                result = "On " + to + " there is " + description + ", who return to " + from;
                break;
            default:
                break;
        }
        System.out.print(result);
    }

}
