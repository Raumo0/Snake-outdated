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

/**
 * Created by raumo0 on 23.05.16.
 */
public class HighscoresState extends State {
    private Image quitBtn;
    private Stage stage;
    private Table table;

    public HighscoresState(final GameStateManager gsm){
        super(gsm);

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
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        table.remove();
    }
}
