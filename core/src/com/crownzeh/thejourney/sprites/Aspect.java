package com.crownzeh.thejourney.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.crownzeh.thejourney.TheJourney;
import com.crownzeh.thejourney.screens.GameScreen;


public class Aspect extends Sprite {

    public State currentState;
    public State previousState;
    public World world;
    public Body body;
    public boolean doubleJump;
    private Animation<Object> aspectRun;
    private boolean alive;
    private boolean finished;
    private boolean castPossible;
    private float spellCooldown;
    private boolean turnedRight;
    private float stateTimer;
    private TextureRegion aspectStand;
    private TextureRegion aspectJump;
    private int health;
    private int actualCoins;
    private int virtualCoins;
    private int score;
    private float scoreTimer;
    private boolean headImpact;
    private boolean leftMovementPossible;
    private boolean rightMovementPossible;
    private boolean destroyed;
    private boolean destroyBody;

    public Aspect(World world, GameScreen screen) {
        //super(screen.getAtlas().findRegion("aspect_run"));
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        rightMovementPossible = true;
        leftMovementPossible = true;
        doubleJump = true;
        turnedRight = true;
        alive = true;
        stateTimer = 0;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(new Texture("characters/aspect_walk.png"), i * 22, 0, 22, 41));
        }
        aspectRun = new Animation<Object>(0.1f, frames);
        frames.clear();
        defineAspect();
        aspectStand = new TextureRegion(new Texture("characters/aspect_stand.png"), 0, 0, 20, 40);
        aspectJump = new TextureRegion(new Texture("characters/aspect_jump.png"), 0, 0, 20, 43);
        setBounds(0, 0, 20 / TheJourney.PIXELS_PER_METER, 40 / TheJourney.PIXELS_PER_METER);
        setRegion(aspectStand);
        health = 3;
        virtualCoins = 0;
        actualCoins = 0;
        score = 1200;
        scoreTimer = 0;
        castPossible = true;
        destroyBody = false;
        destroyed = false;
    }

    public void update(float delta, int coins) {
        if (destroyBody && !destroyed) {
            world.destroyBody(this.body);
            destroyed = true;
        } else if (!destroyed) {
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            setRegion(getFrame(delta));
            handleCooldown(delta);
            updateScore(delta);
            alive = checkForDeath();
            finished = virtualCoins == coins;
            if (!isAlive() || isFinished()) this.death();
        }
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;
        switch (currentState) {
            case RUNNING:
                region = (TextureRegion) aspectRun.getKeyFrame(stateTimer, true);
                setBounds(0, 0, 22 / TheJourney.PIXELS_PER_METER, 40 / TheJourney.PIXELS_PER_METER);
                setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
                break;
            case JUMPING:
            case FALLING:
                region = aspectJump;
                setBounds(0, 0, 20 / TheJourney.PIXELS_PER_METER, 43 / TheJourney.PIXELS_PER_METER);
                setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
                break;
            case STANDING:
            default:
                region = aspectStand;
                setBounds(0, 0, 20 / TheJourney.PIXELS_PER_METER, 40 / TheJourney.PIXELS_PER_METER);
                setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
                break;
        }

        if ((body.getLinearVelocity().x < 0 || !turnedRight) && !region.isFlipX()) {
            region.flip(true, false);
            turnedRight = false;
        } else if ((body.getLinearVelocity().x > 0 || turnedRight) && region.isFlipX()) {
            region.flip(true, false);
            turnedRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else if (body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void defineAspect() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(100 / TheJourney.PIXELS_PER_METER, 200 / TheJourney.PIXELS_PER_METER);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = TheJourney.ASPECT_BIT;
        fixtureDef.filter.maskBits = TheJourney.HEART_BIT |
                TheJourney.COIN_BIT |
                TheJourney.DEFAULT_BIT |
                TheJourney.ENEMY_BIT |
                TheJourney.ENEMY_HEAD_BIT |
                TheJourney.SPIKE_BIT;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10 / TheJourney.PIXELS_PER_METER, 20 / TheJourney.PIXELS_PER_METER);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData("aspect");

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-9 / TheJourney.PIXELS_PER_METER, 20 / TheJourney.PIXELS_PER_METER), new Vector2(9 / TheJourney.PIXELS_PER_METER, 20 / TheJourney.PIXELS_PER_METER));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;
        fixtureDef.filter.maskBits = TheJourney.DEFAULT_BIT;
        body.createFixture(fixtureDef).setUserData("head");

        EdgeShape leftEdge = new EdgeShape();
        leftEdge.set(new Vector2(-10 / TheJourney.PIXELS_PER_METER, -19 / TheJourney.PIXELS_PER_METER), new Vector2(-10 / TheJourney.PIXELS_PER_METER, 19 / TheJourney.PIXELS_PER_METER));
        fixtureDef.shape = leftEdge;
        fixtureDef.isSensor = true;
        fixtureDef.filter.maskBits = TheJourney.DEFAULT_BIT;
        body.createFixture(fixtureDef).setUserData("left_edge");

        EdgeShape rightEdge = new EdgeShape();
        rightEdge.set(new Vector2(10 / TheJourney.PIXELS_PER_METER, -19 / TheJourney.PIXELS_PER_METER), new Vector2(10 / TheJourney.PIXELS_PER_METER, 19 / TheJourney.PIXELS_PER_METER));
        fixtureDef.shape = rightEdge;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("right_edge");

        EdgeShape feet = new EdgeShape();
        feet.set(new Vector2(-9 / TheJourney.PIXELS_PER_METER, -20 / TheJourney.PIXELS_PER_METER), new Vector2(9 / TheJourney.PIXELS_PER_METER, -20 / TheJourney.PIXELS_PER_METER));
        fixtureDef.shape = feet;
        fixtureDef.filter.categoryBits = TheJourney.ASPECT_FEET_BIT;
        fixtureDef.filter.maskBits = TheJourney.ENEMY_HEAD_BIT | TheJourney.DEFAULT_BIT;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData("feet");
    }

    public boolean isNotInTheAir() {
        return (this.currentState != State.FALLING && this.currentState != State.JUMPING);
    }

    public boolean jumpPossible() {
        return isNotInTheAir() && !headImpact;
    }

    private void handleCooldown(float delta) {
        if (spellCooldown > 0) {
            castPossible = false;
            spellCooldown -= delta;
        } else castPossible = true;
    }

    private boolean checkForDeath() {
        return health > 0;
    }

    public void addCoin() {
        virtualCoins++;
    }

    public void updateScore(float delta) {
        scoreTimer += delta;
        if (scoreTimer > 0.5f && score > 0) {
            score -= 10;
            scoreTimer = 0;
        }
    }

    public void death() {
        destroyBody = true;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isCastPossible() {
        return castPossible;
    }

    public boolean isTurnedRight() {
        return turnedRight;
    }

    public void setSpellCooldown(float spellCooldown) {
        this.spellCooldown = spellCooldown;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(short health) {
        this.health = health;
        if (this.health < 0) this.health = 0;
    }

    public int getActualCoins() {
        return actualCoins;
    }

    public void setActualCoins(short actualCoins) {
        this.actualCoins = actualCoins;
    }

    public int getVirtualCoins() {
        return virtualCoins;
    }

    public void setVirtualCoins(short virtualCoins) {
        this.virtualCoins = virtualCoins;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setHeadImpact(boolean headImpact) {
        this.headImpact = headImpact;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int value) {
        this.score = value;
    }

    public boolean isRightMovementPossible() {
        return rightMovementPossible;
    }

    public void setRightMovementPossible(boolean rightMovementPossible) {
        this.rightMovementPossible = rightMovementPossible;
    }

    public boolean isLeftMovementPossible() {
        return leftMovementPossible;
    }

    public void setLeftMovementPossible(boolean leftMovementPossible) {
        this.leftMovementPossible = leftMovementPossible;
    }


}
