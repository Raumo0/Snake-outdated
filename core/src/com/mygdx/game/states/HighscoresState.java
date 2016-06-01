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
import com.mygdx.game.Settings;

/**
 * Created by raumo0 on 23.05.16.
 */
public class HighscoresState extends State {
    private Image quitBtn;
    private Stage stage;
    private Table table;
    private Texture numbers;
    private String lines[] = new String[5];

    public HighscoresState(final GameStateManager gsm){
        super(gsm);
        numbers = new Texture("numbers.png");
//        int[] qse = new int[] { 100, 80, 50, 30, 10 };
        for (int i = 0; i < 5; i++) {
            lines[i] = "" + (i + 1) + ". " + Settings.highscores[i];
        }
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getWidth()/4);
        table.setPosition(Gdx.graphics.getWidth()/2 - table.getWidth()/2,
                Gdx.graphics.getHeight()/5 - table.getHeight()/2);
        table.debug();

        Texture menu = new Texture("pausemenu.png");
        quitBtn = new Image(new TextureRegion(menu, 0, menu.getHeight() / 2,
                menu.getWidth(), menu.getHeight() / 2));
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

        table.add(quitBtn).expand().width(table.getWidth()).height(table.getHeight());
        stage.addActor(table);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
//        sb.draw(Assets.background, 0, 0);
//        sb.draw(Assets.mainMenu, 64, 20, 0, 42, 196, 42);

        int y = 150;
        for (int i = 4; i >= 0; i--) {
            drawScore(sb, lines[i], 20, y);
            y += 50;
        }
        stage.draw();
    }

    private void drawScore(SpriteBatch sb, String line, int x, int y){
        int len = line.length();
        sb.begin();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);

            if (character == ' ') {
                x += 20;
                continue;
            }

            int srcX;
            int srcWidth;
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
        sb.end();
    }

    @Override
    public void dispose() {
        stage.dispose();
        table.remove();
    }
}
