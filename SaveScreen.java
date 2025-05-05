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
        Table table = new Table(); // uses Table from WidgetGroup in LibGDX to create a table layout in the page
        table.setSize(2000,1000); // sets the size of the table
        table.setFillParent(true); // used to size the root table to the stage
        table.setDebug(true); // provides lines to be able to see the alignment from the table, is not necessary just used for debugging
        stage.addActor(table); // adds the table to the stage

        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json")); // creates a new Skin which accepts a json file, which contains different buttons and widgets

        TextButton save1 = new TextButton("SAVE #1", skin);  //creates text buttons using the skin from the json file, and and writes text over button
        TextButton save2 = new TextButton("SAVE #2", skin);
        TextButton save3 = new TextButton("SAVE #3", skin);
        TextButton newSave = new TextButton("NEW SAVE", skin);


        table.add(save1).width(980); // adds the textbutton to the table and sets the size of the save1 textbutton
        table.row().pad(11,0,11,0); // adds a space under the save1 button, the size of the pad is determined
        table.add(save2).fillX().uniformX();  // adds save2 textbutton and fills the table in the x direction
        table.row();   // a new row is created
        table.add(save3).fillX().uniformX();
        table.row().pad(11,0,11,0);
        table.add(newSave).fillX().uniformX();

        save1.addListener(new ChangeListener() { // ChangeListener is used to detect that a change will be made when the textbutton save1 is pressed
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                parent.changeScreen(Blub_Blub.PET); // when the button is clicked, the screen changes
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9F, 0.9F, 0.9F, 1); // determines background color of the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);  // clears the color used in the previous screen

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // helps with the timing of the actors in the stage in this case the table
        stage.draw();  // used to call the table to the stage (stage could be seen to be the same as a screen
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);  // updates the size of the stage

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
        stage.dispose(); // gets rid of the state/screen
    }
}
