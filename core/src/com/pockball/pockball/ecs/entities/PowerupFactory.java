package com.pockball.pockball.ecs.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.DirectionComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.PowerupComponent;
import com.pockball.pockball.ecs.components.SizeComponent;
import com.pockball.pockball.ecs.components.SpriteComponent;

public class PowerupFactory {
    private static PowerupFactory powerupFactory;

    private PowerupFactory() {}

    public static PowerupFactory getInstance() {
        if (powerupFactory == null)
            powerupFactory = new PowerupFactory();
        return powerupFactory;
    }

    public Entity createPowerup(int powerupID) {
        SpriteComponent sprite = Engine.getInstance().createComponent(SpriteComponent.class);
        PositionComponent position = Engine.getInstance().createComponent(PositionComponent.class);
        SizeComponent size = Engine.getInstance().createComponent(SizeComponent.class);
        DirectionComponent direction = Engine.getInstance().createComponent(DirectionComponent.class);
        PowerupComponent powerupComponent = Engine.getInstance().createComponent(PowerupComponent.class);

        size.width = 3;
        size.height = 5.4f;

        position.position.set(12.5f - size.width / 2, 7.5f - size.height / 2);

        sprite.sprite = new Sprite(new Texture("powerups/earthquake.png"));
        sprite.visible = false;
        sprite.layer = 4;

        powerupComponent.powerupID = powerupID;

        Entity powerup = Engine.getInstance().createEntity();
        powerup.add(sprite);
        powerup.add(position);
        powerup.add(size);
        powerup.add(direction);
        powerup.add(powerupComponent);
        return powerup;
    }
}
