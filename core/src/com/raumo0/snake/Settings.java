package com.raumo0.snake;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by raumo0 on 31.05.16.
 */
public class Settings {
    public static int[] highscores = new int[] { 0, 0, 0, 0, 0 };
    private static Preferences scorePref = Gdx.app.getPreferences("Score Preferences");

    public static void load() {
        for (int i = 0; i < highscores.length; i++)
            highscores[i] = scorePref.getInteger("highscore"+String.valueOf(i));
    }

    public static void save() {
        for (int i = 0; i < highscores.length; i++)
            scorePref.putInteger("highscore"+String.valueOf(i), highscores[i]);
        scorePref.flush();
    }

    public static void addScore(int score) {
        for (int i = 0; i < 5; i++) {
            if (highscores[i] < score) {
                for (int j = 4; j > i; j--)
                    highscores[j] = highscores[j - 1];
                highscores[i] = score;
                break;
            }
        }
    }
}