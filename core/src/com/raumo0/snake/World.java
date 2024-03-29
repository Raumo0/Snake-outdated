package com.raumo0.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pools;
import com.raumo0.snake.input.Command;
import com.raumo0.snake.input.DiveChangeCommand;
import com.raumo0.snake.input.Switch;
import com.raumo0.snake.input.TurnLeftCommand;
import com.raumo0.snake.input.TurnRightCommand;
import com.raumo0.snake.sprites.EnemyPool;
import com.raumo0.snake.sprites.Snake;
import com.raumo0.snake.sprites.Enemy;
import com.raumo0.snake.sprites.SnakePart;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by raumo0 on 19.05.16.
 */
public class World {
    public Snake snake;
//    public Enemy enemy;
    public boolean gameOver = false;
    public int score = 0;
    private int scoreIncrement = 1;
    private Random random = new Random();
    private float tickTime = 0f;
    private float tick = 0.032f;
    private Switch action;
    public boolean useAI = false;
    private Sprite bg;
    private float leftButtonEdge;
    private float rightButtonEdge;
    private boolean turnLeft = false;
    private boolean turnRight = false;
    private boolean dive = false;
    private ShapeRenderer shapeDebugger = new ShapeRenderer();
    public float hungerLimit;
    public float hunger;
    public int lives;
    private List<Enemy> enemies = new ArrayList<Enemy>();
    private EnemyPool enemyPool = new EnemyPool();
    private TextureRegion textureEnemy;

    public World() {
        snake = new Snake(GameMain.WIDTH / 2, GameMain.HEIGHT / 2, 2, -4.5f, 3f);
//        enemy = new Enemy(new Vector3(random.nextInt(GameMain.WIDTH - 50),
//                random.nextInt(GameMain.HEIGHT - 50),0), new Texture("bird.png"), 1, 1, false);
        textureEnemy = new TextureRegion(new Texture("bird.png"));
        enemies.add(createEnemy(textureEnemy, false, 1, 0, new Vector3(random.nextInt(GameMain.WIDTH - 50),
                random.nextInt(GameMain.HEIGHT - 50),0)));


        Command switchLeft = new TurnLeftCommand(snake);
        Command switchRight = new TurnRightCommand(snake);
        Command switchDive = new DiveChangeCommand(snake);
        action = new Switch(switchLeft, switchRight, switchDive);
        bg = new Sprite(new Texture("bg3.png"), 0, 0, GameMain.WIDTH, GameMain.HEIGHT);
        leftButtonEdge = Gdx.graphics.getWidth() * 2 / 5;
        rightButtonEdge = Gdx.graphics.getWidth() * 3 / 5;
        hungerLimit = 20;
        hunger = hungerLimit;
        lives = 3;
    }

    public void update(float dt) {
        if (!useAI) {
            handleInput();
        }
        hunger -= dt;
        if (hunger <= 0) {
            Gdx.input.vibrate(1000);
            lives--;
            if (lives != 0)
                hunger = hungerLimit;
        }
        if (lives <= 0)
            gameOver = true;
        tickTime += dt;
        while (tickTime > tick) {
            tickTime -= tick;
            if (turnRight){
                action.turnRight();
                turnRight = false;
            }
            if (turnLeft){
                action.turnLeft();
                turnLeft = false;
            }
            if (dive){
                action.diveChange();
                dive = false;
            }
            snake.advance();
            if (snake.checkBitten()) {
                Gdx.input.vibrate(200);
//                lives--;
//                if (lives != 0)
//                    hunger = hungerLimit;
                gameOver = true;
            }
            SnakePart head = snake.parts.get(0);
            for (Enemy enemy : enemies) {
                if (enemy.dive != head.dive)
                    continue;
                if (Intersector.overlapConvexPolygons(enemy.getBounds(), snake.getBounds(head))) {
                    score += scoreIncrement;
                    snake.eat();
                    enemies.remove(enemy);
                    hunger = hungerLimit;
                    break;
                }
            }
            if (enemies.size() < 3)
                for (int i = 0; i < random.nextInt(5); i++)
                    placeEnemy();
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
//        enemy.position.x = random.nextInt(GameMain.WIDTH - 50);
//        enemy.position.y = random.nextInt(GameMain.HEIGHT - 50);
        enemies.add(createEnemy(textureEnemy, random.nextBoolean(), 1, 0,
                new Vector3(random.nextInt(GameMain.WIDTH - 100)+50,
                random.nextInt(GameMain.HEIGHT - 100)+50,0)));
//        createEnemy(TextureRegion texture, boolean dive, float scale, float rotation,
//        Vector3 position)
    }

    public void draw(SpriteBatch sb){
        enemyDraw(sb, true);
        snakeDraw(sb, true);

        sb.begin();
        bg.draw(sb, .9f);
        sb.end();
        shapeDebugger.begin(ShapeRenderer.ShapeType.Line);
        shapeDebugger.setColor(Color.GRAY);
        shapeDebugger.line(leftButtonEdge, 0, leftButtonEdge, Gdx.graphics.getHeight());
        shapeDebugger.line(rightButtonEdge, 0, rightButtonEdge, Gdx.graphics.getHeight());
        shapeDebugger.end();

        enemyDraw(sb, false);
        snakeDraw(sb, false);

//        sb.begin();
//        for (Enemy enemy: enemies)
//            sb.draw(enemy.texture, enemy.position.x, enemy.position.y);
//        sb.end();
    }

    private void enemyDraw(SpriteBatch sb, boolean dive){
        sb.begin();
        for (Enemy enemy : enemies){
            if (enemy.dive == dive)
                sb.draw(enemy.texture,
                        enemy.position.x - enemy.scale * enemy.texture.getRegionWidth() / 2,
                        enemy.position.y - enemy.scale * enemy.texture.getRegionHeight() / 2,
                        enemy.scale*enemy.texture.getRegionWidth()/2,
                        enemy.scale*enemy.texture.getRegionHeight()/2,
                        enemy.scale*enemy.texture.getRegionWidth(),
                        enemy.scale*enemy.texture.getRegionHeight(),
                        enemy.scale, enemy.scale, enemy.rotation
                );
        }
        sb.end();
    }

    private void snakeDraw(SpriteBatch sb, boolean dive){
        float scale = 1.5f;
        SnakePart part = snake.parts.get(0);
        sb.begin();
        if (part.dive == dive)
            sb.draw(snake.getTexture(part.type),
                    part.position.x - snake.getWidth(part.type) / 2,
                    part.position.y - snake.getHeight(part.type) / 2,
                    snake.getWidth(part.type) / 2, snake.getHeight(part.type) / 2,
                    snake.getWidth(part.type), snake.getHeight(part.type),
                    scale * snake.getScaleX(part), scale * snake.getScaleY(part), part.rotation
            );

        scale = 1f;
        for (int i = 1; i < snake.parts.size(); i++){
            part = snake.parts.get(i);
            if (part.dive == dive)
                sb.draw(snake.getTexture(part.type),
                        part.position.x - snake.getWidth(part.type) / 2,
                        part.position.y - snake.getHeight(part.type) / 2,
                        snake.getWidth(part.type) / 2, snake.getHeight(part.type) / 2,
                        snake.getWidth(part.type), snake.getHeight(part.type),
                        snake.getScaleX(part), snake.getScaleY(part), part.rotation
                );
        }
        part = snake.parts.get(snake.parts.size()-1);
        scale = 2f;
        if (part.dive == dive)
            sb.draw(snake.getTexture(part.type),
                    part.position.x - snake.getWidth(part.type) / 2,
                    part.position.y - snake.getHeight(part.type) / 2,
                    snake.getWidth(part.type) / 2, snake.getHeight(part.type) / 2,
                    snake.getWidth(part.type), snake.getHeight(part.type),
                    scale * snake.getScaleX(part), .9f * snake.getScaleY(part), part.rotation
            );
        sb.end();
    }

    private void handleInput() {
        boolean one = false;
        boolean two = false;
        if (Gdx.input.isTouched(0)) {
            if (Gdx.input.getX(0) >= rightButtonEdge) {
                turnRight = true;
                one = true;
            }
            else if (Gdx.input.getX(0) <= leftButtonEdge) {
                turnLeft = true;
                one = true;
            }
        }
        if (Gdx.input.isTouched(1)) {
            if (Gdx.input.getX(1) >= rightButtonEdge && Gdx.input.getX(0) < rightButtonEdge) {
                turnRight = true;
                two = true;
            }
            else if (Gdx.input.getX(1) <= leftButtonEdge && Gdx.input.getX(0) > leftButtonEdge) {
                turnLeft = true;
                two = true;
            }
        }
        if (Gdx.input.justTouched()) {
            int x;
            int n;
            if (!one) n = 0;
            else if (!two)
                n = 1 ;
            else n = 2;
            x = Gdx.input.getX(n);
            if (Gdx.input.isTouched(n) && x > leftButtonEdge && x < rightButtonEdge) {
                dive = true;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            turnRight = true;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            turnLeft = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            dive = true;
        }
    }

    private Enemy createEnemy(TextureRegion texture, boolean dive, float scale, float rotation,
                              Vector3 position){
        Enemy enemy = enemyPool.obtain();
        enemy.texture = texture;
        enemy.dive = dive;
        enemy.scale = scale;
        enemy.rotation = rotation;
        enemy.position = position;
        return enemy;
    }

    public void dispose() {
    }
}