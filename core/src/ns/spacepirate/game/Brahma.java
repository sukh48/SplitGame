package ns.spacepirate.game;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ns.spacepirate.game.components.*;
import ns.spacepirate.game.components.Collision.BoxCollider;
import ns.spacepirate.game.components.Collision.CCollider;
import ns.spacepirate.game.components.Collision.CircleCollider;

/**
 * Created by sukhmac on 2016-02-11.
 */
public class Brahma
{
    private PooledEngine engine;
    private GameWorld world;

    public Brahma(PooledEngine engine)
    {
        this.engine = engine;
    }

    public GameWorld createWorld()
    {
        this.world = new GameWorld(this);
        return world;
    }

    public Entity createTrail(float x, float y)
    {
        TextureRegion coinTex = Assets.inst.getSpriteTexture("Coin7");
        CTexture graphicsComponent = engine.createComponent(CTexture.class);
        CPosition posComponent = engine.createComponent(CPosition.class);

        CTag tagComponent = engine.createComponent(CTag.class);
        tagComponent.tag = "Trail";

        posComponent.x = x;
        posComponent.y = y;

        graphicsComponent.sprite.setRegion(coinTex);
        graphicsComponent.sprite.setSize(coinTex.getRegionWidth() / 2f, coinTex.getRegionHeight() / 2f);


        Entity trail = engine.createEntity();
        trail.add(tagComponent);
        trail.add(posComponent);
        trail.add(graphicsComponent);

        return trail;
    }

    public Entity createPlayer()
    {
        Entity player = engine.createEntity();

        TextureRegion playerTex = Assets.inst.getSpriteTexture("Ball3");
        CTexture graphicsComponent = engine.createComponent(CTexture.class);
        CPosition posComponent = engine.createComponent(CPosition.class);
        CVelocity velComponent = engine.createComponent(CVelocity.class);
        CDivideBall divideComponent = engine.createComponent(CDivideBall.class);
        divideComponent.applyForce=false;

        CTag tagComponent = engine.createComponent(CTag.class);
        tagComponent.tag = "Player";

        float width = playerTex.getRegionWidth()/2f;
        float height = playerTex.getRegionHeight()/2f;

        posComponent.x = SpacePirate.V_WIDTH/2;//-width/2;
        posComponent.y = SpacePirate.V_HEIGHT/2;//-height/2;

        velComponent.vel.set(0,0);//4.5f);
        graphicsComponent.sprite.setRegion(playerTex);
        graphicsComponent.sprite.setSize(width, height);
        graphicsComponent.sprite.setCenter(playerTex.getRegionWidth()/4f, playerTex.getRegionHeight()/4f);
        graphicsComponent.sprite.setOriginCenter();

        CCollider colliderComponent = new CCollider();
        colliderComponent.setCollider(new CircleCollider(20f));

        player.add(posComponent);
        player.add(velComponent);
        player.add(engine.createComponent(CPlayerInput.class));
        player.add(colliderComponent);
        player.add(graphicsComponent);
        player.add(divideComponent);
        player.add(tagComponent);

        return player;
    }

    public Entity createBall(int dir, float x, float y, float w, float h, Entity parent, float r)
    {
        Entity ball = engine.createEntity();

        TextureRegion ballTex = Assets.inst.getSpriteTexture("Ball3");
        CTexture graphicsComponent = engine.createComponent(CTexture.class);
        CPosition posComponent = engine.createComponent(CPosition.class);
        CVelocity velComponent = engine.createComponent(CVelocity.class);
        CDivideBall divideComponent = engine.createComponent(CDivideBall.class);
        divideComponent.movingDir=dir;
        divideComponent.applyForce=true;
        divideComponent.parent=parent;
        divideComponent.attSpeed=0.5f/2f;
        divideComponent.speed=5f/4 ;

//        divideComponent.attSpeed=0.5f/4f;
//        divideComponent.speed=5f/6;

        CTag tagComponent = engine.createComponent(CTag.class);
        tagComponent.tag = "Ball";

        posComponent.x = x;
        posComponent.y = y;

        velComponent.vel.setZero();
        graphicsComponent.sprite.setRegion(ballTex);
        graphicsComponent.sprite.setSize(w, h);
//        graphicsComponent.sprite.setCenter(ballTex.getRegionWidth() / 4f, ballTex.getRegionHeight() / 4f);
//        graphicsComponent.sprite.setOriginCenter();

        CCollider colliderComponent = new CCollider();
        colliderComponent.setCollider(new CircleCollider(r));

        ball.add(tagComponent);
        ball.add(posComponent);
        ball.add(velComponent);
        ball.add(colliderComponent);
        ball.add(graphicsComponent);
        ball.add(divideComponent);

        return ball;
    }

    public Entity createCoin(float x, float y)
    {
        Entity coin = engine.createEntity();

        TextureRegion coinTex = Assets.inst.getSpriteTexture("Coin7");
        CTexture graphicsComponent = engine.createComponent(CTexture.class);
        CPosition posComponent = engine.createComponent(CPosition.class);
        CVelocity velComponent = engine.createComponent(CVelocity.class);


        CTag tagComponent = engine.createComponent(CTag.class);
        tagComponent.tag = "Coin";

        posComponent.x = x;
        posComponent.y = y;

        velComponent.vel.setZero();
        graphicsComponent.sprite.setRegion(coinTex);
        graphicsComponent.sprite.setSize(coinTex.getRegionWidth() / 2f, coinTex.getRegionHeight() / 2f);
        graphicsComponent.sprite.setCenter(coinTex.getRegionWidth() / 2f, coinTex.getRegionHeight() / 2f);
        graphicsComponent.sprite.setOriginCenter();

        CCollider colliderComponent = new CCollider();
        colliderComponent.setCollider(new CircleCollider(20));

        coin.add(tagComponent);
        coin.add(posComponent);
        coin.add(velComponent);
        coin.add(colliderComponent);
        coin.add(graphicsComponent);

        return coin;
    }

    public Entity createObstacle(float x, float y, float width, float height)
    {
        Entity box = engine.createEntity();

        TextureRegion coinTex = Assets.inst.getSpriteTexture("Block5");
        CTexture graphicsComponent = engine.createComponent(CTexture.class);
        CPosition posComponent = engine.createComponent(CPosition.class);
        CVelocity velComponent = engine.createComponent(CVelocity.class);


        CTag tagComponent = engine.createComponent(CTag.class);
        tagComponent.tag = "Block";

        posComponent.x = x;
        posComponent.y = y;

        velComponent.vel.setZero();
        graphicsComponent.sprite.setRegion(coinTex);
        graphicsComponent.sprite.setSize(width,height);
//        graphicsComponent.sprite.setCenter(width/2, height/2);
//        graphicsComponent.sprite.setOriginCenter();

        CCollider colliderComponent = new CCollider();
        colliderComponent.setCollider(new BoxCollider(width-15, height-15));

        box.add(tagComponent);
        box.add(posComponent);
        box.add(velComponent);
        box.add(colliderComponent);
        box.add(graphicsComponent);

        return box;
    }

    public void addToEngine(Entity entity)
    {
        engine.addEntity(entity);
    }

}
