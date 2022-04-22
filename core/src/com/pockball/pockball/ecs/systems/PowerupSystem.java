package com.pockball.pockball.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.pockball.pockball.PockBall;
import com.pockball.pockball.ecs.Engine;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PlaceEntityComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.PowerupComponent;
import com.pockball.pockball.ecs.components.SizeComponent;
import com.pockball.pockball.ecs.components.SpriteComponent;
import com.pockball.pockball.game_modes.GameModeContext;
import com.pockball.pockball.game_modes.GameMode;
import com.pockball.pockball.screens.GameController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PowerupSystem extends IteratingSystem {
    private final ComponentMapper<PowerupComponent> powerupMapper;
    private final ComponentMapper<SpriteComponent> spriteMapper;
    private final ComponentMapper<SizeComponent> sizeMapper;
    private final ComponentMapper<PositionComponent> positionMapper;
    private final ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;

    private final List<Entity> puInHand;

    public PowerupSystem() {
        super(Family.all(PowerupComponent.class, SpriteComponent.class, SizeComponent.class, PositionComponent.class).get());

        puInHand = new ArrayList<>();

        powerupMapper = ComponentMapper.getFor(PowerupComponent.class);
        spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
        sizeMapper = ComponentMapper.getFor(SizeComponent.class);
        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        physicsBodyMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);
    }

    @Override
    public void processEntity(Entity entity, float deltaTime) {
        PowerupComponent powerup = powerupMapper.get(entity);
        SpriteComponent sprite = spriteMapper.get(entity);
        SizeComponent size = sizeMapper.get(entity);
        PositionComponent position = positionMapper.get(entity);

        int index = puInHand.indexOf(entity);
        if (index == -1) {
            if (puInHand.size() > 2) {
                Engine.getInstance().removeEntity(entity);
            } else {
                puInHand.add(entity);
            }
        }

        position.position.set(12.5f - size.width / 2 + (index - 1) * (size.width + 1), 7.5f - size.height / 2);
        sprite.visible = GameController.currentController.getShowPowerups();

        // Checks if it should be possible to use a powerup
        boolean idle = GameModeContext.getInstance().getState().getIdle();
        boolean hasNotAimed = !GameModeContext.getInstance().getState().hasAimed();
        boolean canApplyPowerup = Gdx.input.justTouched() && sprite.visible && idle && hasNotAimed;

        //apply powerup
        if (canApplyPowerup) {

            //reset aim
            GameMode gameMode = GameModeContext.getInstance().getState();
            gameMode.setHasAimed(false);
            gameMode.setIdle(true);

            Vector3 input = PockBall.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Vector2 inputInWorld = new Vector2(input.x, input.y);

            if (inputInWorld.x > position.position.x && inputInWorld.x < position.position.x + size.width) {
                if (inputInWorld.y > position.position.y && inputInWorld.y < position.position.y + size.height) {
                    switch(powerup.powerupID) {
                        case 0:
                            // Earthquake
                            for(int i = 0; i < Engine.getInstance().getBallsLength(); i++) {
                                Entity ball = Engine.getInstance().getBallAt(i);
                                if (ball != null) {
                                    Random random = new Random();
                                    float force = random.nextFloat() * 500 + 500;
                                    Vector2 direction = new Vector2(random.nextFloat() * 2 - 1, random.nextFloat() * 2 - 1).nor();
                                    physicsBodyMapper.get(ball).body.applyForceToCenter(direction.scl(force), true);
                                }
                            }
                            break;
                        case 1:
                            // Place white ball
                            Entity whiteBall = Engine.getInstance().getWhiteBallEntity();
                            whiteBall.getComponent(PlaceEntityComponent.class).placeable = true;
                            whiteBall.getComponent(SpriteComponent.class).sprite.setAlpha(0);
                            GameModeContext.getInstance().getState().setIdle(true);
                            break;
                    }

                    // Remove this powerup and hide powerup menu
                    GameController.currentController.setShowPowerups(false);
                    puInHand.remove(index);
                    Engine.getInstance().removeEntity(entity);
                }
            }
        }
    }
}
