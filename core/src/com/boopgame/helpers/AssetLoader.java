package com.boopgame.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

/**
 * Created by karl on 11.05.2016.
 */
public class AssetLoader {
    private static Skin skin;

    private static TextureAtlas atlas;
    public static BitmapFont scoreFont;
    private static BitmapFont buttonFont;
    private static BitmapFont labelFont;

    private static TextField textfield;
    private static BitmapFont arialMedium;

    public static void load() {
        atlas = new TextureAtlas(Gdx.files.internal("textures/boopAtlas.atlas"));
        skin = new Skin();
        createFonts();
        skin.add("scoreFont", scoreFont, BitmapFont.class);
        skin.add("buttonFont", buttonFont, BitmapFont.class);
        skin.add("labelFont", labelFont, BitmapFont.class);
        skin.add("arialMedium", arialMedium, BitmapFont.class);
        skin.addRegions(atlas);
        skin.load(Gdx.files.internal("textures/defaultSkin.json"));
        LoadTextField();
    }

    public static void dispose() {

    }

    private static void createFonts() {
        generateBigFonts();
        generateRegularFonts();

    }

    private static void generateRegularFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.Nearest;
        parameter.flip = false;
        arialMedium = generator.generateFont(parameter);
        generator.dispose();
    }

    private static void generateBigFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/triple_dot_digital-7.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.Nearest;
        parameter.flip = false;
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

    private static void LoadTextField() {
        textfield = new TextField("", skin);
        textfield.setMessageText("Insert your name!");
        textfield.setWidth(600f);
        textfield.setAlignment(Align.center);
    }

    public static Skin getSkin() {
        return skin;
    }

    public static TextField getTextfield() {
        return textfield;
    }
}
