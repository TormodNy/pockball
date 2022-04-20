package com.pockball.pockball.ecs;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.pockball.pockball.PockBall;
import com.pockball.pockball.db_models.PlaceBallEvent;
import com.pockball.pockball.db_models.ShotEvent;
import com.pockball.pockball.ecs.components.BackdropComponent;
import com.pockball.pockball.ecs.components.BallComponent;
import com.pockball.pockball.ecs.components.DirectionComponent;
import com.pockball.pockball.ecs.components.PhysicsBodyComponent;
import com.pockball.pockball.ecs.components.PlaceEntityComponent;
import com.pockball.pockball.ecs.components.PositionComponent;
import com.pockball.pockball.ecs.components.SizeComponent;
import com.pockball.pockball.ecs.components.SpriteComponent;
import com.pockball.pockball.ecs.entities.EntityFactory;
import com.pockball.pockball.ecs.systems.BackdropSystem;
import com.pockball.pockball.ecs.systems.BallSystem;
import com.pockball.pockball.ecs.systems.CueSystem;
import com.pockball.pockball.ecs.systems.PhysicsSystem;
import com.pockball.pockball.ecs.systems.PlaceEntitySystem;
import com.pockball.pockball.ecs.systems.PlayerSystem;
import com.pockball.pockball.ecs.systems.PowerupSystem;
import com.pockball.pockball.ecs.systems.RenderSystem;
import com.pockball.pockball.ecs.systems.WorldContactListener;
import com.pockball.pockball.game_states.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Engine extends com.badlogic.ashley.core.Engine {
    private static Engine engineInstance;

    private final EntityFactory entityFactory;

    private World world;

    private Array<Entity> balls = new Array<Entity>();

    private List<Body> bodiesToRemove = new ArrayList<>();

    private final Vector2[][] ballLocations = {
            {
                    new Vector2(3, 6.875f),
                    new Vector2(19.4f, 7.675f),
                    new Vector2(20.8f, 6.075f),
                    new Vector2(20.1f, 7.275f),
                    new Vector2(20.8f, 7.675f),
                    new Vector2(20.8f, 8.475f),
                    new Vector2(20.1f, 5.675f),
                    new Vector2(18.7f, 6.475f),
                    new Vector2(19.4f, 6.875f),
                    new Vector2(18, 6.875f),
                    new Vector2(20.1f, 6.475f),
                    new Vector2(20.8f, 5.275f),
                    new Vector2(18.7f, 7.275f),
                    new Vector2(20.8f, 6.875f),
                    new Vector2(20.1f, 8.075f),
                    new Vector2(19.4f, 6.075f),
            },
            // Add more positions for different game modes.
    };
    private Entity whiteBallEntity;
    private final ComponentMapper<PhysicsBodyComponent> physicsBodyMapper;
    private final ComponentMapper<PlaceEntityComponent> placeEntityMapper;
    private final ComponentMapper<BallComponent> ballMapper;
    private final ComponentMapper<SpriteComponent> spriteMapper;


    private final Vector2[] holeLocations = {
            // Upper left
            new Vector2(0.5f, 12.5f),
            // Upper middle
            new Vector2(12.1f, 12.8f),
            // Upper right
            new Vector2(23.7f, 12.5f),
            // Lower left
            new Vector2(0.5f, 0.45f),
            // Lower  middle
            new Vector2(12.1f, 0.15f),
            // Lower right
            new Vector2(23.7f, 0.45f),
    };

    private Engine() {
        entityFactory = EntityFactory.getInstance();
        physicsBodyMapper = ComponentMapper.getFor(PhysicsBodyComponent.class);
        placeEntityMapper = ComponentMapper.getFor(PlaceEntityComponent.class);
        ballMapper = ComponentMapper.getFor(BallComponent.class);
        spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
    }

    public static Engine getInstance() {
        if (engineInstance == null)
            engineInstance = new Engine();
        return engineInstance;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        float timeStep = 1f / 60f;
        world.step(timeStep, 8, 3);

        for (Body body : bodiesToRemove) {
            world.destroyBody(body);
        }
        bodiesToRemove.clear();
    }

    public void initializeEngine(int gameMode) {
        // Remove all entities
        engineInstance.removeAllEntities();
        balls.clear();

        createWorld();
        createTable();
        createHoles();

        engineInstance.addSystem(new RenderSystem());
        engineInstance.addSystem(new PhysicsSystem());
        engineInstance.addSystem(new BallSystem());
        engineInstance.addSystem(new CueSystem());
        engineInstance.addSystem(new PlaceEntitySystem());
        engineInstance.addSystem(new PlayerSystem());
        engineInstance.addSystem(new PowerupSystem());
        engineInstance.addSystem(new BackdropSystem());

        // Add players from state context
        Entity[] players = Context.getInstance().getState().getPlayers();
        for (Entity player : players) {
            engineInstance.addEntity(player);
        }

        // Powerup backdrop
        createBackdrop();

        // Powerups
        for (int i = 0; i < 0; i++) {
            engineInstance.addEntity(entityFactory.createPowerup(0));
        }
        // Place balls on table
        for (int i = 0; i <= 15; i++) {
            balls.add(entityFactory.createBall(ballLocations[gameMode][i].x, ballLocations[gameMode][i].y, i));
            if (i == 0) {
                whiteBallEntity = balls.get(i);
                engineInstance.addEntity(entityFactory.createCue(whiteBallEntity));
                engineInstance.addEntity(entityFactory.createDottedLine(whiteBallEntity));
            }
            engineInstance.addEntity(balls.get(i));
        }

    }

    private void createWorld() {
        // Create world
        Vector2 gravity = new Vector2(0, 0);
        world = new World(gravity, false);
        world.setContactListener(new WorldContactListener());
    }

    private void createTable() {
        Entity table = engineInstance.createEntity();

        PositionComponent position = engineInstance.createComponent(PositionComponent.class);
        SpriteComponent sprite = engineInstance.createComponent(SpriteComponent.class);
        SizeComponent size = engineInstance.createComponent(SizeComponent.class);
        DirectionComponent direction = engineInstance.createComponent(DirectionComponent.class);

        sprite.sprite = new Sprite(new Texture("pooltable.png"));

        // Set position and size
        position.position.x = 0;
        position.position.y = 0;

        int scl = 80;
        size.width = sprite.sprite.getWidth() / scl;
        size.height = sprite.sprite.getHeight() / scl; // For reference: Height is 13.75

        table.add(position);
        table.add(sprite);
        table.add(size);
        table.add(direction);
        engineInstance.addEntity(table);

        createTableWalls(table);
    }

    private void createTableWalls(Entity table) {
        SizeComponent size = table.getComponent(SizeComponent.class);
        PositionComponent position = table.getComponent(PositionComponent.class);
        SpriteComponent sprite = table.getComponent(SpriteComponent.class);

        // Create walls
        BodyDef wallsDef = new BodyDef();
        Vector2 startPosition = new Vector2(-0.4f + position.position.x, -0.4f + position.position.y);
        wallsDef.position.set(startPosition);

        // Scale vector
        float x = 2f / sprite.sprite.getWidth() * size.width;
        float y = 2f / sprite.sprite.getHeight() * size.height;
        Vector2 scl = new Vector2(x, y);

        Vector2[] vs = new Vector2[40];
        // Bottom left corner
        vs[0] = new Vector2(84, 50).scl(scl);
        vs[1] = new Vector2(66, 30).scl(scl);
        vs[2] = new Vector2(66, 0).scl(scl);
        vs[3] = new Vector2(0, 0);
        vs[4] = new Vector2(0, 64).scl(scl);
        vs[5] = new Vector2(30, 64).scl(scl);
        vs[6] = new Vector2(50, 84).scl(scl);
        // Top left corner
        vs[7] = new Vector2(50, 466).scl(scl);
        vs[8] = new Vector2(30, 486).scl(scl);
        vs[9] = new Vector2(0, 486).scl(scl);
        vs[10] = new Vector2(0, 550).scl(scl);
        vs[11] = new Vector2(66, 550).scl(scl);
        vs[12] = new Vector2(66, 520).scl(scl);
        vs[13] = new Vector2(84, 500).scl(scl);
        // Top mid
        vs[14] = new Vector2(470, 500).scl(scl);
        vs[15] = new Vector2(476, 520).scl(scl);
        vs[16] = new Vector2(476, 550).scl(scl);
        vs[17] = new Vector2(524, 550).scl(scl);
        vs[18] = new Vector2(524, 520).scl(scl);
        vs[19] = new Vector2(530, 500).scl(scl);
        // Top right corner
        vs[20] = new Vector2(916, 500).scl(scl);
        vs[21] = new Vector2(936, 520).scl(scl);
        vs[22] = new Vector2(936, 550).scl(scl);
        vs[23] = new Vector2(1000, 550).scl(scl);
        vs[24] = new Vector2(1000, 486).scl(scl);
        vs[25] = new Vector2(970, 486).scl(scl);
        vs[26] = new Vector2(950, 466).scl(scl);
        // Bottom right corner
        vs[27] = new Vector2(950, 84).scl(scl);
        vs[28] = new Vector2(970, 64).scl(scl);
        vs[29] = new Vector2(1000, 64).scl(scl);
        vs[30] = new Vector2(1000, 0).scl(scl);
        vs[31] = new Vector2(936, 0).scl(scl);
        vs[32] = new Vector2(936, 30).scl(scl);
        vs[33] = new Vector2(916, 50).scl(scl);
        // Top mid
        vs[34] = new Vector2(530, 50).scl(scl);
        vs[35] = new Vector2(524, 30).scl(scl);
        vs[36] = new Vector2(524, 0).scl(scl);
        vs[37] = new Vector2(476, 0).scl(scl);
        vs[38] = new Vector2(476, 30).scl(scl);
        vs[39] = new Vector2(470, 50).scl(scl);

        ChainShape chain = new ChainShape();
        chain.createLoop(vs);

        Body walls = world.createBody(wallsDef);
        Fixture fixture = walls.createFixture(chain, 0);
        fixture.setUserData("wall");
    }

    private void createHoles() {
        for (int i = 0; i < holeLocations.length; i++) {
            Entity hole = entityFactory.createHole(holeLocations[i].x, holeLocations[i].y, i);
            engineInstance.addEntity(hole);
        }
    }

    public void shootBallWithForce(
            Vector2 force,
            boolean changeState
    ) {
        if (changeState) Context.getInstance().getState().addEvent(new ShotEvent(force));

        PhysicsBodyComponent physics = physicsBodyMapper.get(whiteBallEntity);// TODO: Becomes null
        physics.body.applyForceToCenter(force, true);
    }

    public void placeWhiteBall(Vector2 position, boolean changeState) {
        if (changeState) Context.getInstance().getState().addEvent(new PlaceBallEvent(position));
        PhysicsBodyComponent physicsBody = physicsBodyMapper.get(whiteBallEntity);
        PlaceEntityComponent placeEntity = placeEntityMapper.get(whiteBallEntity);
        BallComponent ball = ballMapper.get(whiteBallEntity);
        SpriteComponent sprite = spriteMapper.get(whiteBallEntity);

        placeEntity.placeable = false;
        physicsBody.body.setTransform(position.x - ball.radius, position.y - ball.radius, 0);
        sprite.sprite.setAlpha(1);
    }

    public Entity getWhiteBallEntity() {
        return whiteBallEntity;
    }

    private void createBackdrop() {
        PositionComponent position = engineInstance.createComponent(PositionComponent.class);
        SpriteComponent sprite = engineInstance.createComponent(SpriteComponent.class);
        SizeComponent size = engineInstance.createComponent(SizeComponent.class);
        DirectionComponent direction = engineInstance.createComponent(DirectionComponent.class);
        BackdropComponent backdropComponent = engineInstance.createComponent(BackdropComponent.class);

        sprite.sprite = new Sprite(new Texture("transparentBackdrop.png"));
        sprite.visible = false;
        sprite.layer = 3;
        size.width = PockBall.WIDTH;
        size.height = PockBall.HEIGHT;

        Entity backdrop = createEntity();
        backdrop.add(position);
        backdrop.add(sprite);
        backdrop.add(size);
        backdrop.add(direction);
        backdrop.add(backdropComponent);
        addEntity(backdrop);
    }

    public void givePowerup() {
        Random random = new Random();
        addEntity(entityFactory.createPowerup(random.nextInt(1)));
    }

    public Entity getBallAt(int i) {
        return balls.get(i);
    }

    public int getBallsLength() {
        return balls.size;
    }

    public void removeBall(Entity ball) {
        bodiesToRemove.add(ball.getComponent(PhysicsBodyComponent.class).body);
        balls.removeValue(ball, true);
        removeEntity(ball);
    }

    public boolean isBall(Entity entity) {
        return balls.contains(entity, true);
    }
}
