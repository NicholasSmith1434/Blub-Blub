package com.blub_blub.ver01.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.blub_blub.ver01.Blub_Blub;


public class SaveScreen implements Screen {
    private Blub_Blub parent;
    private final Stage stage;

    public SaveScreen(Blub_Blub blub_blub) {
        parent = blub_blub;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

    }
    @Override
    public void show() {
        Table table = new Table();
        table.setSize(2000,1000);
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("skin2/plain.json"));

        TextButton save1 = new TextButton("SAVE #1", skin);
        TextButton save2 = new TextButton("SAVE #2", skin);
        TextButton save3 = new TextButton("SAVE #3", skin);
        TextButton newSave = new TextButton("NEW SAVE", skin);


        table.add(save1).width(980);
        table.row().pad(11,0,11,0);
        table.add(save2).fillX().uniformX();
        table.row();
        table.add(save3).fillX().uniformX();
        table.row().pad(11,0,11,0);
        table.add(newSave).fillX().uniformX();

        save1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                parent.changeScreen(Blub_Blub.PLAY);
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9F, 0.9F, 0.9F, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
