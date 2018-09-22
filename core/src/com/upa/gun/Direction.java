package com.upa.gun;

import com.badlogic.gdx.math.Vector2;

public enum Direction {
    UP,
    UP_RIGHT,
    RIGHT,
    DOWN_RIGHT,
    DOWN,
    DOWN_LEFT,
    LEFT,
    UP_LEFT;

    static Vector2 getAngle(Direction dir) {
        Vector2 angle;
        switch (dir) {
            case UP:
                angle = new Vector2(0, 1);
                break;
            case UP_RIGHT:
                angle = new Vector2(1, 1);
                break;
            case RIGHT:
                angle = new Vector2(1, 0);
                break;
            case DOWN_RIGHT:
                angle = new Vector2(1, -1);
                break;
            case DOWN:
                angle = new Vector2(0, -1);
                break;
            case DOWN_LEFT:
                angle = new Vector2(-1, -1);
                break;
            case LEFT:
                angle = new Vector2(-1, 0);
                break;
            case UP_LEFT:
                angle = new Vector2(-1, 1);
                break;
            default:
                angle = new Vector2(0, 0);
                break;
        }
        return angle;
    }
}
