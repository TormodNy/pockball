package com.pockball.pockball.ecs.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.CueComponent;
import com.pockball.pockball.ecs.components.DirectionComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.SizeComponent;
import com.pockball.pockball.ecs.components.SpriteComponent;
import com.pockball.pockball.ecs.types.CueType;

public class CueFactory {
    private static CueFactory cueFactory;

    private CueFactory() {}

    public static CueFactory getInstance() {
        if (cueFactory == null)
            cueFactory = new CueFactory();
        return cueFactory;
    }

    public Entity createCue(Entity ball) {
        return createCueOrLine(ball, CueType.CUE);
    }

    public Entity createDottedLine(Entity ball) {
        return createCueOrLine(ball, CueType.LINE);
    }

    private Entity createCueOrLine(Entity ball, CueType type) {
        PositionComponent position = Engine.getInstance().createComponent(PositionComponent.class);
        DirectionComponent direction = Engine.getInstance().createComponent(DirectionComponent.class);
        SizeComponent size = Engine.getInstance().createComponent(SizeComponent.class);
        CueComponent cue = Engine.getInstance().createComponent(CueComponent.class);
        SpriteComponent sprite = Engine.getInstance().createComponent(SpriteComponent.class);

        position.position.set(100, 100);

        size.height = 1;
        if (type.equals(CueType.CUE)) {
            size.width = 9;

            sprite.sprite = new Sprite(new Texture("cue.png"));
            sprite.layer = 2;
        } else if (type.equals(CueType.LINE)) {
            size.width = 25;

            sprite.sprite = new Sprite(new Texture("dotted_line.png"));
            sprite.layer = 0;
        }
        sprite.sprite.setOrigin(size.width, size.height / 2);
        sprite.sprite.setPosition(position.position.x, position.position.y);

        cue.ball = ball;
        cue.cueType = type;

        Entity newCue = Engine.getInstance().createEntity();
        newCue.add(position);
        newCue.add(direction);
        newCue.add(size);
        newCue.add(sprite);
        newCue.add(cue);

        return newCue;
    }
}
