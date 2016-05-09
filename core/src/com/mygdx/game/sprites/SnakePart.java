package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by raumo0 on 19.4.16.
 */
public class SnakePart {
    public Vector3 position;
    public TextureRegion texture;
    public float rotation = 0f;

    public SnakePart(Vector3 position, TextureRegion texture, float rotation){
        this.position = position;
        this.texture = texture;
        this.rotation = rotation;
    }

    public void dispose(){
    }
}
