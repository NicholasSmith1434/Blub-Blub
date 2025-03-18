package com.blub;

import com.badlogic.gdx.Game;
import com.blub.Screens.MainMenuScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    Blub_Blub game;
    @Override
    public void create() {
        setScreen(new MainMenuScreen(this.game));
    }
}
