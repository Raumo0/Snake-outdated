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
    private static final int MOVEMENT = 100;
    private static final int GRAVITY = -15;
    private Vector3 position;
    private Vector3 motion = new Vector3(5, 0, 0);//движение
//    private Vector3 velosity;
    private Rectangle bounds;
//    private Animation birdAnimation;
    private Texture texture_head;
    private Texture texture_body;
    private Texture texture_tail;
    public float scale = 0.04f;
//    public float rotation;
    private float angle = -3f;
    private float speed = .5f;
    public List<SnakePart> parts = new ArrayList<SnakePart>();

    public Snake(int x, int y){
//        velosity = new Vector3(0, 0, 0);
        texture_head = new Texture("snake_head.png");
        texture_body = new Texture("snake_body.png");
        texture_tail = new Texture("snake_tail.png");
//        birdAnimation = new Animation(new TextureRegion(texture_head), 3, 0.5f);
        bounds = new Rectangle(x, y, texture_head.getWidth() * scale, texture_head.getHeight() * scale);

        int n = 30;
        for (int i = 1; i < 10; i++)
            parts.add(new SnakePart(new Vector3(x, y, 0), texture_head));
        for (int i = 1; i <= 30; i++)
            parts.add(new SnakePart(new Vector3(x-i*n, y, 0), texture_body));
        for (int i = 1; i <= 10; i++)
            parts.add(new SnakePart(new Vector3(x-n*parts.size(), y, 0), texture_tail));
        position = parts.get(0).position;
//        rotation = parts.get(0).rotation;
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getSnake() {
//        return birdAnimation.getFrame();
        return new TextureRegion(texture_head);
    }

    public void update(float dt){
//        birdAnimation.update(dt);
//        velosity.scl(dt);
//        position.add(MOVEMENT * dt, velosity.y, 0);
        if (position.x > GameMain.WIDTH)
            position.x = 0;
        else if (position.x < 0)
            position.x = GameMain.WIDTH;
        if (position.y > GameMain.HEIGHT)
            position.y = 0;
        else if (position.y < 0)
            position.y = GameMain.HEIGHT;
        advance();
        position.add(move());
//        velosity.scl(1 / dt);
        bounds.setPosition(position.x, position.y);


    }

    public void turnLeft(){}

    public void turnRight(){
        float x = (float)(motion.x*Math.cos(Math.toRadians(angle)) - motion.y*Math.sin(Math.toRadians(angle)));
        motion.y = (float)(motion.x*Math.sin(Math.toRadians(angle)) + motion.y*Math.cos(Math.toRadians(angle)));
        motion.x = x;
        position.add(new Vector3(motion.x * speed, motion.y * speed, motion.z * speed));
        parts.get(0).rotation += angle;
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
        return new Vector3(motion.x * speed, motion.y * speed, motion.z * speed);
    }

    private void advance() {
        int len = parts.size() - 1;
        for (int i = len; i > 0; i--){
            SnakePart before = parts.get(i-1);
            SnakePart part = parts.get(i);
            part.position.x = before.position.x;
            part.position.y = before.position.y;
            part.rotation = before.rotation;
        }
    }
}