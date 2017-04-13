package com.raumo0.snake.input;

import com.raumo0.snake.sprites.Snake;

/**
 * Created by raumo0 on 25.05.16.
 */
public class DiveChangeCommand implements Command {
    private Snake snake;

    public DiveChangeCommand(Snake snake){
        this.snake = snake;
    }

    @Override
    public void execute() {
        snake.diveChange();
    }
}
