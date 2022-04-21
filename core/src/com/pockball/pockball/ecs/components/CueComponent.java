package com.pockball.pockball.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.pockball.pockball.ecs.types.CueType;

public class CueComponent implements Component {
    public Entity ball;
    public CueType cueType;
}
