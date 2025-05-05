package com.blub.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.blub.Blub_Blub;
import com.badlogic.gdx.math.Rectangle; // Import Rectangle for area checking

public class FeedScreen implements Screen {
    Blub_Blub game;
    Player player = new Player(); // Make sure this Player class has addExperience and levelUp methods
    float xp = player.getExperience();
    BitmapFont levelFont;
    int level = player.getLevel();

    private static final float PAUSE_BUTTON_WIDTH = 80;
    private static final float PAUSE_BUTTON_HEIGHT = 80;
    private static final float LEAVE_BUTTON_WIDTH = 70;
    private static final float LEAVE_BUTTON_HEIGHT = 70;

    // Textures
    Texture leaveButton;
    Texture pauseButton;
    Texture progressBar;
    Texture petNormal; // Renamed for clarity
    Texture petEat;    // Texture for eating animation
    Texture foodItem;  // Texture for the food

    // Game State Variables
    private boolean isHoldingFood = false;
    private boolean isPetEating = false;
    private float eatTimer = 0f;
    private static final float EAT_DURATION = 1.0f; // How long the eating animation lasts (in seconds)

    // Interaction Areas (using bottom-left coordinates)
    private Rectangle foodArea;
    private Rectangle petArea;

    // Food properties
    private static final float FOOD_ITEM_WIDTH = 60;
    private static final float FOOD_ITEM_HEIGHT = 60;
    private static final float FOOD_START_X = Blub_Blub.WIDTH - 150; // Example position: Bottom right
    // ***** CHANGE HERE *****
    private static final float FOOD_START_Y = 50 + 100; // Moved up by 100 pixels (now 150)

    // Pet properties
    private static final float PET_DRAW_X = 200;
    // ***** CHANGE HERE *****
    private static final float PET_DRAW_Y = 100 + 100; // Moved up by 100 pixels (now 200)

    // Get pet dimensions from texture if possible, otherwise estimate
    // Assuming Alien Fella Front.png dimensions here, adjust if needed
    private static final float PET_CLICK_WIDTH = 500; // Adjust based on actual pet image size
    private static final float PET_CLICK_HEIGHT = 700; // Adjust based on actual pet image size


    public FeedScreen(Blub_Blub game) {
        this.game = game;
        leaveButton = new Texture(Gdx.files.internal("LeaveButton.png"));
        progressBar = new Texture("progress-bar.png");
        levelFont = new BitmapFont(Gdx.files.internal("fonts/levelFont.fnt"));
        pauseButton = new Texture(Gdx.files.internal("PauseButton.png"));

        // Load pet textures
        petNormal = new Texture(Gdx.files.internal("Alien Fella Front.png"));
        // Make sure this is the correct eating animation texture
        petEat = new Texture(Gdx.files.internal("Alien Fella Back.png"));

        // Load food texture
        // Using SoapBar as food based on your previous code, change if needed
        foodItem = new Texture(Gdx.files.internal("SoapBar.png"));

        // Define clickable areas using Rectangles
        // These automatically use the updated FOOD_START_Y and PET_DRAW_Y constants
        foodArea = new Rectangle(FOOD_START_X, FOOD_START_Y, FOOD_ITEM_WIDTH, FOOD_ITEM_HEIGHT);
        petArea = new Rectangle(PET_DRAW_X, PET_DRAW_Y, PET_CLICK_WIDTH, PET_CLICK_HEIGHT);
    }

    @Override
    public void show() {
        // Reset state if needed when screen becomes active
        isHoldingFood = false;
        isPetEating = false;
        eatTimer = 0f;
    }

    @Override
    public void render(float delta) { // delta is the time elapsed since the last frame
        // --- Update Logic ---

        // Update eating animation timer
        if (isPetEating) {
            eatTimer -= delta;
            if (eatTimer <= 0) {
                isPetEating = false; // Animation finished
            }
        }

        // Get mouse position (bottom-left origin)
        float mouseX = Gdx.input.getX();
        float mouseY = Blub_Blub.HEIGHT - Gdx.input.getY();

        // --- Input Handling --- (Use justTouched for single actions per click)
        if (Gdx.input.justTouched()) {
            // Check UI buttons first (more specific areas)
            float pauseButtonX = Blub_Blub.WIDTH - 100;
            float pauseButtonY = Blub_Blub.HEIGHT - 130;
            if (mouseX >= pauseButtonX && mouseX < pauseButtonX + PAUSE_BUTTON_WIDTH &&
                mouseY >= pauseButtonY && mouseY < pauseButtonY + PAUSE_BUTTON_HEIGHT) {
                this.dispose();
                game.playClick();
                // Use correct ID for FeedScreen if necessary for PauseMenuScreen
                game.setScreen(new PauseMenuScreen(game, Blub_Blub.FEED));
            } else {
                float leaveButtonX = Blub_Blub.WIDTH - 94;
                float leaveButtonY = Blub_Blub.HEIGHT - 220;
                if (mouseX >= leaveButtonX && mouseX < leaveButtonX + LEAVE_BUTTON_WIDTH &&
                    mouseY >= leaveButtonY && mouseY < leaveButtonY + LEAVE_BUTTON_HEIGHT) {
                    this.dispose();
                    game.playClick();
                    game.setScreen(new PetScreen(game));
                } else {
                    // Check game interactions only if UI buttons weren't clicked

                    // --- Feeding Logic ---
                    if (isHoldingFood) {
                        // Try to feed the pet (Check against the updated petArea)
                        if (petArea.contains(mouseX, mouseY)) {
                            isHoldingFood = false; // Food is consumed
                            isPetEating = true;    // Start eating animation
                            eatTimer = EAT_DURATION; // Reset timer
                            game.playBite(); // Play bite sound

                            // --- XP Logic (Uncommented if you want to use it) ---
                            // xp = player.addExperience(0.1f);
                            // if (player.levelUp()) {
                            //     level = player.getLevel();
                            //     game.playLevelUp();
                            // }
                            // this.xp = player.getExperience(); // Update progress bar value
                            // -----------------------------------------------------

                        }
                        // Optional: Clicking elsewhere could drop food
                        // else { isHoldingFood = false; game.playDropSound(); }
                    } else {
                        // Try to pick up food (Check against the updated foodArea)
                        if (foodArea.contains(mouseX, mouseY)) {
                            isHoldingFood = true;
                            game.playClick(); // Play pickup sound
                        }
                    }
                }
            }
        } // end justTouched

        // --- Drawing Logic ---
        Gdx.gl.glClearColor(0.86F, 0.67F, 0.29F, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        // Draw Pet - Choose texture based on eating state
        // Drawn at the updated PET_DRAW_Y
        if (isPetEating) {
            // Draw the EATING texture shifted 100 pixels to the right
            game.batch.draw(petEat, PET_DRAW_X + 100, PET_DRAW_Y);
        } else {
            // Draw the NORMAL texture at the standard position
            game.batch.draw(petNormal, PET_DRAW_X, PET_DRAW_Y);
        }

        // Draw static food item ONLY if it's not being held
        // Drawn at the updated FOOD_START_Y
        if (!isHoldingFood) {
            game.batch.draw(foodItem, foodArea.x, foodArea.y, foodArea.width, foodArea.height);
        }

        // Draw UI Elements (Positions unchanged)
        float pauseButtonX = Blub_Blub.WIDTH - 100;
        float pauseButtonY = Blub_Blub.HEIGHT - 130;
        game.batch.draw(pauseButton, pauseButtonX, pauseButtonY, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);

        float leaveButtonX = Blub_Blub.WIDTH - 94;
        float leaveButtonY = Blub_Blub.HEIGHT - 220;
        game.batch.draw(leaveButton, leaveButtonX, leaveButtonY, LEAVE_BUTTON_WIDTH, LEAVE_BUTTON_HEIGHT);

        // Progress Bar
        game.batch.draw(progressBar, 0, Blub_Blub.HEIGHT - 20, (Blub_Blub.WIDTH * this.xp), 20);

        // Level Text
        level = player.getLevel(); // Get potentially updated level
        GlyphLayout levelLayout = new GlyphLayout(levelFont,"LEVEL " + level);
        levelFont.draw(game.batch, levelLayout,Blub_Blub.WIDTH/ 2f - levelLayout.width / 2f, Blub_Blub.HEIGHT - levelLayout.height - 30);

        // Draw food following cursor ONLY if being held
        if (isHoldingFood) {
            // Center food on cursor
            game.batch.draw(foodItem, mouseX - FOOD_ITEM_WIDTH / 2, mouseY - FOOD_ITEM_HEIGHT / 2, FOOD_ITEM_WIDTH, FOOD_ITEM_HEIGHT);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) { }
    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }

    @Override
    public void dispose() {
        // Dispose all loaded textures and fonts
        leaveButton.dispose();
        pauseButton.dispose();
        progressBar.dispose();
        levelFont.dispose();
        petNormal.dispose();
        petEat.dispose();
        foodItem.dispose();
    }

    // --- Placeholder/Dummy classes (Ensure you have your actual classes) ---
    // (Player, Blub_Blub, PauseMenuScreen, PetScreen)
    // ... (Keep the dummy classes from the previous example if needed for testing)

}
