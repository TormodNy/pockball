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
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.HoleComponent;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PositionComponent;

public class HoleListenerSystem extends IteratingSystem implements ContactListener {

    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;
    private final ComponentMapper<HoleComponent> holeMapper;

    public HoleListenerSystem() {
        super(Family.all(HoleComponent.class).get());

        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        physicsBodyMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);
        holeMapper = ComponentMapper.getFor(HoleComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureB().isSensor()) { // fixture B er hullet
            Fixture ballFixture = contact.getFixtureA();
            System.out.println("contact");

            Engine.getInstance().removeEntity((Entity) ballFixture.getBody().getUserData());
        }
        if (contact.getFixtureA().isSensor()) { // fixture A er hullet
            Fixture ballFixture = contact.getFixtureB();
            System.out.println("contact");

            Engine.getInstance().removeEntity((Entity) ballFixture.getBody().getUserData());
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
