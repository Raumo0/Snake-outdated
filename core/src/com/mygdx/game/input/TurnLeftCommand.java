package com.mygdx.game.input;

import com.mygdx.game.sprites.Snake;

/**
 * Created by raumo0 on 08.05.16.
 */
public class TurnLeftCommand implements Command {
    private Snake snake;

    public TurnLeftCommand(Snake snake){
        this.snake = snake;
    }
    @Override
    public void execute() {
        snake.turnLeft();
    }
}
