package com.pockball.pockball.ecs.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.BallComponent;
import com.pockball.pockball.ecs.components.CueComponent;
import com.pockball.pockball.ecs.components.DirectionComponent;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.SizeComponent;
import com.pockball.pockball.ecs.components.SpriteComponent;
import com.pockball.pockball.ecs.types.BallType;

public class CueFactory {
    private static CueFactory cueFactory;

    private CueFactory() {}

    public static CueFactory getInstance() {
        if (cueFactory == null)
            cueFactory = new CueFactory();
        return cueFactory;
    }

    public Entity createCue(Entity ball) {
        PositionComponent position = Engine.getInstance().createComponent(PositionComponent.class);
        DirectionComponent direction = Engine.getInstance().createComponent(DirectionComponent.class);
        SizeComponent size = Engine.getInstance().createComponent(SizeComponent.class);
        CueComponent cue = Engine.getInstance().createComponent(CueComponent.class);
        SpriteComponent sprite = Engine.getInstance().createComponent(SpriteComponent.class);

        size.width = 9;
        size.height = 1;

        Vector2 ballPos = ball.getComponent(PositionComponent.class).position;
        BallComponent ballComp = ball.getComponent(BallComponent.class);
        position.position.set(ballPos.x - size.width - ballComp.radius, ballPos.y);

        sprite.sprite = new Sprite(new Texture("cue.png"));
        sprite.sprite.setOrigin(size.width + 1, size.height / 2);
        sprite.sprite.setPosition(position.position.x, position.position.y);

        cue.ball = ball;

        Entity newCue = Engine.getInstance().createEntity();
        newCue.add(position);
        newCue.add(direction);
        newCue.add(size);
        newCue.add(sprite);
        newCue.add(cue);

        return newCue;
    }
}
