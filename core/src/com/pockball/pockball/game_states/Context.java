package com.pockball.pockball.game_states;

public class Context {

    // Singleton
    private static Context context;
    private State state;

    public Context() {
        // Standard value
        state = null;
    }

    public static Context getInstance() {
        if (context == null) {
            context = new Context();
        }
        return context;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
