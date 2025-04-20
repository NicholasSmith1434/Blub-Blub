package com.blub.Screens;


import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.blub.Blub_Blub;


public class PauseMenuScreen implements Screen {
    private final Blub_Blub parent;
    private final Stage stage;


    public PauseMenuScreen(Blub_Blub blub_blub) {
        parent = blub_blub;

        stage = new Stage(new ScreenViewport()); // a new stage is created 
        Gdx.input.setInputProcessor(stage); 


    }
    @Override
    public void show() {
        Table table = new Table(); // a new table is created
        table.setSize(2000,1000); // the table size is set
        table.setFillParent(true); // used to size the root table to the stage
        table.setDebug(true); // provides lines to be able to see the alignment from the table, is not necessary just used for debugging 
        stage.addActor(table); // adds the table as an actor, who will be acting in the stage

        Skin skin = new Skin(Gdx.files.internal("Skin/glassy-ui.json")); // creates a new Skin which accepts a json file. The json file contains buttons that could be used for the UI

        TextButton exit = new TextButton("EXIT", skin); //creates text buttons using the skin from the json file, and and writes text over button, in this case it writes EXIT
        TextButton menu = new TextButton("MENU", skin);
        TextButton save = new TextButton("SAVE", skin);
        TextButton resume = new TextButton("RESUME", skin);

        table.add(resume).width(980); // adds the textbutton to the table and sets the size of the resume textbutton
        table.row().pad(11,0,11,0); // adds a space under resume textbutton, the size of the pad is determined 
        table.add(save).fillX().uniformX();  // adds save textbutton and fills the table with the button in the x direction 
        table.row(); 
        table.add(menu).fillX().uniformX();
        table.row().pad(11,0,11,0);
        table.add(exit).fillX().uniformX();


        exit.addListener(new ChangeListener() { // ChangeListener is used to detect that a change will be made when the textbutton exit is pressed
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit(); // when the button is clicked, the user exits the app
            }
        });

        resume.addListener(new ChangeListener() { // ChangeListener is used to detect that a change will be made when the textbutton resume is pressed
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Blub_Blub.PLAY); // when the button is clicked, the screen changes
            }
        });

        save.addListener(new ChangeListener() { // ChangeListener is used to detect that a change will be made when the textbutton save is pressed
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Blub_Blub.SAVE); // when the button is clicked, the screen changes
            }
        });
        menu.addListener(new ChangeListener() { // ChangeListener is used to detect that a change will be made when the textbutton menu is pressed
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Blub_Blub.MENU); // when the button is clicked, the screen changes
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9F, 0.9F, 0.9F, 1); // determines background color of the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // clears the color used in the previous screen

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // helps with the timing of the actors in the stage in this case the table
        stage.draw(); // used to call the table to the stage (stage could be seen to be the same as a screen
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // updates the size of the stage
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


