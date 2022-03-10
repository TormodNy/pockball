package com.pockball.pockball.ecs.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.SizeComponent;
import com.pockball.pockball.ecs.components.SpriteComponent;

public class BallFactory {
    private static BallFactory ballFactory;

    private BallFactory() {}

    public static BallFactory getInstance() {
        if (ballFactory == null)
            ballFactory = new BallFactory();
        return ballFactory;
    }

    public Entity createBall(float x, float y) {
        PositionComponent position = Engine.getInstance().createComponent(PositionComponent.class);
        SizeComponent size = Engine.getInstance().createComponent(SizeComponent.class);
        PhysicsBodyComponent physicsBody = Engine.getInstance().createComponent(PhysicsBodyComponent.class);
        SpriteComponent sprite = Engine.getInstance().createComponent(SpriteComponent.class);

        sprite.texture = new Texture("redBall.png");
        position.position.set(x, y);
        size.width = 32;
        size.height = 32;

        physicsBody.bodyDef = new BodyDef();
        physicsBody.bodyDef.type = BodyDef.BodyType.DynamicBody;
        physicsBody.bodyDef.position.set(x, y);

        Entity ball = Engine.getInstance().createEntity();
        ball.add(position);
        ball.add(size);
        ball.add(physicsBody);
        ball.add(sprite);

        return ball;
    }
}
