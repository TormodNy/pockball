package com.pockball.pockball.db_models;

/**
 * Cannot be abstract due to deserializing
 */
public class EventModel {
    public enum Type {
        SHOT,
        PLACE_BALL
    }
    public Type type;
}
