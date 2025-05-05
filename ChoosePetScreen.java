package com.blub.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.blub.Blub_Blub;
import com.badlogic.gdx.math.Rectangle;


public class ChoosePetScreen implements Screen {

    Blub_Blub game;

    Texture characterSelection;
    BitmapFont choosePetFont;
    float x,y;

    public ChoosePetScreen(Blub_Blub game) {
        this.game = game;
        choosePetFont =new BitmapFont(Gdx.files.internal("fonts/levelFont.fnt"));

        characterSelection = new Texture(Gdx.files.internal("characterSelection.png"));
    }

    @Override
    public void show() {

    }

    //Loads images/Screens
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float i = (1080 - characterSelection.getWidth()) / 2;
        float j = (1080 - characterSelection.getHeight()) / 2;
        game.batch.begin();
        game.batch.draw(characterSelection, i, j);
        GlyphLayout choosepetLayout = new GlyphLayout();
        choosepetLayout.setText(choosePetFont, "Choose Your Pet!");
        float x = (1080 - choosepetLayout.width)/2;
        float y = 900;
        choosePetFont.draw(game.batch, choosepetLayout, x,y);
        if (Gdx.input.justTouched()) {
            this.dispose();
            game.setScreen(new PetScreen(game));
        }

        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {

    }
}
