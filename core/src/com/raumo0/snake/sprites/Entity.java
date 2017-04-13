package com.raumo0.snake.sprites;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by raumo0 on 01.06.16.
 */
public abstract class Entity {
    public Vector3 position;
    public float rotation;
    public float scale;
    public boolean dive;

    public Entity(Vector3 position, float rotation, float scale, boolean dive){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.dive = dive;
    }
}
