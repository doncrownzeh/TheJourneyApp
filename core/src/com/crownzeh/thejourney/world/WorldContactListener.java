package com.crownzeh.thejourney.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.crownzeh.thejourney.TheJourney;
import com.crownzeh.thejourney.other.GameConfig;
import com.crownzeh.thejourney.sprites.Aspect;
import com.crownzeh.thejourney.sprites.collision.CollisionObject;
import com.crownzeh.thejourney.sprites.enemies.Enemy;
import com.crownzeh.thejourney.sprites.enemies.Soldier;

public class WorldContactListener implements ContactListener {
    private Aspect player;

    public WorldContactListener(Aspect player) {
        super();
        this.player = player;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int colisionBits = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;
        switch (colisionBits) {
            case GameConfig.ASPECT_FEET_BIT | GameConfig.ENEMY_HEAD_BIT:
                if (fixtureA.getFilterData().categoryBits == GameConfig.ENEMY_HEAD_BIT) {
                    ((Enemy) fixtureA.getUserData()).die();
                } else {
                    ((Enemy) fixtureB.getUserData()).die();
                }
                break;

            case GameConfig.ASPECT_BIT | GameConfig.ENEMY_BIT:
                player.setHealth((short) 0);
                Gdx.app.log("def", "death");
                break;

            case GameConfig.ENEMY_BIT | GameConfig.INVISIBLE_WALL_BIT:
                if (fixtureA.getFilterData().categoryBits == GameConfig.ENEMY_BIT) {
                    ((Soldier) fixtureA.getUserData()).flipVelocity();
                    Gdx.app.log("flip", "velocity");
                } else {
                    ((Soldier) fixtureB.getUserData()).flipVelocity();
                    Gdx.app.log("flip", "velocity");
                }
                break;

        }

        if (fixtureA.getUserData() == "aspect" || fixtureB.getUserData() == "aspect") {
            Fixture aspect = fixtureA.getUserData() == "aspect" ? fixtureA : fixtureB;
            Fixture object = aspect == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() instanceof CollisionObject) {
                Gdx.app.log("k", "k");
                ((CollisionObject) object.getUserData()).onHit();
            }


        }

        if ((fixtureA.getUserData() == "right_edge" || fixtureB.getUserData() == "right_edge") && (fixtureA.getUserData() == "static" || fixtureB.getUserData() == "static")) {
            Fixture rightEdge = fixtureA.getUserData() == "right_edge" ? fixtureA : fixtureB;
            Fixture wall = rightEdge == fixtureA ? fixtureB : fixtureA;
            Gdx.app.log(rightEdge.getUserData() + " and " + wall.getUserData(), "begin contact");
            player.setRightMovementPossible(false);
        }

        if ((fixtureA.getUserData() == "left_edge" || fixtureB.getUserData() == "left_edge") && (fixtureA.getUserData() == "static" || fixtureB.getUserData() == "static")) {
            Fixture leftEdge = fixtureA.getUserData() == "left_edge" ? fixtureA : fixtureB;
            Fixture wall = leftEdge == fixtureA ? fixtureB : fixtureA;
            Gdx.app.log(leftEdge.getUserData() + " and " + wall.getUserData(), "begin contact");
            player.setLeftMovementPossible(false);
        }

        if ((fixtureA.getUserData() == "head" || fixtureB.getUserData() == "head") && (fixtureA.getUserData() == "static" || fixtureB.getUserData() == "static")) {
            Fixture head = fixtureA.getUserData() == "head" ? fixtureA : fixtureB;
            Fixture wall = head == fixtureA ? fixtureB : fixtureA;
            Gdx.app.log(head.getUserData() + " and " + wall.getUserData(), "begin contact");
            player.setHeadImpact(true);
        }

        if ((fixtureA.getUserData() == "feet" || fixtureB.getUserData() == "feet") && (fixtureA.getUserData() == "static" || fixtureB.getUserData() == "static")) {
            Fixture head = fixtureA.getUserData() == "feet" ? fixtureA : fixtureB;
            Fixture wall = head == fixtureA ? fixtureB : fixtureA;
            player.doubleJump = true;
        }


    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if ((fixtureA.getUserData() == "right_edge" || fixtureB.getUserData() == "right_edge") && (fixtureA.getUserData() == "static" || fixtureB.getUserData() == "static")) {
            Fixture rightEdge = fixtureA.getUserData() == "right_edge" ? fixtureA : fixtureB;
            Fixture wall = rightEdge == fixtureA ? fixtureB : fixtureA;
            Gdx.app.log(rightEdge.getUserData() + " and " + wall.getUserData(), "end contact");
            player.setRightMovementPossible(true);
        }

        if ((fixtureA.getUserData() == "left_edge" || fixtureB.getUserData() == "left_edge") && (fixtureA.getUserData() == "static" || fixtureB.getUserData() == "static")) {
            Fixture leftEdge = fixtureA.getUserData() == "left_edge" ? fixtureA : fixtureB;
            Fixture wall = leftEdge == fixtureA ? fixtureB : fixtureA;
            Gdx.app.log(leftEdge.getUserData() + " and " + wall.getUserData(), "end contact");
            player.setLeftMovementPossible(true);
        }

        if ((fixtureA.getUserData() == "head" || fixtureB.getUserData() == "head") && (fixtureA.getUserData() == "static" || fixtureB.getUserData() == "static")) {
            Fixture head = fixtureA.getUserData() == "head" ? fixtureA : fixtureB;
            Fixture wall = head == fixtureA ? fixtureB : fixtureA;
            Gdx.app.log(head.getUserData() + " and " + wall.getUserData(), "end contact");
            player.setHeadImpact(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // TODO Auto-generated method stub

    }

}
