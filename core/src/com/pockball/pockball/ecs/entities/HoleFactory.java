package com.pockball.pockball.ecs.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.DirectionComponent;
import com.pockball.pockball.ecs.components.HoleComponent;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.SizeComponent;

public class HoleFactory {
    private static HoleFactory holefactory;

    private HoleFactory() {}

    public static HoleFactory getInstance() {
        if (holefactory == null)
            holefactory = new HoleFactory();
        return holefactory;
    }

    // Remove comments to show the sprite of the holes if needed for testing
    public Entity createHole(float x, float y, int holeID) {
        PositionComponent position = Engine.getInstance().createComponent(PositionComponent.class);
        PhysicsBodyComponent physicsBody = Engine.getInstance().createComponent(PhysicsBodyComponent.class);
        HoleComponent hole = Engine.getInstance().createComponent(HoleComponent.class);
        SizeComponent size = Engine.getInstance().createComponent(SizeComponent.class);

        size.width = hole.radius * 2;
        size.height = hole.radius * 2;

        hole.holeID = holeID;
        position.position.set(x, y);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position.position);


        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(hole.radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        fixtureDef.shape = circleShape;

        physicsBody.body = Engine.getInstance().getWorld().createBody(bodyDef);
        Fixture fixture = physicsBody.body.createFixture(fixtureDef);
        fixture.setUserData("hole");

        Entity newHole = Engine.getInstance().createEntity();
        newHole.add(position);
        newHole.add(physicsBody);
        newHole.add(hole);
        newHole.add(size);

        physicsBody.body.setUserData(holeID);

        return newHole;
    }

}
