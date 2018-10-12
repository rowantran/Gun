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

    static Direction getDirection(Vector2 velocity) {
        if (velocity.y == 0) {
            return pick(velocity.x, LEFT, LEFT, RIGHT);
        } else if (velocity.y < 0) {
            return pick(velocity.x, DOWN_LEFT, DOWN, DOWN_RIGHT);
        } else {
            return pick(velocity.x, UP_LEFT, UP, UP_RIGHT);
        }
    }

    private static Direction pick(float scalar, Direction lessThanZero, Direction zero, Direction greaterThanZero) {
        if (scalar < 0) {
            return lessThanZero;
        } else if (scalar == 0) {
            return zero;
        } else {
            return greaterThanZero;
        }
    }
}
