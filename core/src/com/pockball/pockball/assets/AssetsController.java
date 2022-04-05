package com.pockball.pockball.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

public class AssetsController implements Disposable {
    private static AssetsController assetsControllerInstance = null;

    private final Skin skin;

    // Uses the skin found here: https://github.com/czyzby/gdx-skins/tree/master/shade
    private AssetsController(){
        // Putting font into skin is based on this: https://stackoverflow.com/questions/24856201/putting-freetypefont-into-libgdx-skin/39174630
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Bebas-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 100;
        BitmapFont font24 = generator.generateFont(parameter);

        this.skin = new Skin(new TextureAtlas(Gdx.files.internal("shade_skin/uiskin.atlas")));
        skin.add("default-font", font24, BitmapFont.class);
        this.skin.load(Gdx.files.internal("shade_skin/uiskin.json"));

        generator.dispose();
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

    public float getAssetScaler() {
        return (float) Gdx.graphics.getHeight() / 1080;
    }

    @Override
    public void dispose() {
        skin.dispose();
    }
}
