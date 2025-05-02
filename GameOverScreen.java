package com.blub.Screens;

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
import com.blub.Blub_Blub;


public class GameOverScreen implements Screen {
    private Blub_Blub parent;
    private final Stage stage;

    public GameOverScreen(Blub_Blub blub_blub) {
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

        Skin skin = new Skin(Gdx.files.internal("Skin/glassy-ui.json"));

        TextButton gameOver = new TextButton("GAME OVER", skin);
        TextButton playButton = new TextButton("Play Again?", skin);
        TextButton leaveButton = new TextButton("RETURN", skin);


        table.add(gameOver).width(980);
        table.row().pad(11,0,11,0);
        table.add(playButton).fillX().uniformX();
        table.row();
        table.add(leaveButton).fillX().uniformX();

        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Blub_Blub.BALL);
            }
        });
        leaveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Blub_Blub.MENU);
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
