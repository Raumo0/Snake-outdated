package com.mygdx.game.input;

import com.mygdx.game.sprites.Snake;

/**
 * Created by raumo0 on 08.05.16.
 */
public class TurnRightCommand implements Command {
    private Snake snake;

    public TurnRightCommand(Snake snake){
        this.snake = snake;
    }
    @Override
    public void execute() {
        snake.turnRight();
    }
}
