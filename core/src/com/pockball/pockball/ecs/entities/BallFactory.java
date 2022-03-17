package com.pockball.pockball.ecs.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.BallComponent;
import com.pockball.pockball.ecs.components.DirectionComponent;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.SizeComponent;
import com.pockball.pockball.ecs.components.SpriteComponent;
import com.pockball.pockball.ecs.types.BallType;

public class BallFactory {
    private static BallFactory ballFactory;

    private BallFactory() {}

    public static BallFactory getInstance() {
        if (ballFactory == null)
            ballFactory = new BallFactory();
        return ballFactory;
    }

    public Entity createBall(float x, float y, int ballID) {
        PositionComponent position = Engine.getInstance().createComponent(PositionComponent.class);
        DirectionComponent direction = Engine.getInstance().createComponent(DirectionComponent.class);
        SizeComponent size = Engine.getInstance().createComponent(SizeComponent.class);
        BallComponent ball = Engine.getInstance().createComponent(BallComponent.class);
        PhysicsBodyComponent physicsBody = Engine.getInstance().createComponent(PhysicsBodyComponent.class);
        SpriteComponent sprite = Engine.getInstance().createComponent(SpriteComponent.class);

        position.position.set(x, y);
        size.width = ball.radius * 2;
        size.height = ball.radius * 2;

        if (ballID == 0) {
            ball.type = BallType.WHITE;
        } else if (ballID < 8) {
            ball.type = BallType.SOLID;
        } else if (ballID == 8) {
            ball.type = BallType.BLACK;
        } else {
            ball.type = BallType.STRIPED;
        }

        sprite.sprite = new Sprite(new Texture("balls/ball_" + ballID + ".png"));
        sprite.sprite.setOrigin(size.width / 2, size.height / 2);
        sprite.sprite.setPosition(position.position.x, position.position.y);

        // Body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.bullet = true;
        bodyDef.position.set(x, y);
        bodyDef.linearDamping = 1.5f;
        bodyDef.angularDamping = 1f;

        // Place body in world
        physicsBody.body = Engine.getInstance().getWorld().createBody(bodyDef);

        // Add collider
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(ball.radius);

        // Add physics details
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 2f;
        fixtureDef.restitution = 0.825f;
        fixtureDef.friction = 0.3f;
        physicsBody.body.createFixture(fixtureDef);

        Entity newBall = Engine.getInstance().createEntity();
        newBall.add(position);
        newBall.add(direction);
        newBall.add(size);
        newBall.add(physicsBody);
        newBall.add(sprite);
        newBall.add(ball);

        return newBall;
    }
}
