package com.pockball.pockball.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

public class PhysicsBodyComponent implements Component {
    public BodyDef bodyDef;
}
