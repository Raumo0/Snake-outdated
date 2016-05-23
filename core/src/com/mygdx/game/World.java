package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.input.Command;
import com.mygdx.game.input.Switch;
import com.mygdx.game.input.TurnLeftCommand;
import com.mygdx.game.input.TurnRightCommand;
import com.mygdx.game.sprites.Snake;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.SnakePart;

import java.util.Random;

/**
 * Created by raumo0 on 19.05.16.
 */
public class World {
    public Snake snake;
    public Enemy enemy;
    public boolean gameOver = false;;
    public int score = 0;
    private int scoreIncrement = 1;
    private Random random = new Random();
    private float tickTime = 0f;
    private float tick = 0.032f;
    private Switch action;
    public boolean useAI = false;

    public World() {
        snake = new Snake(GameMain.WIDTH / 2, GameMain.HEIGHT / 2, 2, -4.5f, 3f);
        enemy = new Enemy(new Vector3(random.nextInt(GameMain.WIDTH - 50),
                random.nextInt(GameMain.HEIGHT - 50),0), new Texture("bird.png"));
        Command switchLeft = new TurnLeftCommand(snake);
        Command switchRight = new TurnRightCommand(snake);
        action = new Switch(switchLeft, switchRight);
    }

    public void update(float dt) {
        tickTime += dt;
        while (tickTime > tick) {
            tickTime -= tick;
            if (!useAI)
                handleInput();
            snake.advance();
            if (snake.checkBitten()) {
                Gdx.input.vibrate(200);
                gameOver = true;
            }
            SnakePart head = snake.parts.get(0);
            if (Intersector.overlapConvexPolygons(enemy.getBounds(),snake.getBounds(head))) {
                score += scoreIncrement;
                snake.eat();
                placeEnemy();
            }
        }
        Gdx.app.log("FPS: ", String.valueOf(Gdx.graphics.getFramesPerSecond()));
        Gdx.app.log("Snake parts count: ", String.valueOf(snake.parts.size()));
        Gdx.app.log("Distance: ", String.valueOf(
                snake.parts.get(0).position.dst(snake.parts.get(1).position)));
        Gdx.app.log("Part width: ", String.valueOf(snake.getWidth(SnakePart.TextureType.head)));
        Gdx.app.log("Native heap(Mb): ", String.valueOf(Gdx.app.getNativeHeap()/(1024*1024)));
        Gdx.app.log("Java heap(Mb): ", String.valueOf(Gdx.app.getJavaHeap()/(1024*1024)));
    }

    private void placeEnemy() {
        enemy.position.x = random.nextInt(GameMain.WIDTH - 50);
        enemy.position.y = random.nextInt(GameMain.HEIGHT - 50);
    }

    public void draw(SpriteBatch sb){
        float scale = 1.5f;
        SnakePart part = snake.parts.get(0);
        sb.begin();
        sb.draw(snake.getTexture(part.type),
                part.position.x - snake.getWidth(part.type)/2,
                part.position.y - snake.getHeight(part.type)/2,
                snake.getWidth(part.type)/2, snake.getHeight(part.type)/2,
                snake.getWidth(part.type), snake.getHeight(part.type),
                scale * snake.getScaleX(part), scale * snake.getScaleY(part), part.rotation);
        scale = 1f;
        for (int i = 1; i < snake.parts.size(); i++){
            part = snake.parts.get(i);
            sb.draw(snake.getTexture(part.type),
                    part.position.x - snake.getWidth(part.type)/2,
                    part.position.y - snake.getHeight(part.type)/2,
                    snake.getWidth(part.type)/2, snake.getHeight(part.type)/2,
                    snake.getWidth(part.type), snake.getHeight(part.type),
                    snake.getScaleX(part), snake.getScaleY(part), part.rotation);
        }
        part = snake.parts.get(snake.parts.size()-1);
        scale = 2f;
        sb.draw(snake.getTexture(part.type),
                part.position.x - snake.getWidth(part.type)/2,
                part.position.y - snake.getHeight(part.type)/2,
                snake.getWidth(part.type)/2, snake.getHeight(part.type)/2,
                snake.getWidth(part.type), snake.getHeight(part.type),
                scale * snake.getScaleX(part), .9f * snake.getScaleY(part), part.rotation);

        sb.draw(enemy.texture, enemy.position.x, enemy.position.y);
        sb.end();
    }

    private void handleInput() {
        int centre = Gdx.graphics.getWidth() / 2;
        if (Gdx.input.isTouched(0)) {
            if (Gdx.input.getX(0) > centre) {
                action.turnRight();
            }
            else if (Gdx.input.getX(0) <= centre) {
                action.turnLeft();
            }
        }
        if (Gdx.input.isTouched(1)) {
            if (Gdx.input.getX(1) > centre && Gdx.input.getX(0) <= centre) {
                action.turnRight();
            }
            else if (Gdx.input.getX(1) <= centre && Gdx.input.getX(0) > centre) {
                action.turnLeft();
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            action.turnRight();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            action.turnLeft();
    }

    public void dispose() {

    }
}