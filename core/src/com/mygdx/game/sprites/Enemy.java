package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by raumo0 on 09.05.16.
 */
public class Enemy extends Entity implements Pool.Poolable{
    public TextureRegion texture;

    public Enemy(Vector3 position, Texture texture, float rotation, float scale, boolean dive){
        super(position, rotation, scale, dive);

//        this.position = position;
        if (texture != null)
            this.texture = new TextureRegion(texture);
//        this.rotation = rotation;
//        this.scale = scale;
//        this.dive = dive;
    }

    public Polygon getBounds(){
        Polygon bounds = new Polygon(new float[] {0, 0,texture.getRegionWidth(), 0,
                texture.getRegionWidth(), texture.getRegionHeight(), 0, texture.getRegionHeight()});
        bounds.setOrigin(0, 0);
        bounds.setPosition(position.x, position.y);
        return bounds;
    }

    @Override
    public void reset() {
        position = null;
        rotation = 0;
        scale = 0;
        dive = false;
        texture = null;
    }
}
