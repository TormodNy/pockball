package com.pockball.pockball.ecs.components;

import com.badlogic.ashley.core.Component;
import com.pockball.pockball.ecs.types.BallType;

public class PlayerComponent implements Component {
    public String playerName;
    public BallType ballType;
}
