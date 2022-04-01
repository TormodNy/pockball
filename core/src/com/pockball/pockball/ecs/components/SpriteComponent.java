package com.pockball.pockball.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class SpriteComponent implements Component {
    public Sprite sprite;
    public boolean visible = true;
    public int layer = 0;
}
