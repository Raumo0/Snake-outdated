package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import com.mygdx.game.GameMain;
import com.mygdx.game.sprites.Snake;
import com.mygdx.game.sprites.Tube;

/**
 * Created by raumo0 on 18.4.16.
 */
public class PlayState extends State {

    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -30;

    private Snake snake;
    private Texture bg;
//    private Texture ground;
    private Vector2 groundPos1, groundPos2;

//    private Array<Tube> tubes;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        snake = new Snake(GameMain.WIDTH / 2, GameMain.HEIGHT / 2);
        camera.setToOrtho(false, GameMain.WIDTH , GameMain.HEIGHT );
        bg = new Texture("bg3.png");
//        ground = new Texture("ground.png");
//        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth / 2, GROUND_Y_OFFSET);
//        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

//        tubes = new Array<Tube>();
//
//        for (int i = 0; i < TUBE_COUNT; i++){
//            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
//        }
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched())
            snake.turn();
    }

    @Override
    public void update(float dt) {
        handleInput();
//        updateGround();
        snake.update(dt);
//        camera.position.x = snake.getPosition().x;
//        camera.position.y = snake.getPosition().y;

//        for (int i = 0; i < tubes.size; i++){
//
//            Tube tube = tubes.get(i);
//
//            if (camera.position.x - (camera.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()){
//                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
//            }
//
//            if (tube.collides(snake.getBounds()))
//                gsm.set(new GameOver(gsm));
//        }
        camera.update();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(bg, camera.position.x - (camera.viewportWidth / 2), 0);
//        sb.draw(snake.getSnake(), snake.getPosition().x, snake.getPosition().y,
//                snake.getWidth(), snake.getHeight());
        sb.draw(snake.getSnake(), snake.getPosition().x, snake.getPosition().y, 0, 0,
                snake.getWidth(), snake.getHeight(), 1, 1, snake.rotation);
//        draw (TextureRegion region, float x, float y, float originX, float originY, float width, float height,
//        float scaleX, float scaleY, float rotation)

//        for (Tube tube : tubes) {
//            sb.draw(tube.getTopTube(), tube.getPosBotTube().x, tube.getPosTopTube().y);
//            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
//        }
//        sb.draw(ground, groundPos1.x, groundPos1.y);
//        sb.draw(ground, groundPos2.x, groundPos2.y);

        sb.end();

    }

    @Override
    public void dispose() {
        bg.dispose();
        snake.dispose();
//        ground.dispose();
//        for (Tube tube : tubes)
//            tube.dispose();
        System.out.println("PlayState Disposed");

    }

//    private void updateGround(){
//        if (camera.position.x - (camera.viewportWidth / 2) > groundPos1.x + ground.getWidth())
//            groundPos1.add(ground.getWidth() * 2, 0);
//        if (camera.position.x - (camera.viewportWidth / 2) > groundPos2.x + ground.getWidth())
//            groundPos2.add(ground.getWidth() * 2, 0);
//    }
}