package com.blub.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.blub.Blub_Blub;
import java.util.ArrayList;

public class HygieneScreen implements Screen {
    Blub_Blub game;

    Player player = new Player();
    float xp = player.getExperience();
    BitmapFont levelFont;
    int level = player.getLevel();
    int cleaningCount;

    private static final float PAUSE_BUTTON_WIDTH = 80;
    private static final float PAUSE_BUTTON_HEIGHT = 80;
    private static final float LEAVE_BUTTON_WIDTH = 70;
    private static final float LEAVE_BUTTON_HEIGHT = 70;

    // Define areas for clarity (using bottom-left coordinates)
    private static final float TOILET_X_MIN = 900;
    private static final float TOILET_X_MAX = 1020;
    // Original check was Y > 605 && Y < 725 (top-left origin)
    // Assuming screen height 1080:
    // Bottom = 1080 - 725 = 355
    // Top = 1080 - 605 = 475
    private static final float TOILET_Y_MIN = 355;
    private static final float TOILET_Y_MAX = 475;

    // Original check was X > 240 && X < 560 (okay)
    // Original check Y > 120 && Y < 650 (top-left origin)
    // Assuming screen height 1080:
    // Bottom = 1080 - 650 = 430
    // Top = 1080 - 120 = 960
    private static final float PET_X_MIN = 240;
    private static final float PET_X_MAX = 560;
    private static final float PET_Y_MIN = 430;
    private static final float PET_Y_MAX = 960;


    Texture progressBar;
    Texture petBath;
    Texture pauseButton, leaveButton;
    Texture soap;
    Texture bathroom;
    Texture bubbles;

    private boolean soapy = false;
    private int soapCount = 0;
    private ArrayList<Vector2> placedBubbles; // Store bubble positions

    public HygieneScreen(Blub_Blub game){
        this.game = game;
//        cleaningCount = 0;

        // It's good practice to define constants for screen dimensions if used often
        // Or get them dynamically if the window can resize (using Gdx.graphics.getWidth/Height)
        // Assuming fixed 1080x1080 based on your code

        progressBar = new Texture(Gdx.files.internal("progress-bar.png"));
        levelFont = new BitmapFont(Gdx.files.internal("fonts/levelFont.fnt"));
        bathroom = new Texture(Gdx.files.internal("Bathroom.png"));
        pauseButton = new Texture(Gdx.files.internal("PauseButton.png"));
        leaveButton = new Texture(Gdx.files.internal("Back_Arrow.png"));
        petBath = new Texture(Gdx.files.internal("AlienFellaBath.png"));
        soap = new Texture(Gdx.files.internal("soap.png"));
        bubbles = new Texture(Gdx.files.internal("Bubbles.png"));
        placedBubbles = new ArrayList<>(); // Initialize the list
    }

    @Override
    public void show() {
        // Optional: Reset state if returning to this screen
        // soapy = false;
        // soapCount = 0;
        // placedBubbles.clear();
    }

    @Override
    public void render(float v) {
        // Clear screen (important!) - Usually done before batch.begin()
        Gdx.gl.glClearColor(0, 0, 0, 1); // Black background if needed
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Get mouse position in LibGDX coordinates (origin bottom-left) once per frame
        float mouseX = Gdx.input.getX();
        float mouseY = Blub_Blub.HEIGHT - Gdx.input.getY(); // Use screen height

        // --- Input Logic --- (Process input before drawing if it affects state)

        // Check for toilet dip (on click)
        boolean overToilet = mouseX > TOILET_X_MIN && mouseX < TOILET_X_MAX &&
            mouseY > TOILET_Y_MIN && mouseY < TOILET_Y_MAX;

        if (overToilet && Gdx.input.justTouched()) { // Use justTouched()
            // Only get soapy if not already, or allow re-dipping to reset count
            soapy = true;
            soapCount = 5;
            cleaningCount = 0;
            // Consider adding a sound effect for dipping game.playDipSound();
        }


        // Check for applying soap to pet (on click)
        boolean overPet = mouseX > PET_X_MIN && mouseX < PET_X_MAX &&
            mouseY > PET_Y_MIN && mouseY < PET_Y_MAX;

        if (overPet && soapy && soapCount > 0 && Gdx.input.justTouched()) { // Use justTouched()
            // Place bubble slightly centered on cursor (adjust offsets as needed)
            float bubbleSize = 60;
            float bubbleX = mouseX - bubbleSize / 2;
            float bubbleY = mouseY - bubbleSize / 2;

            placedBubbles.add(new Vector2(bubbleX, bubbleY)); // Store position
            soapCount--;
            cleaningCount ++;
            // Consider adding a sound effect game.playBubbleSound();

            // Check if soap is used up *after* placing the last bubble
            if (soapCount <= 0) {
                soapy = false;
            }
        }


        // --- Drawing Logic ---
        game.batch.begin();
        // Background
        game.batch.draw(bathroom, 0, 0, Blub_Blub.WIDTH, Blub_Blub.HEIGHT); // Use constants/getters

        // Pet
        game.batch.draw(petBath, 120, 224); // Make sure position is correct

        // Draw placed bubbles (persistent)
        for (Vector2 bubblePos : placedBubbles) {
            game.batch.draw(bubbles, bubblePos.x, bubblePos.y, 60, 60); // Draw stored bubbles
        }

        if(cleaningCount == 5){
            player.addExperience(0.3F);// then xp goes up, when xp goes up, we update experience from player using the Player.addExperience(0.1)
         // level
            player.levelUp();
            level = player.getLevel();
            cleaningCount = 0;
        }
        xp = player.getExperience();
        game.batch.draw(progressBar, 0, 1040, (Blub_Blub.WIDTH * xp), 20);

        // Default Bubbles in toilet (visual flair)
        game.batch.draw(bubbles, 900, 355, 120, 120); // Adjust position if needed

        // --- UI Elements (Draw last to be on top) ---

        // Progress Bar
        game.batch.draw(progressBar, 0, Blub_Blub.HEIGHT - 20, (Blub_Blub.WIDTH * xp), 20); // Adjust Y

        // Level Text
        GlyphLayout levelLayout = new GlyphLayout(levelFont,"LEVEL " + level);
        levelFont.draw(game.batch, levelLayout,Blub_Blub.WIDTH/ 2 - levelLayout.width /2, Blub_Blub.HEIGHT - levelLayout.height - 30);

        // Pause Button
        float pauseButtonX = Blub_Blub.WIDTH - 100;
        float pauseButtonY = Blub_Blub.HEIGHT - 130;
        // Button hover/click logic (using mouseX, mouseY for consistency)
        if (mouseX >= pauseButtonX && mouseX < pauseButtonX + PAUSE_BUTTON_WIDTH &&
            mouseY >= pauseButtonY && mouseY < pauseButtonY + PAUSE_BUTTON_HEIGHT) {
            // Maybe tint button on hover?
            if (Gdx.input.justTouched()) { // Use justTouched for button clicks too
                this.dispose(); // Dispose resources if leaving screen permanently
                game.playClick();
                game.setScreen(new PauseMenuScreen(game, Blub_Blub.HYGIENE));
            }
        }
        game.batch.draw(pauseButton, pauseButtonX, pauseButtonY, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);


        // Back Button
        float leaveButtonX = Blub_Blub.WIDTH - 94;
        float leaveButtonY = Blub_Blub.HEIGHT - 220;
        if(mouseX >= leaveButtonX && mouseX < leaveButtonX + LEAVE_BUTTON_WIDTH &&
            mouseY >= leaveButtonY && mouseY < leaveButtonY + LEAVE_BUTTON_HEIGHT) {
            // Maybe tint button on hover?
            if (Gdx.input.justTouched()) { // Use justTouched for button clicks
                this.dispose(); // Dispose resources
                game.playClick();
                // Clear bubbles when leaving?
                // placedBubbles.clear();
                game.setScreen(new PetScreen(game));
            }
        }
        game.batch.draw(leaveButton, leaveButtonX, leaveButtonY, LEAVE_BUTTON_WIDTH, LEAVE_BUTTON_HEIGHT);

        // Soap Cursor (Draw last so it's on top of everything)
        float soapSize = 80;
        float soapDrawX = mouseX - soapSize / 2; // Center soap on cursor
        float soapDrawY = mouseY - soapSize / 2;
        game.batch.draw(soap, soapDrawX, soapDrawY, soapSize, soapSize);

        // Draw bubbles overlay on soap cursor if soapy
        if(soapy){
            // Draw smaller bubbles, maybe slightly offset?
            game.batch.draw(bubbles, soapDrawX, soapDrawY, soapSize, soapSize);
        }

        game.batch.end();
    }

    // Make sure to dispose textures and fonts to prevent memory leaks
    @Override
    public void dispose() {
        // Dispose all disposable resources (Textures, BitmapFonts, etc.)
        progressBar.dispose();
        levelFont.dispose();
        bathroom.dispose();
        pauseButton.dispose();
        leaveButton.dispose();
        petBath.dispose();
        soap.dispose();
        bubbles.dispose();
        // Optional: Clear the list if needed, though it will be garbage collected with the screen
        // placedBubbles.clear();
    }

    // Other Screen methods (resize, pause, resume, hide) - implement if needed
    @Override
    public void resize(int width, int height) {
        // Handle window resizing if necessary (update viewport, etc.)
    }

    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
}

// Make sure your Player class exists and has the methods getExperience() and getLevel()
// Make sure your Blub_Blub class has WIDTH, HEIGHT constants (or getters)
// and the playClick() method, and the HYGIENE identifier for pause screen
// Example:
// public class Blub_Blub extends Game {
//    public static final int WIDTH = 1080;
//    public static final int HEIGHT = 1080;
//    public static final int HYGIENE = 1; // Example ID
//    public SpriteBatch batch;
//    // ... other fields like sounds
//    @Override
//    public void create() { batch = new SpriteBatch(); /* ... load assets ... */ }
//    public void playClick() { /* play sound */ }
//    // ... other methods
// }

//package com.blub.Screens;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.GlyphLayout;
//import com.blub.Blub_Blub;
//
//public class HygieneScreen implements Screen {
//    Blub_Blub game;
//    Player player = new Player(); // added for leveling feature. could be changed for saving feature
//    float xp = player.getExperience(); // used for leveling feature. when 1 it means enough experience points for a level up. experience resets to 0. level plus 1
//    private static final float BUTTON_WIDTH = 80;
//    private static final float BUTTON_HEIGHT = 80;
//    BitmapFont levelFont;
//    int level = player.getLevel(); // used for leveling feature, starts at 1
//
//    //Background
//    Texture backgroundTexture;
//    //Pause Menu button
//    Texture pauseButtonActive;
//    Texture pauseButtonInactive;
//    //Throw Ball Button
//    Texture cleanPetButton;
//    //Back Button
//    Texture backButton;
//    //XP bar
//    Texture progressBar;
//
//
//
//    public HygieneScreen(Blub_Blub game) {
//        this.game = game;
//        backButton = new Texture(Gdx.files.internal("Back_Arrow.png"));
//        progressBar = new Texture("progress-bar.png");
//        cleanPetButton = new Texture(Gdx.files.internal("soap.png"));
//        levelFont = new BitmapFont(Gdx.files.internal("fonts/levelFont.fnt")); // used for level feature
//        backgroundTexture = new Texture("Bathroom.png");
//        pauseButtonActive = new Texture(Gdx.files.internal("PauseButtonActive.png"));
//        pauseButtonInactive = new Texture(Gdx.files.internal("PauseButtonInactive.png"));
//    }
//    @Override
//    public void show() {
//
//    }
//
//    @Override
//    public void render(float delta) {
//        game.batch.begin();
//
//        // background
//        game.batch.draw(backgroundTexture, 0, 0, 1080, 1080);
//
//        //Pause Button
//        float pauseButtonX = Blub_Blub.WIDTH - 100;
//        float pauseButtonY = Blub_Blub.HEIGHT - 130;
//
//        if (Gdx.input.getX() > pauseButtonX && Gdx.input.getX() < pauseButtonX + BUTTON_WIDTH &&
//            Blub_Blub.HEIGHT - Gdx.input.getY() > pauseButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
//            < pauseButtonY + BUTTON_HEIGHT) {
//
//            game.batch.draw(pauseButtonActive, pauseButtonX, pauseButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
//
//            //Pause button clicked
//            if (Gdx.input.isTouched()) {
//                this.dispose();
//                game.setScreen(new PauseMenuScreen(game, Blub_Blub.HYGIENE)); //Closes (exits)
//            }
//        } else {
//            game.batch.draw(pauseButtonInactive, pauseButtonX, pauseButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
//        }
//
//        //Back Button
//        float backButtonX = Blub_Blub.WIDTH - 1050;
//        float backButtonY = Blub_Blub.HEIGHT - 130;
//
//        if (Gdx.input.getX() > backButtonX && Gdx.input.getX() < backButtonX + BUTTON_WIDTH &&
//            Blub_Blub.HEIGHT - Gdx.input.getY() > backButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
//            < backButtonY + BUTTON_HEIGHT) {
//
//            game.batch.draw(backButton, backButtonX, backButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
//
//            //Back button clicked
//            if (Gdx.input.isTouched()) {
//                this.dispose();
//                game.setScreen(new PetScreen(game)); //Closes (exits)
//            }
//        } else {
//            game.batch.draw(backButton, backButtonX, backButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
//        }
//        // Clean Pet
//        float cleanButtonX = Blub_Blub.WIDTH - 1000;
//        float cleanButtonY = Blub_Blub.HEIGHT - 1000;
//
//        if (Gdx.input.getX() > cleanButtonX && Gdx.input.getX() < cleanButtonX + BUTTON_WIDTH &&
//            Blub_Blub.HEIGHT - Gdx.input.getY() > cleanButtonY && Blub_Blub.HEIGHT - Gdx.input.getY()
//            < cleanButtonY + BUTTON_HEIGHT) {
//
//            game.batch.draw(cleanPetButton, cleanButtonX, cleanButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
//
//            //Clean Pet when button clicked
//            if (Gdx.input.isTouched()) { // if feed button clicked
//                player.addExperience(0.1F);// then xp goes up, when xp goes up, we update experience from player using the Player.addExperience(0.1)
//                xp = player.getExperience();
//                game.batch.draw(progressBar, 0, 1040, (Blub_Blub.WIDTH * xp), 20);
//            }
//        } else {
//            game.batch.draw(cleanPetButton, cleanButtonX, cleanButtonY, BUTTON_WIDTH, BUTTON_HEIGHT);
//        }
//
//
//        // Progress Bar
//        game.batch.draw(progressBar, 0, 1040, (Blub_Blub.WIDTH * xp), 20);
//
//        // level
//        player.levelUp();
//        level = player.getLevel();
//        GlyphLayout levelLayout = new GlyphLayout(levelFont,"LEVEL " + level);
//        levelFont.draw(game.batch, levelLayout,Blub_Blub.WIDTH/ 2 - levelLayout.width /2, Blub_Blub.HEIGHT - levelLayout.height - 30);
//        game.batch.end();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        // Resize your screen here. The parameters represent the new window size.
//    }
//
//    @Override
//    public void pause() {
//        // Invoked when your application is paused.
//    }
//
//    @Override
//    public void resume() {
//        // Invoked when your application is resumed after pause.
//    }
//
//    @Override
//    public void hide() {
//        // This method is called when another screen replaces this one.
//    }
//
//    @Override
//    public void dispose() {
//        // Destroy screen's assets here.
//    }
//}
