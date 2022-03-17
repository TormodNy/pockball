package com.pockball.pockball.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.pockball.pockball.ecs.types.BallType;

public class BallComponent implements Component {
    public BallType type = BallType.SOLID;
    public final float radius = 0.4f;
    public Vector2 dir;
    public Vector2 power;
}
