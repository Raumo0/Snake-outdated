package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameMain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raumo0 on 19.4.16.
 */
public class Snake {
    private Vector3 position;
    private Vector3 direction = new Vector3(1, 0, 0);
//    private Animation birdAnimation;
    private TextureRegion texture_head;
    private TextureRegion texture_body;
    private TextureRegion texture_tail;
    private float scale = 0.04f;
    private float angle = -3f;
    public float speed = 2.5f;
    public List<SnakePart> parts = new ArrayList<SnakePart>();

    public Snake(int x, int y){
        texture_head = new TextureRegion(new Texture("snake_head.png"));
        texture_body = new TextureRegion(new Texture("snake_body.png"));
        texture_tail = new TextureRegion(new Texture("snake_tail.png"));
//        birdAnimation = new Animation(new TextureRegion(texture_head), 3, 0.5f);
        position = new Vector3(x, y, 0);
        parts.add(new SnakePart(position, SnakePart.TextureType.head, 0f, 1f));
        for (int i = 1; i <= 50; i++)
            parts.add(new SnakePart(new Vector3(position.x-parts.size()*speed, position.y, 0f),
                    SnakePart.TextureType.body, 0f, 1f));
        parts.add(new SnakePart(new Vector3(position.x-parts.size()*speed, position.y, 0f),
                    SnakePart.TextureType.tail, 0f, 1f));
    }

    public TextureRegion getTexture(SnakePart.TextureType type){
        switch (type){
            case head: return texture_head;
            case body: return texture_body;
            case tail: return texture_tail;
        }
        return null;
    }

    public void turnLeft(){
        float route = -angle;
        direction = turn(direction, route);
        roundAngle(route);
    }

    public void turnRight(){
        float route = angle;
        direction = turn(direction, route);
        roundAngle(route);
    }

    private Vector3 turn(Vector3 vector, float route){
        float x = (float)(vector.x*Math.cos(Math.toRadians(route)) - vector.y*Math.sin(Math.toRadians(route)));
        float y = (float)(vector.x*Math.sin(Math.toRadians(route)) + vector.y*Math.cos(Math.toRadians(route)));
        return new Vector3(x, y, 0);
    }

    private void roundAngle(float route){
        parts.get(0).rotation += route;
        if (parts.get(0).rotation >= 360 || parts.get(0).rotation <= -360)
            parts.get(0).rotation %= 360;
    }

    public Polygon getBounds(SnakePart part){
        TextureRegion texture = getTexture(part.type);
        float n;
        if (part.type == SnakePart.TextureType.head)
            n = .05f;
        else
            n = 1f;
        float width = texture.getRegionWidth()*scale;
        float height = texture.getRegionHeight()*scale;
        Polygon bounds = new Polygon(new float[]{width-width*n, 0, width, 0,
                width, height, width-width*n, height});
        bounds.setPosition(part.position.x, part.position.y);
        bounds.setRotation(part.rotation);
        bounds.setOrigin(width/2, height/2);
        return bounds;
    }


    public void dispose() {
    }

    public float getWidth(SnakePart.TextureType type){
        if (type == SnakePart.TextureType.head)
            return texture_head.getRegionWidth() * scale;
        if (type == SnakePart.TextureType.body)
            return texture_body.getRegionWidth() * scale;
        return texture_tail.getRegionWidth() * scale;
    }

    public float getHeight(SnakePart.TextureType type){
        if (type == SnakePart.TextureType.head)
            return texture_head.getRegionHeight() * scale;
        if (type == SnakePart.TextureType.body)
            return texture_body.getRegionHeight() * scale;
        return texture_tail.getRegionHeight() * scale;
    }

    public float getScaleX(SnakePart part){
        if (part.type == SnakePart.TextureType.head)
            return part.scale;
        if (part.type == SnakePart.TextureType.body)
            return part.scale * texture_head.getRegionWidth() / texture_body.getRegionWidth();
        return part.scale * texture_head.getRegionWidth() / texture_tail.getRegionWidth();
    }

    public float getScaleY(SnakePart part){
        if (part.type == SnakePart.TextureType.head)
            return part.scale;
        if (part.type == SnakePart.TextureType.body)
            return part.scale * texture_head.getRegionHeight() / texture_body.getRegionHeight();
        return part.scale * texture_head.getRegionHeight() / texture_tail.getRegionHeight();
    }

    private Vector3 move(){
        return new Vector3(direction.x * speed, direction.y * speed, 0);
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
            part.position.z = before.position.z;
            part.rotation = before.rotation;
            part.scale = before.scale;
        }
        position.add(move());
    }

    public void eat() {
        if (parts.size() < 2)
            return;
        SnakePart end = parts.get(1);
        for(int i = 0; i < 10; i++)
            parts.add(parts.indexOf(end), new SnakePart(new Vector3(end.position.x, end.position.y,
                    end.position.z), end.type, end.rotation, 1.5f));
    }

    public boolean checkBitten() {
        int len = parts.size();
        SnakePart head = parts.get(0);
        for(int i = 1; i < len; i+=10) {
            SnakePart part = parts.get(i);
            if (Intersector.overlapConvexPolygons(getBounds(head), getBounds(part)))
                return true;
        }
        return false;
    }
}