package com.mygdx.game.input;

/**
 * Created by raumo0 on 08.05.16.
 */
public class Switch {
    private Command turnLeftCommand;
    private Command turnRightCommand;

    public Switch(Command turnLeftCommand, Command turnRightCommand){
        this.turnLeftCommand = turnLeftCommand;
        this.turnRightCommand = turnRightCommand;
    }

    public void turnLeft(){
        turnLeftCommand.execute();
    }

    public void turnRight(){
        turnRightCommand.execute();
    }
}

