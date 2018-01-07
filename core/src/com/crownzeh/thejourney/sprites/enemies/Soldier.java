package com.crownzeh.thejourney.sprites.enemies;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.crownzeh.thejourney.TheJourney;
import com.crownzeh.thejourney.other.GameConfig;
import com.crownzeh.thejourney.screens.GameScreen;


public class Soldier extends Enemy {

    private float frameTimer;
    private Animation<Object> walk;
    private Array<TextureRegion> frames;
    private boolean destroyBody;
    private boolean destroyed;
    private TextureRegion enemyDead;
    private float xVelocity;
    private TextureRegion tempRegion;
    private int type;
    private String walkPath;
    private String deathPath;


    public Soldier(World world, GameScreen screen, float x, float y, int type) {
        super(world, screen, x, y);
        this.type = type;
        xVelocity = 3;
        destroyBody = false;
        destroyed = false;
        switch (type) {
            case 1:
                enemyGraphicPath("characters/green_soldier.png", "characters/green_dead.png");
                xVelocity = 3.0f;
                break;
            case 2:
                enemyGraphicPath("characters/yellow_soldier.png", "characters/desert_dead.png");
                xVelocity = 3.5f;
                break;
            case 3:
                enemyGraphicPath("characters/grey_soldier.png", "characters/grey_dead.png");
                xVelocity = 4.0f;
                break;
            case 4:
                enemyGraphicPath("characters/doc_soldier.png", "characters/doc_dead.png");
                xVelocity = 4.5f;
                break;
            default:
                enemyGraphicPath("characters/green_soldier.png", "characters/green_dead.png");
                xVelocity = 3.0f;
        }
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(new Texture(walkPath), i * 22, 0, 22, 40));
        }
        walk = new Animation<Object>(0.15f, frames);
        enemyDead = new TextureRegion(new Texture(deathPath), 0, 0, 40, 20);
        frameTimer = 0;
        setBounds(x, y, 22 / GameConfig.PIXELS_PER_METER, 40 / GameConfig.PIXELS_PER_METER);
    }

    public void update(float delta) {
        frameTimer += delta;
        if (destroyBody && !destroyed) {
            setBounds(getX(), getY(), 40 / GameConfig.PIXELS_PER_METER, 20 / GameConfig.PIXELS_PER_METER);
            screen.getPlayer().setScore(screen.getPlayer().getScore() + 150);
            setRegion(enemyDead);
            world.destroyBody(this.body);
            destroyed = true;
        } else if (!destroyed) {
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
            tempRegion = (TextureRegion) walk.getKeyFrame(frameTimer, true);
            this.setRegion(tempRegion);
            body.setLinearVelocity(new Vector2(xVelocity, body.getLinearVelocity().y));
        }

    }

    @Override
    protected void defineEnemy() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = GameConfig.ENEMY_BIT;
        fixtureDef.filter.maskBits = GameConfig.ASPECT_BIT |
                GameConfig.DEFAULT_BIT |
                GameConfig.ENEMY_BIT |
                GameConfig.INVISIBLE_WALL_BIT;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10 / GameConfig.PIXELS_PER_METER, 20 / GameConfig.PIXELS_PER_METER);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef).setUserData(this);

        PolygonShape head = new PolygonShape();
        fixtureDef.filter.categoryBits = GameConfig.ENEMY_HEAD_BIT;
        fixtureDef.filter.maskBits = GameConfig.ASPECT_BIT | GameConfig.ASPECT_FEET_BIT;
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-11, 25).scl(1 / GameConfig.PIXELS_PER_METER);
        vertice[1] = new Vector2(11, 25).scl(1 / GameConfig.PIXELS_PER_METER);
        vertice[2] = new Vector2(-9, 19).scl(1 / GameConfig.PIXELS_PER_METER);
        vertice[3] = new Vector2(9, 19).scl(1 / GameConfig.PIXELS_PER_METER);
        head.set(vertice);
        fixtureDef.shape = head;
        fixtureDef.restitution = 0.6f;
        body.createFixture(fixtureDef).setUserData(this);
    }

    @Override
    public void die() {
        destroyBody = true;
    }

    public void flipVelocity() {
        xVelocity = -xVelocity;
        for (TextureRegion frame : frames) {
            frame.flip(true, false);
        }
    }

    private void enemyGraphicPath(String pathWalk, String pathDead) {
        walkPath = pathWalk;
        deathPath = pathDead;
    }
}
