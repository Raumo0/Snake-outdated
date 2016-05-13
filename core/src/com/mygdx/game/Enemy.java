package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by raumo0 on 09.05.16.
 */
public class Enemy {
    public Vector3 position;
    public TextureRegion texture;

    public Enemy(Vector3 position, Texture texture){
        this.position = position;
        this.texture = new TextureRegion(texture);
    }

    public Polygon getBounds(){
        Polygon bounds = new Polygon(new float[] {0, 0,texture.getRegionWidth(), 0,
                texture.getRegionWidth(), texture.getRegionHeight(), 0, texture.getRegionHeight()});
        bounds.setOrigin(0, 0);
        bounds.setPosition(position.x, position.y);
        return bounds;
    }
}
