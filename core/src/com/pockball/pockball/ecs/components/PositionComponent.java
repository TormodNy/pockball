package com.pockball.pockball.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {
    public final Vector2 position = new Vector2();
}
