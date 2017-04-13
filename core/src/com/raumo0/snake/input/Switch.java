package com.raumo0.snake.input;

/**
 * Created by raumo0 on 08.05.16.
 */
public class Switch {
    private Command turnLeftCommand;
    private Command turnRightCommand;
    private Command diveChangeCommand;

    public Switch(Command turnLeftCommand, Command turnRightCommand,
                  Command diveChangeCommand){
        this.turnLeftCommand = turnLeftCommand;
        this.turnRightCommand = turnRightCommand;
        this.diveChangeCommand = diveChangeCommand;
    }

    public void turnLeft(){
        turnLeftCommand.execute();
    }

    public void turnRight(){
        turnRightCommand.execute();
    }

    public void diveChange() {
        diveChangeCommand.execute();
    }
}

