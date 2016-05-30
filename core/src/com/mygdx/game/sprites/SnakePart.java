package com.mygdx.game.sprites;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by raumo0 on 19.4.16.
 */
public class SnakePart {
    public Vector3 position;
    public float rotation = 0f;
    public final TextureType type;
    public float scale;
    public boolean dive;
    public enum TextureType {
        head, body, tail
    }

    public SnakePart(Vector3 position, TextureType type, float rotation, float scale, boolean dive){
        this.position = position;
        this.rotation = rotation;
        this.type = type;
        this.scale = scale;
        this.dive = dive;
    }

    public void dispose(){
    }
}
