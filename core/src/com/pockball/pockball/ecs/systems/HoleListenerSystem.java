package com.pockball.pockball.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
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


public class HoleListenerSystem extends IteratingSystem implements ContactListener {

    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;
    private final ComponentMapper<HoleComponent> holeMapper;
    private final ComponentMapper<PlaceEntityComponent> placeEntityMapper;
    private final ComponentMapper<SpriteComponent> spriteMapper;

    public HoleListenerSystem() {
        super(Family.all(HoleComponent.class).get());

        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        physicsBodyMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);
        holeMapper = ComponentMapper.getFor(HoleComponent.class);
        placeEntityMapper = ComponentMapper.getFor(PlaceEntityComponent.class);
        spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    @Override
    public void beginContact(Contact contact) {
        Entity ball;
        if (contact.getFixtureB().isSensor()) { // fixture B er hullet
            ball = (Entity) contact.getFixtureA().getBody().getUserData();
            handleBallInHole(ball);
        } else if (contact.getFixtureA().isSensor()) { // fixture A er hullet
            ball = (Entity) contact.getFixtureB().getBody().getUserData();
            handleBallInHole(ball);
        }
    }

    private void handleBallInHole(Entity ball) {
        try {
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
