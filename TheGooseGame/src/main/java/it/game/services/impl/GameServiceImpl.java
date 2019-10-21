package it.game.services.impl;

import it.game.model.Player;
import it.game.services.GameService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static it.game.services.GameService.MessageType.*;

public class GameServiceImpl implements GameService {
    private ArrayList<Player> players = new ArrayList<>();

    private int indexPlayer = 0;

    @Override
    public void addPlayer(Player newPlayer) {
        Player alreadyExistingPlayer = players.stream().filter(persona -> persona.getNome().equalsIgnoreCase(newPlayer.getNome())).findAny().orElse(null);
        if (alreadyExistingPlayer != null) {
            System.out.println(alreadyExistingPlayer.getNome() + ": already existing player ");
            return;
        }
        players.add(newPlayer);
        System.out.println("players: " + getAllPlayers());
    }

    @Override
    public Player getPlayer(String name) {
        return players.stream().filter(persona -> persona.getNome().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    @Override
    public void movePlayer(Player player, int dice1, int dice2) {
        int start = player.getStep();
        updateStepPlayer(player, start, dice1, dice2);
    }

    @Override
    public void moveNextPlayer() {
        movePlayer(players.get(indexPlayer), throwDice(), throwDice());

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

    private int throwDice() {
        return (int) (Math.random() * 6) + 1;
    }

    private String getAllPlayers() {
        StringBuilder allPlayers = new StringBuilder();
        for (int i = 0; i <= players.size() - 1; i++) {
            allPlayers.append(players.get(i).getNome());
            if (players.size() != i + 1) {
                allPlayers.append(", ");
            }
        }
        return allPlayers.toString();
    }

    private void updateStepPlayer(Player player, int startPoint, int dice1, int dice2) {
        int endPoint = startPoint + dice1 + dice2;
        String descStart = startPoint == 0 ? "Start" : startPoint + "";
        if (endPoint == toWin) {
            print(WIN, player.getNome(), descStart, endPoint, dice1, dice2, null);
        } else if (endPoint == bridgeFrom) {
            endPoint = bridgeTo;
            print(BRIDGE, player.getNome(), descStart, endPoint, dice1, dice2, null);
        } else if (contains(endPoint)) {
            print(STANDARD, player.getNome(), descStart, endPoint, dice1, dice2, ", The Goose. ");
            while (contains(endPoint)) {
                endPoint += dice1 + dice2;
                print(GOOSE, player.getNome(), null, endPoint, null, null, contains(endPoint) ? ", The Goose. " : ". ");
            }
        } else if (endPoint > toWin) {
            endPoint = toWin - (endPoint - toWin);
            print(BOUNCES, player.getNome(), descStart, endPoint, dice1, dice2, null);
        } else print(STANDARD, player.getNome(), descStart, endPoint, dice1, dice2, ". ");

        Player occupiedBox = getOccupiedBox(endPoint);
        if (occupiedBox != null) {
            occupiedBox.setStep(startPoint);
            print(OCCUPIED, null, descStart, endPoint, null, null, occupiedBox.getNome());
        }
        System.out.println();
        player.setStep(endPoint);
        updateIndexPlayer();
    }

    private void updateIndexPlayer() {
        if (indexPlayer == players.size() - 1) {
            indexPlayer = 0;
        } else {
            indexPlayer++;
        }
    }

    private static boolean contains(final int key) {
        return Arrays.stream(theGoose).anyMatch(i -> i == key);
    }

    private Player getOccupiedBox(final int end) {
        return players.stream().filter(player1 -> player1.getStep() == end).findFirst().orElse(null);
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
