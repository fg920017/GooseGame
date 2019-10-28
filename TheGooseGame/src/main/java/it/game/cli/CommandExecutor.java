package it.game.cli;

import it.game.cli.exception.CommandNotFoundException;
import it.game.cli.exception.GameStoppedException;
import it.game.cli.exception.MalformedCommandException;
import it.game.exception.PlayerAlreadyExistsException;
import it.game.exception.PlayerNotFoundException;
import it.game.services.GameService;

import java.util.List;

public class CommandExecutor {

    private final GameService game;


    public CommandExecutor(GameService game) {
        this.game = game;
    }

    public void executeGameCommand(String userString) throws Exception {
        Command command = Command.getCommandFromString(userString);
        List<String> userArguments = command.getCommandArguments(userString);
        switch (command) {
            case ADD_PLAYER:
                handleAddPlayer(userArguments);
                break;
            case MOVE_PLAYER:
                handleMovePlayer(userArguments);
                break;
            case EXIT:
                throw new GameStoppedException();
            default:
                throw new CommandNotFoundException(userString);
        }
    }

    private void handleMovePlayer(List<String> userArguments) throws PlayerNotFoundException, MalformedCommandException {
        if(userArguments.size() == 0){
            throw new MalformedCommandException();
        }
        String playerName = userArguments.get(0);

        if (userArguments.size() > 1) {
            userArguments.subList(1,userArguments.size()-1);
            try{
                int dice1, dice2;
                dice1 = Integer.parseInt(userArguments.get(1));
                dice2 = Integer.parseInt(userArguments.get(2));
                game.movePlayer(playerName, dice1, dice2);
            } catch(Exception ex){
                throw new MalformedCommandException();
            }
        } else{
            game.movePlayer(playerName);
        }

    }

    private void handleAddPlayer(List<String> userArguments) throws PlayerAlreadyExistsException {
        String playerName = userArguments.get(0);

        game.addPlayer(playerName);
    }
}
