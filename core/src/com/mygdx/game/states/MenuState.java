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
import com.mygdx.game.GameMain;
/**
 * Created by raumo0 on 18.4.16.
 */
public class MenuState extends State {
    private Texture background;
    private Image playBtn;
    private Image highscoresBtn;
    private Image helpBtn;
    private Stage stage;
    private Table table;

    public MenuState(final GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, GameMain.WIDTH / 2, GameMain.HEIGHT / 2);
        background = new Texture("bg3.png");

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        table = new Table();
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(table);
        table.debug();

        Texture menu = new Texture("mainmenu.png");
        playBtn = new Image(new TextureRegion(menu, 0, 0, menu.getWidth(), menu.getHeight() / 3));
        highscoresBtn = new Image(new TextureRegion(menu, 0, menu.getHeight() / 3,
                menu.getWidth(), menu.getHeight() / 3));
        helpBtn = new Image(new TextureRegion(menu, 0, 2 * menu.getHeight() / 3,
                menu.getWidth(), menu.getHeight() / 3));
        playBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("my app", "Pressed");
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if (x > 0 && x < playBtn.getWidth() && y > 0 && y < playBtn.getHeight()) {
                    Gdx.app.log("my app", "Released");
                    gsm.set(new PlayState(gsm));
                }
            }
        });
        highscoresBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("highscores", "Pressed");
                return true;
            }
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if (x > 0 && x < playBtn.getWidth() && y > 0 && y < playBtn.getHeight()) {
                    Gdx.app.log("highscores", "Released");
                    gsm.set(new HighscoresState(gsm));
                }
            }
        });
        table.setWidth(Gdx.graphics.getWidth()/2);
        table.setHeight(Gdx.graphics.getHeight()/2);
        table.setPosition(Gdx.graphics.getWidth()/2 - table.getWidth()/2,
                Gdx.graphics.getHeight()/2 - table.getHeight()/2);
        table.add(playBtn).expand().width(table.getWidth()).height(table.getHeight()/6);//.width(image2.getRegionWidth()).height(image2.getRegionHeight());
        table.row();
        table.add(highscoresBtn).expand().width(table.getWidth()).height(table.getHeight()/6);
        table.row();
        table.add(helpBtn).expand().width(table.getWidth()).height(table.getHeight()/6);
    }

    @Override
    public void handleInput() {
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);//whether it is necessary here?
        sb.begin();
        sb.draw(background, 0, 0, GameMain.WIDTH/2, GameMain.HEIGHT/2);
        sb.end();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        stage.dispose();
        table.remove();
        System.out.println("MenuState Disposed");
    }
}