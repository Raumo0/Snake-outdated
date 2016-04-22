package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameMain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raumo0 on 19.4.16.
 */
public class Snake {
    private Vector3 position;
    private Vector3 motion = new Vector3(5, 0, 0);//движение
    private Rectangle bounds;
//    private Animation birdAnimation;
    private Texture texture_head;
    private Texture texture_body;
    private Texture texture_tail;
    public float scale = 0.04f;
    private float angle = -3f;
    private float speed = .5f;
    public List<SnakePart> parts = new ArrayList<SnakePart>();

    public Snake(int x, int y){
        texture_head = new Texture("snake_head.png");
        texture_body = new Texture("snake_body.png");
        texture_tail = new Texture("snake_tail.png");
//        birdAnimation = new Animation(new TextureRegion(texture_head), 3, 0.5f);
        bounds = new Rectangle(x, y, texture_head.getWidth() * scale, texture_head.getHeight() * scale);

        for (int i = 1; i < 10; i++)
            parts.add(new SnakePart(new Vector3(x, y, 0), texture_head));
        for (int i = 1; i <= 30; i++)
            parts.add(new SnakePart(new Vector3(x, y, 0), texture_body));
        for (int i = 1; i <= 10; i++)
            parts.add(new SnakePart(new Vector3(x, y, 0), texture_tail));
        position = parts.get(0).position;
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getSnake() {
        return new TextureRegion(texture_head);
    }

    public void turnLeft(){
        turn(-1f);
    }

    public void turnRight(){
        turn(1f);
    }

    private void turn(float route){
        float x = (float)(motion.x*Math.cos(Math.toRadians(angle*route)) - motion.y*Math.sin(Math.toRadians(angle*route)));
        motion.y = (float)(motion.x*Math.sin(Math.toRadians(angle*route)) + motion.y*Math.cos(Math.toRadians(angle*route)));
        motion.x = x;
        parts.get(0).rotation += angle*route;
        if (parts.get(0).rotation >= 360 || parts.get(0).rotation <= -360)
            parts.get(0).rotation %= 360;
    }

    public Rectangle getBounds(){
        return bounds;
    }


    public void dispose() {
        texture_head.dispose();
        texture_body.dispose();
        texture_tail.dispose();
    }

    public float getWidth(){
        return texture_head.getWidth() * scale;
    }

    public float getHeight(){
        return texture_head.getHeight() * scale;
    }

    private Vector3 move(){
        return new Vector3(motion.x * speed, motion.y * speed, 0);
    }

    public void advance() {
        if (position.x > GameMain.WIDTH)
            position.x = 0;
        else if (position.x < 0)
            position.x = GameMain.WIDTH;
        if (position.y > GameMain.HEIGHT)
            position.y = 0;
        else if (position.y < 0)
            position.y = GameMain.HEIGHT;
        int len = parts.size() - 1;
        for (int i = len; i > 0; i--){
            SnakePart before = parts.get(i-1);
            SnakePart part = parts.get(i);
            part.position.x = before.position.x;
            part.position.y = before.position.y;
            part.rotation = before.rotation;
        }
        position.add(move());
        bounds.setPosition(position.x, position.y);
    }
}