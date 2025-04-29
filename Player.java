package com.blub.Screens;

public class Player {
    private Player player;
    public int level;
    public float experience;
    public float levelExperience; // experience needed to level up, could be 1

    public Player(){
        level = 1;
        experience = 1; // starts at 0, but for testing it is 1 in here
        levelExperience = 0;
    }

    public Player(Player player, int level, int experience) { // could be used for saving feature
        this.player = player ;
        this.level = level;
        this.experience = experience;
    }

    public void addExperience(float amount) {
        this.levelExperience += amount; // example, + 0.1

    }
    public void levelUp() {
        if(experience > levelExperience){
            level += 1;
            experience = 0;
        }

    }
    public int getLevel() {
        return level;
    }
    public float getExperience() {
        return experience;
    }

}
