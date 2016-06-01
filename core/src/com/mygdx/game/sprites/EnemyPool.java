package com.mygdx.game.sprites;

import com.badlogic.gdx.utils.Pool;

/**
 * Created by raumo0 on 01.06.16.
 */
public class EnemyPool extends Pool<Enemy> {
    @Override
    protected Enemy newObject() {
        return new Enemy(null, null, 0, 0, false);
    }
}
