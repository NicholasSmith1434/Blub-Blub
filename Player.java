package com.blub.Screens;

public class Player {
    public int level;
    public float experience;
    public float levelExperience; // experience needed to level up, could be 1
    boolean leftMove;
    boolean rightMove;


    public Player(){
        level = 1;
        experience = 0; // starts at 0, but for testing it is 1 in here
        levelExperience = 1;
    }

    public Player(Player player, int level, int experience) { // could be used for saving feature
        this.level = level;
        this.experience = experience;
    }

    public void addExperience(float amount) {
        this.experience += amount; // example, + 0.1

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

    public void setLeftMove(boolean t){
        if(rightMove && t) rightMove = false;
        leftMove = t;
    }
    public void setRightMove(boolean t){
        if(leftMove && t) leftMove = false;
        rightMove = t;
    }

}
