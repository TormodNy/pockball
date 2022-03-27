package com.pockball.pockball.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.pockball.pockball.assets.SoundController;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.HoleComponent;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PlaceEntityComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.BallComponent;
import com.pockball.pockball.ecs.components.SpriteComponent;
import com.pockball.pockball.ecs.entities.EntityFactory;
import com.pockball.pockball.ecs.types.BallType;
import com.pockball.pockball.game_states.Context;


public class WorldContactListener implements ContactListener {

    private final ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;
    private final ComponentMapper<PlaceEntityComponent> placeEntityMapper;
    private final ComponentMapper<SpriteComponent> spriteMapper;

    private final SoundController soundController;

    public WorldContactListener() {
        soundController = SoundController.getInstance();

        physicsBodyMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);
        placeEntityMapper = ComponentMapper.getFor(PlaceEntityComponent.class);
        spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        String typeA = (String) fixtureA.getUserData();
        String typeB = (String) fixtureB.getUserData();

        if ((typeA.equals("wall") && typeB.equals("ball"))
                || (typeA.equals("ball") && typeB.equals("wall"))) {

            if (typeA.equals("ball")) {
                soundController.playSound("wallHit", fixtureA.getBody().getLinearVelocity().len() / 100);
            } else {
                soundController.playSound("wallHit", fixtureB.getBody().getLinearVelocity().len() / 100);
            }

        }

        if (typeA.equals("ball") && typeB.equals("ball")) {
            soundController.playSound("ballHit", fixtureA.getBody().getLinearVelocity().sub(fixtureB.getBody().getLinearVelocity()).len() / 100);
        }

        if ((typeA.equals("hole") && typeB.equals("ball"))
                || (typeA.equals("ball") && typeB.equals("hole"))) {
            soundController.playSound("holeHit", 0.25f);

            Entity ball;
            if (fixtureB.isSensor()) { // fixture B is the hole
                ball = (Entity) fixtureA.getBody().getUserData();
                handleBallInHole(ball);
            } else if (fixtureA.isSensor()) { // fixture A is the hole
                ball = (Entity) fixtureB.getBody().getUserData();
                handleBallInHole(ball);
            }
        }
    }

    private void handleBallInHole(Entity ball) {
        try {
            System.out.println("Fuggedaboutit");
            BallType ballType = ball.getComponent(BallComponent.class).type;

            // Fire state change
            Context.getInstance().getState().ballIntoHole(ballType);

            switch (ballType) {
                case WHITE:
                    // Handle white ball falling into hole
                    PlaceEntityComponent placeEntity = placeEntityMapper.get(ball);
                    SpriteComponent sprite = spriteMapper.get(ball);
                    PhysicsBodyComponent physics = physicsBodyMapper.get(ball);

                    physics.body.setLinearVelocity(0, 0);
                    physics.body.setAngularVelocity(0);
                    sprite.sprite.setAlpha(-1);

                    placeEntity.placeable = true;
                    break;

                default:
                    Engine.getInstance().removeEntity(ball);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
