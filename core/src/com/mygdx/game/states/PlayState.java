package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

    private Image resumeBtn;
    private Image quitBtn;
    private Stage stage;
    private Table table;

    public PlayState(final GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, GameMain.WIDTH , GameMain.HEIGHT );
        bg = new Texture("bg3.png");
        buttons = new Texture("buttons.png");
        pause = new Texture("pausemenu.png");
        numbers = new Texture("numbers.png");
        gameover = new Texture("gameover.png");
        world = new World();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        table.debug();

        Texture menu = new Texture("pausemenu.png");
        resumeBtn = new Image(new TextureRegion(menu, 0, 0, menu.getWidth(), menu.getHeight() / 2));
        quitBtn = new Image(new TextureRegion(menu, 0, menu.getHeight() / 2,
                menu.getWidth(), menu.getHeight() / 2));
        resumeBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Resume ", "Pressed");
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if (x > 0 && x < resumeBtn.getWidth() && y > 0 && y < resumeBtn.getHeight()) {
                    Gdx.app.log("Resume", "Released");
                    state = GameState.Running;
                    table.remove();
                }
            }
        });
        quitBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("Quit", "Pressed");
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if (x > 0 && x < quitBtn.getWidth() && y > 0 && y < quitBtn.getHeight()) {
                    Gdx.app.log("Quit", "Released");
                    gsm.set(new MenuState(gsm));
                }
            }
        });
        table.setWidth(Gdx.graphics.getWidth()/2);
        table.setHeight(Gdx.graphics.getHeight()/2);
        table.setPosition(Gdx.graphics.getWidth()/2 - table.getWidth()/2,
                Gdx.graphics.getHeight()/2 - table.getHeight()/2);
        table.add(resumeBtn).expand().width(table.getWidth()).height(table.getHeight()/4);//.width(image2.getRegionWidth()).height(image2.getRegionHeight());
        table.row();
        table.add(quitBtn).expand().width(table.getWidth()).height(table.getHeight()/4);
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
                stage.addActor(table);
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
//        for (int i = 0; i < 5; i++)
//            if (Gdx.input.isTouched(i) && Gdx.input.getX(i) > Gdx.graphics.getWidth()/2-200 &&
//                Gdx.input.getX(i) < Gdx.graphics.getWidth()/2+200 &&
//                Gdx.input.getY(i) > Gdx.graphics.getHeight()/2-200 &&
//                Gdx.input.getY(i) < Gdx.graphics.getHeight()/2+200) {
//            state = GameState.Running;
//            return;
//        }
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
        sb.end();
        world.draw(sb);
        if (state == GameState.Ready);
        if (state == GameState.Running)
            drawRunningUI(sb);
        if (state == GameState.Paused)
            drawPausedUI();
        if (state == GameState.GameOver)
            drawGameOverUi(sb);

        sb.begin();
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
        sb.begin();
        sb.draw(buttons, GameMain.WIDTH-width, GameMain.HEIGHT-height, width, height,
                64, 128, 64, 64, false, false);
        sb.end();
    }

    private void drawPausedUI(){
        stage.draw();
    }

    private void drawGameOverUi(SpriteBatch sb){
        sb.begin();
        sb.draw(gameover, camera.position.x - gameover.getWidth() / 2, camera.position.y);
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        stage.dispose();
        table.remove();
    }
}