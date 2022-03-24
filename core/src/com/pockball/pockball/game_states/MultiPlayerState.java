package com.pockball.pockball.game_states;

import com.badlogic.ashley.core.Entity;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.PlayerComponent;
import com.pockball.pockball.ecs.components.ScoreComponent;
import com.pockball.pockball.ecs.entities.EntityFactory;
import com.pockball.pockball.ecs.types.BallType;

import javax.swing.Scrollable;

public class MultiPlayerState implements State {

    private final Entity player1, player2;
    Entity[] players = new Entity[2];
    private int currentPlayerIndex;
    private boolean firstBall;

    public MultiPlayerState() {
        // Set up player 1 - TODO: most likely host player?
        player1 = EntityFactory.getInstance().createPlayer("player1");

        // Set up player 2 - TODO: External player, should maybe be handled in a different way?
        player2 = EntityFactory.getInstance().createPlayer("player2");

        // Assign to players array
        players[0] = player1;
        players[1] = player2;
        currentPlayerIndex = 0;

        // To assign ball type to when the first ball drops
        firstBall = true;
    }

    @Override
    public void ballIntoHole(BallType ballType) {

        switch (ballType) {
            case WHITE:
                System.out.println("Next player! " +
                        getActivePlayer().getComponent(PlayerComponent.class).playerName +
                        " shot WHITE ball into hole.");
                changePlayer();
                System.out.println(getActivePlayer().getComponent(PlayerComponent.class).playerName + "'s turn.");
                break;

            case BLACK:
                System.out.println(getActivePlayer().getComponent(PlayerComponent.class).playerName +
                        " lost! Shot BLACK ball into hole.");
                break;

            default:
                // Set ball type on players if it is first ball to fall down
                if (firstBall) {
                    getActivePlayer().getComponent(PlayerComponent.class).ballType = ballType;
                    getInactivePlayer().getComponent(PlayerComponent.class).ballType =
                            ballType == BallType.STRIPED ? BallType.SOLID : BallType.STRIPED;

                    // Only set once
                    firstBall = false;
                }

                if (getActivePlayer().getComponent(PlayerComponent.class).ballType.equals(ballType))
                    getActivePlayer().getComponent(ScoreComponent.class).balls++;

                // TODO: Should the other player get points if one shoots down wrong ball?
                else
                    changePlayer();

                System.out.println(
                        getActivePlayer().getComponent(PlayerComponent.class).playerName
                                + " shot ball " + ballType + " into hole.");
        }

        System.out.println(
                "Current player: " + getActivePlayer().getComponent(PlayerComponent.class).playerName + "\n" +
                        "Current score\n" +
                        player1.getComponent(PlayerComponent.class).playerName + "\t" +
                        player1.getComponent(PlayerComponent.class).ballType + "\t" +
                        "Score: " + player1.getComponent(ScoreComponent.class).balls + "\n" +
                        player2.getComponent(PlayerComponent.class).playerName + "\t" +
                        player2.getComponent(PlayerComponent.class).ballType + "\t" +
                        "Score: " + player2.getComponent(ScoreComponent.class).balls + "\n"
        );
    }

    // Should maybe be public to be able to show on screen
    private Entity getActivePlayer() {
        return players[currentPlayerIndex];
    }

    private int getNextIndex() {
        if (currentPlayerIndex == 0) return 1;
        return 0;
    }

    private Entity getInactivePlayer() {
        return players[getNextIndex()];
    }

    private void changePlayer() {
        currentPlayerIndex = getNextIndex();
    }

    @Override
    public Entity[] getPlayers() {
        Entity[] players = new Entity[2];
        players[0] = player1;
        players[1] = player2;
        return players;
    }

    @Override
    public int getNumberOfShots() {
        return -1;
    }

    @Override
    public void incNumberOfShots() {}
}
