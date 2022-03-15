package com.pockball.pockball.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class AssetsController implements Disposable {
    private static AssetsController assetsControllerInstance = null;

    private Skin skin;

    private AssetsController(){
        this.skin = new Skin(Gdx.files.internal("shade_skin/uiskin.json"));
    }

    public static AssetsController getInstance() {
        if (assetsControllerInstance == null) {
            assetsControllerInstance = new AssetsController();
        }
        return assetsControllerInstance;
    }

    public Skin getSkin() {
        return skin;
    }

    @Override
    public void dispose() {
        skin.dispose();
    }
}
