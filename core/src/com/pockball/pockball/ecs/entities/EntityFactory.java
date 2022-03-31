package com.pockball.pockball.ecs.entities;

import com.badlogic.ashley.core.Entity;

public class EntityFactory {
    private static EntityFactory entityFactory;
    private BallFactory ballFactory;
    private CueFactory cueFactory;
    private HoleFactory holeFactory;
    private PlayerFactory playerFactory;
    private PowerupFactory powerupFactory;

    private EntityFactory() {
        ballFactory = BallFactory.getInstance();
        holeFactory = HoleFactory.getInstance();
        cueFactory = CueFactory.getInstance();
        playerFactory = PlayerFactory.getInstance();
        powerupFactory = PowerupFactory.getInstance();
    }

    public static EntityFactory getInstance() {
        if (entityFactory == null)
            entityFactory = new EntityFactory();
        return entityFactory;
    }

    public Entity createBall(float x, float y, int ballID) {
        return ballFactory.createBall(x, y, ballID);
    }

    public Entity createCue(Entity ball) {
        System.out.println("ENT FACTORY - createCue");
        return cueFactory.createCue(ball);
    }

    public Entity createHole(float x, float y, int holeID) {
        return holeFactory.createHole(x, y, holeID);
    }

    public Entity createPlayer(String playerName) {
        return playerFactory.createPlayer(playerName);
    }

    public Entity createPowerup(int powerupID) {
        return powerupFactory.createPowerup(powerupID);
    }
}
