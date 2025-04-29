package com.blub.Screens;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.blub.Blub_Blub;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


/** First screen of the application. Displayed after the application is created. */
public class PauseMenuScreen implements Screen {
    private final Blub_Blub parent;
    private final Stage stage;
    private int previousScreen; //Store the previous screen value


    public PauseMenuScreen(Blub_Blub blub_blub, int previousScreen) {
        parent = blub_blub;
        this.previousScreen = previousScreen;

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

        TextButton exit = new TextButton("EXIT", skin);
        TextButton menu = new TextButton("MENU", skin);
        TextButton save = new TextButton("SAVE", skin);
        TextButton resume = new TextButton("RESUME", skin);
        TextButton soundPreference = new TextButton("SOUND PREFERENCE", skin);


        table.add(resume).width(980);
        table.row().pad(11,0,11,0);
        table.add(save).fillX().uniformX();
        table.row();
        table.add(menu).fillX().uniformX();
        table.row().pad(11,0,11,0);
        table.add(soundPreference).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();


        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(previousScreen);
            }
        });

        save.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Blub_Blub.SAVE);
            }
        });
        menu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Blub_Blub.MENU);
            }
        });
        soundPreference.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Blub_Blub.SOUND);
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
