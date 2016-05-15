package com.boopgame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by karl on 11.05.2016.
 */
public class AssetLoader {
    private static Skin skin;

    private static TextureAtlas atlas;
    private static BitmapFont scoreFont;
    private static BitmapFont buttonFont;
    private static BitmapFont labelFont;
    public static void load() {
        atlas = new TextureAtlas(Gdx.files.internal("textures/atlas.atlas"));
        skin = new Skin();
        createFonts();
        skin.add("scoreFont", scoreFont, BitmapFont.class);
        skin.add("buttonFont", buttonFont, BitmapFont.class);
        skin.add("labelFont", labelFont, BitmapFont.class);
        skin.addRegions(atlas);
        skin.load(Gdx.files.internal("textures/defaultSkin.json"));
    }

    public static void dispose() {

    }

    private static void createFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/triple_dot_digital-7.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.Nearest;
        parameter.flip = true;
        scoreFont = generator.generateFont(parameter);
        parameter.flip = false;
        parameter.size = 20;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.CYAN;
        buttonFont = generator.generateFont(parameter);
        parameter.size = 24;
        parameter.borderWidth = 1.4f;
        labelFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public static Skin getSkin() {
        return skin;
    }
}
