package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.game.World;
import com.mygdx.game.GameMain;

/**
 * Created by raumo0 on 18.4.16.
 */
public class PlayState extends State {
    private enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }
    private GameState state = GameState.Running;
    private Texture bg;
    private Texture buttons;
    private Texture pause;
    private Texture numbers;
    private Texture gameover;
    private World world;
    private int oldScore = 0;
    private String score = "0";

    public PlayState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, GameMain.WIDTH , GameMain.HEIGHT );
        bg = new Texture("bg3.png");
        buttons = new Texture("buttons.png");
        pause = new Texture("pausemenu.png");
        numbers = new Texture("numbers.png");
        gameover = new Texture("gameover.png");
        world = new World();
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        if (state == GameState.Ready);
        if (state == GameState.Running)
            updateRunning(dt);
        if (state == GameState.Paused)
            updatePaused();
        if (state == GameState.GameOver)
            updateGameOver();
    }

    private void updateRunning(float dt){
        world.useAI = false;
        for (int i = 0; i < 5; i++)
            if (Gdx.input.isTouched(i) &&
                    Gdx.input.getX(i) > Gdx.graphics.getWidth()-Gdx.graphics.getWidth()/5 &&
                    Gdx.input.getY(i) < Gdx.graphics.getHeight()/7) {
                state = GameState.Paused;
                return;
            }
        world.update(dt);
        if (world.gameOver)
            state = GameState.GameOver;
        if (oldScore != world.score) {
            oldScore = world.score;
            score = "" + oldScore;
        }
//        camera.position.x = snake.getPosition().x;
//        camera.position.y = snake.getPosition().y;
        camera.update();
    }

    private void updatePaused(){
        for (int i = 0; i < 5; i++)
            if (Gdx.input.isTouched(i) && Gdx.input.getX(i) > Gdx.graphics.getWidth()/2-200 &&
                Gdx.input.getX(i) < Gdx.graphics.getWidth()/2+200 &&
                Gdx.input.getY(i) > Gdx.graphics.getHeight()/2-200 &&
                Gdx.input.getY(i) < Gdx.graphics.getHeight()/2+200) {
            state = GameState.Running;
            return;
        }
    }

    private void updateGameOver(){
        if (Gdx.input.justTouched())
            gsm.set(new MenuState(gsm));
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(bg, 0, 0, GameMain.WIDTH, GameMain.HEIGHT);
        world.draw(sb);
        if (state == GameState.Ready);
        if (state == GameState.Running)
            drawRunningUI(sb);
        if (state == GameState.Paused)
            drawPausedUI(sb);
        if (state == GameState.GameOver)
            drawGameOverUi(sb);

        drawText(sb, score, GameMain.WIDTH/2 - score.length()*20/2, GameMain.HEIGHT - 42);
        sb.end();
    }

    private void drawText(SpriteBatch sb, String line, int x, int y){
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX = 0;
            int srcWidth = 0;
            if (character == '.') {
                srcX = 200;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * 20;
                srcWidth = 20;
            }

            sb.draw(numbers, x, y, srcX, 0, srcWidth, 32);
            x += srcWidth;
        }
    }

    private void drawRunningUI(SpriteBatch sb){
        int width = GameMain.WIDTH/10;
        int height = GameMain.HEIGHT/10;
        sb.draw(buttons, GameMain.WIDTH-width, GameMain.HEIGHT-height, width, height,
                64, 128, 64, 64, false, false);
    }

    private void drawPausedUI(SpriteBatch sb){
        sb.draw(pause, GameMain.WIDTH/2-100, GameMain.HEIGHT/2-100, 200, 200, 0, 0,
                pause.getWidth(), pause.getHeight()/2, false, false);
    }

    private void drawGameOverUi(SpriteBatch sb){
        sb.draw(gameover, camera.position.x - gameover.getWidth() / 2, camera.position.y);
    }

    @Override
    public void dispose() {
        bg.dispose();
//        snake.dispose();
    }
}