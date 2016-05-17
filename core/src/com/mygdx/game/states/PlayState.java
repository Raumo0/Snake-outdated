package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Enemy;
import com.mygdx.game.GameMain;
import com.mygdx.game.input.Command;
import com.mygdx.game.input.Switch;
import com.mygdx.game.input.TurnLeftCommand;
import com.mygdx.game.input.TurnRightCommand;
import com.mygdx.game.sprites.Snake;
import com.mygdx.game.sprites.SnakePart;
import java.util.Random;

/**
 * Created by raumo0 on 18.4.16.
 */
public class PlayState extends State {
    private Snake snake;
    private Texture bg;
    private float tickTime = 0f;
    private float tick = 0.016f;
    private Switch action;
    private Enemy enemy;
    private Random random = new Random();

    public PlayState(GameStateManager gsm) {
        super(gsm);
        snake = new Snake(GameMain.WIDTH / 2, GameMain.HEIGHT / 2);
        camera.setToOrtho(false, GameMain.WIDTH , GameMain.HEIGHT );
        bg = new Texture("bg3.png");
        Command switchLeft = new TurnLeftCommand(snake);
        Command switchRight = new TurnRightCommand(snake);
        action = new Switch(switchLeft, switchRight);
        enemy = new Enemy(new Vector3(random.nextInt(GameMain.WIDTH - 50),
                random.nextInt(GameMain.HEIGHT - 50),0), new Texture("bird.png"));
    }

    @Override
    protected void handleInput() {
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

    private void placeEnemy() {
        enemy.position.x = random.nextInt(GameMain.WIDTH - 50);
        enemy.position.y = random.nextInt(GameMain.HEIGHT - 50);
    }

    @Override
    public void update(float dt) {
        tickTime += dt;
        while (tickTime > tick) {
            tickTime -= tick;
            handleInput();
            snake.advance();
            if (snake.checkBitten()) {
                Gdx.input.vibrate(200);
                gsm.set(new GameOver(gsm));
            }
            SnakePart head = snake.parts.get(0);
            if (Intersector.overlapConvexPolygons(enemy.getBounds(),snake.getBounds(head))) {
                //score++
                snake.eat();
                placeEnemy();
            }
        }
//        camera.position.x = snake.getPosition().x;
//        camera.position.y = snake.getPosition().y;
        camera.update();
        Gdx.app.log("FPS: ", String.valueOf(Gdx.graphics.getFramesPerSecond()));
        Gdx.app.log("Snake parts count: ", String.valueOf(snake.parts.size()));
        Gdx.app.log("Distance: ", String.valueOf(
                snake.parts.get(0).position.dst(snake.parts.get(1).position)));
        Gdx.app.log("Part width: ", String.valueOf(snake.getTexture(SnakePart.TextureType.head).
                getRegionWidth()*snake.scale));
        Gdx.app.log("Native heap(Mb): ", String.valueOf(Gdx.app.getNativeHeap()/(1024*1024)));
        Gdx.app.log("Java heap(Mb): ", String.valueOf(Gdx.app.getJavaHeap()/(1024*1024)));
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(bg, camera.position.x - (camera.viewportWidth / 2), 0, GameMain.WIDTH, GameMain.HEIGHT);
        for (int i = 0; i < snake.parts.size(); i++){
            SnakePart part = snake.parts.get(i);
            sb.draw(snake.getTexture(part.type), part.position.x, part.position.y,
                    snake.getWidth()/2, snake.getHeight()/2,
                    snake.getWidth(), snake.getHeight(),
                    1, 1, part.rotation);
        }
        sb.draw(enemy.texture, enemy.position.x, enemy.position.y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        snake.dispose();
        System.out.println("PlayState Disposed");
    }
}